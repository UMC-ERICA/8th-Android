package com.example.umc_flo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class SavedSongFragment : Fragment() {
    lateinit var binding: FragmentLockerSavedsongBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLockerSavedsongBinding.inflate(inflater, container, false)
        return binding.root
    }



}