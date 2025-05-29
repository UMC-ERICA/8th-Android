package com.example.umc_flo


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_flo.Song
import com.example.umc_flo.databinding.ItemSongBinding


class LikedSongRVAdapter(
    private val songs: ArrayList<Song>,
    private val onClickSong: (Song) -> Unit,
    private val onToggleLike: (Song) -> Unit
) : RecyclerView.Adapter<LikedSongRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = songs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songs[position])
    }

    inner class ViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.itemSongImgIv.setImageResource(song.coverImg!!)
            binding.itemSongTitleTv.text = song.title
            binding.itemSongSingerTv.text = song.singer

            binding.root.setOnClickListener {
                onClickSong(song)
            }

            // 좋아요 취소 버튼
            binding.itemSongMoreIv.setOnClickListener {
                onToggleLike(song)
                removeSong(adapterPosition)
            }
        }
    }

    private fun removeSong(position: Int) {
        songs.removeAt(position)
        notifyItemRemoved(position)
    }
}
