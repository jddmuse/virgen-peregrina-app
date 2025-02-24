package com.virgen.peregrina.ui.login

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.ActivityLoginBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.virgen.peregrina.MainActivity
import com.virgen.peregrina.ui.dialog.LoadingDialogView
import com.virgen.peregrina.ui.login.enumerator.EnumLoginInputType
import com.virgen.peregrina.ui.register.RegisterActivity
import com.virgen.peregrina.util.view.IView
import com.virgen.peregrina.util.view.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), IView {

    companion object {
        private const val TAG = "LoginActivity"
    }

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loadingDialog: LoadingDialogView
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initObservers()
        initListeners()

        binding.rememberDataSwitch.isChecked = true
        viewModel.onCreate { email, password ->
            with(binding) {
                emailEditText.setText(email)
                passwordEditText.setText(password)
            }
        }
    }

    override fun initView() {
        try {
            loadingDialog = LoadingDialogView(this)
            binding.signupTextView.paintFlags = Paint.UNDERLINE_TEXT_FLAG;
        } catch (ex: Exception) {
            Log.e(TAG, "initUI(): Exception -> $ex")
        }
    }

    override fun initObservers() {
        viewModel.loginSuccessEvent.observe(this) {
            Log.i(TAG, "LOGIN SUCCESSFULLY!!!")
            //startActivity(Intent(this, MainActivity::class.java))
        }
        viewModel.errorEditText.observe(this) { pair ->
            when (pair.first) {
                EnumLoginInputType.EMAIL -> binding.emailEditText.error = pair.second
                EnumLoginInputType.PASSWORD -> binding.passwordEditText.error = pair.second
                EnumLoginInputType.REMEMBER -> {}
            }
        }
        viewModel.formValidatedEvent.observe(this) {
            viewModel.login()
        }
        viewModel.error.observe(this) { value: String ->
            MaterialAlertDialogBuilder(this)
                .setMessage(value.ifEmpty { getString(R.string.error_generic) })
                .setPositiveButton(getString(R.string.action_button_yes)) { dialog, which ->  }
                .show()
        }
        viewModel.loading.observe(this) { value ->
            if (value.first)
                loadingDialog.setMessage(value.second).show()
            else
                loadingDialog.dismiss()
        }
    }

    override fun initListeners() {
        with(binding) {
            emailEditText.addTextChangedListener {
                viewModel.onValueChanged(it, EnumLoginInputType.EMAIL)
            }
            passwordEditText.addTextChangedListener {
                viewModel.onValueChanged(it, EnumLoginInputType.PASSWORD)
            }
            loginButton.setSafeOnClickListener {
                viewModel.validateForm()
            }
            rememberDataSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                viewModel.onValueChanged(null, EnumLoginInputType.REMEMBER)
            }
            signupTextView.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
        }
    }

    override fun onStop() {
        super.onStop()
        with(binding) {
            loginButton.isEnabled = true
            progressBar.visibility = View.GONE
        }
    }

}