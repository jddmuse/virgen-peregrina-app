package com.virgen.peregrina.ui.replica.info

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.virgen_peregrina_app.databinding.ActivityReplicaDetailsBinding
import com.google.gson.Gson
import com.virgen.peregrina.data.model.ReplicaModel
import com.virgen.peregrina.data.model.TestimonyModel
import com.virgen.peregrina.ui.pilgrimage.PilgrimageActivity
import com.virgen.peregrina.util.view.IView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReplicaDetailsActivity : AppCompatActivity(), IView {

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
        initView()
        initObservers()
        initListeners()
    }

    override fun initView() {
        try {
            val jsonObject = intent.getStringExtra("replica")
            val pilgrimageEnabled = intent.getBooleanExtra("pilgrimage_enabled", false)

            val data = Gson().fromJson(jsonObject, ReplicaModel::class.java)
//            with(binding) {
//                codeTextView.text = data.code
//                oldTextView.text = data.received_date
//                ownerNameTextView.text = data.user_name
//                ownerPhoneTextView.text = data.user_cellphone
//                ownerEmailTextView.text = data.user_email
//                ownerCityTextView.text = formatLocation(
//                    city = data.user_city ?: EMPTY_STRING,
//                    country = data.user_country ?: EMPTY_STRING
//                )
//                pilgrimageButton.visibility = if (data.isAvailable) View.VISIBLE else View.GONE
//                if (!data.isAvailable) {
//                    pilgrimageButton.text = getString(R.string.label_pilgrimage_in_progress)
//                    pilgrimageButton.isEnabled = false
//                }
//            }
//            viewModel.onCreate(
//                replica_id = data.id
//            )
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
                    putExtra("replica_id", viewModel.getReplicaId)
                }
            )
        }
        binding.appBarLayout.toolbar.setNavigationOnClickListener { finish() }
    }
}