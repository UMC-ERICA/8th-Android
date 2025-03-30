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
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

        val navController = navHostFragment.navController

        // BottomNavigationView 설정
        binding.navBar.setupWithNavController(navController)

        // BottomNavigationView 아이템 선택 리스너 설정
        binding.navBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    navController.navigate(R.id.fragment_home)
                    true
                }
                R.id.map -> {
                    navController.navigate(R.id.fragment_map)
                    true
                }

                R.id.chat -> {
                    navController.navigate(R.id.fragment_chat)
                    true
                }

                R.id.mypage -> {
                    navController.navigate(R.id.fragment_mypage)
                    true
                }


                else -> false
            }

        }
    }
}