package com.combyne.android.tvshowmanager.movies.controller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.combyne.android.tvshowmanager.R
import com.combyne.android.tvshowmanager.network.Resource.Status.*
import com.combyne.android.tvshowmanager.databinding.FragmentMoviesBinding
import com.combyne.android.tvshowmanager.di.ServiceLocator

/**
 * A simple [Fragment] subclass.
 * Use the [MoviesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding

    private lateinit var viewModel: MoviesViewModel
    private lateinit var viewModelFactory: MoviesViewModelFactory

    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false)
        viewModelFactory = MoviesViewModelFactory(ServiceLocator.provideQueryMoviesUseCase())
        viewModel = ViewModelProvider(this, viewModelFactory).get(MoviesViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with(binding.allShowsRecyclerView) {
            setHasFixedSize(false)
            movieAdapter = MovieAdapter(mutableListOf())
            this.adapter = movieAdapter


            movieAdapter.onEndOfListReached = { viewModel.channel.trySend(Unit).isSuccess }
        }

        viewModel.addShow.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { add ->
                if (add) {
                    findNavController().navigate(R.id.add_shows_bottom_dialog_fragment)
                }
            }
        }

        viewModel.endOfList.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { endOfList ->
                if (endOfList) {
                    movieAdapter.onEndOfListReached = null
                }
            }
        }

        viewModel.result.observe(viewLifecycleOwner) { result ->
            result?.let {
                // submit data to adapter if there appears to be any
                it.data?.let { list ->
                    with (movieAdapter) {
                        movies.addAll(list)
                        notifyItemRangeInserted(movies.lastIndex, list.size)
                    }
                }

                when (it.status) {
                    LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.allShowsRecyclerView.visibility = View.GONE
                    }
                    ERROR -> findNavController().navigate(R.id.default_fragment)

                    SUCCESS -> {
                        if (it.data?.isEmpty() == true) {
                            findNavController().navigate(R.id.default_fragment)
                        }
                        else {
                            binding.progressBar.visibility = View.GONE
                            binding.allShowsRecyclerView.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment MovieListFragment.
         */
        @JvmStatic
        fun newInstance() = MoviesFragment()
    }
}