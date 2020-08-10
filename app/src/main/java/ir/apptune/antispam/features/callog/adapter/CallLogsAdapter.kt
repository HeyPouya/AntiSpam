package ir.apptune.antispam.features.callog.adapter

import android.provider.CallLog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ir.apptune.antispam.R
import ir.apptune.antispam.features.callog.adapter.CallLogsAdapter.CallLogsViewHolder
import ir.apptune.antispam.pojos.CallModel
import kotlinx.android.synthetic.main.call_history_item.view.*

/**
 * An adapter to show call logs in recycler view
 *
 */
class CallLogsAdapter : ListAdapter<CallModel, CallLogsViewHolder>(CallLogsDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallLogsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.call_history_item, parent, false)
        return CallLogsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CallLogsViewHolder, position: Int) = holder.bind(getItem(position))

    /**
     * Viewholder class for [CallLogsAdapter]
     *
     * @constructor
     * receives a [View]
     *
     * @param itemView
     */
    inner class CallLogsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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