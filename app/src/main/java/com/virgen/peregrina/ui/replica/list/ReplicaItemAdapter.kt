package com.virgen.peregrina.ui.replica.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.ItemAvailableReplicaBinding
import com.virgen.peregrina.data.model.replica.ReplicaModel
import com.virgen.peregrina.util.camelCase

class ReplicaItemAdapter(
    val list: List<ReplicaModel>,
    val listener: (ReplicaModel) -> Unit
) : RecyclerView.Adapter<ReplicaItemAdapter.ViewHolder>() {

    companion object {
        private const val TAG = "ReplicaItemAdapter"
    }

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
            listener(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemAvailableReplicaBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(item: ReplicaModel) {
            binding.codeTextView.text = item.code
            binding.cityTextView.text = item.user.city.camelCase()
            binding.ownerTextView.text = "${item.user.name.camelCase()} ${item.user.lastName.camelCase()}"
        }
    }


}