package ir.apptune.antispam

import android.content.Context
import android.net.Uri
import android.provider.CallLog
import android.provider.ContactsContract
import ir.apptune.antispam.pojos.CallModel
import ir.apptune.antispam.utils.CALL_LOG_SORT
import ir.apptune.antispam.utils.CalendarTool
import java.util.*

fun getCallDetails(context: Context): ArrayList<CallModel> {
    val list = ArrayList<CallModel>()
    val cursor = context.contentResolver.query(CallLog.Calls.CONTENT_URI,
            null, null, null, CALL_LOG_SORT)

    if (cursor != null) {
        val number = cursor.getColumnIndex(CallLog.Calls.NUMBER)
        val type = cursor.getColumnIndex(CallLog.Calls.TYPE)
        val date = cursor.getColumnIndex(CallLog.Calls.DATE)

        while (cursor.moveToNext()) {
            val number = cursor.getString(number)
            val model = CallModel(number, getIranianDate(cursor.getString(date).toLong()), cursor.getInt(type), getContactName(context, number))
            list.add(model)
        }
    }
    cursor?.close()
    return list
}

private fun getIranianDate(date: Long): String {
    val calendar = GregorianCalendar()
    calendar.time = Date(date)
    val tool = CalendarTool(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
    return tool.iranianDate
}

private fun getContactName(context: Context, phoneNumber: String?): String? {
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

