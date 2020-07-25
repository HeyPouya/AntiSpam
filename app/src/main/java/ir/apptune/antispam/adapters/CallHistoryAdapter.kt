package ir.apptune.antispam.adapters

import android.provider.CallLog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.apptune.antispam.R
import ir.apptune.antispam.adapters.CallHistoryAdapter.MyViewHolder
import ir.apptune.antispam.pojos.CallModel
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
            with(itemView) {
                with(model) {
                    txtDate.text = callDate
                    txtName.text = contactName ?: number
                    txtCity.text = callLocation
                    when (model.callStatus) {
                        CallLog.Calls.OUTGOING_TYPE -> imgCallStatus.setImageResource(R.drawable.ic_outgoing_call)
                        CallLog.Calls.INCOMING_TYPE, CallLog.Calls.REJECTED_TYPE -> imgCallStatus.setImageResource(R.drawable.ic_incomming_call)
                        CallLog.Calls.MISSED_TYPE -> imgCallStatus.setImageResource(R.drawable.ic_missed_call)
                        else -> imgCallStatus.setImageResource(R.drawable.ic_unknown_call)
                    }

                }
            }


        }
    }
}