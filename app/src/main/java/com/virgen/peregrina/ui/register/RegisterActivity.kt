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
import com.virgen.peregrina.ui.date_picker_dialog.DatePickerFragment
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

    private lateinit var dialog: ReplicaDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    override fun initUI() {
        try {
            dialog = ReplicaDialog(this)
            initListeners()
            initObservers()
            viewModel.onCreate()
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
            viewModel.onCloseDatePickerDialog.observe(this) {
                dialog.binding.also {
                    it.codeEditText.text?.clear()
                    it.dateEditText.text?.clear()
                }
                dialog.dismiss()
            }
            viewModel.codeReplicaErrorMsg.observe(this) { msg ->
                with(dialog.binding.codeEditText){
                    error = msg
                    requestFocus()
                }
            }
            viewModel.receivedDateErrorMsg.observe(this) { msg ->
                with(dialog.binding.dateEditText){
                    error = msg
                    requestFocus()
                }
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
                addReplicaButton.setOnClickListener {
                    dialog.show()
                }
                dialog.binding.also {
                    it.codeEditText.addTextChangedListener { text ->
                        viewModel.onValueChanged(text, ReplicaDialogInputType.CODE)
                    }
                    it.dateEditText.addTextChangedListener { text ->
                        viewModel.onValueChanged(text, ReplicaDialogInputType.DATE)
                    }
                    it.dateEditText.setOnClickListener { view ->
                        val picker = DatePickerFragment { year, month, day ->
                            it.dateEditText.setText("$day/${month + 1}/$year")
                        }
                        picker.show(supportFragmentManager, TAG)
                    }
                    it.positiveRadioButton.setOnCheckedChangeListener { compoundButton, isChecked ->
                        if (isChecked)
                            viewModel.onValueChanged(true, ReplicaDialogInputType.REPAIR_REQUIRED)
                    }
                    it.actionButton.setOnClickListener {
                        viewModel.onReplicaSaved()
                    }
                }
                countryCodePicker.setOnCountryChangeListener(this@RegisterActivity)

            }
        } catch (ex: Exception) {
            Log.e(TAG, "initListeners(): Exception -> $ex")
        }
    }

    override fun onCountrySelected() {
        with(binding) {
            countryEditText.setText(countryCodePicker.selectedCountryName)
            viewModel.onValueChanged(
                countryCodePicker.selectedCountryName,
                RegisterInputType.COUNTRY
            )
            viewModel.onValueChanged(
                countryCodePicker.selectedCountryCode,
                RegisterInputType.COUNTRY_CODE
            )
        }
    }


}