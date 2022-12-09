package ir.apptune.antispam.features.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.apptune.antispam.databinding.SearchWarningItemBinding

/**
 * An adapter that shows a list of warnings to the user, so the user can write the number correctly
 *
 * @property list list of warnings
 */
class WarningsAdapter(private val list: List<String>) :
    RecyclerView.Adapter<WarningsAdapter.WarningsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WarningsViewHolder {
        val binding =
            SearchWarningItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WarningsViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: WarningsViewHolder, position: Int) =
        holder.onBind(list[position])

    class WarningsViewHolder(private val binding: SearchWarningItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(text: String) {
            binding.txtWarning.text = text
        }
    }
}