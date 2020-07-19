package ir.apptune.antispam.adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import ir.apptune.antispam.CalendarTool;
import ir.apptune.antispam.Checkcontact;
import ir.apptune.antispam.ExternalDbOpenHelper;
import ir.apptune.antispam.R;
import ir.apptune.antispam.pojos.CallModel;


public class CallHistoryAdapter extends RecyclerView.Adapter<CallHistoryAdapter.MyViewHolder> {
    ArrayList<CallModel> list;
    Context mContext;
    LayoutInflater inflater;
    SQLiteDatabase database;
    private static final String DB_NAME = "my.db";

    public CallHistoryAdapter(ArrayList<CallModel> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.call_history_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView city;
        TextView date;
        TextView time;
        ImageView call;


        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.txtName);
            city = (TextView) itemView.findViewById(R.id.txtCity);
            date = (TextView) itemView.findViewById(R.id.txtDate);
            time = (TextView) itemView.findViewById(R.id.time);
            call = (ImageView) itemView.findViewById(R.id.imgCallStatus);
        }

        void bind(CallModel model) {
            String number;
            name.setText(model.getPhNumber());
            number = model.getPhNumber();
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
                    CallHistoryAdapter.this.database = new ExternalDbOpenHelper(mContext, CallHistoryAdapter.DB_NAME).openDataBase();
                    for (int i = 2; i <= 8; i++) {
                        Cursor friendCursor = CallHistoryAdapter.this.database.rawQuery("SELECT * from phone_location where _id=" + number.substring(0, i), null);
                        friendCursor.moveToFirst();
                        if (friendCursor.getCount() != 0) {
                            name = friendCursor.getString(1);
                            city.setText(name);
                        }
                        friendCursor.close();
                    }
                    if (TextUtils.isEmpty(name)) {
                        city.setText("شماره تلفن پیدا نشد!");
                    }
                }

            }

            Calendar calendar = new GregorianCalendar();
            calendar.setTime(model.getCallDayTime());
            CalendarTool tool = new CalendarTool(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            date.setText(tool.getIranianDate());
            String dir = model.getDir();
            if (dir != null) {
                if (dir.equals("OUTGOING")) {
                    call.setImageResource(R.drawable.phone_call_out_going);

                } else if (dir.equals("INCOMING")) {
                    call.setImageResource(R.drawable.ic_incomming_call);
                } else if (dir.equals("MISSED")) {
                    call.setImageResource(R.drawable.ic_missed_call);
                }
            }
            String contactName = null;
            try {
                contactName = Checkcontact.getContactName(mContext, model.getPhNumber());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (contactName != null && !contactName.equals("")) {
                name.setText(contactName);
            } else {
                name.setText("شماره ناشناس");

            }
        }
    }
}
