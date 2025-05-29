package com.example.umc_flo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.umc_flo.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private var song: Song = Song()
    private var gson: Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputDummysong()
        inputDummyAlbums()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.mainMiniplayerPreviousBtn.setOnClickListener {
            moveSong(-1)
        }
        binding.mainMiniplayerNextBtn.setOnClickListener {
            moveSong(1)
        }

        binding.mainPlayerCl.setOnClickListener {

            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("second", song.second)
            intent.putExtra("playTime", song.playTime)
            intent.putExtra("isPlaying", song.isPlaying)
            intent.putExtra("music", song.music)

            val editor = getSharedPreferences("song",MODE_PRIVATE).edit()
            editor.putInt("songId",song.id)
            editor.apply()

            val intent = Intent(this, SongActivity::class.java)

            startActivity(intent)
        }

        initBottomNavigation()
    }


    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        private fun moveSong(direction: Int) {
            val songDB = SongDatabase.getInstance(this)!!
            val songList = songDB.songDao().getSongs()
            val nowPos = songList.indexOfFirst { it.id == song.id }

            val newPos = nowPos + direction

            if (newPos < 0) {
                Toast.makeText(this, "첫 번째 곡입니다", Toast.LENGTH_SHORT).show()
                return
            }
            if (newPos >= songList.size) {
                Toast.makeText(this, "마지막 곡입니다", Toast.LENGTH_SHORT).show()
                return
            }

            song = songList[newPos]
            saveSongToPrefs(song)
            setMiniPlayer(song)
        }

        private fun saveSongToPrefs(song: Song) {
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId", song.id)
            editor.apply()
        }

        private fun initBottomNavigation(){
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()


        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    true
                }

                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    true
                }

                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    true
                }

                else -> false
            }
        }
    }

    private fun setMiniPlayer(song: Song) {
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainMiniplayerProgressSb.progress = (song.second * 100000) / song.playTime
    }

    override fun onStart() {
        super.onStart()
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val songJson = sharedPreferences.getString("songData", null)

        song = if (songJson == null) {
            Song("라일락", "아이유(IU)", 0, 60, false, "music_lilac")
        } else {
            gson.fromJson(songJson, Song::class.java)

        fun setMiniPlayer(song: Song){
            binding.mainMiniplayerTitleTv.text = song.title
            binding.mainMiniplayerSingerTv.text = song.singer
            binding.mainMiniplayerProgressSb.progress = (song.second*100000)/song.playTime


        }

        setMiniPlayer(song)
    }
}

        }


        override fun onStart(){
            super.onStart()
//            val sharedPreferences = getSharedPreferences("song",MODE_PRIVATE)
//            val songJson = sharedPreferences.getString("songData",null)
//
//            song = if(songJson == null){
//                Song("라일락", "아이유(IU)", 0, 60, false, "music_lilac")
//            }else{
//                gson.fromJson(songJson, Song::class.java)
//            }
            val spf = getSharedPreferences("song",MODE_PRIVATE)
            val songId = spf.getInt("songId",0)

            val songDB = SongDatabase.getInstance(this)!!

            song = if(songId == 0){
                songDB.songDao().getSong(1)
            }
            else{
                songDB.songDao().getSong(songId)
            }

            Log.d("song ID",song.id.toString())

            setMiniPlayer(song)

        }

        private fun inputDummysong(){
            val songDB = SongDatabase.getInstance(this)!!
            val songs = songDB.songDao().getSongs()

            if(songs.isNotEmpty()) return

            songDB.songDao().insert(
                Song(
                    "Lilac",
                    "아이유 (IU)",
                    0,
                    200,
                    false,
                    "music_lilac",
                    R.drawable.img_album_exp2,
                    false,
                )
            )

            songDB.songDao().insert(
                Song(
                    "Flu",
                    "아이유 (IU)",
                    0,
                    200,
                    false,
                    "music_flu",
                    R.drawable.img_album_exp2,
                    false,
                )
            )

            songDB.songDao().insert(
                Song(
                    "Butter",
                    "방탄소년단 (BTS)",
                    0,
                    190,
                    false,
                    "music_butter",
                    R.drawable.img_album_exp,
                    false,
                )
            )

            songDB.songDao().insert(
                Song(
                    "Next Level",
                    "에스파 (AESPA)",
                    0,
                    210,
                    false,
                    "music_next",
                    R.drawable.img_album_exp3,
                    false,
                )
            )


            songDB.songDao().insert(
                Song(
                    "Boy with Luv",
                    "방탄소년단 (BTS)",
                    0,
                    230,
                    false,
                    "music_boy",
                    R.drawable.img_album_exp4,
                    false,
                )
            )


            songDB.songDao().insert(
                Song(
                    "BBoom BBoom",
                    "모모랜드 (MOMOLAND)",
                    0,
                    240,
                    false,
                    "music_bboom",
                    R.drawable.img_album_exp5,
                    false,
                )
            )

            val _songs = songDB.songDao().getSongs() // 데이터 잘 들어갔는지 확인
            Log.d("DB data",_songs.toString())
        }

        private fun inputDummyAlbums(){
            val songDB = SongDatabase.getInstance(this)!!
            val albums = songDB.albumDao().getAlbums()

            if(albums.isNotEmpty()) return

            songDB.albumDao().insert(
                Album(
                    0,
                    "IU 5th Album 'LILAC'", "아이유 (IU)", R.drawable.img_album_exp2
                )
            )

            songDB.albumDao().insert(
                Album(
                    1,
                    "Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp
                )
            )

            songDB.albumDao().insert(
                Album(
                    2,
                    "iScreaM Vol.10 : Next Level Remixes", "에스파 (AESPA)", R.drawable.img_album_exp3
                )
            )

            songDB.albumDao().insert(
                Album(
                    3,
                    "MAP OF THE SOUL : PERSONA", "방탄소년단 (BTS)", R.drawable.img_album_exp4
                )
            )

            songDB.albumDao().insert(
                Album(
                    4,
                    "GREAT!", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5
                )
            )

        }






}

