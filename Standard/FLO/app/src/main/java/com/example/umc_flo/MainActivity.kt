package com.example.umc_flo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.umc_flo.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    private var song: Song = Song()
    private var gson: Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Umc_flo)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        binding.mainPlayerCl.setOnClickListener {


            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("second", song.second)
            intent.putExtra("playtime", song.playtime)
            intent.putExtra("isPlaying", song.isPlaying)
            intent.putExtra("music", song.music)
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

    private fun setMiniPlayer(song : Song) {
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainMiniplayerProgressSb.progress = (song.second*100000) / song.playtime
    }

    override fun onStart() {
        super.onStart()
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val songJson = sharedPreferences.getString("songData", null)

        song = if(songJson == null) {
            Song("LILAC", "아이유(IU)", 0, 60, false, "music_psy")
        } else {
            gson.fromJson(songJson, Song::class.java)
        }

        setMiniPlayer(song)

    }

}