package com.virgen.peregrina.ui.replica.info

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.ActivityReplicaDetailsBinding
import com.google.gson.Gson
import com.virgen.peregrina.data.model.replica.ReplicaModel
import com.virgen.peregrina.data.model.replica.ReplicaParcelableModel
import com.virgen.peregrina.ui.pilgrimage.PilgrimageActivity
import com.virgen.peregrina.util.DateUtils
import com.virgen.peregrina.util.camelCase
import com.virgen.peregrina.util.enumerator.EnumDateFormat
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
            val jsonObject = intent.getStringExtra("replica")
            val replica = Gson().fromJson(jsonObject, ReplicaParcelableModel::class.java)
            binding.codeTextView.text = replica.code
            replica.birthdate?.let { binding.birthdateTextView.text = DateUtils.format(it, EnumDateFormat.DD_MMM_YYYY) }
            binding.ownerTextView.text = StringBuilder()
                .append(replica.user?.nameAndLastName?.camelCase()).append(" ${getString(R.string.label_separator_middle_point)} ")
                .append(replica.user?.city?.camelCase()).append(" ${getString(R.string.label_separator_middle_point)} ")
                .append(replica.user?.country?.camelCase()).append(" ${getString(R.string.label_separator_middle_point)} ")
                .append(replica.user?.email?.lowercase()).append(" ${getString(R.string.label_separator_middle_point)} ").toString()
                // .append(replica.user?.cellphone?.lowercase()).toString()
        } catch (ex: Exception) {
            Log.e(TAG, "initView(): Exception -> $ex")
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