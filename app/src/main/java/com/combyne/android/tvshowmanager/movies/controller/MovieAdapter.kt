package com.combyne.android.tvshowmanager.movies.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.combyne.android.tvshowmanager.R
import com.combyne.android.tvshowmanager.databinding.MovieListItemBinding
import com.combyne.android.tvshowmanager.movies.domain.Movie

class MovieAdapter(val movies: MutableList<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var onEndOfListReached: (() -> Unit)? = null

    class MovieViewHolder(private val binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.movie = movie
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MovieViewHolder {
                val binding: MovieListItemBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.movie_list_item,
                    parent,
                    false
                )
                return MovieViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
        if (position == movies.size - 1) onEndOfListReached?.invoke()
    }

    override fun getItemCount() = movies.size
}