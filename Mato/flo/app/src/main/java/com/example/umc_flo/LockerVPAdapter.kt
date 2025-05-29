package com.example.umc_flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flo.SavedAlbumFragment

class LockerVPAdapter (fragment : Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int  = 4

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> LikeSongFragment()
            1 -> SavedSongFragment()
            2 -> MusicFileFragment()
            else -> SavedAlbumFragment()

        }
    }
}