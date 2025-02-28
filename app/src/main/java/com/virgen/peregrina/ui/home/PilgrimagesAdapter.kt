package com.virgen.peregrina.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.ItemPilgrimageBinding
import com.virgen.peregrina.data.model.pilgrimage.PilgrimageModel
import com.virgen.peregrina.util.DateUtils
import com.virgen.peregrina.util.camelCase
import com.virgen.peregrina.util.enumerator.EnumDateFormat

class PilgrimagesAdapter(
    private var list: MutableList<PilgrimageModel> = mutableListOf(),
    private val listener: (PilgrimageModel) -> Unit
) : RecyclerView.Adapter<PilgrimageViewHolder>() {

    companion object {
        private const val TAG = "PilgrimagesAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PilgrimageViewHolder {
        return PilgrimageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_pilgrimage, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PilgrimageViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener(item)
        }
    }

    override fun getItemCount(): Int = list.size



    /** Public functions ********************/
    /****************************************/

    fun addAll(items: List<PilgrimageModel>) {
        val previousSize = list.size
        list.addAll(items)
        notifyItemRangeInserted(previousSize, items.size)
    }
}