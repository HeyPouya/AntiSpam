package ir.apptune.antispam.utils

import android.content.Context
import android.net.Uri
import android.provider.CallLog
import android.provider.ContactsContract
import ir.apptune.antispam.R
import ir.apptune.antispam.pojos.CallModel
import ir.apptune.antispam.repository.Repository
import kotlinx.coroutines.flow.flow
import java.util.*

suspend fun getCallDetails(context: Context, repository: Repository) = flow {
    val cursor = context.contentResolver.query(CallLog.Calls.CONTENT_URI,
            null, null, null, CALL_LOG_SORT)

    if (cursor != null) {
        val callLogNumber = cursor.getColumnIndex(CallLog.Calls.NUMBER)
        val callLogType = cursor.getColumnIndex(CallLog.Calls.TYPE)
        val callLogDate = cursor.getColumnIndex(CallLog.Calls.DATE)

        while (cursor.moveToNext()) {
            val number = cursor.getString(callLogNumber)
            val model = CallModel(number, getIranianDate(cursor.getString(callLogDate).toLong()), cursor.getInt(callLogType), getContactName(context, number), getCallLocation(number, context, repository))
            emit(model)
        }
    }
    cursor?.close()
}


private suspend fun getCallLocation(number: String, context: Context, repository: Repository): String {
    var userNumber = number
    if (userNumber.substring(0, 1)[0] == '*')
        return context.getString(R.string.instructional_code)

    if (userNumber.substring(0, 1)[0] == '0')
        userNumber = "+98" + userNumber.substring(1)

    userNumber = userNumber.replace("+98", "")
    return repository.getAddress(userNumber) ?: context.getString(R.string.no_matching_result)
}

private suspend fun getIranianDate(date: Long): String {
    val calendar = GregorianCalendar()
    calendar.time = Date(date)
    val tool = CalendarTool(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
    return tool.iranianDate
}

private suspend fun getContactName(context: Context, phoneNumber: String?): String? {
    val cr = context.contentResolver
    val uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber))
    val cursor = cr.query(uri, arrayOf(ContactsContract.PhoneLookup.DISPLAY_NAME), null, null, null)
            ?: return null
    val contactName = if (cursor.moveToFirst())
        cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME))
    else
        null

    cursor.close()
    return contactName
}

