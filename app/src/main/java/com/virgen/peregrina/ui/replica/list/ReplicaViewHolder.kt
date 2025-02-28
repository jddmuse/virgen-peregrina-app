package com.virgen.peregrina.ui.replica.list

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.virgen_peregrina_app.databinding.ItemAvailableReplicaBinding
import com.virgen.peregrina.data.model.replica.ReplicaModel
import com.virgen.peregrina.util.camelCase

class ReplicaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemAvailableReplicaBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun bind(item: ReplicaModel) {
        binding.codeTextView.text = item.code
        binding.cityTextView.text = item.user.city.camelCase()
        binding.ownerTextView.text =
            "${item.user.name.camelCase()} ${item.user.lastName.camelCase()}"
    }
}