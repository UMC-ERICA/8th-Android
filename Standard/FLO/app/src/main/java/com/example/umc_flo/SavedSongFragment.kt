package com.example.umc_flo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_flo.databinding.FragmentSavedSongBinding

class SavedSongFragment : Fragment() {
    lateinit var binding : FragmentSavedSongBinding
    private val songs = ArrayList<Song>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedSongBinding.inflate(inflater, container, false)

        songs.apply {
            add(Song("Butter", "방탄소년단 (BTS)", 60, 0, false,"a", R.drawable.img_album_exp))
            add(Song("Lilac", "아이유 (IU)", 60, 0, false,"a", R.drawable.img_album_exp2))
            add(Song("Next Level", "에스파 (AESPA)",60, 0, false,"a", R.drawable.img_album_exp3))
            add(Song("Boy with Luv", "방탄소년단 (BTS)",60, 0, false,"a", R.drawable.img_album_exp4))
            add(Song("BBoom BBoom", "모모랜드 (MOMOLAND)",60, 0, false,"a", R.drawable.img_album_exp5))
            add(Song("Weekend", "태연 (Tae Yeon)",60, 0, false,"a", R.drawable.img_album_exp6))
            add(Song("Butter", "방탄소년단 (BTS)", 60, 0, false,"a", R.drawable.img_album_exp))
            add(Song("Lilac", "아이유 (IU)", 60, 0, false,"a", R.drawable.img_album_exp2))
            add(Song("Next Level", "에스파 (AESPA)",60, 0, false,"a", R.drawable.img_album_exp3))
            add(Song("Boy with Luv", "방탄소년단 (BTS)",60, 0, false,"a", R.drawable.img_album_exp4))
            add(Song("BBoom BBoom", "모모랜드 (MOMOLAND)",60, 0, false,"a", R.drawable.img_album_exp5))
            add(Song("Weekend", "태연 (Tae Yeon)",60, 0, false,"a", R.drawable.img_album_exp6))
        }

        val savedsongRVAdapter = SavedSongRVAdapter(songs)
        binding.savedSongRv.adapter = savedsongRVAdapter
        binding.savedSongRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        return binding.root
    }
}