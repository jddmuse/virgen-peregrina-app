package com.virgen.peregrina.ui.replica_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.ActivityPeregrinacionBinding
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.virgen.peregrina.data.model.ReplicaModel
import com.virgen.peregrina.data.response.LoginResponse
import com.virgen.peregrina.ui.replica_details.ReplicaDetailsActivity
import com.virgen.peregrina.ui.replica_list.create.CreateReplicaActivity
import com.virgen.peregrina.util.METHOD_CALLED
import com.virgen.peregrina.util.OnItemActionListener
import com.virgen.peregrina.util.UIBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReplicasListActivity : AppCompatActivity(), UIBehavior {

    companion object {
        private const val TAG = "ReplicasListActivity"
    }

    private lateinit var binding: ActivityPeregrinacionBinding
    private lateinit var allReplicaItemAdapter: ReplicaItemAdapter
    private lateinit var yourReplicaItemAdapter: ReplicaItemAdapter

    private val viewModel: ReplicaListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeregrinacionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        initObservers()
        initListeners()
    }

    override fun initUI() {
        try {
            yourReplicaItemAdapter = ReplicaItemAdapter()
            allReplicaItemAdapter = ReplicaItemAdapter()
            binding.appBarLayout.textView.text = getString(R.string.label_replicas)
            binding.replicasRecyclerView.let {
                it.layoutManager = LinearLayoutManager(
                    this@ReplicasListActivity,
                    RecyclerView.VERTICAL,
                    false
                )
                it.adapter = allReplicaItemAdapter
            }
            binding.yourReplicasRecyclerView.let {
                it.layoutManager = LinearLayoutManager(
                    this, RecyclerView.VERTICAL, false
                )
                it.adapter = yourReplicaItemAdapter
            }
            viewModel.onCreate()
        } catch (ex: Exception) {
            Log.e(TAG, "initUI(): Exception -> $ex")
        }
    }

    override fun initObservers() {
        try {
            with(viewModel) {
                replicas.observe(this@ReplicasListActivity) { list ->
                    Log.i(TAG, "replicas Change Observed: $list")
                    if (list.isNotEmpty()) {
                        with(binding) {
                            allReplicasInfo.visibility = View.GONE
                            replicasRecyclerView.visibility = View.VISIBLE
                        }
                        allReplicaItemAdapter.updateData(list)
                    } else {
                        with(binding) {
                            allReplicasInfo.visibility = View.VISIBLE
                            replicasRecyclerView.visibility = View.GONE
                        }
                    }
                }
                errorMsg.observe(this@ReplicasListActivity) { msg ->
                    Snackbar.make(
                        binding.replicasRecyclerView,
                        msg.toString(), Snackbar.LENGTH_SHORT
                    ).show()
                }
                userData.observe(this@ReplicasListActivity) { data: LoginResponse? ->
                    if (data != null && !data.replicas.isNullOrEmpty()) {
                        binding.yourReplicasRecyclerView.visibility = View.VISIBLE
                        yourReplicaItemAdapter.updateData(data.replicas)
                        binding.yourReplicasInfo.visibility = View.GONE
                    } else {
                        binding.yourReplicasInfo.visibility = View.VISIBLE
                        binding.yourReplicasRecyclerView.visibility = View.INVISIBLE
                    }
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "initObservers(): Exceptipn -> $ex")
        }
    }

    override fun initListeners() {
        try {
            yourReplicaItemAdapter.addObserver(object : OnItemActionListener<ReplicaModel> {
                override fun onClick(item: ReplicaModel) {
                    Log.i(TAG, "$METHOD_CALLED onClick() PARAMS: $item")
                    startActivity(
                        Intent(
                            this@ReplicasListActivity,
                            ReplicaDetailsActivity::class.java
                        ).apply {
                            putExtra("replica", Gson().toJson(item))
                            putExtra("pilgrimage_enabled", true)
                        }
                    )
                }
            })

            allReplicaItemAdapter.addObserver(object : OnItemActionListener<ReplicaModel> {
                override fun onClick(item: ReplicaModel) {
                    Log.i(TAG, "$METHOD_CALLED onClick() PARAMS: $item")
                    startActivity(
                        Intent(
                            this@ReplicasListActivity,
                            ReplicaDetailsActivity::class.java
                        ).apply {
                            putExtra("replica", Gson().toJson(item))
                            putExtra("pilgrimage_enabled", false)
                        }
                    )
                }
            })

            binding.addReplicaButton.setOnClickListener {
                startActivity(
                    Intent(
                        this@ReplicasListActivity,
                        CreateReplicaActivity::class.java
                    )
                )
            }

            binding.appBarLayout.toolbar.setNavigationOnClickListener { finish() }

        } catch (ex: Exception) {
            Log.e(TAG, "initListeners(): Exception -> $ex")
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.onCreate()
    }
}