package com.virgen.peregrina

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.virgen_peregrina_app.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.virgen.peregrina.data.model.PilgrimageModel
import com.virgen.peregrina.ui.guidelines.GuidelinesActivity
import com.virgen.peregrina.ui.home.HomeViewModel
import com.virgen.peregrina.ui.home.PilgrimagesAdapter
import com.virgen.peregrina.ui.home.dialogs.SendTestimonyDialog
import com.virgen.peregrina.ui.pilgrimage.PilgrimageDetailsActivity
import com.virgen.peregrina.ui.replica.list.ReplicasListActivity
import com.virgen.peregrina.util.*
import com.virgen.peregrina.util.view.IView
import com.virgen.peregrina.util.view.IActionListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IView {

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

        initView()
        initObservers()
        initListeners()
    }

    override fun initView() {
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
            pilgrimagesAdapter = PilgrimagesAdapter(object: IActionListener<PilgrimageModel> {
                override fun onClick(item: PilgrimageModel) {
                    val jsonObject = Gson().toJson(item)
                    val intent = Intent(this@MainActivity, PilgrimageDetailsActivity::class.java).apply { putExtra("pilgrimage", jsonObject) }
                    startActivity(intent)
                }
            })
            binding.pilgrimagesRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
                adapter = pilgrimagesAdapter
            }
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
                if(data.isNotEmpty()) {
                    binding.infoPilgrimages.visibility = View.GONE
                    binding.pilgrimagesRecyclerView.visibility = View.VISIBLE
                    pilgrimagesAdapter.updateData(data)
                } else {
                    binding.infoPilgrimages.visibility = View.VISIBLE
                    binding.pilgrimagesRecyclerView.visibility = View.GONE
                }
            }
            viewModel.userNameTitle.observe(this) { value ->
                binding.appBarLayout.textView.text = value
            }
        } catch (ex: Exception) {
            Log.e(TAG, "initObservers(): Exception -> $ex")
        }
    }

    override fun initListeners() {
        try {
            binding.peregrinacionCardView.setOnClickListener {
                startActivity(
                    Intent(this, ReplicasListActivity::class.java)
                )
            }
            binding.guidelinesCardView.setOnClickListener {
                startActivity(
                    Intent(this, GuidelinesActivity::class.java)
                )
            }
        } catch (ex: Exception) {
            Log.e(TAG, "initListeners(): Exception -> $ex")
        }
    }

}