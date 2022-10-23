package com.virgen.peregrina.ui.replica_details.testimony

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.ItemTestimonyBinding
import com.virgen.peregrina.data.model.TestimonyModel

class TestimonyItemAdapter(
    private var list: List<TestimonyModel> = emptyList()
) : RecyclerView.Adapter<TestimonyItemAdapter.ViewHolder>() {

    companion object {
        private const val TAG = "TestimonyItemAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_testimony, parent, false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun updateData(data: List<TestimonyModel>) {
        list = data
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemTestimonyBinding.bind(view)

        fun bind(item: TestimonyModel) {
            with(binding) {
                nameTextView.text = item.user_name
                testimonyTextView.text = item.value
            }
        }

    }

}