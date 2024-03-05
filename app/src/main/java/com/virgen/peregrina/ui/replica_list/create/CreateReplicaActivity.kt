package com.virgen.peregrina.ui.replica_list.create

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.ActivityCreateReplicaBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.virgen.peregrina.ui.date_picker_dialog.DatePickerFragment
import com.virgen.peregrina.ui.loading_dialog.LoadingDialogView
import com.virgen.peregrina.ui.register.EnumReplicaDialogInputType
import com.virgen.peregrina.util.UIBehavior
import com.virgen.peregrina.util.enum.EnumReplicaState
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CreateReplicaActivity : AppCompatActivity(), UIBehavior {

    companion object {
        private const val TAG = "CreateReplicaActivity"
    }

    private lateinit var binding: ActivityCreateReplicaBinding
    private val viewModel: CreateReplicaViewModel by viewModels()
    private lateinit var loadingDialog: LoadingDialogView
    private lateinit var replicaStatusAdapter: ArrayAdapter<String>
    private lateinit var fixRequiredAdapter: ArrayAdapter<String>
    private lateinit var containerAdapter: ArrayAdapter<String>

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

        // Inicializa desplegable de estado de Replica
        replicaStatusAdapter = ArrayAdapter(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            EnumReplicaState.values().map { it.name }
        )
        replicaStatusAdapter.notifyDataSetChanged()
        with(binding.statusEditText) {
            setAdapter(replicaStatusAdapter)
            threshold = 1
        }
        // Inicializa desplegable de Reparacion requerida
        fixRequiredAdapter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listOf("SI", "NO"))
        fixRequiredAdapter.notifyDataSetChanged()
        with(binding.fixRequiredEditText) {
            setAdapter(fixRequiredAdapter)
            threshold = 1
        }
        // Inicializa desplegable de Estuche
        containerAdapter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listOf("SI", "NO"))
        containerAdapter.notifyDataSetChanged()
        with(binding.containerEditText) {
            setAdapter(containerAdapter)
            threshold = 1
        }
        // year picker
        binding.yearPicker.apply {
            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
            minValue = 1960
            maxValue = currentYear
        }
    }

    override fun initObservers() {
        viewModel.loading.observe(this) {
            if(it.first) loadingDialog.show()
            else loadingDialog.dismiss()
        }
        viewModel.dispatchSuccessful.observe(this) {
            loadingDialog.dismiss()
            MaterialAlertDialogBuilder(this)
                .setMessage(getString(R.string.label_replica_created_successfull))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.action_button_yes)) { dialog, which -> finish() }
                .show()
        }
    }

    override fun initListeners() {
        with(binding) {
            codeEditText.addTextChangedListener { text ->
                viewModel.onValueChanged(text, EnumReplicaDialogInputType.CODE)
            }
            yearPicker.setOnValueChangedListener { picker, oldVal, newVal ->
                viewModel.onValueChanged(newVal.toString(), EnumReplicaDialogInputType.DATE)
            }
            fixRequiredEditText.setOnItemClickListener { parent, view, position, id ->
                fixRequiredAdapter.getItem(position)?.let { item ->
                    val value = item == "SI"
                    viewModel.onValueChanged(value, EnumReplicaDialogInputType.REPAIR_REQUIRED)
                }
            }
            containerEditText.setOnItemClickListener { parent, view, position, id ->
                containerAdapter.getItem(position)?.let { item ->
                    val value = item == "SI"
                    viewModel.onValueChanged(value, EnumReplicaDialogInputType.CONTAINER)
                }
            }
            statusEditText.setOnItemClickListener { adapterView: AdapterView<*>, view, i, l ->
                replicaStatusAdapter.getItem(i)?.let { item ->
                    viewModel.onValueChanged(item, EnumReplicaDialogInputType.STATE)
                }
            }
            dispatchButton.setOnClickListener {
                viewModel.dispatch()
            }
        }
    }
}