package com.virgen.peregrina.ui.replica.info

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.virgen_peregrina_app.databinding.ActivityReplicaDetailsBinding
import com.google.gson.Gson
import com.virgen.peregrina.data.model.replica.ReplicaModel
import com.virgen.peregrina.data.model.replica.ReplicaParcelableModel
import com.virgen.peregrina.ui.pilgrimage.PilgrimageActivity
import com.virgen.peregrina.util.view.IView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReplicaDetailsActivity : AppCompatActivity(), IView {

    private lateinit var binding: ActivityReplicaDetailsBinding
    private val viewModel: ReplicaDetailsViewModel by viewModels()
    private lateinit var testimonyItemAdapter: TestimonyItemAdapter

    private val replica: ReplicaParcelableModel by lazy {
        val jsonObject = intent.getStringExtra("replica")
        Gson().fromJson(jsonObject, ReplicaParcelableModel::class.java)
    }

    companion object {
        private const val TAG = "ReplicasListActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReplicaDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initObservers()
        initListeners()
    }

    override fun initView() {
        try {


        } catch (ex: Exception) {
            Log.e(TAG, "initUI(): Exception -> $ex")
        }
    }

    override fun initObservers() {

    }

    override fun initListeners() {
        binding.pilgrimageButton.setOnClickListener {
            startActivity(
                Intent(this, PilgrimageActivity::class.java).apply {
                    putExtra("replica", Gson().toJson(replica))
                }
            )
        }
        binding.appBarLayout.toolbar.setNavigationOnClickListener { finish() }
    }
}