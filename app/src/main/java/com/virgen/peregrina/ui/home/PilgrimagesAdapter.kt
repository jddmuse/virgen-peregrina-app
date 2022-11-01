package com.virgen.peregrina.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.ItemPilgrimageBinding
import com.virgen.peregrina.data.model.PilgrimageModel
import com.virgen.peregrina.util.METHOD_CALLED
import com.virgen.peregrina.util.OnItemActionListener
import com.virgen.peregrina.util.formatDateForView

class PilgrimagesAdapter(
    private val onItemActionListener: OnItemActionListener<PilgrimageModel>,
    private var list: List<PilgrimageModel> = emptyList()
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
            onItemActionListener.onClick(item)
        }
    }

    override fun getItemCount(): Int =
        list.size

    fun updateData(data: List<PilgrimageModel>) {
        Log.i(TAG, "$METHOD_CALLED updateData() PARAMS: $data")
        list = data
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemPilgrimageBinding.bind(view)

        fun bind(item: PilgrimageModel) {
            with(binding) {
                startDateTextView.text = formatDateForView(itemView.context, item.date_start)
                intentionTextView.text = item.intention
                cityTextView.text = "${item.city}, ${item.country}"
                stateTextView.text = item.state
            }
        }
    }

}