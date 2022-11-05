package com.virgen.peregrina

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.virgen_peregrina_app.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.virgen.peregrina.data.model.PilgrimageModel
import com.virgen.peregrina.data.response.LoginResponse
import com.virgen.peregrina.ui.home.HomeViewModel
import com.virgen.peregrina.ui.home.PilgrimagesAdapter
import com.virgen.peregrina.ui.home.dialogs.SendTestimonyDialog
import com.virgen.peregrina.ui.pilgrimage.PilgrimageDetailsActivity
import com.virgen.peregrina.ui.replica_list.ReplicasListActivity
import com.virgen.peregrina.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.max

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), UIBehavior, OnItemActionListener<PilgrimageModel> {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var pilgrimagesAdapter: PilgrimagesAdapter
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    override fun initUI() {
        try {
            var maxLinesActived = true
            with(binding) {
                monthMessageTextView.setOnClickListener {
                    monthMessageTextView.apply {
                        maxLinesActived = !maxLinesActived
                        maxLines = if(maxLinesActived) 3 else Integer.MAX_VALUE
                    }
                }
            }
            initRecyclerView()
            initListeners()
            initObservers()
            viewModel.onCreate()
            askForNotifications()
        } catch (ex: Exception) {
            Log.e(TAG, "initUI(): Exception -> $ex")
        }
    }

    override fun initObservers() {
        try {
            viewModel.errorMsg.observe(this) { msg ->
                getToast(this, msg ?: EMPTY_STRING)
            }
            viewModel.pilgrimages.observe(this) { data ->
                Log.i(TAG, "viewModel.pilgrimages.observe = $data")
                pilgrimagesAdapter.updateData(data)
            }
            viewModel.userData.observe(this) { data: LoginResponse? ->
                Log.i(TAG, "Change observed = $data")
                binding.welcomeTextView.text =
                    "${data?.name ?: EMPTY_STRING} ${data?.last_name ?: EMPTY_STRING}"
            }
        } catch (ex: Exception) {
            Log.e(TAG, "initObservers(): Exception -> $ex")
        }
    }

    override fun initListeners() {
        try {
            binding.peregrinacionCardView.setOnClickListener {
                Log.i(TAG, "peregrinacionCardView.onClick()")
                startActivity(
                    Intent(this, ReplicasListActivity::class.java)
                )
            }
        } catch (ex: Exception) {
            Log.e(TAG, "initListeners(): Exception -> $ex")
        }
    }

    private fun initRecyclerView() {
        try {
            pilgrimagesAdapter = PilgrimagesAdapter(this)
            binding.pilgrimagesRecyclerView.let {
                it.layoutManager = LinearLayoutManager(
                    this, RecyclerView.VERTICAL, false
                )
                it.adapter = pilgrimagesAdapter
            }
        } catch (ex: Exception) {
            Log.e(TAG, "initRecyclerView(): Exception -> $ex")
        }
    }

    private fun askForNotifications() {
        try {
            Log.i(TAG, "$METHOD_CALLED askForNotifications()")
            viewModel.userData.value?.let { user ->
//                user.pilgrimages.forEach { pilgrimage ->
//                    askForReturningReplicaAndTestimony(pilgrimage, Pilgrimage.ATTENDANT)
//                }
                user.replicas.forEach { replica ->
                    replica.pilgrimages.forEach { pilgrimage ->
                        askForReturningReplicaAndTestimony(pilgrimage)
                    }
                }
            }
        } catch (ex: Exception) {
            getExceptionLog(TAG, "askForNotifications", ex)
        }
    }

    private fun askForReturningReplicaAndTestimony(pilgrimage: PilgrimageModel) {
        try {
            if (!pilgrimage.have_testimony && pilgrimage.isFinished) {
                SendTestimonyDialog(this) { testimony ->
                    viewModel.onSendTestimony(
                        testimonyMsg = testimony,
                        replica_id = pilgrimage.replica_id,
                        user_id = pilgrimage.user_id,
                        pilgrimage_id = pilgrimage.id!!
                    )
                }.show()
            }
        } catch (ex: Exception) {
            getExceptionLog(TAG, "askForReturningReplica", ex)
        }
    }

    override fun onClick(item: PilgrimageModel) {
        try {
            val jsonObject = Gson().toJson(item)
            startActivity(
                Intent(this, PilgrimageDetailsActivity::class.java).apply {
                    putExtra("pilgrimage", jsonObject)
                }
            )
        } catch (ex: Exception) {
            getExceptionLog(TAG, "onClick", ex)
        }
    }


}