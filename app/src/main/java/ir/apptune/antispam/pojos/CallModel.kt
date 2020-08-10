package ir.apptune.antispam.pojos

/**
 * Data class for showing call logs
 */
data class CallModel(val number: String, val callDate: String, val callStatus: Int, val contactName: String?, val callLocation: String)