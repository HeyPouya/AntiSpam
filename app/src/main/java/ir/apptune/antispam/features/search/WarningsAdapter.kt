package ir.apptune.antispam.features.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.apptune.antispam.R
import kotlinx.android.synthetic.main.search_warning_item.view.*

class WarningsAdapter(private val list: List<String>) : RecyclerView.Adapter<WarningsAdapter.WarningsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WarningsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_warning_item, parent, false)
        return WarningsViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: WarningsViewHolder, position: Int) =
            holder.onBind(list[position])

    class WarningsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(text: String) {
            itemView.txtWarning.text = text
        }
    }
}