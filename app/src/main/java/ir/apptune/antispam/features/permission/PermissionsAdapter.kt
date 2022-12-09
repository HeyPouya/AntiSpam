package ir.apptune.antispam.features.permission

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ir.apptune.antispam.databinding.PermissionListItemBinding
import ir.apptune.antispam.pojos.PermissionsModel

/**
 * Adapter for showing needed permissions in recyclerView
 *
 * @property clickListener
 */
class PermissionsAdapter(val clickListener: (String) -> Unit) :
    ListAdapter<PermissionsModel, PermissionsAdapter.PermissionsViewHolder>(PermissionsDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PermissionsViewHolder {
        val binding =
            PermissionListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PermissionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PermissionsViewHolder, position: Int) =
        holder.onBind(getItem(position))

    /**
     * ViewHolder for [PermissionsAdapter]
     *
     * @constructor
     * Receives a [View]
     *
     * @param binding
     */
    inner class PermissionsViewHolder(private val binding: PermissionListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: PermissionsModel) {
            binding.txtPermissionName.text = model.permissionName
            binding.txtPermissionDescription.text = model.description
            binding.root.setOnClickListener { clickListener(getItem(adapterPosition).permission) }
        }
    }
}