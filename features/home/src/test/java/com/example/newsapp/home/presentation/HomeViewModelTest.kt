package com.example.newsapp.home.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.common.base.TestCoroutinesRule
import com.example.newsapp.home.data.MockHighLights
import com.example.newsapp.home.data.network.usecase.GetHighlightsNewsUseCase
import com.example.newsapp.home.data.network.usecase.GetNewsUseCase
import com.example.newsapp.home.domain.usecases.FavoriteNewsUseCase
import com.example.newsapp.home.presentation.state.HomeState
import com.example.newsapp.home.presentation.viewmodel.HomeViewModel
import com.example.newsapp.home.presentation.viewmodel.ReloadNewsManager
import com.example.newsapp.home.presentation.viewmodel.ReloadNewsManagerImpl
import com.example.newsapp.local.domain.RemoveSavedTokenUseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.time.ExperimentalTime
import kotlin.time.seconds


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class HomeViewModelTest  {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutinesRule()


    @MockK
    private lateinit var removeSavedTokenUseCase: RemoveSavedTokenUseCase

    @MockK
    private lateinit var getHighlightsNewsUseCase: GetHighlightsNewsUseCase

    private var reloadNewsManager: ReloadNewsManager = ReloadNewsManagerImpl()

    @MockK
    private lateinit var favoriteNewsUseCase: FavoriteNewsUseCase

    @MockK
    private lateinit var getNewsUseCase: GetNewsUseCase

    private lateinit var homeViewModel: HomeViewModel

    init {
        MockKAnnotations.init(this, relaxUnitFun = true)
        homeViewModel = HomeViewModel(
            removeSavedTokenUseCase,
            getHighlightsNewsUseCase,
            reloadNewsManager,
            favoriteNewsUseCase,
            getNewsUseCase
        )
    }

    @ExperimentalTime
    @Test
    fun `when_init_class_should_observe_refresh_news_flow`() = testCoroutineRule.runBlockingTest {
        val stateObserver = mockk<Observer<HomeState>>(relaxed = true)
        val expectedDuration = 30.seconds
        homeViewModel.state.observeForever(stateObserver)
        //mock
        coEvery { getHighlightsNewsUseCase.execute(null) } returns MockHighLights.getMock()

        advanceTimeBy(30_000)
        verify {
            stateObserver.onChanged(HomeState.Loading)
            stateObserver.onChanged(HomeState.FetchedHighLightsNews(any()))
            stateObserver.onChanged(HomeState.ReloadNews)
        }
    }
}