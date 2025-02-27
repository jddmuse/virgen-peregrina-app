package com.virgen.peregrina.ui.pilgrimage

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.datepicker.DatePickerDaysOffRange
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.ActivityPilgrimageBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.hbb20.CountryCodePicker
import com.virgen.peregrina.data.model.replica.ReplicaParcelableModel
import com.virgen.peregrina.ui.dialog.DatePickerFragment
import com.virgen.peregrina.ui.dialog.LoadingDialogView
import com.virgen.peregrina.ui.pilgrimage.util.EnumPilgrimageInputType
import com.virgen.peregrina.ui.register.enumerator.EnumRegisterInputType
import com.virgen.peregrina.util.view.IView
import com.virgen.peregrina.util.view.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PilgrimageActivity : AppCompatActivity(), IView {

    companion object {
        private const val TAG = "PilgrimageActivity"
    }

    private lateinit var binding: ActivityPilgrimageBinding
    private lateinit var loadingDialog: LoadingDialogView
    private val viewModel: PilgrimageViewModel by viewModels()

    private val replica: ReplicaParcelableModel by lazy {
        val jsonObject = intent.getStringExtra("replica")
        Gson().fromJson(jsonObject, ReplicaParcelableModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPilgrimageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initObservers()
        initListeners()
    }

    override fun initView() {
        loadingDialog = LoadingDialogView(this)
        val daysOff = replica.pilgrimages?.filter { it.startDate != null && it.endDate != null }?.map { DatePickerDaysOffRange(it.startDate!!, it.endDate!!) } ?: emptyList()
        binding.startDatePicker
            .setOnDateSelectedListener {  }
            .setDaysOff(daysOff)
            .build()
        binding.endDatePicker
            .setOnDateSelectedListener {  }
            .setDaysOff(daysOff)
            .build()
    }

    override fun initObservers() {
        try {
            viewModel.error.observe(this) {
                MaterialAlertDialogBuilder(this)
                    .setMessage(it.ifEmpty { getString(R.string.error_generic) })
                    .setPositiveButton(getString(R.string.action_button_yes)) { dialog, which -> finish() }
                    .show()
            }
            viewModel.errorEditText.observe(this) { pair ->
                when (pair.first) {
//                    EnumPilgrimageInputType.START_DATE -> binding.startDateEditText.error = pair.second
//                    EnumPilgrimageInputType.END_DATE -> binding.endDateEditText.error = pair.second
                    EnumPilgrimageInputType.USER_ID -> binding.intentionEditText.error = pair.second
                    else -> {}
                }
            }
//            viewModel.onFinishActivity.observe(this) {
//                MaterialAlertDialogBuilder(this)
//                    .setMessage(getString(R.string.label_pilgrimage_success))
//                    .setPositiveButton(getString(R.string.action_button_yes)) { dialog, which -> finish() }
//                    .show()
//            }
            viewModel.loading.observe(this) { value ->
                if(value.first) loadingDialog.setMessage(value.second).show()
                else loadingDialog.dismiss()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "initObservers(): Exception -> $ex")
        }
    }

    override fun initListeners() {
        binding.intentionEditText.addTextChangedListener {
            viewModel.valueChanged(it?.toString() ?: "", EnumPilgrimageInputType.INTENTION)
        }
        binding.actionButton.setSafeOnClickListener {
            viewModel.create()
        }
        binding.startDatePicker.setOnDateSelectedListener {
            // COMPLETE
        }
        binding.appBarLayout.toolbar.setNavigationOnClickListener { finish() }
    }


}