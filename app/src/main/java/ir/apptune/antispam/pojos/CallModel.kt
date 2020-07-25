package ir.apptune.antispam.pojos

/**
 * Created by Pouya on 4/19/2017.
 */
data class CallModel(val number: String, val callDate: String, val callStatus: Int, val contactName: String?, val callLocation: String)