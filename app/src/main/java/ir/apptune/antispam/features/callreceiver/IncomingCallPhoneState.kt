package ir.apptune.antispam.features.callreceiver

import android.content.Context
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager.CALL_STATE_OFFHOOK
import android.telephony.TelephonyManager.CALL_STATE_RINGING
import android.widget.Toast
import ir.apptune.antispam.repository.Repository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class IncomingCallPhoneState(private val repository: Repository, private val context: Context) : PhoneStateListener() {

    override fun onCallStateChanged(state: Int, phoneNumber: String?) {
        when (state) {
            CALL_STATE_RINGING, CALL_STATE_OFFHOOK -> phoneNumber?.let { showInfoByToast(it) }
        }
    }

    private fun showInfoByToast(phoneNumber: String) {
        MainScope().launch {
            if (phoneNumber.isNotEmpty()) {
                val address = repository.getAddress(phoneNumber)
                Toast.makeText(context, address, Toast.LENGTH_SHORT).show()
            }
        }
    }
}