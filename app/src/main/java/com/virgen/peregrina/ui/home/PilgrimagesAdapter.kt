package com.virgen.peregrina.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.ItemPilgrimageBinding
import com.virgen.peregrina.data.model.PilgrimageModel
import com.virgen.peregrina.util.DateUtils
import com.virgen.peregrina.util.enumerator.EnumDateFormat

class PilgrimagesAdapter(
    private var list: List<PilgrimageModel> = emptyList(),
    private val listener: (PilgrimageModel) -> Unit
) : RecyclerView.Adapter<PilgrimagesAdapter.ViewHolder>() {

    companion object {
        private const val TAG = "PilgrimagesAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_pilgrimage, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener(item)
        }
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemPilgrimageBinding.bind(view)

        fun bind(item: PilgrimageModel) {
            // COMPLETE
//            binding.cityTextView.text = item.city
            binding.startDateTextView.text = DateUtils.format(item.startDate, EnumDateFormat.WEEKDAY_DD_MMM)
            binding.intentionTextView.text = item.intention
//            binding.statusTextView.text = item.status

        }
    }

}