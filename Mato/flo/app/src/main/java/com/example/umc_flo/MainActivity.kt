    package com.example.umc_flo

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.util.Log
import com.example.umc_flo.databinding.ActivityMainBinding
import com.google.gson.Gson

    class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding


    private var song: Song = Song()
        private var gson: Gson = Gson()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.mainPlayerCl.setOnClickListener {
            val intent = Intent(this,SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("second", song.second)
            intent.putExtra("playTime", song.playTime)
            intent.putExtra("isPlaying", song.isPlaying)
            intent.putExtra("music",song.music)
            startActivity(intent)

        }

        initBottomNavigation()


        //Log.d("Song", song.title + song.singer)
    }

        private fun initBottomNavigation(){
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()

            binding.mainBnv.setOnItemSelectedListener{ item ->
                when (item.itemId) {

                    R.id.homeFragment -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, HomeFragment())
                            .commitAllowingStateLoss()
                        return@setOnItemSelectedListener true
                    }

                    R.id.lookFragment -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, LookFragment())
                            .commitAllowingStateLoss()
                        return@setOnItemSelectedListener true
                    }
                    R.id.searchFragment -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, SearchFragment())
                            .commitAllowingStateLoss()
                        return@setOnItemSelectedListener true
                    }
                    R.id.lockerFragment -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, LockerFragment())
                            .commitAllowingStateLoss()
                        return@setOnItemSelectedListener true
                    }
                }
                false
            }
        }

        private fun setMiniPlayer(song: Song){
            binding.mainMiniplayerTitleTv.text = song.title
            binding.mainMiniplayerTitleTv.text = song.singer
            binding.mainMiniplayerProgressSb.progress = (song.second*100000)/song.playTime
        }


        override fun onStart(){
            super.onStart()
            val sharedPreferences = getSharedPreferences("song",MODE_PRIVATE)
            val songJson = sharedPreferences.getString("songData",null)

            song = if(songJson == null){
                Song("라일락", "아이유(IU)", 0, 60, false, "music_lilac")
            }else{
                gson.fromJson(songJson, Song::class.java)
            }
            setMiniPlayer(song)

        }



}