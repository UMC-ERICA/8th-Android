package com.example.umc_8th_android

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.umc_8th_android.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.sub_fragment_container) as NavHostFragment

        val navController = navHostFragment.navController

        // BottomNavigationView 설정
        binding.navBar.setupWithNavController(navController)

        // BottomNavigationView 아이템 선택 리스너 설정
        binding.navBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    if (navController.currentDestination?.id != R.id.homeFragment)
                        navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.mapFragment -> {
                    if (navController.currentDestination?.id != R.id.mapFragment)
                        navController.navigate(R.id.mapFragment)
                    true
                }
                R.id.chatFragment -> {
                    if (navController.currentDestination?.id != R.id.chatFragment)
                        navController.navigate(R.id.chatFragment)
                    true
                }
                R.id.mypageFragment -> {
                    if (navController.currentDestination?.id != R.id.mypageFragment)
                        navController.navigate(R.id.mypageFragment)
                    true
                }
                else -> false
            }
        }

    }
}