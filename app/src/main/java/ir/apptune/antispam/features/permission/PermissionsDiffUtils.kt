package ir.apptune.antispam.features.permission

import androidx.recyclerview.widget.DiffUtil
import ir.apptune.antispam.pojos.PermissionsModel

/**
 * DiffUtils for showing needed permissions in a recycler view
 *
 */
class PermissionsDiffUtils : DiffUtil.ItemCallback<PermissionsModel>() {
    override fun areItemsTheSame(oldItem: PermissionsModel, newItem: PermissionsModel) =
            oldItem.permissionName == newItem.permissionName

    override fun areContentsTheSame(oldItem: PermissionsModel, newItem: PermissionsModel) =
            oldItem.permission == newItem.permission && oldItem.description == newItem.description
}