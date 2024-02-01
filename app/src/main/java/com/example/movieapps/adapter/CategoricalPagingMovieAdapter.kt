package com.example.movieapps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapps.data.moviedbapi.response.MovieItem
import com.example.movieapps.databinding.ItemMovieCoverBinding

class CategoricalPagingMovieAdapter  : PagingDataAdapter<MovieItem, CategoricalPagingMovieAdapter.MovieViewHolder>(MovieComparator) {

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

        fun bind(movieItem: MovieItem) {
            binding.apply {
                Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/w500" + movieItem.posterPath)
                    .centerCrop()
                    .into(ivMovieCover)
                tvMovieTitle.text = movieItem.title
            }
        }
    }

    object MovieComparator : DiffUtil.ItemCallback<MovieItem>() {
        override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
            return oldItem == newItem
        }
    }
}