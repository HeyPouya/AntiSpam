package ir.apptune.antispam.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import ir.apptune.antispam.R
import ir.apptune.antispam.pojos.PermissionsModel

private fun getPermissionsList(context: Context): List<PermissionsModel> {
    val list = arrayListOf<PermissionsModel>()
    list.add(PermissionsModel(Manifest.permission.READ_CALL_LOG, context.getString(R.string.call_log), context.getString(R.string.call_log_permission_description)))
    list.add(PermissionsModel(Manifest.permission.READ_CONTACTS, context.getString(R.string.contacts), context.getString(R.string.read_contacts_permission_description)))
    list.add(PermissionsModel(Manifest.permission.READ_PHONE_STATE, context.getString(R.string.call_log), context.getString(R.string.call_log_permission_description)))
    return list
}

 fun checkNeededPermissions(context: Context): List<PermissionsModel> {
    val result = arrayListOf<PermissionsModel>()
    getPermissionsList(context).forEach {
        if (ContextCompat.checkSelfPermission(context, it.permission) == PackageManager.PERMISSION_DENIED)
            result.add(it)
    }
    return result
}
