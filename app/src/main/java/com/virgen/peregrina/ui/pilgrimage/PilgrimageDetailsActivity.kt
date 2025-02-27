package com.virgen.peregrina.ui.pilgrimage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.ActivityPilgrimageDetailsBinding
import com.google.gson.Gson
import com.virgen.peregrina.data.model.pilgrimage.PilgrimageParcelableModel
import com.virgen.peregrina.util.DateUtils
import com.virgen.peregrina.util.camelCase
import com.virgen.peregrina.util.enumerator.EnumDateFormat
import com.virgen.peregrina.util.getExceptionLog

class PilgrimageDetailsActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "PilgrimageDetailsActivity"
    }

    private lateinit var binding: ActivityPilgrimageDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPilgrimageDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initListeners()
    }

    private fun initListeners() {
        binding.appBarLayout.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun initView() {
        try {
            binding.appBarLayout.textView.text = getString(R.string.label_pilgrimage_details)
            val jsonObject = intent.getStringExtra("pilgrimage")
            val pilgrimage = Gson().fromJson(jsonObject, PilgrimageParcelableModel::class.java)

            binding.cityTextView.text = "CUCUTA".camelCase() //pilgrimage.city
            binding.intentionTextView.text = pilgrimage.intention?.camelCase()
            binding.replicaCodeTextView.text = "Código de réplica: ${pilgrimage?.replica?.code}"
            pilgrimage.startDate?.let { binding.dateTextView.text = DateUtils.format(it, EnumDateFormat.WEEKDAY_DD_MMM).camelCase() }
        } catch (ex: Exception) {
            getExceptionLog(TAG, "initView", ex)
        }
    }

}