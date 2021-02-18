package com.example.newsapp.home.presentation.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.common.base.BaseFragment
import com.example.common.exceptions.transformCaroucel
import com.example.common.extensions.flipModuleFlow
import com.example.newsapp.home.R
import com.example.newsapp.home.databinding.FragmentHomeBinding
import com.example.newsapp.home.domain.News
import com.example.newsapp.home.presentation.adapter.NewsAdapter
import com.example.newsapp.home.presentation.adapter.NewsHighLightAdapter
import com.example.newsapp.home.presentation.event.HomeEvents
import com.example.newsapp.home.presentation.state.HomeState
import com.example.newsapp.home.presentation.viewmodel.HomeViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject
import timber.log.Timber

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by inject()
    private val highLightsNewsAdapter =
        NewsHighLightAdapter()
    private val newsAdapter =
        NewsAdapter()

    private lateinit var fragmentHomeBinding: FragmentHomeBinding
    private var slideJob: Job? = null
    private var newsJob: Job? = null
    private val slideCallback = SlideHighLightsNewsCallback()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activeMenu()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        instantiateBinding(inflater, container)
        return fragmentHomeBinding.root
    }

    private fun instantiateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
    }

    private fun activeMenu() {
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupHighLightNews()
        setupNews()
        fetchHighLightsNews()
        getNews()

    }

    override fun onDestroy() {
        fragmentHomeBinding.vpCarouselNews.unregisterOnPageChangeCallback(slideCallback)
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu);
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.favorites ->             // do stuff
                goToFavoritesFragment()
        }
        return false
    }

    private fun goToFavoritesFragment() {
        findNavController().navigate(R.id.action_navigation_home_to_navigation_favorites)
    }

    private fun observeViewModel() = with(homeViewModel) {
        state.observe(viewLifecycleOwner, Observer {
            handleState(it)
        })
        favoritesNews.observe(viewLifecycleOwner, Observer {
            Timber.d(it.toString())
        })
    }

    private fun fetchHighLightsNews() {
        homeViewModel.handleEvent(HomeEvents.FetchHighLightNews)
    }

    private fun handleState(state: HomeState) = when (state) {
        is HomeState.Loading -> {
            showProgressDialog()
        }
        is HomeState.FetchedHighLightsNews -> {
            hideProgressDialog()
            updateHighLightNews(state.news)
        }
        is HomeState.Error -> {
            hideProgressDialog()
        }
        is HomeState.ReloadNews -> {
            getNews()
        }
        is HomeState.NewsRemovedFromFavorite -> showRemovedFavoriteNewsMessage()
        is HomeState.NewsSavedFavorite -> showSavedFavoriteNewsMessage()
    }

    private fun showSavedFavoriteNewsMessage() {
        Toast.makeText(
            requireContext(),
            getString(R.string.news_favorited_message),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showRemovedFavoriteNewsMessage() {
        Toast.makeText(
            requireContext(),
            getString(R.string.news_favorited_removed_message),
            Toast.LENGTH_LONG
        ).show()
    }


    private fun updateHighLightNews(newsList: List<News>) {
        highLightsNewsAdapter.submitList(newsList)
        startSlide()
    }

    private fun setupHighLightNews() = with(fragmentHomeBinding.vpCarouselNews) {
        transformCaroucel()
        registerOnPageChangeCallback(slideCallback)
        adapter = highLightsNewsAdapter
        configTabLayout()

    }

    private fun setupNews() {
        fragmentHomeBinding.rvNews.adapter = newsAdapter
        newsAdapter.favoriteClickListener = ::favoriteNews
    }

    private fun configTabLayout() {
        TabLayoutMediator(fragmentHomeBinding.intoTabLayout, fragmentHomeBinding.vpCarouselNews)
        { _, _ -> }.attach()
    }

    private fun startSlide() {
        slideJob?.cancel()
        slideJob = lifecycleScope.launchWhenCreated {
            flipModuleFlow(
                fragmentHomeBinding.vpCarouselNews.currentItem,
                highLightsNewsAdapter.itemCount
            )
                .collect { currentPosition ->
                    fragmentHomeBinding.vpCarouselNews.setCurrentItem(currentPosition, true)
                }
        }
    }

    private fun getNews() {
        newsJob?.cancel()
        newsJob = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            homeViewModel.newsListFlow.collectLatest {
                newsAdapter.submitData(it)
            }
        }
    }

    private fun favoriteNews(news: News) {
        homeViewModel.handleEvent(HomeEvents.FavoriteNews(news))
    }

    inner class SlideHighLightsNewsCallback : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            startSlide()
        }
    }
}