package com.virgen.peregrina.ui.pilgrimage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.ActivityPilgrimageDetailsBinding
import com.google.gson.Gson
import com.virgen.peregrina.data.model.PilgrimageModel
import com.virgen.peregrina.util.formatDateForView
import com.virgen.peregrina.util.formatLocation
import com.virgen.peregrina.util.getExceptionLog

class PilgrimageDetailsActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "PilgrimageDetailsActivity"
    }

    private lateinit var binding: ActivityPilgrimageDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPilgrimageDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        initListeners()
    }

    private fun initListeners() {
        binding.appBarLayout.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun initUI() {
        try {
            binding.appBarLayout.textView.text = getString(R.string.label_pilgrimage_details)


            val jsonObject = intent.getStringExtra("pilgrimage")
            val pilgrimageModel = Gson().fromJson(jsonObject, PilgrimageModel::class.java)
//            with(binding) {
//                intentionTextView.text = pilgrimageModel.intention
//                dateTextView.text = getDatesRank(
//                    pilgrimageModel.date_start ?: "", pilgrimageModel.date_end ?: ""
//                )
//                cityTextView.text = formatLocation(
//                    pilgrimageModel.city, pilgrimageModel.country
//                )
//                receiverUserNameTextView.text = pilgrimageModel.receiver_user_name
//                receiverUserTelephoneTextView.text = pilgrimageModel.receiver_user_telephone
//                receiverUserEmailTextView.text = pilgrimageModel.receiver_user_email
//
//                replicaOwnerUserNameTextView.text = pilgrimageModel.replica_owner_name_id
//                replicaOwnerUserEmailTextView.text = pilgrimageModel.replica_owner_user_email
//                replicaOwnerUserTelephoneTextView.text = pilgrimageModel.replica_owner_user_telephone
//
//                replicaCodeTextView.text = getString(R.string.label_replica_code_value, pilgrimageModel.replica_code)
//
//                attendantNameTextView.text = pilgrimageModel.attendantName
//                attendantEmailTextView.text = pilgrimageModel.attendantEmail
//                attendantTelephoneTextView.text = pilgrimageModel.attendantPhone
//            }
        } catch (ex: Exception) {
            getExceptionLog(TAG, "initUI", ex)
        }
    }

    private fun getDatesRank(start: String, end: String): String {
        try {
            return getString(
                R.string.label_date_rank,
                formatDateForView(this, start),
                formatDateForView(this, end)
            )
        } catch (ex: Exception) {
            getExceptionLog(TAG, "getDatesRank", ex)
        }
        return getString(R.string.error_generic)
    }

}