package com.example.newsapp.home.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.common.base.execute
import com.example.newsapp.home.data.MocksNews
import com.example.newsapp.home.domain.usecases.GetHighlightsNewsUseCase
import com.example.newsapp.home.domain.usecases.GetNewsUseCase
import com.example.newsapp.home.domain.usecases.FavoriteNewsUseCase
import com.example.newsapp.home.presentation.event.HomeEvents
import com.example.newsapp.home.presentation.state.HomeState
import com.example.newsapp.home.presentation.viewmodel.HomeViewModel
import com.example.newsapp.home.presentation.viewmodel.ReloadNewsManager
import com.example.newsapp.home.presentation.viewmodel.ReloadNewsManagerImpl
import com.example.newsapp.local.domain.RemoveSavedTokenUseCase
import com.example.newsapp.test.TestCoroutinesRule
import com.example.newsapp.test.extensions.spykKCollector
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
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
    private var reloadNewsManager: ReloadNewsManager = ReloadNewsManagerImpl()

    @RelaxedMockK
    private lateinit var getNewsUseCase: GetNewsUseCase

    @RelaxedMockK
    private lateinit var getHighlightsNewsUseCase: GetHighlightsNewsUseCase

    private lateinit var homeViewModel: HomeViewModel

    @RelaxedMockK
    private lateinit var stateObserver: Observer<HomeState>


    init {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Before
    fun setup() {
        homeViewModel = HomeViewModel(
            removeSavedTokenUseCase,
            getHighlightsNewsUseCase,
            reloadNewsManager,
            favoriteNewsUseCase,
            getNewsUseCase
        )
    }

    @After
    fun teardown() {
        homeViewModel.state.removeObserver(stateObserver)
    }

    @Test
    fun `when call event fetch highlight news should observe state`() = runBlockingTest {
        //given
        val expectedHighLightsNews = MocksNews.mocks
        //when
        coEvery { getHighlightsNewsUseCase.execute() } returns expectedHighLightsNews
        //then
        homeViewModel.handleEvent(HomeEvents.FetchHighLightNews)

        verify {
            stateObserver.onChanged(HomeState.FetchedHighLightsNews(expectedHighLightsNews))
        }
    }

    @Test
    fun `when call even logout_should useCase remove token call`() = runBlockingTest {
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
    fun `when call event favorite news should update state with saved`() = runBlockingTest {
        //given
        val expectedNews = MocksNews.mocks.first()

        //when
        coEvery { favoriteNewsUseCase.execute(any()) } returns FavoriteNewsUseCase.FavoriteState.Saved

        //then
        homeViewModel.handleEvent(HomeEvents.FavoriteNews(expectedNews))

        verify {
            stateObserver.onChanged(HomeState.NewsSavedFavorite)
        }
    }

    @InternalCoroutinesApi
    @Test
    fun `when call state should call onchange one time`() = runBlockingTest {
        //given
        val jobs = mutableListOf<Job>()
        val collector = spykKCollector<HomeState>{
            println("Test $it")
        }

        val expectedHighLightsNews = MocksNews.mocks

        //when
        coEvery { getHighlightsNewsUseCase.execute() } returns expectedHighLightsNews

        launch {
            homeViewModel.stateFlow.collect(collector)
        }.also {
            jobs.add(it)
        }
        homeViewModel.handleEvent(HomeEvents.FetchHighLightNews)
        //simulate recreate view lifecycle
        launch {
            homeViewModel.stateFlow.collect(collector)
        }.also {
            jobs.add(it)
        }

        coVerify(exactly = 1) {
            collector.invoke(HomeState.FetchedHighLightsNews(expectedHighLightsNews))
        }
        jobs.forEach { it.cancel() }
    }

    @Test
    fun `when call state should call onchansage one time`() = runBlockingTest {
        //given
        val jobs = mutableListOf<Job>()
        val mutableStateFlow = MutableSharedFlow<String>()


        launch {
            mutableStateFlow.emit("Teste")
        }.also {
            jobs.add(it)
        }
        launch {
            mutableStateFlow.collect {
                println("Teste 1 message is: $it")
            }
        }.also {
            jobs.add(it)
        }
        launch {
            mutableStateFlow.collect {
                println("Teste 2 message is: $it")
            }
        }.also {
            jobs.add(it)
        }

    }
}