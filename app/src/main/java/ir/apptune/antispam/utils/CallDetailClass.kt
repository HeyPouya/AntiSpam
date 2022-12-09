package ir.apptune.antispam.utils

import android.content.Context
import android.net.Uri
import android.provider.CallLog
import android.provider.ContactsContract
import ir.apptune.antispam.R
import ir.apptune.antispam.pojos.CallModel
import ir.apptune.antispam.repository.Repository
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import kotlinx.coroutines.flow.flow

private const val CALL_LOG_SORT = "${CallLog.Calls.DATE} DESC"

/**
 * This class provides details about calls and contacts
 *
 * @property context
 * @property repository
 * @property contactsPermission
 */
class CallDetailClass(
    private val context: Context,
    private val repository: Repository,
    private val contactsPermission: Boolean
) {
    suspend fun getCallDetails() = flow {
        val cursor = context.contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            null, null, null, CALL_LOG_SORT
        )

        if (cursor != null) {
            val callLogNumber = cursor.getColumnIndex(CallLog.Calls.NUMBER)
            val callLogType = cursor.getColumnIndex(CallLog.Calls.TYPE)
            val callLogDate = cursor.getColumnIndex(CallLog.Calls.DATE)

            while (cursor.moveToNext()) {
                val number = cursor.getString(callLogNumber)
                val contactName = if (contactsPermission && !number.isNullOrEmpty()) getContactName(
                    context,
                    number
                ) else number
                val model = CallModel(
                    number,
                    getIranianDate(cursor.getString(callLogDate).toLong()),
                    cursor.getInt(callLogType),
                    contactName,
                    repository.getAddress(number)
                        ?: context.getString(R.string.no_matching_result)
                )
                emit(model)
            }
        }
        cursor?.close()
    }


    private fun getIranianDate(date: Long): String {
        val calendar = GregorianCalendar()
        calendar.time = Date(date)
        val tool = CalendarTool(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        return tool.iranianDate
    }

    private fun getContactName(context: Context, phoneNumber: String): String? {
        val uri = Uri.withAppendedPath(
            ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
            Uri.encode(phoneNumber)
        )
        val cursor = context.contentResolver.query(
            uri,
            arrayOf(ContactsContract.PhoneLookup.DISPLAY_NAME),
            null,
            null,
            null
        )
            ?: return null
        val contactName = if (cursor.moveToFirst()) {
            val columnNumber = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME)
            if (columnNumber > 0) {
                cursor.getString(columnNumber)
            } else {
                null
            }
        } else {
            null
        }
        cursor.close()
        return contactName
    }
}
