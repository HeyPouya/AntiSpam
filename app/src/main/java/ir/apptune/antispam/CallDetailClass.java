package ir.apptune.antispam;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;

import java.util.ArrayList;
import java.util.Date;

import ir.apptune.antispam.models.CallModel;

public class CallDetailClass {
    public static ArrayList<CallModel> getCallDetails(Context context) {
        ArrayList<CallModel> list = new ArrayList<>();
        CallModel model;
        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null, null, null, CallLog.Calls.DATE + " DESC");
        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
        while (cursor.moveToNext()) {
            model = new CallModel();
            model.setPhNumber(cursor.getString(number));
            String callType = cursor.getString(type);
            String callDate = cursor.getString(date);
            model.setCallDayTime(new Date(Long.valueOf(callDate)));
            model.setCallDuration(cursor.getString(duration));
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    model.setDir("OUTGOING");
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    model.setDir("INCOMING");
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    model.setDir("MISSED");
                    break;
            }
            list.add(model);
        }
        cursor.close();
        return list;
    }
}