package com.virgen.peregrina.ui.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.ItemPilgrimageBinding
import com.virgen.peregrina.data.model.PilgrimageModel
import com.virgen.peregrina.util.METHOD_CALLED
import com.virgen.peregrina.util.view.IActionListener
import com.virgen.peregrina.util.enumerator.EnumPilgrimageStatus
import com.virgen.peregrina.util.formatDateForView
import com.virgen.peregrina.util.getExceptionLog

class PilgrimagesAdapter(
    private val iActionListener: IActionListener<PilgrimageModel>,
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
            iActionListener.onClick(item)
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

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(item: PilgrimageModel) {
            try {
//                with(binding) {
//                    startDateTextView.text = formatDateForView(itemView.context, item.date_start ?: "")
//                    intentionTextView.text = item.intention
//                    cityTextView.text = "${item.city}, ${item.country}"
//
//                    var colorBackground: Int = -1
//                    var textStatus = ""
//                    when (item.state) {
//                        EnumPilgrimageStatus.PENDING.value -> {
//                            colorBackground = itemView.context.getColor(R.color.pilgrimage_status_pending)
//                            textStatus = itemView.context.getString(R.string.pilgrimage_status_pending)
//                        }
//                        EnumPilgrimageStatus.FINISHED.value -> {
//                            colorBackground = itemView.context.getColor(R.color.pilgrimage_status_finished)
//                            textStatus =itemView.context.getString(R.string.pilgrimage_status_finished)
//                        }
//                        EnumPilgrimageStatus.IN_PROGRESS.value -> {
//                            colorBackground = itemView.context.getColor(R.color.pilgrimage_status_in_progress)
//                            textStatus = itemView.context.getString(R.string.pilgrimage_status_in_progress)
//                        }
//                    }
//                    stateTextView.apply {
//                        text = textStatus
//                    background = itemView.context.getDrawable(R.drawable.shape_state_pilgrimage)?.apply {
//                        colorFilter = PorterDuffColorFilter(colorBackground, PorterDuff.Mode.SRC_IN)
//                    }
//                    }
//                }
            } catch (ex:Exception) {
                getExceptionLog(TAG, "bind", ex)
            }
        }
    }

}