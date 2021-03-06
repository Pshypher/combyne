package com.combyne.android.tvshowmanager.addmovie.controllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.combyne.android.tvshowmanager.R
import com.combyne.android.tvshowmanager.addmovie.domain.Movie
import com.combyne.android.tvshowmanager.application.TVShowApplication
import com.combyne.android.tvshowmanager.databinding.BottomSheetMovieEntryBinding
import com.combyne.android.tvshowmanager.network.Resource.Status.LOADING
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

class AddMovieBottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetMovieEntryBinding

    private val viewModel by viewModels<AddMovieViewModel> { viewModelFactory }
    private lateinit var viewModelFactory: AddMovieViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_movie_entry, container, false)

        val application = requireActivity().application as TVShowApplication
        viewModelFactory =
            AddMovieViewModelFactory(application, application.mutator, application.validator)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            addMovieButton.setOnClickListener {
                viewModel?.addMovie(Movie().apply {
                    title = titleEditText.text.toString().trim()
                    releaseDate = releaseDateEditText.text.toString().trim()
                    season = seasonEditText.text.toString().trim()
                })
            }
        }

        viewModel.missingEntryFieldEvent.observe(viewLifecycleOwner) { invalidEntry ->
            invalidEntry.getContentIfNotHandled()?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
                    .show()
                dismiss()
            }
        }

        viewModel.dismissBottomDialogEvent.observe(viewLifecycleOwner) { dismiss ->
            dismiss.getContentIfNotHandled()?.let {
                if (it) {
                    dismiss()
                    findNavController().navigate(R.id.movies_fragment)
                }
            }

        }

        viewModel.status.observe(viewLifecycleOwner) { status ->
            status.getContentIfNotHandled()?.also {
                when (it) {
                    LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.formFieldLayout.visibility = View.INVISIBLE
                    }
                    else -> {
                        binding.progressBar.visibility = View.GONE
                        binding.formFieldLayout.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}