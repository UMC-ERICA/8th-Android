package com.example.umc_flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_flo.databinding.ItemSongBinding

class SavedSongRVAdapter(private val songList: ArrayList<Song>): RecyclerView.Adapter<SavedSongRVAdapter.ViewHolder>() {


    private val songs = ArrayList<Song>()

    interface MyItemClickListener{
        fun onRemoveSong(songId: Song)

    }

    private lateinit var myItemClickListener: SavedSongRVAdapter.MyItemClickListener

    fun setMyItemClickListener(itemClickListener: SavedSongRVAdapter.MyItemClickListener) {
        myItemClickListener = itemClickListener
    }

    fun addSongs(songs: ArrayList<Song>) {
        this.songs.clear()
        this.songs.addAll(songs)
        notifyDataSetChanged()
    }

    private fun removeSong(position: Int){
        songs.removeAt(position)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SavedSongRVAdapter.ViewHolder {
        val binding: ItemSongBinding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedSongRVAdapter.ViewHolder, position: Int) {
        holder.bind(songList[position])
        holder.itemView.setOnClickListener{ myItemClickListener.onRemoveSong(songs[position]) }    }

    override fun getItemCount(): Int = songs.size


    inner class ViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.itemSongTitleTv.text = song.title
            binding.itemSongSingerTv.text = song.singer
            binding.itemSongCoverImgIv.setImageResource(song.coverImg!!)
        }
    }
}