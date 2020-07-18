package ir.apptune.antispam.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import ir.apptune.antispam.CallDetailClass;
import ir.apptune.antispam.ExternalDbOpenHelper;
import ir.apptune.antispam.R;
import ir.apptune.antispam.adapters.CallHistoryAdapter;
import ir.apptune.antispam.models.CallModel;


public class MyFragment extends Fragment {

    private TextView txtShowCity;
    Button btnSearch;
    EditText edtGetCity;
    TextInputLayout inputLayout;
    RecyclerView rc;
    SQLiteDatabase database;
    private static final String DB_NAME = "my.db";

    public static MyFragment getInstance(int position) {
        MyFragment myFragment = new MyFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        myFragment.setArguments(args);
        return myFragment;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = null;
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.getInt("position") == 0) {
                layout = inflater.inflate(R.layout.fragment_call_history, container, false);
                rc = (RecyclerView) layout.findViewById(R.id.rc);
                ArrayList<CallModel> list = CallDetailClass.getCallDetails(getContext());
                CallHistoryAdapter adapter = new CallHistoryAdapter(list, getContext());

                rc.setAdapter(adapter);
                rc.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            }
            if (bundle.getInt("position") == 1) {
                layout = inflater.inflate(R.layout.fragment_my, container, false);
                txtShowCity = (TextView) layout.findViewById(R.id.txt_show_city);
                btnSearch = (Button) layout.findViewById(R.id.btn_search);
                edtGetCity = (EditText) layout.findViewById(R.id.edt_get_number);
                inputLayout = (TextInputLayout) layout.findViewById(R.id.input_layout);

                btnSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (edtGetCity.getText().toString().length() < 11) {
                            inputLayout.setError("لطفا در ورود شماره دقت کنید");
                            return;
                        }
                        String number = edtGetCity.getText().toString();
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
                                MyFragment.this.database = new ExternalDbOpenHelper(getContext(), MyFragment.this.DB_NAME).openDataBase();
                                for (int i = 2; i <= 8; i++) {
                                    Cursor friendCursor = MyFragment.this.database.rawQuery("SELECT * from phone_location where _id=" + number.substring(0, i), null);
                                    friendCursor.moveToFirst();
                                    if (friendCursor.getCount() != 0) {
                                        name = friendCursor.getString(1);
                                        txtShowCity.setText(name);
                                        inputLayout.setErrorEnabled(false);
                                        Log.d("TAG", "onClick: inside first if");
                                    }
                                    friendCursor.close();
                                }
                                Log.d("TAG", "onClick: name is" + name);
                                if (TextUtils.isEmpty(name)) {
                                    txtShowCity.setText("شماره تلفن پیدا نشد!");
                                    inputLayout.setErrorEnabled(false);
                                    Log.d("TAG", "onClick: inside second if");
                                }
                            }

                        } else {
                            Log.d("TAG", "onClick: lenght is < 13");
                            txtShowCity.setText("لطفا دقت کنید که شماره تلفن حتما با 0 و پیش شماره ی درست شروع شده باشد");
                        }
                    }
                });
            }

        }
        return layout;
    }
}


