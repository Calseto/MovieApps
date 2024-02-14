package com.example.movieapps.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapps.data.moviedbapi.response.GenresItem
import com.example.movieapps.data.moviedbapi.response.MovieItem
import com.example.movieapps.databinding.ItemCategoricalMovieListBinding
import com.example.movieapps.presentation.DashBoardFragmentDirections

class DashboardMovieAdapter(
    private val context: Context,
    private val data: List<GenresItem?>,
    private val onClickNav:(genreID:Int,genreName:String)->Unit
) : RecyclerView.Adapter<DashboardMovieAdapter.SavingViewHolder>() {

    private var innerData: MutableMap<Int, List<MovieItem?>?> = mutableMapOf()

    fun updateMovieRV(position: Int, newMovieList: List<MovieItem?>?) {
        innerData[position] = newMovieList
        notifyItemChanged(position)

    }

    inner class SavingViewHolder(private val itemBinding: ItemCategoricalMovieListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(model: GenresItem?, innerModel: List<MovieItem?>?) {
            itemBinding.tvCategory.text = model?.name?.uppercase()
            if (innerModel != null) {
                itemBinding.rvMovieItem.visibility= View.VISIBLE
                itemBinding.rvMovieItem.adapter = CategoricalMovieAdapter(context, innerModel){
                    val bundle= bundleOf("movieId" to it )
                    val action= DashBoardFragmentDirections.actionDashBoardFragmentToDetailMovieFragment().actionId
                    findNavController(itemView).navigate(action,bundle)
                }
            }else{
                itemBinding.rvMovieItem.visibility= View.GONE
            }

            itemBinding.tvShowMOre.setOnClickListener{
                if (model != null) {
                    if(model.id!=null && model.name!=null) {
                        onClickNav(model.id, model.name)
                    }
                }
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