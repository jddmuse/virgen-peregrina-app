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
import com.virgen.peregrina.data.model.pilgrimage.PilgrimageModel
import com.virgen.peregrina.data.model.pilgrimage.parcelable
import com.virgen.peregrina.ui.guidelines.GuidelinesActivity
import com.virgen.peregrina.ui.home.HomeViewModel
import com.virgen.peregrina.ui.home.PilgrimagesAdapter
import com.virgen.peregrina.ui.pilgrimage.PilgrimageDetailsActivity
import com.virgen.peregrina.ui.replica.list.ReplicasListActivity
import com.virgen.peregrina.util.view.IView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IView {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initObservers()
        initListeners()

        defaultSettings()
    }

    private fun defaultSettings() {
        viewModel.pilgrimages()
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
        } catch (ex: Exception) {
            Log.e(TAG, "initUI(): Exception -> $ex")
        }
    }

    override fun initObservers() {
        try {
            viewModel.errorMsg.observe(this) { msg ->
                // COMPLETE
            }
            viewModel.pilgrimages.observe(this) { data: List<PilgrimageModel> ->
                Log.i(TAG, "viewModel.pilgrimages.observe = $data")
                if(data.isNotEmpty()) {
                    binding.infoPilgrimages.visibility = View.GONE
                    binding.pilgrimagesRecyclerView.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
                        visibility = View.VISIBLE
                        adapter = PilgrimagesAdapter(
                            list = data,
                            listener = { item: PilgrimageModel ->
                                val jsonObject = Gson().toJson(item.parcelable())
                                val intent = Intent(this@MainActivity, PilgrimageDetailsActivity::class.java)
                                startActivity(intent.apply { putExtra("pilgrimage", jsonObject) })
                            }
                        )
                    }
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