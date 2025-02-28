package com.virgen.peregrina.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.virgen_peregrina_app.databinding.ItemPilgrimageBinding
import com.virgen.peregrina.data.model.pilgrimage.PilgrimageModel
import com.virgen.peregrina.util.DateUtils
import com.virgen.peregrina.util.camelCase
import com.virgen.peregrina.util.enumerator.EnumDateFormat

class PilgrimageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemPilgrimageBinding.bind(view)

    fun bind(item: PilgrimageModel) {
        // COMPLETE
        binding.cityTextView.text = item.user.city.camelCase()
        binding.startDateTextView.text = DateUtils.format(item.startDate, EnumDateFormat.WEEKDAY_DD_MMM).camelCase()
        binding.intentionTextView.text = item.intention.camelCase()
//            binding.statusTextView.text = item.status

    }
}