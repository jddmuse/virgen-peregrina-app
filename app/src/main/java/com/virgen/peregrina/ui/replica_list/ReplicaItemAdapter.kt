package com.virgen.peregrina.ui.replica_list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.ItemAvailableReplicaBinding
import com.virgen.peregrina.data.model.ReplicaModel
import com.virgen.peregrina.util.METHOD_CALLED
import com.virgen.peregrina.util.OnItemActionListener

class ReplicaItemAdapter : RecyclerView.Adapter<ReplicaItemAdapter.ViewHolder>() {

    companion object {
        private const val TAG = "ReplicaItemAdapter"
    }

    private val list = mutableListOf<ReplicaModel>()
    private val observers: MutableList<OnItemActionListener<ReplicaModel>> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_available_replica, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            observers.forEach { it.onClick(item) }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateData(data: List<ReplicaModel>) {
        Log.i(TAG, "$METHOD_CALLED updateData() PARAMS: $data")
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    fun addObserver(data: OnItemActionListener<ReplicaModel>) {
        observers.add(data)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemAvailableReplicaBinding.bind(view)

        fun bind(item: ReplicaModel) {
            with(binding) {
                codeTextView.text = item.code
                cityTextView.text = item.user_country + ", " + item.user_city
                ownerTextView.text = item.user_name
                stateTextView.text = if (item.isAvailable) "Available" else "Busy"
            }
        }
    }


}