package com.example.newsapp.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.newsapp.home.presentation.viewmodel.HomeViewModel
import org.koin.android.ext.android.inject

class HomeFragment : Fragment(R.layout.fragment_home){

    private val homeViewModel: HomeViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getHighLightNews()
    }
}