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
import me.everything.android.ui.overscroll.IOverScrollDecor
import me.everything.android.ui.overscroll.IOverScrollState
import me.everything.android.ui.overscroll.IOverScrollUpdateListener
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IView {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private val viewModel: HomeViewModel by viewModels()

    /** Adapters */
    private lateinit var pilgrimagesAdapter: PilgrimagesAdapter

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
        var maxLinesActivated = true
        binding.monthMessageTextView.setOnClickListener {
            binding.monthMessageTextView.apply {
                maxLinesActivated = !maxLinesActivated
                maxLines = if(maxLinesActivated) 3 else Integer.MAX_VALUE
            }
        }
        pilgrimagesAdapter = PilgrimagesAdapter(
            listener = { item: PilgrimageModel ->
                val jsonObject = Gson().toJson(item.parcelable())
                val intent = Intent(this@MainActivity, PilgrimageDetailsActivity::class.java)
                startActivity(intent.apply { putExtra("pilgrimage", jsonObject) })
            })
        binding.pilgrimagesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            visibility = View.VISIBLE
            adapter = pilgrimagesAdapter
        }
        OverScrollDecoratorHelper.setUpOverScroll(binding.pilgrimagesRecyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL).apply {
            setOverScrollUpdateListener { decor, state, offset ->
                if (state == IOverScrollState.STATE_BOUNCE_BACK && offset < 0) {
                    viewModel.pilgrimages()
                }
            }
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
                    pilgrimagesAdapter.addAll(data)
                }
//                else {
//                    binding.infoPilgrimages.visibility = View.VISIBLE
//                    binding.pilgrimagesRecyclerView.visibility = View.GONE
//                }
            }
            viewModel.userNameTitle.observe(this) { value ->
                binding.appBarLayout.textView.text = value
            }
        } catch (ex: Exception) {
            Log.e(TAG, "initObservers(): Exception -> $ex")
        }
    }

    override fun initListeners() {
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
//        binding.pilgrimagesRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
//                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
//                if (lastVisibleItem == layoutManager.itemCount - 1 && dy > 0){
//                    viewModel.pilgrimages()
//                }
//            }
//        })
    }

}