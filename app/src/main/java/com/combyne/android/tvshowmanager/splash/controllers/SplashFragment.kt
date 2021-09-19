package com.combyne.android.tvshowmanager.splash.controllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.combyne.android.tvshowmanager.R
import com.combyne.android.tvshowmanager.databinding.FragmentSplashBinding

/**
 * A simple [Fragment] subclass.
 * Use the [SplashFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSplashBinding.inflate(inflater)
        binding.addMovieButton.setOnClickListener {
            findNavController().navigate(R.id.add_movies_bottom_dialog_fragment)
        }
        binding.showMoviesButton.setOnClickListener {
            findNavController().navigate(R.id.movies_fragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }

        return binding.root
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment DefaultFragment.
         */
        @JvmStatic
        fun newInstance() = SplashFragment()
    }
}