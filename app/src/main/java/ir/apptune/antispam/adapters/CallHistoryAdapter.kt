package ir.apptune.antispam.adapters

import android.provider.CallLog
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.apptune.antispam.ExternalDbOpenHelper
import ir.apptune.antispam.R
import ir.apptune.antispam.adapters.CallHistoryAdapter.MyViewHolder
import ir.apptune.antispam.pojos.CallModel
import ir.apptune.antispam.utils.DATABASE_NAME
import kotlinx.android.synthetic.main.call_history_item.view.*
import java.util.*

class CallHistoryAdapter(private val list: ArrayList<CallModel>) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.call_history_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) = holder.bind(list[position])

    override fun getItemCount() = list.size

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: CallModel) {
            var number = model.number
            if (number.substring(0, 1)[0] == '*') {
                return
            }
            if (number.substring(0, 1)[0] == '0')
                number = "+98" + number.substring(1)

            number = number.replace("+98", "")
            var name: CharSequence? = null
            val database = ExternalDbOpenHelper(itemView.context, DATABASE_NAME).openDataBase()
            for (i in 2..8) {
                val friendCursor = database.rawQuery("SELECT * from phone_location where _id=" + number.substring(0, i), null)
                friendCursor.moveToFirst()
                if (friendCursor.count != 0) {
                    name = friendCursor.getString(1)
                    itemView.txtCity.text = name
                }
                friendCursor.close()
            }
            if (TextUtils.isEmpty(name)) {
                itemView.txtCity.text = "شماره تلفن پیدا نشد!"
            }

            itemView.txtDate.text = model.callDate
            itemView.txtName.text = model.contactName ?: model.number

            when (model.callStatus) {
                CallLog.Calls.OUTGOING_TYPE -> itemView.imgCallStatus.setImageResource(R.drawable.ic_outgoing_call)
                CallLog.Calls.INCOMING_TYPE, CallLog.Calls.REJECTED_TYPE -> itemView.imgCallStatus.setImageResource(R.drawable.ic_incomming_call)
                CallLog.Calls.MISSED_TYPE -> itemView.imgCallStatus.setImageResource(R.drawable.ic_missed_call)
                else -> itemView.imgCallStatus.setImageResource(R.drawable.ic_unknown_call)
            }

        }
    }
}