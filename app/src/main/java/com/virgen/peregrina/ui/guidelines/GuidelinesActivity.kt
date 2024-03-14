package com.virgen.peregrina.ui.guidelines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.ActivityGuidelinesBinding
import com.virgen.peregrina.util.getExceptionLog

class GuidelinesActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "GuidelinesActivity"
    }

    private lateinit var binding: ActivityGuidelinesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuidelinesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        initListeners()
    }

    private fun initListeners() {
        binding.appBarLayout.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun initUI() {
        try {
            binding.appBarLayout.textView.text = getString(R.string.label_pilgrimage_guidelines)
        } catch (ex:Exception) {
            getExceptionLog(TAG, "", ex)
        }
    }
}