package com.example.umc_flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.umc_flo.databinding.ItemSongBinding

class SavedSongRVAdapter(): RecyclerView.Adapter<SavedSongRVAdapter.ViewHolder>() {
    private val songs = ArrayList<Song>()
    interface MyItemClickListener{

    }
    private lateinit var mItemClickListener : MyItemClickListener

    fun setMyItemClickListener(itemClickListener: AlbumRVAdapter.MyItemClickListener){
        mItemClickListener = itemClickListener
    }
}

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SavedSongRVAdapter.ViewHolder {
    val binding: ItemSongBinding = ItemSongBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

    return ViewHolder(binding)
    }

    override fun getItemCount(): Int = songs.size()

    override fun onBindViewHolder(holder: SavedSongRVAdapter.ViewHolder, position: Int) {
    holder.bind(songs[position])
    holder.binding.itemSongMoreIv.setOnClickListener {
        mItemClickListener.onRemoveSong(songs[position].id)

    }

    inner class ViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(song: Song){
            binding.itemSongImgIv.setImageResource(song.coverImg!!)
            binding.itemSongTitleTv.text = song.title
            binding.itemSongSingerTv.text = song.singer
        }
    }
}
