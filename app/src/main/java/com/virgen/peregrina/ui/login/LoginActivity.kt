package com.virgen.peregrina.ui.login

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.example.virgen_peregrina_app.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.virgen.peregrina.MainActivity
import com.virgen.peregrina.ui.loading_dialog.LoadingDialogView
import com.virgen.peregrina.ui.register.RegisterActivity
import com.virgen.peregrina.util.UIBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), UIBehavior {

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
        initUI()
        initListeners()
        initObservers()
    }

    override fun initUI() {
        try {
            viewModel.onCreate { email, password ->
                with(binding) {
                    emailEditText.setText(email)
                    passwordEditText.setText(password)
                }
            }
            binding.signupTextView.paintFlags = Paint.UNDERLINE_TEXT_FLAG;
        } catch (ex: Exception) {
            Log.e(TAG, "initUI(): Exception -> $ex")
        }
    }

    override fun initObservers() {
        try {
            viewModel.emailErrorMsg.observe(this) { msg ->
                Log.i(TAG, "CHANGED OBSERVED: emailErrorMsg = $msg")
                with(binding.emailEditText) {
                    error = msg
                    requestFocus()
                }
            }
            viewModel.passwordErrorMsg.observe(this) { msg ->
                Log.i(TAG, "CHANGED OBSERVED: passwordErrorMsg = $msg")
                with(binding.passwordEditText) {
                    error = msg
                    requestFocus()
                }
            }
            viewModel.errorMsg.observe(this) { msg: String? ->
                Log.i(TAG, "CHANGED OBSERVED: errorMsg = $msg")
                Snackbar.make(binding.loginButton, msg!!, Snackbar.LENGTH_SHORT).show()
            }
            viewModel.startRegisterActivity.observe(this) {
                startActivity(
                    Intent(this, RegisterActivity::class.java)
                )
            }
            viewModel.enableButton.observe(this) { response ->
                Log.i(TAG, "CHANGED OBSERVED: enableButton = $response")
                with(binding) {
                    loginButton.isEnabled = response
                    progressBar.visibility = if (response) View.GONE else View.VISIBLE
                }
            }
            viewModel.loginWithFirebase.observe(this) { value ->
                if (value) viewModel.loginWithVirgenPeregrina()
            }
            viewModel.loginWithVirgenPeregrina.observe(this) { value ->
                if (value) {
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    with(binding) {
                        loginButton.isEnabled = true
                        progressBar.visibility = View.GONE
                    }
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "initObservers(): Exception -> $ex")
        }
    }

    override fun initListeners() {
        try {
            with(binding) {
                emailEditText.addTextChangedListener {
                    viewModel.onValueChanged(it, LoginInputType.EMAIL)
                }
                passwordEditText.addTextChangedListener {
                    viewModel.onValueChanged(it, LoginInputType.PASSWORD)
                }
                loginButton.setOnClickListener {
                    viewModel.onLoginWithFirebase()
                }
                rememberDataSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                    viewModel.onValueChanged(null, LoginInputType.REMEMBER)
                }
                signupTextView.setOnClickListener {
                    viewModel.onSignUpWithFirebase()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "initListeners(): Exception -> $ex")
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