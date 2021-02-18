package com.example.newsapp.home.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.common.base.BaseFragment
import com.example.newsapp.home.R
import com.example.newsapp.home.databinding.FragmentFavoritesBinding
import com.example.newsapp.home.domain.News
import com.example.newsapp.home.presentation.adapter.FavoritesNewsAdapter
import com.example.newsapp.home.presentation.event.FavoritesEvents
import com.example.newsapp.home.presentation.event.HomeEvents
import com.example.newsapp.home.presentation.viewmodel.FavoritesNewsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class FavoritesNewsFragment : BaseFragment(R.layout.fragment_favorites) {

    private val favoritesViewModel: FavoritesNewsViewModel by viewModel()
    private lateinit var fragmentFavoritesBinding: FragmentFavoritesBinding
    private val favoritesNewsAdapter = FavoritesNewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentFavoritesBinding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return fragmentFavoritesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupNewsList()
    }

    private fun setupNewsList() {
        favoritesNewsAdapter.favoriteClickListener = ::favoriteNews
        fragmentFavoritesBinding.rvNews.adapter = favoritesNewsAdapter
    }

    private fun observeViewModel() {
        favoritesViewModel.favoritesNews.observe(viewLifecycleOwner, Observer {
            updateList(it)
        })
    }

    private fun favoriteNews(news: News) {
        favoritesViewModel.handleEvent(FavoritesEvents.FavoriteNews(news))
    }

    private fun updateList(list: List<News>) {
        favoritesNewsAdapter.submitList(list)
    }

}