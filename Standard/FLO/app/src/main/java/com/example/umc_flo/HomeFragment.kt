package com.example.umc_flo

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.umc_flo.databinding.FragmentHomeBinding
import com.google.gson.Gson

class HomeFragment :Fragment() {

    interface AlbumSelectedListener {
        fun onAlbumSelected(album: Album)
    }

    private lateinit var songDB : SongDatabase
    private lateinit var albumSelectedListener: AlbumSelectedListener

    lateinit var binding: FragmentHomeBinding
    lateinit var adapter: BannerVPAdapter
    private var albumDatas = ArrayList<Album>()


    val slideDelay = 2500
    val sliderHandler = Handler(Looper.getMainLooper())

    private val sliderRunnable = object : Runnable {
        override fun run() {
            val nextItem = (binding.homePannelVp.currentItem + 1) % adapter.itemCount
            binding.homePannelVp.setCurrentItem(nextItem, true)
            sliderHandler.postDelayed(this, slideDelay.toLong())
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AlbumSelectedListener) {
            albumSelectedListener = context
        } else {
            throw RuntimeException("$context must implement AlbumSelectedListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                          container: ViewGroup?,
                          savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        songDB = SongDatabase.getInstance(requireContext())!!
        albumDatas.addAll(songDB.albumDao().getAlbums())

//        binding.homeAlbumImg1Iv.setOnClickListener {
//            (context as MainActivity).supportFragmentManager.beginTransaction()
//                .replace(R.id.main_fragment_container, AlbumFragment())
//                .commitAllowingStateLoss()
//        }
//        albumDatas.apply {
//            add(Album("Butter","방탄소년단(BTS)", R.drawable.img_album_exp))
//            add(Album("LILAC","아이유(IU)", R.drawable.img_album_exp2))
//            add(Album("Next Level","에스파(AESPA)", R.drawable.img_album_exp3))
//            add(Album("Boy with Luv","방탄소년단(BTS)", R.drawable.img_album_exp4))
//            add(Album("BBoom BBoom","모모랜드(MOMOLAND)", R.drawable.img_album_exp5))
//            add(Album("Weekend","태연(Tae Yeon", R.drawable.img_album_exp6))
//        }

        val albumDB = SongDatabase.getInstance(requireContext())!!
        val albumDao = albumDB.albumDao()

        if (albumDao.getAlbums().isEmpty()) {
            albumDao.insert(Album(1, "Butter", "방탄소년단(BTS)", R.drawable.img_album_exp))
            albumDao.insert(Album(2, "LILAC", "아이유(IU)", R.drawable.img_album_exp2))
            albumDao.insert(Album(3, "Next Level", "에스파(AESPA)", R.drawable.img_album_exp3))
            albumDao.insert(Album(4, "Boy with Luv", "방탄소년단(BTS)", R.drawable.img_album_exp4))
            albumDao.insert(Album(5, "BBoom BBoom", "모모랜드(MOMOLAND)", R.drawable.img_album_exp5))
            albumDao.insert(Album(6, "Weekend", "태연(Tae Yeon)", R.drawable.img_album_exp6))
        }

        val albumDatas = ArrayList(albumDao.getAlbums())
        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        albumRVAdapter.setMyItemClickListener(object : AlbumRVAdapter.MyItemClickListener {
            override fun onItemClick(album: Album) {
                albumSelectedListener.onAlbumSelected(album)
                val gson = Gson()
                val albumJson = gson.toJson(album)

                val albumFragment = AlbumFragment()
                val bundle = Bundle()
                bundle.putString("album", albumJson)
                albumFragment.arguments = bundle

                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, albumFragment)
                    .commitAllowingStateLoss()
            }

            override fun onRemoveAlbum(position: Int) {
                albumRVAdapter.removeItem(position)
            }
        })

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

        binding.homePannelVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, slideDelay.toLong())
            }
        })

        return binding.root
    }
}