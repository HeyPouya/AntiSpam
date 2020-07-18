package ir.apptune.antispam.adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import ir.apptune.antispam.CalendarTool;
import ir.apptune.antispam.Checkcontact;
import ir.apptune.antispam.ExternalDbOpenHelper;
import ir.apptune.antispam.R;
import ir.apptune.antispam.models.CallModel;


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

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
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
        TextView phone;
        ImageView call;


        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            city = (TextView) itemView.findViewById(R.id.city);
            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);
            phone = (TextView) itemView.findViewById(R.id.phone);
            call = (ImageView) itemView.findViewById(R.id.call);
        }

        void bind(CallModel model) {
            String number;
            phone.setText(model.getPhNumber());
            number = model.getPhNumber();
            if (number.substring(0, 1).charAt(0) == '*') {
                Log.d("TAG", "onClick: first return");
                return;
            }
            if (number.substring(0, 1).charAt(0) == '+' || number.substring(0, 1).charAt(0) == '0') {
                if (number.substring(0, 1).charAt(0) == '0') {
                    number = "+98" + number.substring(1);
                    Log.d("TAG", "onClick: second if");
                }
                if (number.length() == 13) {
                    Log.d("TAG", "onClick: lenght is > 13");
                    String txt = "";
                    number = number.replace("+98", "");
                    CharSequence name = null;
                    CallHistoryAdapter.this.database = new ExternalDbOpenHelper(mContext, CallHistoryAdapter.this.DB_NAME).openDataBase();
                    for (int i = 2; i <= 8; i++) {
                        Cursor friendCursor = CallHistoryAdapter.this.database.rawQuery("SELECT * from phone_location where _id=" + number.substring(0, i), null);
                        friendCursor.moveToFirst();
                        if (friendCursor.getCount() != 0) {
                            name = friendCursor.getString(1);
                            city.setText(name);
                            Log.d("TAG", "onClick: inside first if");
                        }
                        friendCursor.close();
                    }
                    Log.d("TAG", "onClick: name is" + name);
                    if (TextUtils.isEmpty(name)) {
                        city.setText("شماره تلفن پیدا نشد!");
                        Log.d("TAG", "onClick: inside second if");
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
                    call.setImageResource(R.drawable.phone_call);
                } else if (dir.equals("MISSED")) {
                    call.setImageResource(R.drawable.phone_call_missed);
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
