package com.virgen.peregrina.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.DialogLoadingViewBinding
import com.virgen.peregrina.util.getExceptionLog

class LoadingDialogView(
    context: Context
) : Dialog(context) {

    lateinit var binding: DialogLoadingViewBinding

    companion object {
        private const val TAG = "LoadingDialogView"
    }

    init {
        binding = DialogLoadingViewBinding
            .inflate(LayoutInflater.from(context))
        initView()
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }

    private fun initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        window?.also {
            it.setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            // set animations
            it.attributes?.windowAnimations = R.style.DialogAnimation
            it.setGravity(Gravity.CENTER)
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    fun setMessage(msg: String): LoadingDialogView = try {
        run {
            binding.labelTextView.text = msg
            this
        }
    } catch (ex: Exception) {
        getExceptionLog(TAG, "setMessage", ex)
        this
    }

    override fun show() {
        super.show()
    }

}