package com.example.umc_flo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.umc_flo.databinding.FragmentHomeBinding

class HomeFragment :Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var adapter: BannerVPAdapter

    val slideDelay = 2500
    val sliderHandler = Handler(Looper.getMainLooper())

    private val sliderRunnable = object : Runnable {
        override fun run() {
            val nextItem = (binding.homePannelVp.currentItem + 1) % adapter.itemCount
            binding.homePannelVp.setCurrentItem(nextItem, true)
            sliderHandler.postDelayed(this, slideDelay.toLong())
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                          container: ViewGroup?,
                          savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.homeAlbumImg1Iv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, AlbumFragment())
                .commitAllowingStateLoss()
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

        binding.homePannelVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, slideDelay.toLong())
            }
        })

        return binding.root
    }
}