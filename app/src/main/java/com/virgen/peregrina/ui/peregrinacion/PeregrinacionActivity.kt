package com.virgen.peregrina.ui.peregrinacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.ActivityMainBinding
import com.example.virgen_peregrina_app.databinding.ActivityPeregrinacionBinding
import com.google.android.material.snackbar.Snackbar
import com.virgen.peregrina.util.UIBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PeregrinacionActivity : AppCompatActivity(), UIBehavior {

    companion object {
        private const val TAG = "PeregrinacionActivity"
    }

    private lateinit var binding: ActivityPeregrinacionBinding
    private lateinit var replicaItemAdapter: ReplicaItemAdapter

    private val viewModel: PeregrinacionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeregrinacionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    override fun initUI() {
        try {
            replicaItemAdapter = ReplicaItemAdapter()
            binding.replicasRecyclerView.apply {
                layoutManager = LinearLayoutManager(
                    this@PeregrinacionActivity,
                    RecyclerView.VERTICAL,
                    false
                )
                adapter = replicaItemAdapter
            }
            viewModel.onCreate()
            initObservers()
        } catch (ex: Exception) {
            Log.e(TAG, "initUI(): Exception -> $ex")
        }
    }

    override fun initObservers() {
        try {
            with(viewModel) {
                replicas.observe(this@PeregrinacionActivity) { list ->
                    Log.i(TAG, "Change Observed: $list")
                    replicaItemAdapter.updateData(list)
                }
                errorMsg.observe(this@PeregrinacionActivity) { msg ->
                    Snackbar.make(
                        binding.replicasRecyclerView,
                        msg.toString(), Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "initObservers(): Exceptipn -> $ex")
        }
    }

    override fun initListeners() {
        TODO("Not yet implemented")
    }
}