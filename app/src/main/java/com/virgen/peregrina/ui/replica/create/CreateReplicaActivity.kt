package com.virgen.peregrina.ui.replica.create

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.ActivityCreateReplicaBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.virgen.peregrina.ui.dialog.LoadingDialogView
import com.virgen.peregrina.ui.register.enumerator.EnumReplicaDialogInputType
import com.virgen.peregrina.util.view.IView
import com.virgen.peregrina.util.enumerator.EnumReplicaStatus
import com.virgen.peregrina.util.view.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CreateReplicaActivity : AppCompatActivity(), IView {

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

        initView()
        initObservers()
        initListeners()
    }

    override fun initView() {
        loadingDialog = LoadingDialogView(this)

    }

    override fun initObservers() {
        viewModel.loading.observe(this) {
            if (it.first) loadingDialog.show()
            else loadingDialog.dismiss()
        }
        viewModel.createReplicaSuccess.observe(this) {
            loadingDialog.dismiss()
            MaterialAlertDialogBuilder(this)
                .setMessage(getString(R.string.label_replica_created_successfull))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.action_button_yes)) { dialog, which -> finish() }
                .show()
        }
    }

    override fun initListeners() {
        binding.yearEditText.addTextChangedListener {
            viewModel.valueChanged(it.toString(), EnumReplicaDialogInputType.DATE)
        }
        binding.codeEditText.addTextChangedListener {
            viewModel.valueChanged(it.toString(), EnumReplicaDialogInputType.CODE)
        }
    }
}