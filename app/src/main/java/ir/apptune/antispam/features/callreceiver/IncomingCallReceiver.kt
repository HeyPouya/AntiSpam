package ir.apptune.antispam.features.callreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import ir.apptune.antispam.repository.Repository
import org.koin.java.KoinJavaComponent

class IncomingCallReceiver : BroadcastReceiver() {

    private val repository: Repository by KoinJavaComponent.inject(Repository::class.java)

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            if (it.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
                val receiver = IncomingCallPhoneState(repository, context!!)
                val telephony = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                telephony.listen(receiver, PhoneStateListener.LISTEN_CALL_STATE)
            }
        }

    }
}