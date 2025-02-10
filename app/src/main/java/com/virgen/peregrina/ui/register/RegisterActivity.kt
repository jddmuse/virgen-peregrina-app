package com.virgen.peregrina.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.ActivityRegisterBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hbb20.CountryCodePicker
import com.virgen.peregrina.MainActivity
import com.virgen.peregrina.ui.dialog.LoadingDialogView
import com.virgen.peregrina.ui.register.enumerator.EnumRegisterInputType
import com.virgen.peregrina.util.view.IView
import com.virgen.peregrina.util.view.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity(), IView,
    CountryCodePicker.OnCountryChangeListener {

    companion object {
        private const val TAG = "RegisterActivity"
    }

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var loadingDialog: LoadingDialogView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initListeners()
        initObservers()
    }

    override fun initView() {
        loadingDialog = LoadingDialogView(this)
    }

    override fun initObservers() {
        try {
            viewModel.errorEditText.observe(this) { pair ->
                when(pair.first) {
                    EnumRegisterInputType.NAME -> binding.nameEditText.error = pair.second
                    EnumRegisterInputType.LAST_NAME -> binding.lastNameEditText.error = pair.second
                    EnumRegisterInputType.ADDRESS -> binding.addressEditText.error = pair.second
                    EnumRegisterInputType.COUNTRY -> binding.countryEditText.error = pair.second
                    EnumRegisterInputType.CITY -> binding.cityEditText.error = pair.second
//                    EnumRegisterInputType.COUNTRY_CODE -> binding.countryCodePicker.error = pair.second
                    EnumRegisterInputType.CELLPHONE -> binding.cellphoneEditText.error = pair.second
                    EnumRegisterInputType.EMAIL -> binding.emailEditText.error = pair.second
                    EnumRegisterInputType.PASSWORD -> binding.passwordEditText.error = pair.second
                }
            }
            viewModel.formValidatedEvent.observe(this) { value ->
                if(value) {
                    viewModel.register()
                }
            }
            viewModel.registerFinishedEvent.observe(this) {
                startActivity(Intent(this, MainActivity::class.java))
            }
            viewModel.loading.observe(this) { value ->
                if(value.first)
                    loadingDialog.setMessage(value.second).show()
                else
                    loadingDialog.dismiss()
            }
            viewModel.error.observe(this) { value: String ->
                MaterialAlertDialogBuilder(this)
                    .setMessage(value.ifEmpty { getString(R.string.error_generic) })
                    .setPositiveButton(getString(R.string.action_button_yes)) { dialog, which ->  }
                    .show()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "initObservers(): Exception -> $ex")
        }
    }

    override fun initListeners() {
        try {
            with(binding) {
                emailEditText.addTextChangedListener {
                    viewModel.onValueChanged(it, EnumRegisterInputType.EMAIL)
                }
                passwordEditText.addTextChangedListener {
                    viewModel.onValueChanged(it, EnumRegisterInputType.PASSWORD)
                }
                nameEditText.addTextChangedListener {
                    viewModel.onValueChanged(it, EnumRegisterInputType.NAME)
                }
                lastNameEditText.addTextChangedListener {
                    viewModel.onValueChanged(it, EnumRegisterInputType.LAST_NAME)
                }
                addressEditText.addTextChangedListener {
                    viewModel.onValueChanged(it, EnumRegisterInputType.ADDRESS)
                }
                countryEditText.setOnClickListener {
                    with(binding) {
                        countryEditText.setText(countryCodePicker.selectedCountryName)
                        viewModel.onValueChanged(
                            countryCodePicker.selectedCountryName,
                            EnumRegisterInputType.COUNTRY
                        )
                        viewModel.onValueChanged(
                            countryCodePicker.selectedCountryCode,
                            EnumRegisterInputType.COUNTRY_CODE
                        )
                    }
                }
                cityEditText.addTextChangedListener {
                    viewModel.onValueChanged(it, EnumRegisterInputType.CITY)
                }
                cellphoneEditText.addTextChangedListener {
                    viewModel.onValueChanged(it, EnumRegisterInputType.CELLPHONE)
                }
                actionButton.setSafeOnClickListener {
                    viewModel.validateForm()
                }
                countryCodePicker.setOnCountryChangeListener(this@RegisterActivity)

            }
        } catch (ex: Exception) {
            Log.e(TAG, "initListeners(): Exception -> $ex")
        }
    }

    override fun onCountrySelected() {
        //
    }
}