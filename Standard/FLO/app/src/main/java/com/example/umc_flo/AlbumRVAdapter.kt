package com.example.umc_flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_flo.databinding.ItemAlbumBinding

class AlbumRVAdapter(private val albumList: ArrayList<Album>):
    RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(album: Album)
        fun onRemoveAlbum(position: Int)
    }


        private var myItemClickListener: MyItemClickListener? = null
        fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
            myItemClickListener = itemClickListener
        }

        fun addItem(album: Album) {
            albumList.add(album)
            notifyDataSetChanged()
        }

        fun removeItem(position: Int) {
            albumList.removeAt(position)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumRVAdapter.ViewHolder {
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumRVAdapter.ViewHolder, position: Int) {
        holder.bind(albumList[position])
        holder.itemView.setOnClickListener{ myItemClickListener?.onItemClick(albumList[position]) }
//        holder.binding.itemAlbumTitleTv.setOnClickListener { myItemClickListener.onRemoveAlbum(position) }

    }


    override fun getItemCount(): Int = albumList.size

    inner class ViewHolder(val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)
            binding.itemAlbumPlayImgIv.setOnClickListener {
                myItemClickListener?.onItemClick(album)
            }
        }

    }

}