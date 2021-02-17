package com.example.newsapp.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.common.TokenManager
import com.example.newsapp.home.presentation.NewsAdapter
import com.example.newsapp.home.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.component.inject
import timber.log.Timber

class HomeFragment : Fragment(R.layout.fragment_home){

    private val homeViewModel: HomeViewModel by inject()
    private val newsAdapter = NewsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupAdapter()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            homeViewModel.newsListFlow.collectLatest {
                newsAdapter.submitData(it)
            }
        }
    }

    private fun setupAdapter() {

    }
}