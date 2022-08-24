package ir.apptune.antispam.features.callreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.PhoneStateListener
import android.telephony.TelephonyCallback
import android.telephony.TelephonyManager
import android.widget.Toast
import ir.apptune.antispam.repository.Repository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

/**
 * A broadcast receiver to receive calls whenever the phone rings
 *
 */
class IncomingCallReceiver : BroadcastReceiver() {

    private val repository: Repository by KoinJavaComponent.inject(Repository::class.java)

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let { nonNullIntent ->
            if (nonNullIntent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
                val telephonyManager = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    telephonyManager.registerTelephonyCallback(
                        context.mainExecutor,
                        object : TelephonyCallback(), TelephonyCallback.CallStateListener {
                            override fun onCallStateChanged(state: Int) {
                                when (state) {
                                    TelephonyManager.CALL_STATE_RINGING, TelephonyManager.CALL_STATE_OFFHOOK -> {
                                        val phoneNumber = intent.extras?.getString("incoming_number")
                                        phoneNumber?.let { showInfoByToast(it, context) }
                                    }
                                    TelephonyManager.CALL_STATE_IDLE -> {}
                                }
                            }
                        })
                } else {
                    val receiver = IncomingCallPhoneState(repository, context)
                    telephonyManager.listen(receiver, PhoneStateListener.LISTEN_CALL_STATE)
                }
            }
        }
    }

    private fun showInfoByToast(phoneNumber: String, context: Context) {
        MainScope().launch {
            if (phoneNumber.isNotEmpty()) {
                val address = repository.getAddress(phoneNumber)
                Toast.makeText(context, address, Toast.LENGTH_SHORT).show()
            }
        }
    }
}