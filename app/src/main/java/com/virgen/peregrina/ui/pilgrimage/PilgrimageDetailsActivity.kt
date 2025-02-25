package com.virgen.peregrina.ui.pilgrimage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.ActivityPilgrimageDetailsBinding
import com.google.gson.Gson
import com.virgen.peregrina.data.model.PilgrimageModel
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
        initUI()
        initListeners()
    }

    private fun initListeners() {
        binding.appBarLayout.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun initUI() {
        try {
            binding.appBarLayout.textView.text = getString(R.string.label_pilgrimage_details)
            val jsonObject = intent.getStringExtra("pilgrimage")
            val pilgrimageModel = Gson().fromJson(jsonObject, PilgrimageModel::class.java)
        } catch (ex: Exception) {
            getExceptionLog(TAG, "initUI", ex)
        }
    }

}