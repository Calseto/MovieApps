package com.example.movieapps.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapps.data.moviedbapi.response.GenresItem
import com.example.movieapps.data.moviedbapi.response.MovieItem
import com.example.movieapps.databinding.ItemCategoricalMovieListBinding
import java.security.PrivateKey

class DashboardMovieAdapter(
    private val context: Context,
    private val data: List<GenresItem?>,
    private val onClickNav:(genreID:Int)->Unit
) : RecyclerView.Adapter<DashboardMovieAdapter.SavingViewHolder>() {

    private var innerData: MutableMap<Int, List<MovieItem?>?> = mutableMapOf()

    fun updateMovieRV(position: Int, newMovieList: List<MovieItem?>?) {
        innerData[position] = newMovieList
        notifyItemChanged(position)

    }

    inner class SavingViewHolder(private val itemBinding: ItemCategoricalMovieListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(model: GenresItem?, innerModel: List<MovieItem?>?) {
            itemBinding.tvCategory.text = model?.name
            if (innerModel != null) {
                itemBinding.rvMovieItem.visibility= View.VISIBLE
                itemBinding.rvMovieItem.adapter = CategoricalMovieAdapter(context, innerModel)
            }else{
                itemBinding.rvMovieItem.visibility= View.GONE
            }

            itemBinding.tvShowMOre.setOnClickListener{
                model?.id?.let {
                    onClickNav.invoke(it) }
            }

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
            holder.bind(data[position], innerData[position])
    }
}