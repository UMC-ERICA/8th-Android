package com.example.umc_8th_android

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
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

        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.anim_fade_in)
            .setExitAnim(R.anim.anim_fade_out)
            .setPopEnterAnim(R.anim.anim_fade_in)
            .setPopExitAnim(R.anim.anim_fade_out)
            .build()

        // 애니메이션 기능을 넣기 위해 BottomNavigationView 아이템 선택 리스너 설정
        binding.navBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    if (navController.currentDestination?.id != R.id.homeFragment)
                        navController.navigate(R.id.homeFragment, null, navOptions)
                    true
                }
                R.id.mapFragment -> {
                    if (navController.currentDestination?.id != R.id.mapFragment)
                        navController.navigate(R.id.mapFragment, null, navOptions)
                    true
                }
                R.id.chatFragment -> {
                    if (navController.currentDestination?.id != R.id.chatFragment)
                        navController.navigate(R.id.chatFragment, null, navOptions)
                    true
                }
                R.id.mypageFragment -> {
                    if (navController.currentDestination?.id != R.id.mypageFragment)
                        navController.navigate(R.id.mypageFragment, null, navOptions)
                    true
                }
                else -> false
            }
        }

    }
}