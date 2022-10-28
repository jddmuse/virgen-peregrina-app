package com.virgen.peregrina.ui.home.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.DialogSendTestimonyDialogBinding
import com.virgen.peregrina.util.EMPTY_STRING

class SendTestimonyDialog(context: Context, private val listener: (String) -> Unit) :
    Dialog(context) {

    companion object {
        private const val TAG = "SendTestimonyDialog"
    }

    lateinit var binding: DialogSendTestimonyDialogBinding

    init {
        binding = DialogSendTestimonyDialogBinding
            .inflate(LayoutInflater.from(context))
        initView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            sendButton.setOnClickListener {
                val text = testimonyEditText.text
                if (text.isNullOrEmpty()) {
                    sendButton.let {
                        it.error = context.getString(R.string.error_field_required)
                        it.requestFocus()
                    }
                } else {
                    listener(text.toString())
                    dismiss()
                }
            }
        }
    }

    private fun initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        window?.also {
            it.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            // set animations
            it.attributes?.windowAnimations = R.style.DialogAnimation
            it.setGravity(Gravity.CENTER)
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        }
    }

}