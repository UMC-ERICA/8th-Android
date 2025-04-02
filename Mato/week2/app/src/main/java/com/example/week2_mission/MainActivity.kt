package com.example.week2_mission

import android.app.Activity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.week2_mission.databinding.ActivityMainBinding
import com.example.week2_mission.fragments.calendarFragment
import com.example.week2_mission.fragments.homeFragment
import com.example.week2_mission.fragments.pencilFragment
import com.example.week2_mission.fragments.profileFragment

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(binding.root)

        setBottomNavigationView()

        if(savedInstanceState == null){
            binding.bottomNavigationView.selectedItemId = R.id.home
        }
    }
    fun setBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.home -> {
                    supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.anime_fade_in,R.anim.anime_fade_out)
                    .replace(R.id.fragment_container,homeFragment())
                    .commit()
                    true
                }
                R.id.pencil -> {
                    supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.anime_fade_in,R.anim.anime_fade_out)
                    .replace(R.id.fragment_container,pencilFragment())
                    .commit()
                    true
                }
                R.id.calendar -> {
                    supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.anime_fade_in,R.anim.anime_fade_out)
                    .replace(R.id.fragment_container,calendarFragment()).commit()
                    true
                }
                R.id.user -> {
                    supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.anime_fade_in,R.anim.anime_fade_out)
                    .replace(R.id.fragment_container,profileFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }

}