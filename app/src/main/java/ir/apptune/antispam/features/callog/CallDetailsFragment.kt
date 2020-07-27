package ir.apptune.antispam.features.callog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import ir.apptune.antispam.R
import ir.apptune.antispam.features.callog.adapter.CallHistoryAdapter
import ir.apptune.antispam.utils.getStatusBarHeight
import kotlinx.android.synthetic.main.fragment_call_history.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CallDetailsFragment : Fragment() {

    private val viewModel: CallDetailsViewModel by viewModel()
    private val adapter by lazy { CallHistoryAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_call_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)

        setStatusBar()
        rc.adapter = adapter

        viewModel.getLiveDataResponse().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    private fun setStatusBar() {
        val layout = FrameLayout(requireContext())
        layout.layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, getStatusBarHeight(requireContext()))
        layout.background = ContextCompat.getDrawable(requireContext(), R.drawable.toolbar_gradiant)
        root.addView(layout, 0)

    }
    //    private void showSearchData() {

    //        txtShowCity = (TextView) getView().findViewById(R.id.txt_show_city);
    //        btnSearch = (Button) getView().findViewById(R.id.btn_search);
    //        edtGetCity = (EditText) getView().findViewById(R.id.edt_get_number);
    //        inputLayout = (TextInputLayout) getView().findViewById(R.id.input_layout);
    //
    //        btnSearch.setOnClickListener(new View.OnClickListener() {
    //            @Override
    //            public void onClick(View view) {
    //                if (edtGetCity.getText().toString().length() < 11) {
    //                    inputLayout.setError("لطفا در ورود شماره دقت کنید");
    //                    return;
    //                }
    //                String number = edtGetCity.getText().toString();
    //                if (number.substring(0, 1).charAt(0) == '*') {
    //                    Log.d("TAG", "onClick: first return");
    //                    return;
    //                }
    //                if (number.substring(0, 1).charAt(0) == '+' || number.substring(0, 1).charAt(0) == '0') {
    //                    if (number.substring(0, 1).charAt(0) == '0') {
    //                        number = "+98" + number.substring(1);
    //                        Log.d("TAG", "onClick: second if");
    //                    }
    //                    if (number.length() == 13) {
    //                        Log.d("TAG", "onClick: lenght is > 13");
    //                        String txt = "";
    //                        number = number.replace("+98", "");
    //                        CharSequence name = null;
    //                        MyFragment.this.database = new ExternalDbOpenHelper(getContext(), MyFragment.this.DB_NAME).openDataBase();
    //                        for (int i = 2; i <= 8; i++) {
    //                            Cursor friendCursor = MyFragment.this.database.rawQuery("SELECT * from phone_location where _id=" + number.substring(0, i), null);
    //                            friendCursor.moveToFirst();
    //                            if (friendCursor.getCount() != 0) {
    //                                name = friendCursor.getString(1);
    //                                txtShowCity.setText(name);
    //                                inputLayout.setErrorEnabled(false);
    //                                Log.d("TAG", "onClick: inside first if");
    //                            }
    //                            friendCursor.close();
    //                        }
    //                        Log.d("TAG", "onClick: name is" + name);
    //                        if (TextUtils.isEmpty(name)) {
    //                            txtShowCity.setText("شماره تلفن پیدا نشد!");
    //                            inputLayout.setErrorEnabled(false);
    //                            Log.d("TAG", "onClick: inside second if");
    //                        }
    //                    }
    //
    //                } else {
    //                    Log.d("TAG", "onClick: lenght is < 13");
    //                    txtShowCity.setText("لطفا دقت کنید که شماره تلفن حتما با 0 و پیش شماره ی درست شروع شده باشد");
    //                }
    //            }
    //        });
    //
    //    }
}