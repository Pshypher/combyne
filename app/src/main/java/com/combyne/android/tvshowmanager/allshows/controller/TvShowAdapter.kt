package com.combyne.android.tvshowmanager.allshows.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.combyne.android.tvshowmanager.R
import com.combyne.android.tvshowmanager.allshows.domain.TvShow
import com.combyne.android.tvshowmanager.databinding.TvShowListItemBinding

class TvShowAdapter(val shows: MutableList<TvShow>) : RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>() {

    class TvShowViewHolder(private val binding: TvShowListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(show: TvShow) {
            binding.tvShow = show
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TvShowViewHolder {
                val binding: TvShowListItemBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.tv_show_list_item,
                    parent,
                    false
                )
                return TvShowViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        return TvShowViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        holder.bind(shows[position])
    }

    override fun getItemCount() = shows.size
}