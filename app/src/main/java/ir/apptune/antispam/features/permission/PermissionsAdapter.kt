package ir.apptune.antispam.features.permission

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ir.apptune.antispam.R
import ir.apptune.antispam.pojos.PermissionsModel
import kotlinx.android.synthetic.main.permission_list_item.view.*

/**
 * Adapter for showing needed permissions in recyclerView
 *
 * @property clickListener
 */
class PermissionsAdapter(val clickListener: (String) -> Unit) : ListAdapter<PermissionsModel, PermissionsAdapter.PermissionsViewHolder>(PermissionsDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PermissionsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.permission_list_item, parent, false)
        return PermissionsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PermissionsViewHolder, position: Int) =
            holder.onBind(getItem(position))

    /**
     * ViewHolder for [PermissionsAdapter]
     *
     * @constructor
     * Receives a [View]
     *
     * @param itemView
     */
    inner class PermissionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(model: PermissionsModel) {
            itemView.txtPermissionName.text = model.permissionName
            itemView.txtPermissionDescription.text = model.description
            itemView.setOnClickListener { clickListener(getItem(adapterPosition).permission) }
        }
    }
}