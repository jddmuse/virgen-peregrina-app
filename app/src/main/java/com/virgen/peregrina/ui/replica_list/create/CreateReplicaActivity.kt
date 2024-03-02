package com.virgen.peregrina.ui.replica_list.create

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.virgen_peregrina_app.databinding.ActivityCreateReplicaBinding
import com.virgen.peregrina.ui.date_picker_dialog.DatePickerFragment
import com.virgen.peregrina.ui.loading_dialog.LoadingDialogView
import com.virgen.peregrina.ui.register.EnumReplicaDialogInputType
import com.virgen.peregrina.util.UIBehavior
import com.virgen.peregrina.util.enum.EnumReplicaState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateReplicaActivity : AppCompatActivity(), UIBehavior {

    companion object {
        private const val TAG = "CreateReplicaActivity"
    }

    private lateinit var binding: ActivityCreateReplicaBinding
    private val viewModel: CreateReplicaViewModel by viewModels()
    private lateinit var loadingDialog: LoadingDialogView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateReplicaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        initObservers()
        initListeners()
    }

    override fun initUI() {
        loadingDialog = LoadingDialogView(this)
    }

    override fun initObservers() {
        viewModel.loading.observe(this) {
            if(it.first) loadingDialog.show()
            else loadingDialog.dismiss()
        }
        viewModel.dispatchSuccessful.observe(this) {

        }
    }

    override fun initListeners() {
        with(binding) {
            codeEditText.addTextChangedListener { text ->
                viewModel.onValueChanged(text, EnumReplicaDialogInputType.CODE)
            }
            dateEditText.addTextChangedListener { text ->
                viewModel.onValueChanged(text, EnumReplicaDialogInputType.DATE)
            }
            dateEditText.setOnClickListener { view ->
                val picker = DatePickerFragment { year, month, day ->
                    dateEditText.setText("$day/${month + 1}/$year")
                }
                picker.show(supportFragmentManager, TAG)
            }
            fixRequiredRadioGroup.setOnCheckedChangeListener { group, checkedId ->
                if (fixRequiredRadioGroup.checkedRadioButtonId != -1) {
                    when {
                        positiveFixRequiredRadioButton.isChecked -> {
                            viewModel.onValueChanged(true, EnumReplicaDialogInputType.REPAIR_REQUIRED)
                        }
                        negativeFixRequiredRadioButton.isChecked -> {
                            viewModel.onValueChanged(false, EnumReplicaDialogInputType.REPAIR_REQUIRED)
                        }
                    }
                }
            }
            stateRadioGroup.setOnCheckedChangeListener { group, checkedId ->
                if (stateRadioGroup.checkedRadioButtonId != -1) {
                    when {
                        stateGoodRadioButton.isChecked -> {
                            viewModel.onValueChanged(EnumReplicaState.GOOD.name, EnumReplicaDialogInputType.STATE)
                        }
                        stateBadRadioButton.isChecked -> {
                            viewModel.onValueChanged(EnumReplicaState.BAD.name, EnumReplicaDialogInputType.STATE)
                        }
                        stateRegularRadioButton.isChecked -> {
                            viewModel.onValueChanged(EnumReplicaState.REGULAR.name, EnumReplicaDialogInputType.STATE)
                        }
                    }
                }
            }
            containerRadioGroup.setOnCheckedChangeListener { group, checkedId ->
                if (containerRadioGroup.checkedRadioButtonId != -1) {
                    when {
                        positiveContainerRadioButton.isChecked -> {
                            viewModel.onValueChanged(true, EnumReplicaDialogInputType.CONTAINER)
                        }
                        negativeContainerRadioButton.isChecked -> {
                            viewModel.onValueChanged(false, EnumReplicaDialogInputType.CONTAINER)
                        }
                    }
                }
            }
            dispatchButton.setOnClickListener {
                viewModel.dispatch()
            }
        }
    }
}