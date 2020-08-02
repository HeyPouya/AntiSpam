package ir.apptune.antispam.features.permission

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.apptune.antispam.R
import ir.apptune.antispam.pojos.PermissionsModel
import kotlinx.android.synthetic.main.permission_list_item.view.*

class PermissionsAdapter(private val list: List<PermissionsModel>) : RecyclerView.Adapter<PermissionsAdapter.WarningsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WarningsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.permission_list_item, parent, false)
        return WarningsViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: WarningsViewHolder, position: Int) =
            holder.onBind(list[position])

    class WarningsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(model: PermissionsModel) {
            itemView.txtPermissionName.text = model.permissionName
            itemView.txtPermissionDescription.text = model.description
        }
    }
}