package com.example.news.ui.tabs.bookmarks

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.news.databinding.BookmarksListItemBinding

class BookmarksAdapter(
    private val listener: OnItemClickListenerBookmarks
) : RecyclerView.Adapter<BookmarksAdapter.BookmarksViewHolder>() {

    private lateinit var binding: BookmarksListItemBinding
    private var newsList = listOf<BookmarksModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarksViewHolder {
        binding =
            BookmarksListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarksViewHolder, position: Int) {
        holder.bind(newsList[position])

        holder.itemView.setOnClickListener {
            val url = newsList[position].url
            url?.let { listener.onItemNewsClick(it) }
        }

        holder.itemView.findViewById<ImageButton>(R.id.iconShare).setOnClickListener {
            val url = newsList[position].url
            url?.let { listener.onShareImageClick(it) }
        }

        holder.itemView.findViewById<ImageButton>(R.id.deleteButton).setOnClickListener {
            listener.onItemDelete(newsList[position].id)
        }
    }

    override fun getItemCount(): Int = newsList.size

    fun addData(list: List<BookmarksModel>) {
        newsList = list
        notifyDataSetChanged()
    }


    class BookmarksViewHolder(private val binding: BookmarksListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(current: BookmarksModel) {
            binding.newsTitle.text = current.title

            Glide.with(binding.newsImage.context)
                .load(current.urlToImage)
                .error(R.drawable.image_icon)
                .into(binding.newsImage)
        }
    }

    interface OnItemClickListenerBookmarks {
        fun onItemNewsClick(url: String)
        fun onShareImageClick(url: String)
        fun onItemDelete(bookmarksId: String)
    }
}