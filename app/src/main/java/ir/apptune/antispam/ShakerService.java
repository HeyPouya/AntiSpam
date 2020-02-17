package ir.apptune.antispam;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Pouya on 20/07/2017.
 */

public class ShakerService extends Service {
    private ThePhoneStateListener callHelper;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {
        this.callHelper.stop();
        super.onDestroy();
    }

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        this.callHelper = new ThePhoneStateListener(this);
        this.callHelper.start();
        return START_STICKY;
    }


}
