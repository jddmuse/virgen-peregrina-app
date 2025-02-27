package com.virgen.peregrina.ui.replica.create

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.ActivityCreateReplicaBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.virgen.peregrina.MainActivity
import com.virgen.peregrina.ui.dialog.LoadingDialogView
import com.virgen.peregrina.ui.replica.EnumReplicaInputType
import com.virgen.peregrina.util.navigateToLoginActivity
import com.virgen.peregrina.util.navigateToMainActivity
import com.virgen.peregrina.util.view.IView
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
        binding.birthdatePicker.build()
    }

    override fun initObservers() {
        viewModel.loading.observe(this) {
            if (it.first) loadingDialog.show()
            else loadingDialog.dismiss()
        }
        viewModel.createReplicaSuccess.observe(this) {
            loadingDialog.dismiss()
            MaterialAlertDialogBuilder(this)
                .setMessage(getString(R.string.replica_label_replica_created_successfull))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.action_button_yes)) { dialog, which ->
                    navigateToMainActivity()
                }
                .show()
        }
        viewModel.errorMessage.observe(this) { message ->
            MaterialAlertDialogBuilder(this)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.action_button_yes)) { dialog, which ->
                    navigateToMainActivity()
                }
                .show()
        }
        viewModel.errorEditText.observe(this) { pair ->
            when (pair.first) {
                EnumReplicaInputType.CODE -> {
                    binding.codeErrorTextView.apply {
                        visibility = if(pair.second != null) View.VISIBLE else View.GONE
                        text = pair.second
                    }
                }
                EnumReplicaInputType.BIRTHDATE -> {
                    binding.dateErrorTextView.apply {
                        visibility = if(pair.second != null) View.VISIBLE else View.GONE
                        text = pair.second
                    }
                }
                EnumReplicaInputType.AUTH -> {
                    if(pair.second != null) {
                        MaterialAlertDialogBuilder(this)
                            .setMessage(getString(R.string.pilgrimage_error_user_authentication))
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.action_button_yes)) { dialog, which ->
                                navigateToLoginActivity()
                            }
                            .show()
                    }
                }
                else -> {}
            }
        }
    }

    override fun initListeners() {
        binding.birthdatePicker.setOnDateSelectedListener {
            viewModel.valueChanged(it, EnumReplicaInputType.BIRTHDATE)
        }
        binding.codeEditText.addTextChangedListener {
            viewModel.valueChanged(it.toString(), EnumReplicaInputType.CODE)
        }
        binding.actionButton.setSafeOnClickListener {
            viewModel.create()
        }
    }
}