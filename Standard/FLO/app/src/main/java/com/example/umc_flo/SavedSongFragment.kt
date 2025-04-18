package com.example.umc_flo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.umc_flo.databinding.FragmentSavedSongBinding
import com.example.umc_flo.databinding.FragmentVideoBinding

class SavedSongFragment : Fragment() {
    lateinit var binding : FragmentSavedSongBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedSongBinding.inflate(inflater, container, false)
        return binding.root
    }
}