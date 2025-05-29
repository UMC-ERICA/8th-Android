package com.example.umc_flo


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_flo.databinding.FragmentLikeSongBinding
import com.example.umc_flo.LikedSongRVAdapter


class LikeSongFragment: Fragment() {
    private lateinit var binding: FragmentLikeSongBinding
    private lateinit var songDB: SongDatabase
    private lateinit var adapter: LikedSongRVAdapter
    private val songs = ArrayList<Song>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLikeSongBinding.inflate(inflater, container, false)
        songDB = SongDatabase.getInstance(requireContext())!!

        loadLikedSongs()

        return binding.root
    }

    private fun loadLikedSongs() {
        val likedSongs = ArrayList(songDB.songDao().getLikedSongs(true))

        adapter = LikedSongRVAdapter(
            likedSongs,
            onClickSong = { song ->
                val intent = Intent(context, SongActivity::class.java)
                val editor = requireContext().getSharedPreferences("song", AppCompatActivity.MODE_PRIVATE).edit()
                editor.putInt("songId", song.id)
                editor.apply()
                startActivity(intent)
            },
            onToggleLike = { song ->
                songDB.songDao().updateIsLikeById(false, song.id)
            }
        )

        binding.likeSongRv.layoutManager = LinearLayoutManager(context)
        binding.likeSongRv.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        loadLikedSongs() // 다시 돌아왔을 때 새로고침
    }
}