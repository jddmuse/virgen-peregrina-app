package com.virgen.peregrina.ui.pilgrimage

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.datepicker.DatePickerDaysOffRange
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.ActivityPilgrimageBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.virgen.peregrina.data.model.replica.ReplicaParcelableModel
import com.virgen.peregrina.ui.dialog.LoadingDialogView
import com.virgen.peregrina.ui.pilgrimage.util.EnumPilgrimageInputType
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
        defaultSettings()
    }

    private fun defaultSettings() {
        viewModel.valueChanged(replica.id, EnumPilgrimageInputType.REPLICA)
    }

    override fun initView() {
        loadingDialog = LoadingDialogView(this)
        val daysOff = replica.pilgrimages?.filter { it.startDate != null && it.endDate != null }?.map { DatePickerDaysOffRange(it.startDate!!, it.endDate!!) } ?: emptyList()
        binding.startDatePicker
            .setDaysOff(daysOff)
            .build()
        binding.endDatePicker
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
                    EnumPilgrimageInputType.START_DATE -> {
                        binding.startDateErrorTextView.apply {
                            visibility = if(pair.second != null) View.VISIBLE else View.GONE
                            text = pair.second
                        }
                    }
                    EnumPilgrimageInputType.END_DATE -> {
                        binding.endDateErrorTextView.apply {
                            visibility = if(pair.second != null) View.VISIBLE else View.GONE
                            text = pair.second
                        }
                    }
                    EnumPilgrimageInputType.INTENTION -> {
                        binding.intentionEditText.error = pair.second
                    }
                    EnumPilgrimageInputType.USER -> {
                        if(pair.second != null) {
                            MaterialAlertDialogBuilder(this)
                                .setMessage(getString(R.string.pilgrimage_error_user_authentication))
                                .setCancelable(false)
                                .setPositiveButton(getString(R.string.action_button_yes)) { dialog, which -> finish() }
                                .show()
                        }
                    }
                    else -> {}
                }
            }
            viewModel.loading.observe(this) { value ->
                if(value.first) loadingDialog.setMessage(value.second).show()
                else loadingDialog.dismiss()
            }
            viewModel.createPilgrimageSuccess.observe(this) {
                MaterialAlertDialogBuilder(this)
                    .setMessage(getString(R.string.pilgrimage_label_success))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.action_button_yes)) { dialog, which -> finish() }
                    .show()
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
            viewModel.valueChanged(it, EnumPilgrimageInputType.START_DATE)
        }
        binding.endDatePicker.setOnDateSelectedListener {
            viewModel.valueChanged(it, EnumPilgrimageInputType.END_DATE)
        }
        binding.appBarLayout.toolbar.setNavigationOnClickListener { finish() }
    }


}