package com.virgen.peregrina.ui.replica.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.virgen_peregrina_app.R
import com.virgen.peregrina.data.model.replica.ReplicaModel

class ReplicaItemAdapter(
    val list: MutableList<ReplicaModel> = mutableListOf(),
    val listener: (ReplicaModel) -> Unit
) : RecyclerView.Adapter<ReplicaViewHolder>() {

    companion object {
        private const val TAG = "ReplicaItemAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplicaViewHolder {
        return ReplicaViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_available_replica, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ReplicaViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    /** Public functions ********************/
    /****************************************/

    fun addAll(items: List<ReplicaModel>) {
        val previousSize = list.size
        list.addAll(items)
        notifyItemRangeInserted(previousSize, items.size)
    }


}