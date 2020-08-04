package ir.apptune.antispam.features.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.telephony.TelephonyManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MIN
import androidx.core.content.ContextCompat
import ir.apptune.antispam.MainActivity
import ir.apptune.antispam.R
import ir.apptune.antispam.features.callreceiver.IncomingCallReceiver

const val NOTIFICATION_ID = 100
const val CHANNEL_ID = "NOTIFICATION_FOR_SERVICE"

class ForegroundService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NOTIFICATION_ID, getNotification())
        startBroadcast()
        return START_STICKY
    }

    private fun startBroadcast() {
        val filter = IntentFilter()
        filter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
        registerReceiver(IncomingCallReceiver(), filter)
    }

    private fun getNotification(): Notification {
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, getString(R.string.channel_name),
                    NotificationManager.IMPORTANCE_MIN)
            channel.description = getString(R.string.channel_description)
            channel.importance = NotificationManager.IMPORTANCE_MIN
            mNotificationManager.createNotificationChannel(channel)
        }
        val mBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                .setContentText(getString(R.string.notification_description))
                .setSmallIcon(R.drawable.ic_call)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setPriority(PRIORITY_MIN)

        val intent = Intent(applicationContext, MainActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        mBuilder.setContentIntent(pi)
        return mBuilder.build()
    }
}