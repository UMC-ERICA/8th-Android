package com.example.umc_flo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.umc_flo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        val song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString(), 0, 60, false)

        binding.mainPlayerCl.setOnClickListener {


            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("second", song.second)
            intent.putExtra("playtime", song.playtime)
            intent.putExtra("isPlaying", song.isPlaying)
            startActivity(intent)

        }

        binding.mainMiniplayerBtn.setOnClickListener {
            setPlayerStatus(false)
        }
        binding.mainPauseBtn.setOnClickListener {
            setPlayerStatus(true)
        }

        initBottomNavigation()


        if(savedInstanceState == null){
            binding.mainBnv.selectedItemId = R.id.homefragment
        }

    }

    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, HomeFragment()).commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homefragment -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, HomeFragment()).commitAllowingStateLoss()
                    true
                }

                R.id.lookfragment -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, LookFragment()).commitAllowingStateLoss()
                    true
                }

                R.id.searchfragment -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, SearchFragment()).commitAllowingStateLoss()
                    true
                }

                R.id.lockerfragment -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, LockerFragment()).commitAllowingStateLoss()
                    true
                }
                else -> false
            }
        }
    }

    fun setPlayerStatus(isplaying : Boolean) {
        if(isplaying) {
            binding.mainMiniplayerBtn.visibility = View.GONE
            binding.mainPauseBtn.visibility = View.VISIBLE
        }
        else {
            binding.mainMiniplayerBtn.visibility = View.VISIBLE
            binding.mainPauseBtn.visibility = View.GONE
        }
    }

}