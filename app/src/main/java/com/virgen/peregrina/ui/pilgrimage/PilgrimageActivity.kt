package com.virgen.peregrina.ui.pilgrimage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.ActivityPilgrimageBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hbb20.CountryCodePicker
import com.virgen.peregrina.ui.dialog.DatePickerFragment
import com.virgen.peregrina.ui.dialog.LoadingDialogView
import com.virgen.peregrina.ui.register.enumerator.EnumRegisterInputType
import com.virgen.peregrina.util.view.IView
import com.virgen.peregrina.util.formatDate
import com.virgen.peregrina.util.view.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PilgrimageActivity : AppCompatActivity(), IView,
    CountryCodePicker.OnCountryChangeListener {

    companion object {
        private const val TAG = "PilgrimageActivity"
    }

    private lateinit var binding: ActivityPilgrimageBinding
    private lateinit var loadingDialog: LoadingDialogView
    private val viewModel: PilgrimageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPilgrimageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    override fun initView() {
        try {
            loadingDialog = LoadingDialogView(this)
            val replicaId = intent.getLongExtra("replica_id", -1)
            initListeners()
            initObservers()
//            viewModel.onCreate(
//                replicaId
//            )
        } catch (ex: Exception) {
            Log.e(TAG, "initUI(): Exception -> $ex")
        }
    }

    override fun initObservers() {
        try {
            viewModel.pilgrims.observe(this) { list ->
                Log.i(TAG, "pilgrims CHANGE OBSERVED = $list")
                val adapter = PilgrimsAdapter(
                    this,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                    list.map { "${it.name} ${it.lastName}"}
                )
                with(binding.attendantPilgrimEditText) {
                    setAdapter(adapter)
                    threshold = 1
                    setOnItemClickListener { adapterView, view, i, l ->
                        val userId = list[i].id
                        viewModel.onValueChanged(
                            value = userId,
                            inputType = PilgrimageInputType.USER_ID
                        )
                    }
                }
            }
            viewModel.error.observe(this) {
                MaterialAlertDialogBuilder(this)
                    .setMessage(it.ifEmpty { getString(R.string.error_generic) })
                    .setPositiveButton(getString(R.string.action_button_yes)) { dialog, which -> finish() }
                    .show()
            }
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
            viewModel.emailErrorMsg.observe(this) { msg ->
                with(binding.emailEditText) {
                    error = msg
                    requestFocus()
                }
            }
            viewModel.setUserIdErrorMsg.observe(this) { msg ->
                with(binding.attendantPilgrimEditText) {
                    error = msg
                    requestFocus()
                }
            }
            viewModel.setIntentionErrorMsg.observe(this) { msg ->
                with(binding.intentionEditText) {
                    error = msg
                    requestFocus()
                }
            }
            viewModel.setStartDateErrorMsg.observe(this) { msg ->
                with(binding.startDateEditText) {
                    error = msg
                    requestFocus()
                }
            }
            viewModel.setEndDateErrorMsg.observe(this) { msg ->
                with(binding.endDateEditText) {
                    error = msg
                    requestFocus()
                }
            }
            viewModel.onFinishActivity.observe(this) {
                MaterialAlertDialogBuilder(this)
                    .setMessage(getString(R.string.label_pilgrimage_success))
                    .setPositiveButton(getString(R.string.action_button_yes)) { dialog, which -> finish() }
                    .show()
            }
            viewModel.loading.observe(this) { value ->
                if(value.first)
                    loadingDialog.setMessage(value.second).show()
                else
                    loadingDialog.dismiss()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "initObservers(): Exception -> $ex")
        }
    }

    override fun initListeners() {
        try {
            with(binding) {
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
                    onCountrySelected()
                }
                cityEditText.addTextChangedListener {
                    viewModel.onValueChanged(it, EnumRegisterInputType.CITY)
                }
                cellphoneEditText.addTextChangedListener {
                    viewModel.onValueChanged(it, EnumRegisterInputType.CELLPHONE)
                }
                startDateEditText.addTextChangedListener {
                    viewModel.onValueChanged(it, PilgrimageInputType.START_DATE)
                }
                endDateEditText.addTextChangedListener {
                    viewModel.onValueChanged(it, PilgrimageInputType.END_DATE)
                }
                emailEditText.addTextChangedListener {
                    viewModel.onValueChanged(it, EnumRegisterInputType.EMAIL)
                }
                intentionEditText.addTextChangedListener {
                    viewModel.onValueChanged(it, PilgrimageInputType.INTENTION)
                }

                startDateEditText.setOnClickListener {
                    val picker = DatePickerFragment { year, month, day ->
                        startDateEditText.setText(
                            formatDate(day, month, year)
                        )
                    }
                    picker.show(supportFragmentManager, TAG)
                }
                endDateEditText.setOnClickListener {
                    val picker = DatePickerFragment { year, month, day ->
                        endDateEditText.setText(
                            formatDate(day, month, year)
                        )
                    }
                    picker.show(supportFragmentManager, TAG)
                }
                actionButton.setSafeOnClickListener {
//                    viewModel.onStartPilgrimage()
                }
                appBarLayout.toolbar.setNavigationOnClickListener { finish() }
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
                EnumRegisterInputType.COUNTRY
            )
            viewModel.onValueChanged(
                countryCodePicker.selectedCountryCode,
                EnumRegisterInputType.COUNTRY_CODE
            )
        }
    }


}