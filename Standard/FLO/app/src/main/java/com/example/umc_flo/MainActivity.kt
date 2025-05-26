package com.example.umc_flo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.umc_flo.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity(), HomeFragment.AlbumSelectedListener {

    lateinit var binding : ActivityMainBinding

    private var song: Song = Song()
    private var gson: Gson = Gson()

    override fun onAlbumSelected(album: Album) {
        binding.mainMiniplayerTitleTv.text = album.title
        binding.mainMiniplayerSingerTv.text = album.singer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Umc_flo)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputDummySongs()

        binding.mainPlayerCl.setOnClickListener {
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId", song.id)
            editor.apply()

            val intent = Intent(this, SongActivity::class.java)
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

    private fun inputDummySongs() {
        val songDB = SongDatabase.getInstance(this)
        val songs = songDB?.songDao()?.getSongs()

        if (songs != null) {
            if (songs.isNotEmpty()) return
        }

        songDB?.songDao()?.insert(
            Song("Butter", "방탄소년단 (BTS)", 0, 166, false,"music_butter", R.drawable.img_album_exp, false)

        )
        songDB?.songDao()?.insert(
            Song("Lilac", "아이유 (IU)", 0, 214, false,"music_lilac", R.drawable.img_album_exp2, false)

        )
        songDB?.songDao()?.insert(
            Song("Next Level", "에스파 (AESPA)",0, 221, false,"music_next", R.drawable.img_album_exp3, false)

        )
        songDB?.songDao()?.insert(
            Song("Boy with Luv", "방탄소년단 (BTS)",0, 232, false,"music_boy", R.drawable.img_album_exp4, false)

        )
        songDB?.songDao()?.insert(
            Song("BBoom BBoom", "모모랜드 (MOMOLAND)",0, 209, false,"music_bboom", R.drawable.img_album_exp5, false)

        )
        songDB?.songDao()?.insert(
            Song("Weekend", "태연 (Tae Yeon)",0, 240, false,"music_weekend", R.drawable.img_album_exp6, false)
        )

        val _songs = songDB?.songDao()?.getSongs()
        Log.d("DB Data", _songs.toString())
    }

    override fun onStart() {
        super.onStart()
//        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
//        val songJson = sharedPreferences.getString("songData", null)
//
//        song = if(songJson == null) {
//            Song("LILAC", "아이유(IU)", 0, 60, false, "music_psy")
//        } else {
//            gson.fromJson(songJson, Song::class.java)
//        }

        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        val songDB = SongDatabase.getInstance(this)!!

        song = if (songId == 0) {
            songDB.songDao().getSong(1)
        } else {
            songDB.songDao().getSong(songId)
        }

        Log.d("song ID", song.id.toString())
        setMiniPlayer(song)

    }


}