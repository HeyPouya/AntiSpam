package ir.apptune.antispam.features.callog.adapter

import androidx.recyclerview.widget.DiffUtil
import ir.apptune.antispam.pojos.CallModel

class CallHistoryDiffUtils : DiffUtil.ItemCallback<CallModel>() {
    override fun areItemsTheSame(oldItem: CallModel, newItem: CallModel) =
            oldItem.number == newItem.number && oldItem.callDate == newItem.callDate && oldItem.callStatus == newItem.callStatus

    override fun areContentsTheSame(oldItem: CallModel, newItem: CallModel) =
            oldItem.number == newItem.number && oldItem.callDate == newItem.callDate && oldItem.callStatus == newItem.callStatus
}