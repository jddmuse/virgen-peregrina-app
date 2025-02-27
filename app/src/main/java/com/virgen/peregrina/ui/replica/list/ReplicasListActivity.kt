package com.virgen.peregrina.ui.replica.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.ActivityPeregrinacionBinding
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.virgen.peregrina.data.model.replica.parcelable
import com.virgen.peregrina.ui.replica.create.CreateReplicaActivity
import com.virgen.peregrina.ui.replica.info.ReplicaDetailsActivity
import com.virgen.peregrina.util.view.IView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReplicasListActivity : AppCompatActivity(), IView {

    companion object {
        private const val TAG = "ReplicasListActivity"
    }

    private lateinit var binding: ActivityPeregrinacionBinding

    private val viewModel: ReplicaListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeregrinacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initObservers()
        initListeners()

        defaultSettings()
    }

    private fun defaultSettings() {
        viewModel.replicas()
    }

    override fun initView() {
        binding.appBarLayout.textView.text = getString(R.string.replica_label_replicas)
    }

    override fun initObservers() {
        viewModel.replicas.observe(this@ReplicasListActivity) { list ->
            if (list.isNotEmpty()) {
                binding.replicasRecyclerView.apply {
                    layoutManager = LinearLayoutManager(
                        this@ReplicasListActivity,
                        RecyclerView.VERTICAL,
                        false
                    )
                    adapter = ReplicaItemAdapter(list, {
                        // COMPLETE
                        val intent = Intent(this@ReplicasListActivity, ReplicaDetailsActivity::class.java).apply {
                            putExtra("replica", Gson().toJson(it.parcelable()))
                        }
                        startActivity(intent)
                    })
                }
            } else {
                binding.replicasRecyclerView.visibility = View.GONE
                binding.infoTextView.visibility = View.VISIBLE
            }
        }
        viewModel.errorMsg.observe(this@ReplicasListActivity) { msg ->
            Snackbar.make(
                binding.replicasRecyclerView,
                msg.toString(), Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    override fun initListeners() {
        binding.newButton.setOnClickListener {
            startActivity(
                Intent(
                    this@ReplicasListActivity,
                    CreateReplicaActivity::class.java
                )
            )
        }
        binding.appBarLayout.toolbar.setNavigationOnClickListener { finish() }
    }

}