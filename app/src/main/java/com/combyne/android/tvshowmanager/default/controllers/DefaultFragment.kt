package com.combyne.android.tvshowmanager.default.controllers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.combyne.android.tvshowmanager.R

/**
 * A simple [Fragment] subclass.
 * Use the [DefaultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DefaultFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_default, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment DefaultFragment.
         */
        @JvmStatic
        fun newInstance() = DefaultFragment()
    }
}