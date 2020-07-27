package ir.apptune.antispam;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class ThePhoneStateListener {
    Context context;
    CountDownTimer timer;
    Toast tag;
    private static final String DB_NAME = "locations.db";
    private CallStateListener callStateListener = new CallStateListener();
    private SQLiteDatabase database;
    private OutgoingReceiver outgoingReceiver = new OutgoingReceiver();
    private TelephonyManager tm;


    public ThePhoneStateListener(Context context) {
        this.context = context;
    }

    private class CallStateListener extends PhoneStateListener {
        private CallStateListener() {
        }

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case 1:
                    String number = incomingNumber;
                    if (number.substring(0, 1).charAt(0) == '*') {
                        return;
                    }
                    if (number.substring(0, 1).charAt(0) == '+' || number.substring(0, 1).charAt(0) == '0') {
                        if (number.substring(0, 1).charAt(0) == '0') {
                            number = "+98" + number.substring(1);
                        }
                        if (number.length() == 13) {
                            String txt = "";
                            number = number.replace("+98", "");
                            String name = null;
                            ThePhoneStateListener.this.database = new ExternalDbOpenHelper(ThePhoneStateListener.this.context, ThePhoneStateListener.DB_NAME).openDataBase();
                            for (int i = 2; i <= 8; i++) {
                                Cursor friendCursor = ThePhoneStateListener.this.database.rawQuery("SELECT * from phone_location where _id=" + number.substring(0, i), null);
                                friendCursor.moveToFirst();
                                if (friendCursor.getCount() != 0) {
                                    name = friendCursor.getString(1);
                                }
                                friendCursor.close();
                            }
                            final Toast tag = Toast.makeText(ThePhoneStateListener.this.context, name, Toast.LENGTH_LONG);
                            tag.show();
                            new CountDownTimer(10000, 1000) {
                                public void onTick(long millisUntilFinished) {
                                    tag.show();
                                }

                                public void onFinish() {
                                }
                            }.start();
                            return;
                        }
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public class OutgoingReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String number = intent.getStringExtra("android.intent.extra.PHONE_NUMBER");
            if (number.substring(0, 1).charAt(0) == '*') {
                return;
            }
            if (number.substring(0, 1).charAt(0) == '+' || number.substring(0, 1).charAt(0) == '0') {
                if (number.substring(0, 1).charAt(0) == '0') {
                    number = "+98" + number.substring(1);
                }
                if (number.length() == 13) {
                    String txt = "";
                    number = number.replace("+98", "");
                    CharSequence name = null;
                    ThePhoneStateListener.this.database = new ExternalDbOpenHelper(ThePhoneStateListener.this.context, ThePhoneStateListener.DB_NAME).openDataBase();
                    for (int i = 2; i <= 8; i++) {
                        Cursor friendCursor = ThePhoneStateListener.this.database.rawQuery("SELECT * from phone_location where _id=" + number.substring(0, i), null);
                        friendCursor.moveToFirst();
                        if (friendCursor.getCount() != 0) {
                            name = friendCursor.getString(1);
                        }
                        friendCursor.close();
                    }
                    final Toast tag = Toast.makeText(ThePhoneStateListener.this.context, name, Toast.LENGTH_LONG);
                    tag.show();
                    new CountDownTimer(10000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            tag.show();
                        }

                        public void onFinish() {
                        }
                    }.start();
                }
            }
        }

    }

    public void start() {
        this.tm = (TelephonyManager) this.context.getSystemService(Context.TELEPHONY_SERVICE);
        this.tm.listen(this.callStateListener, 32);
        this.context.registerReceiver(this.outgoingReceiver, new IntentFilter("android.intent.action.NEW_OUTGOING_CALL"));
    }

    public void stop() {
        this.tm.listen(this.callStateListener, 0);
        this.context.unregisterReceiver(this.outgoingReceiver);
    }

}



