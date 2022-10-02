package com.virgen.peregrina.ui.peregrinacion

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.ItemAvailableReplicaBinding
import com.virgen.peregrina.data.model.ReplicaModel
import com.virgen.peregrina.util.METHOD_CALLED

class ReplicaItemAdapter : RecyclerView.Adapter<ReplicaItemAdapter.ViewHolder>() {

    companion object {
        private const val TAG = "ReplicaItemAdapter"
    }

    private val list = mutableListOf<ReplicaModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_available_replica, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
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

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemAvailableReplicaBinding.bind(view)

        fun bind(item: ReplicaModel) {
            binding.codeTextView.text = item.code
        }
    }


}