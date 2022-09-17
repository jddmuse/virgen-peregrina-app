package com.virgen.peregrina.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.virgen_peregrina_app.databinding.ActivityRegisterBinding
import com.hbb20.CountryCodePicker
import com.virgen.peregrina.MainActivity
import com.virgen.peregrina.util.UIBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity(), UIBehavior,
    CountryCodePicker.OnCountryChangeListener {

    companion object {
        private const val TAG = "RegisterActivity"
    }

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    override fun initUI() {
        try {
            viewModel.onCreate()
            initListeners()
            initObservers()
        } catch (ex: Exception) {
            Log.e(TAG, "initUI(): Exception -> $ex")
        }
    }

    override fun initObservers() {
        try {
            viewModel.nameErrorMsg.observe(this) { msg ->
                with(binding.nameEditText) {
                    error = msg
                    requestFocus()
                }
            }
            viewModel.lastNameErrorMsg.observe(this) { msg ->
                with(binding.lastNameEditText) {
                    error = msg
                    requestFocus()
                }
            }
            viewModel.addressErrorMsg.observe(this) { msg ->
                with(binding.addressEditText) {
                    error = msg
                    requestFocus()
                }
            }
            viewModel.countryErrorMsg.observe(this) { msg ->
                with(binding.countryEditText) {
                    error = msg
                    requestFocus()
                }
            }
            viewModel.cityErrorMsg.observe(this) { msg ->
                with(binding.cityEditText) {
                    error = msg
                    requestFocus()
                }
            }
            viewModel.cellphoneErrorMsg.observe(this) { msg ->
                with(binding.cellphoneEditText) {
                    error = msg
                    requestFocus()
                }
            }
            viewModel.startMainActivity.observe(this) {
                startActivity(
                    Intent(this, MainActivity::class.java)
                )
            }

        } catch (ex: Exception) {
            Log.e(TAG, "initObservers(): Exception -> $ex")
        }
    }

    override fun initListeners() {
        try {
            with(binding) {
                nameEditText.addTextChangedListener {
                    viewModel.onValueChanged(it, RegisterInputType.NAME)
                }
                lastNameEditText.addTextChangedListener {
                    viewModel.onValueChanged(it, RegisterInputType.LAST_NAME)
                }
                addressEditText.addTextChangedListener {
                    viewModel.onValueChanged(it, RegisterInputType.ADDRESS)
                }
                countryEditText.setOnClickListener {
                    onCountrySelected()
                }
                cityEditText.addTextChangedListener {
                    viewModel.onValueChanged(it, RegisterInputType.CITY)
                }
                cellphoneEditText.addTextChangedListener {
                    viewModel.onValueChanged(it, RegisterInputType.CELLPHONE)
                }
                actionButton.setOnClickListener {
                    viewModel.onActionButton()
                }
                countryCodePicker.setOnCountryChangeListener(this@RegisterActivity)
            }
        } catch (ex: Exception) {
            Log.e(TAG, "initListeners(): Exception -> $ex")
        }
    }

    override fun onCountrySelected() {
        with(binding) {
            // Country name
            countryEditText.setText(countryCodePicker.selectedCountryName)
            viewModel.onValueChanged(
                countryCodePicker.selectedCountryName,
                RegisterInputType.COUNTRY
            )
            //

            viewModel.onValueChanged(
                countryCodePicker.selectedCountryCode,
                RegisterInputType.COUNTRY_CODE
            )
        }
    }
}