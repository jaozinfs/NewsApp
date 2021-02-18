package com.example.newsapp.home.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.example.newsapp.home.base.TestCoroutinesRule
import com.example.newsapp.home.data.MockHighLights
import com.example.newsapp.home.domain.News
import com.example.newsapp.home.domain.usecases.FavoriteNewsUseCase
import com.example.newsapp.home.domain.usecases.GetFavoritesNewsUseCase
import com.example.newsapp.home.presentation.event.FavoritesEvents
import com.example.newsapp.home.presentation.state.FavoritesStates
import com.example.newsapp.home.presentation.viewmodel.FavoritesNewsViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class FavoritesNewsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val testCoroutineRule = TestCoroutinesRule()

    @RelaxedMockK
    private lateinit var getFavoritesNewsUseCase: GetFavoritesNewsUseCase

    @RelaxedMockK
    private lateinit var favoriteNewsUseCase: FavoriteNewsUseCase

    private lateinit var favoriteNewsViewModel: FavoritesNewsViewModel

    @RelaxedMockK
    private lateinit var stateObserver: Observer<FavoritesStates>

    @RelaxedMockK
    private lateinit var newsObserver: Observer<List<News>>

    init {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Before
    fun setup() {
        coEvery { getFavoritesNewsUseCase.execute() } returns liveData { emit(MockHighLights.getMock()) }
        favoriteNewsViewModel = FavoritesNewsViewModel(
            getFavoritesNewsUseCase,
            favoriteNewsUseCase
        ).also {
            it.state.observeForever(stateObserver)
            it.favoritesNews.observeForever(newsObserver)
        }
    }

    @After
    fun tearDown() {
        with(favoriteNewsViewModel){
            state.removeObserver(stateObserver)
            favoritesNews.removeObserver(newsObserver)
        }
    }


    @Test
    fun `when call favorite news should return state`() {
        val expectedNews = MockHighLights.getMock().first()

        //when
        coEvery { favoriteNewsUseCase.execute(any()) } returns FavoriteNewsUseCase.FavoriteState.Saved

        //then
        favoriteNewsViewModel.handleEvent(FavoritesEvents.FavoriteNews(expectedNews))

        coVerify {
            favoriteNewsUseCase.execute(expectedNews)
        }
    }

    @Test
    fun `when observe news should change observer`() {
        val expectedNews = MockHighLights.getMock()

        //when
        //then
        verify {
            newsObserver.onChanged(expectedNews)
        }
    }
}