package com.example.movieapps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapps.R
import com.example.movieapps.data.moviedbapi.response.MovieQueryItem
import com.example.movieapps.databinding.ItemMovieCoverBinding

class MoviewQueryResultAdapter(protected val onClickNav:(id:Int)->Unit) : PagingDataAdapter<MovieQueryItem, MoviewQueryResultAdapter.MovieViewHolder>(MovieComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieCoverBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class MovieViewHolder(private val binding: ItemMovieCoverBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieQueryItem) {
            binding.apply {
                Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/w500" + item.posterPath)
                    .centerCrop()
                    .placeholder(R.drawable.icon_home) // Replace with your placeholder drawable
                    .error(R.drawable.icon_home)
                    .into(ivMovieCover)
                    tvMovieTitle.text=item.title

            }
        }
    }

    object MovieComparator : DiffUtil.ItemCallback<MovieQueryItem>() {
        override fun areItemsTheSame(oldItem: MovieQueryItem, newItem: MovieQueryItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieQueryItem, newItem: MovieQueryItem): Boolean {
            return oldItem == newItem
        }
    }
}