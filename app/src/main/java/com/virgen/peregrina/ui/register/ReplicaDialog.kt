package com.virgen.peregrina.ui.register

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.DialogCardViewBinding
import com.virgen.peregrina.ui.date_picker_dialog.DatePickerFragment

class ReplicaDialog(context: Context) : Dialog(context) {

    companion object {
        private const val TAG = "ReplicaDialog"
    }

    lateinit var binding: DialogCardViewBinding

    init {
        binding = DialogCardViewBinding
            .inflate(LayoutInflater.from(context))
        initView()
        initListener()
    }

    private fun initListener() {
        try {

        } catch (ex: Exception) {

        }
    }

    private fun initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        window?.also {
            it.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            // set animations
            it.attributes?.windowAnimations = R.style.DialogAnimation
            it.setGravity(Gravity.BOTTOM)
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }


}