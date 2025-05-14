package com.example.roomdbpractice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.roomdbpractice.databinding.ListItemBinding

class ProfileAdapter(
    private val context: Context,
    private val list: List<Profile>
) : BaseAdapter() {

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ListItemBinding


        if (convertView == null) {
            binding = ListItemBinding.inflate(LayoutInflater.from(context), parent, false)
            binding.root.tag = binding
        } else {
            binding = convertView.tag as ListItemBinding
        }

        val profile = list[position]
        binding.nameText.text = profile.name
        binding.detailText.text = "나이: ${profile.age}, 전화번호: ${profile.phone}"

        return binding.root
    }
}


