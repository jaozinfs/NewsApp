package com.example.newsapp.home.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.newsapp.home.base.TestCoroutinesRule
import com.example.newsapp.home.data.MockHighLights
import com.example.newsapp.home.data.network.usecase.GetHighlightsNewsUseCase
import com.example.newsapp.home.data.network.usecase.GetNewsUseCase
import com.example.newsapp.home.domain.NewsRepository
import com.example.newsapp.home.domain.usecases.FavoriteNewsUseCase
import com.example.newsapp.home.presentation.event.HomeEvents
import com.example.newsapp.home.presentation.state.HomeState
import com.example.newsapp.home.presentation.viewmodel.HomeViewModel
import com.example.newsapp.home.presentation.viewmodel.ReloadNewsManager
import com.example.newsapp.home.presentation.viewmodel.ReloadNewsManagerImpl
import com.example.newsapp.local.domain.RemoveSavedTokenUseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val testCoroutineRule = TestCoroutinesRule()


    @MockK
    private lateinit var removeSavedTokenUseCase: RemoveSavedTokenUseCase

    @MockK
    private lateinit var favoriteNewsUseCase: FavoriteNewsUseCase

    @RelaxedMockK
    private lateinit var newsRepository: NewsRepository

    private var reloadNewsManager: ReloadNewsManager = ReloadNewsManagerImpl()
    private lateinit var getNewsUseCase: GetNewsUseCase
    private lateinit var getHighlightsNewsUseCase: GetHighlightsNewsUseCase
    private lateinit var homeViewModel: HomeViewModel

    @RelaxedMockK
    private lateinit var stateObserver: Observer<HomeState>


    init {
        MockKAnnotations.init(this, relaxUnitFun = true)
        getNewsUseCase = GetNewsUseCase(newsRepository)
        getHighlightsNewsUseCase = GetHighlightsNewsUseCase(newsRepository)
        homeViewModel = HomeViewModel(
            removeSavedTokenUseCase,
            getHighlightsNewsUseCase,
            reloadNewsManager,
            favoriteNewsUseCase,
            getNewsUseCase
        )
    }


    @Test
    fun `when_call_event_fetch_highlight_news_should_observe_state`() =
        testCoroutineRule.testDispatcher.runBlockingTest {
            //given
            val expectedHighLightsNews = MockHighLights.getMock()
            homeViewModel.state.observeForever(stateObserver)
            //when
            coEvery { newsRepository.getHighlightsNews() } returns expectedHighLightsNews


            //then
            homeViewModel.handleEvent(HomeEvents.FetchHighLightNews)
            verify {
                stateObserver.onChanged(HomeState.FetchedHighLightsNews(expectedHighLightsNews))
            }
        }

    @Test
    fun `when_call_event_logout_should_usecase_remove_token_call`() =
        testCoroutineRule.testDispatcher.runBlockingTest {
            //given
            //when
            coEvery { removeSavedTokenUseCase.execute() } just runs
            //then
            homeViewModel.handleEvent(HomeEvents.Logout)
            coVerify {
                removeSavedTokenUseCase.execute()
            }
        }

    @Test
    fun `when_call_event_favorite_news_should_update_state_with_saved`() =
        testCoroutineRule.testDispatcher.runBlockingTest {
            //given
            val expectedNews = MockHighLights.getMock().first()

            //when
            coEvery { favoriteNewsUseCase.execute(any()) } returns FavoriteNewsUseCase.FavoriteState.Saved

            //then
            homeViewModel.handleEvent(HomeEvents.FavoriteNews(expectedNews))
            coVerify {
                stateObserver.onChanged(HomeState.NewsSavedFavorite)
            }
        }
}