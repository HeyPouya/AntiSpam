package ir.apptune.antispam.features.callog.adapter

import android.provider.CallLog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ir.apptune.antispam.R
import ir.apptune.antispam.databinding.CallHistoryItemBinding
import ir.apptune.antispam.features.callog.adapter.CallLogsAdapter.CallLogsViewHolder
import ir.apptune.antispam.pojos.CallModel

/**
 * An adapter to show call logs in recycler view
 *
 */
class CallLogsAdapter : ListAdapter<CallModel, CallLogsViewHolder>(CallLogsDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallLogsViewHolder {
        val binding =
            CallHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CallLogsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CallLogsViewHolder, position: Int) =
        holder.bind(getItem(position))

    /**
     * Viewholder class for [CallLogsAdapter]
     *
     * @constructor
     * receives a [View]
     *
     * @param binding
     */
    inner class CallLogsViewHolder(private val binding: CallHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: CallModel) {
            with(binding) {
                with(model) {
                    txtDate.text = callDate
                    txtName.text = contactName ?: number
                    txtCity.text = callLocation
                    when (model.callStatus) {
                        CallLog.Calls.OUTGOING_TYPE -> imgCallStatus.setImageResource(R.drawable.ic_outgoing_call)
                        CallLog.Calls.INCOMING_TYPE, CallLog.Calls.REJECTED_TYPE -> imgCallStatus.setImageResource(
                            R.drawable.ic_incomming_call
                        )
                        CallLog.Calls.MISSED_TYPE -> imgCallStatus.setImageResource(R.drawable.ic_missed_call)
                        else -> imgCallStatus.setImageResource(R.drawable.ic_unknown_call)
                    }
                }
            }
        }
    }
}
