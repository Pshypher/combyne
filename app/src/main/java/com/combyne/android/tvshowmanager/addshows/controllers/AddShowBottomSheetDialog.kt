package com.combyne.android.tvshowmanager.addshows.controllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.combyne.android.tvshowmanager.R
import com.combyne.android.tvshowmanager.addshows.domain.Show
import com.combyne.android.tvshowmanager.databinding.BottomSheetTvShowEntryBinding
import com.combyne.android.tvshowmanager.di.ServiceLocator
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

class AddShowBottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetTvShowEntryBinding

    private lateinit var viewModel: AddShowViewModel
    private lateinit var viewModelFactory: AddShowViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_tv_show_entry, container, false)

        viewModelFactory = AddShowViewModelFactory(
            requireActivity().application,
            ServiceLocator.provideAddShowUseCase(),
            ServiceLocator.provideValidateEntryUseCase()
        )
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddShowViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            addMovieButton.setOnClickListener {
                viewModel.addShow(Show().apply {
                    title = titleEditText.text.toString().trim()
                    date = releaseDateEditText.text.toString().trim()
                    season = seasonEditText.text.toString().trim()
                })
            }
        }

        viewModel.missingEntryFieldEvent.observe(viewLifecycleOwner) { invalidEntry ->
            invalidEntry.getContentIfNotHandled()?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        viewModel.dismissBottomDialogEvent.observe(viewLifecycleOwner) { dismiss ->
            dismiss.getContentIfNotHandled()?.let {
                if (it) {
                    dismiss()
                    findNavController().navigate(R.id.all_shows_fragment)
                }
            }

        }
    }
}