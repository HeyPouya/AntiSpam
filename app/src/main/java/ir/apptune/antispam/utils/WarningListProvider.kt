package ir.apptune.antispam.utils

import android.content.Context
import ir.apptune.antispam.R

fun getWarningList(context: Context): List<String> {
    val list = arrayListOf<String>()
    list.add(context.getString(R.string.cant_track_rightel_and_irancell))
    list.add(context.getString(R.string.dont_forget_to_enter_zip_code))
    return list
}
