package ir.apptune.antispam.utils

import android.content.Context

const val STATUS_BAR_HEIGHT = "status_bar_height"
const val DIMEN = "dimen"
const val ANDROID = "android"

/**
 * Determines the height of status bar
 *
 * @param context
 * @return height of status bar
 */
fun getStatusBarHeight(context: Context): Int {
    val resourceId = context.resources.getIdentifier(STATUS_BAR_HEIGHT, DIMEN, ANDROID)
    return if (resourceId > 0) context.resources.getDimensionPixelSize(resourceId) else 0
}
