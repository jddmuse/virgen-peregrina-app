package com.virgen.peregrina

import android.app.Notification
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.virgen_peregrina_app.R
import com.example.virgen_peregrina_app.databinding.ActivityMainBinding
import com.virgen.peregrina.ui.home.HomeFragment
import com.virgen.peregrina.ui.notification.NotificationFragment
import com.virgen.peregrina.ui.profile.ProfileFragment
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
        initBottomNavigationView()
    }

    override fun initObservers() {
        TODO("Not yet implemented")
    }

    override fun initListeners() {
        TODO("Not yet implemented")
    }

    private fun initBottomNavigationView() {
        try {
            with(supportFragmentManager) {
                // initializing
                val navigation = binding.bottomNavigation
                val hostFragment = R.id.nav_host_fragment

                // set navController
                navigation.setupWithNavController(
                    // getting navController
                    (findFragmentById(hostFragment) as NavHostFragment).run { navController }
                )

                // set Listener
                navigation.setOnItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.home_fragment -> {
                            beginTransaction().apply {
                                replace(hostFragment, HomeFragment.instance)
                                addToBackStack(null)
                                commit()
                            }
                            true
                        }
                        R.id.inbox_fragment -> {
                            beginTransaction().apply {
                                replace(hostFragment, NotificationFragment.instance)
                                addToBackStack(null)
                                commit()
                            }
                            true
                        }
                        R.id.profile_fragment -> {
                            beginTransaction().apply {
                                replace(hostFragment, ProfileFragment.instance)
                                addToBackStack(null)
                                commit()
                            }
                            true
                        }
                        else -> false
                    }
                }
            }
        } catch (ex:Exception) {
            Log.e(TAG, "initBottomNavigationView(): Exception -> $ex")
        }
    }

}