package com.example.movieapps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapps.data.moviedbapi.response.MovieItem
import com.example.movieapps.databinding.ItemMovieCoverBinding

class CategoricalPagingMovieAdapter(
    protected val onClickNav:(id:Int)->Unit
)  : PagingDataAdapter<MovieItem, CategoricalPagingMovieAdapter.MovieViewHolder>(MovieComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieCoverBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem,onClickNav)
        }
    }

    class MovieViewHolder(private val binding: ItemMovieCoverBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movieItem: MovieItem,onClickNav:(id:Int)->Unit) {
            binding.apply {
                Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/w500" + movieItem.posterPath)
                    .centerCrop()
                    .into(ivMovieCover)
                tvMovieTitle.text = movieItem.title
                binding.itemMovieLayout.setOnClickListener {
                    movieItem.id?.let { it1 -> onClickNav.invoke(it1) }
                }
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