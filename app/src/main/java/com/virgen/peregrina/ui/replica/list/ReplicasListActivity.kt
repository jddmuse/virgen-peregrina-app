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
import me.everything.android.ui.overscroll.IOverScrollState
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

@AndroidEntryPoint
class ReplicasListActivity : AppCompatActivity(), IView {

    companion object {
        private const val TAG = "ReplicasListActivity"
    }

    private lateinit var binding: ActivityPeregrinacionBinding
    private val viewModel: ReplicaListViewModel by viewModels()

    /** Adapters */
    private lateinit var replicaAdapter: ReplicaItemAdapter

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
        binding.infoTextView.visibility = View.GONE
        replicaAdapter = ReplicaItemAdapter {
            val intent = Intent(this@ReplicasListActivity, ReplicaDetailsActivity::class.java).apply {
                putExtra("replica", Gson().toJson(it.parcelable()))
            }
            startActivity(intent)
        }
        binding.replicasRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ReplicasListActivity, RecyclerView.VERTICAL, false)
            adapter = replicaAdapter
            visibility = View.VISIBLE
        }
        OverScrollDecoratorHelper.setUpOverScroll(binding.replicasRecyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL).apply {
            setOverScrollUpdateListener { decor, state, offset ->
                if (state == IOverScrollState.STATE_BOUNCE_BACK && offset < 0) {
                    viewModel.replicas()
                }
            }
        }
    }

    override fun initObservers() {
        viewModel.replicas.observe(this@ReplicasListActivity) { list ->
            if (list.isNotEmpty()) {
                replicaAdapter.addAll(list)
            }
//            else {
//                binding.replicasRecyclerView.visibility = View.GONE
//                binding.infoTextView.visibility = View.VISIBLE
//            }
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