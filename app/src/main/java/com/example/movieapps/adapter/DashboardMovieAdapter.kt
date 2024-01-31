package com.example.movieapps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapps.data.moviedbapi.response.GenresItem
import com.example.movieapps.databinding.ItemCategoricalMovieListBinding

class DashboardMovieAdapter(
    private val data: List<GenresItem?>
) : RecyclerView.Adapter<DashboardMovieAdapter.SavingViewHolder>() {
    inner class SavingViewHolder(private val itemBinding: ItemCategoricalMovieListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(model: GenresItem?) {
            itemBinding.tvCategory.text=model?.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavingViewHolder {
        return SavingViewHolder(
            ItemCategoricalMovieListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: SavingViewHolder, position: Int) {
        holder.bind(data[position])
    }
}