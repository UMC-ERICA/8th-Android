package com.example.umc_flo

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_flo.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding
    lateinit var timer : Timer
    private var mediaPlayer : MediaPlayer? = null
    private var gson : Gson = Gson()

    val songs = arrayListOf<Song>()
    lateinit var songDB: SongDatabase

    var nowPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPlayList()
        initSong()
        initClickListener()

    }

    override fun onPause() {
        super.onPause()
        setPlayerStatus(false)
        songs[nowPos].second = ((binding.songProgressSb.progress * songs[nowPos].playtime) / 100) / 1000
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("songId", songs[nowPos].id)

        editor.apply()


    }
    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun initPlayList() {
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }

    private fun initClickListener() {
        binding.songDownIb.setOnClickListener {
            finish()
        }
        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(true)
        }
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }

        binding.songNextIv.setOnClickListener {
            moveSong(1)
        }
        binding.songPreviousIv.setOnClickListener {
            moveSong(-1)
        }
        binding.songLikeIv.setOnClickListener {
            setLike(songs[nowPos].isLike)
        }

    }

    fun initSong() {
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        nowPos = getPlayingSongPosition(songId)
        Log.d("now Song ID", songs[nowPos].id.toString())
        startTimer()
        setPlayer(songs[nowPos])
    }

    private fun setLike(isLike: Boolean) {
        val song = songs[nowPos]

        song.isLike = !isLike

        songDB.songDao().updateIsLikeById(song.isLike, song.id)

        binding.songLikeIv.setImageResource(
            if (song.isLike) R.drawable.ic_my_like_on else R.drawable.ic_my_like_off
        )

        val msg = if (song.isLike) "좋아요가 추가되었습니다" else "좋아요가 취소되었습니다"
        val icon = if (song.isLike) R.drawable.ic_my_like_on else R.drawable.ic_my_like_off
        showCustomToast(this, msg, icon)
    }


    private fun moveSong(direct: Int) {
        if (nowPos + direct < 0) {
            Toast.makeText(this, "first song", Toast.LENGTH_SHORT).show()
            return
        }
        if (nowPos + direct >= songs.size) {
            Toast.makeText(this, "last song", Toast.LENGTH_SHORT).show()
            return
        }

        nowPos += direct
        timer.interrupt()
        startTimer()

        mediaPlayer?.release()
        mediaPlayer = null

        setPlayer(songs[nowPos])
    }

    private fun getPlayingSongPosition(songId : Int): Int {
        for (i in 0 until songs.size) {
            if (songs[i].id == songId) {
                return i
            }
        }
        return 0
    }

    fun setPlayer(song : Song) {
        binding.songMusicTitleTv.text = song.title
        binding.songSingerNameTv.text = song.singer
        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playtime / 60, song.playtime % 60)
        binding.songAlbumIv.setImageResource(song.coverImg!!)
        binding.songProgressSb.progress = (song.second * 1000 / song.playtime)

        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)

        if (song.isLike) {
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }

        setPlayerStatus(song.isPlaying)
    }

    fun setPlayerStatus(isPlaying : Boolean) {
        songs[nowPos].isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying) {
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
            mediaPlayer?.start()
        }
        else {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            }
        }
    }

    fun startTimer() {
        timer = Timer(songs[nowPos].playtime, songs[nowPos].isPlaying)
        timer.start()
    }

    inner class Timer(private val playtime : Int, var isPlaying: Boolean = true) : Thread() {
        private var second : Int= 0
        private var mills : Float = 0f

        override fun run() {
            super.run()
            try {
                while (true) {
                    if (second >= playtime) {
                        break
                    }
                    if (isPlaying) {
                        sleep(50)
                        mills += 50

                        runOnUiThread {
                            binding.songProgressSb.progress = ((mills / playtime) * 100).toInt()
                        }
                        if (mills % 1000 == 0f) {
                            runOnUiThread {
                                binding.songStartTimeTv.text = String.format("%02d:%02d", second / 60, second % 60)
                            }
                            second++
                        }
                    }
                }
            }
            catch (e : InterruptedException) {
                Log.d("Song", "Thread가 죽었습니다. ${e.message}")
            }

        }
    }

    fun showCustomToast(context: Context, message: String, iconResId: Int) {
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.custom_toast, null)

        val toastText = layout.findViewById<TextView>(R.id.toast_text)
        val toastIcon = layout.findViewById<ImageView>(R.id.toast_icon)

        toastText.text = message
        toastIcon.setImageResource(iconResId)

        val toast = Toast(context)
        toast.view = layout
        toast.duration = Toast.LENGTH_SHORT
        toast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 150)
        toast.show()
    }

}