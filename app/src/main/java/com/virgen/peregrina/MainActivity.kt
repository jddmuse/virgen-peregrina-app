package com.virgen.peregrina

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.virgen_peregrina_app.databinding.ActivityMainBinding
import com.virgen.peregrina.ui.peregrinacion.replica_list.ReplicasListActivity
import com.virgen.peregrina.util.UIBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), UIBehavior {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    override fun initUI() {
        try {
            initListeners()
        } catch (ex: Exception) {
            Log.e(TAG, "initUI(): Exception -> $ex")
        }
    }

    override fun initObservers() {
        TODO("Not yet implemented")
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


}