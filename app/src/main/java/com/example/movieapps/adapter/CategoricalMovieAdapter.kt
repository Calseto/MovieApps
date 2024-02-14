package com.example.movieapps.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapps.data.moviedbapi.response.GenresItem
import com.example.movieapps.data.moviedbapi.response.MovieItem
import com.example.movieapps.databinding.ItemCategoricalMovieListBinding
import com.example.movieapps.databinding.ItemMovieCoverBinding

class CategoricalMovieAdapter(
    private val context: Context,
    private val data: List<MovieItem?>,
    protected val onClickNav:(id:Int)->Unit
) : RecyclerView.Adapter<CategoricalMovieAdapter.CategoricalViewHolder>() {
    inner class CategoricalViewHolder(private val itemBinding: ItemMovieCoverBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(model: MovieItem?) {
            if(data.isNotEmpty()) {
                Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w500" + model?.posterPath)
                    .centerCrop()
                    .into(itemBinding.ivMovieCover)
                itemBinding.tvMovieTitle.visibility = View.GONE
                itemBinding.view.visibility=View.GONE
                itemBinding.itemMovieLayout.setOnClickListener {
                    model?.id?.let { it1 -> onClickNav.invoke(it1) }
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoricalViewHolder {
        return CategoricalViewHolder(
            ItemMovieCoverBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CategoricalViewHolder, position: Int) {
        holder.bind(data[position])
    }
}