package com.example.umc_flo

import android.graphics.Insets.add
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.postDelayed
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.umc_flo.databinding.FragmentHomeBinding
import com.google.gson.Gson


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: BannerVPAdapter
    private var albumDatas = ArrayList<Album>()

    private val slideDelay = 3000L
    private val sliderHandler = Handler(Looper.getMainLooper())

    private val sliderRunnable = object : Runnable {
        override fun run() {
            val nextItem = (binding.homePannelVp.currentItem + 1) % adapter.itemCount
            binding.homePannelVp.setCurrentItem(nextItem, true)
            sliderHandler.postDelayed(this, slideDelay)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

//        binding.homeAlbumImgIv1.setOnClickListener {
//            (context as MainActivity).supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.main_frm, AlbumFragment())
//                .commitAllowingStateLoss()
//        }
        albumDatas.apply {
            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp, null, "music_butter"))
            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2, null, "music_lilac"))
            add(Album("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3, null, "music_next"))
            add(Album("Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_exp4, null, "music_boy"))
            add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5, null, "music_bboom"))
            add(Album("Weekend", "태연 (Tae Yeon)", R.drawable.img_album_exp6, null, "music_flu"))
        }


        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL


        adapter = BannerVPAdapter(this)
        adapter.addFragment(PannelFragment(R.drawable.img_first_album_default))
        adapter.addFragment(PannelFragment(R.drawable.img_album_exp3))
        adapter.addFragment(PannelFragment(R.drawable.img_album_exp4))
        binding.homePannelVp.adapter = adapter
        binding.homePannelVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.circleIndicator.setViewPager(binding.homePannelVp)

        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)

        albumRVAdapter.setMyItemClickListener(object : AlbumRVAdapter.MyItemClickListener{
            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }

            override fun onPlayClick(album: Album) {
                (activity as MainActivity).setMiniPlayer(
                    Song(
                        title = album.title,
                        singer = album.singer,
                        second = 0,
                        playTime = 60,
                        isPlaying = true,
                        music = album.music
                    )
                )
            }
        })

        binding.homePannelVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, slideDelay)
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, slideDelay)
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }
}

private fun HomeFragment.changeAlbumFragment(album: Album) {
    (context as MainActivity).supportFragmentManager.beginTransaction()
        .replace(R.id.main_frm, AlbumFragment().apply {
            arguments = Bundle().apply {
                val gson = Gson()
                val albumJson = gson.toJson(album)
                putString("album", albumJson)
            }
        })
        .commitAllowingStateLoss()
}
