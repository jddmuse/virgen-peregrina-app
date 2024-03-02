package com.virgen.peregrina.ui.replica_details

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.ActivityReplicaDetailsBinding
import com.google.gson.Gson
import com.virgen.peregrina.data.model.ReplicaModel
import com.virgen.peregrina.data.model.TestimonyModel
import com.virgen.peregrina.ui.pilgrimage.PilgrimageActivity
import com.virgen.peregrina.ui.replica_details.testimony.TestimonyItemAdapter
import com.virgen.peregrina.util.EMPTY_STRING
import com.virgen.peregrina.util.UIBehavior
import com.virgen.peregrina.util.formatLocation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReplicaDetailsActivity : AppCompatActivity(), UIBehavior {

    private lateinit var binding: ActivityReplicaDetailsBinding
    private val viewModel: ReplicaDetailsViewModel by viewModels()
    private lateinit var testimonyItemAdapter: TestimonyItemAdapter

    companion object {
        private const val TAG = "ReplicasListActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReplicaDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        initObservers()
        initListeners()
    }

    override fun initUI() {
        try {
            initRecyclerView()
            val jsonObject = intent.getStringExtra("replica")
            val pilgrimageEnabled = intent.getBooleanExtra("pilgrimage_enabled", false)

            val data = Gson().fromJson(jsonObject, ReplicaModel::class.java)
            with(binding) {
                codeTextView.text = data.code
                oldTextView.text = data.received_date
                ownerNameTextView.text = data.user_name
                ownerPhoneTextView.text = data.user_cellphone
                ownerEmailTextView.text = data.user_email
                ownerCityTextView.text = formatLocation(
                    city = data.user_city ?: EMPTY_STRING,
                    country = data.user_country ?: EMPTY_STRING
                )
                pilgrimageButton.visibility = if (data.isAvailable) View.VISIBLE else View.GONE
                if (!data.isAvailable) {
                    pilgrimageButton.text = getString(R.string.label_pilgrimage_in_progress)
                    pilgrimageButton.isEnabled = false
                }
            }
            viewModel.onCreate(
                replica_id = data.id
            )
        } catch (ex: Exception) {
            Log.e(TAG, "initUI(): Exception -> $ex")
        }
    }

    private fun initRecyclerView() {
        try {
            testimonyItemAdapter = TestimonyItemAdapter()
            binding.testimoniesRecyclerView.let {
                it.layoutManager = LinearLayoutManager(
                    this, RecyclerView.VERTICAL, false
                )
                it.adapter = testimonyItemAdapter
            }
        } catch (ex: Exception) {
            Log.e(TAG, "initRecyclerView(): Exception -> $ex")
        }
    }

    override fun initObservers() {
        try {
            viewModel.testimonies.observe(this) { data: List<TestimonyModel> ->
                if(data.isNotEmpty()) {
                    testimonyItemAdapter.updateData(data)
                } else {
                    binding.infoTestimoniesTextView.visibility = View.VISIBLE
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "initObservers(): Exception -> $ex")
        }
    }

    override fun initListeners() {
        try {
            binding.pilgrimageButton.setOnClickListener {
                startActivity(
                    Intent(this, PilgrimageActivity::class.java).apply {
                        putExtra("replica_id", viewModel.getReplicaId)
                    }
                )
            }
        } catch (ex: Exception) {
            Log.e(TAG, "initListeners(): Exception -> $ex")
        }
    }
}