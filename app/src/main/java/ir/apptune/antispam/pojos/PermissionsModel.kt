package ir.apptune.antispam.pojos

/**
 * A model to show permissions in a list
 *
 * @property permission
 * @property permissionName
 * @property description
 */
data class PermissionsModel(
    val permission: String,
    val permissionName: String,
    val description: String
)