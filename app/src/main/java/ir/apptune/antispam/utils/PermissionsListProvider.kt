package ir.apptune.antispam.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import ir.apptune.antispam.R
import ir.apptune.antispam.pojos.PermissionsModel

/**
 * Checks if we have the given permissions or not
 *
 * @param context
 * @return a list of permissions to grant
 */
fun checkNeededPermissions(context: Context): List<PermissionsModel> {
    val result = arrayListOf<PermissionsModel>()
    getPermissionsList(context).forEach {
        if (ContextCompat.checkSelfPermission(context, it.permission) == PackageManager.PERMISSION_DENIED)
            result.add(it)
    }
    return result
}

private fun getPermissionsList(context: Context): List<PermissionsModel> {
    val list = arrayListOf<PermissionsModel>()
    list.add(PermissionsModel(Manifest.permission.READ_CALL_LOG, context.getString(R.string.call_log), context.getString(R.string.call_log_permission_description)))
    list.add(PermissionsModel(Manifest.permission.READ_CONTACTS, context.getString(R.string.contacts), context.getString(R.string.read_contacts_permission_description)))
    list.add(PermissionsModel(Manifest.permission.READ_PHONE_STATE, context.getString(R.string.call_status), context.getString(R.string.read_phone_state_permission_description)))
    return list
}

