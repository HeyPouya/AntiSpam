package ir.apptune.antispam

import android.content.Context
import android.net.Uri
import android.provider.CallLog
import android.provider.ContactsContract
import ir.apptune.antispam.pojos.CallModel
import ir.apptune.antispam.utils.CALL_LOG_SORT
import ir.apptune.antispam.utils.CalendarTool
import ir.apptune.antispam.utils.DATABASE_NAME
import java.util.*

fun getCallDetails(context: Context): ArrayList<CallModel> {
    val list = ArrayList<CallModel>()
    val cursor = context.contentResolver.query(CallLog.Calls.CONTENT_URI,
            null, null, null, CALL_LOG_SORT)

    if (cursor != null) {
        val callLogNumber = cursor.getColumnIndex(CallLog.Calls.NUMBER)
        val callLogType = cursor.getColumnIndex(CallLog.Calls.TYPE)
        val callLogDate = cursor.getColumnIndex(CallLog.Calls.DATE)

        while (cursor.moveToNext()) {
            val number = cursor.getString(callLogNumber)
            val model = CallModel(number, getIranianDate(cursor.getString(callLogDate).toLong()), cursor.getInt(callLogType), getContactName(context, number), getCallLocation(number, context))
            list.add(model)
        }
    }
    cursor?.close()
    return list
}

private fun getCallLocation(number: String, context: Context): String {
    var address = "شماره تلفن پیدا نشد!"
    var userNumber = number
    if (userNumber.substring(0, 1)[0] == '*')
        address = "کد دستوری"

    if (userNumber.substring(0, 1)[0] == '0')
        userNumber = "+98" + userNumber.substring(1)

    userNumber = userNumber.replace("+98", "")

    val database = ExternalDbOpenHelper(context, DATABASE_NAME).openDataBase()
    for (i in 2..8) {
        val friendCursor = database.rawQuery("SELECT * from phone_location where _id=" + userNumber.substring(0, i), null)
        friendCursor.moveToFirst()
        if (friendCursor.count != 0) {
            address = friendCursor.getString(1)
        }
        friendCursor.close()
    }
    return address

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

