package com.example.newsapp.home.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.newsapp.test.extensions.liveDataBuilder
import com.example.newsapp.home.data.MocksNews
import com.example.newsapp.home.domain.usecases.GetFavoritesNewsUseCase
import com.example.newsapp.test.TestCoroutinesRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class GetFavoritesNewsUseCaseTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val testCoroutineRule = TestCoroutinesRule()

    @RelaxedMockK
    private lateinit var newsRepository: NewsRepository

    private lateinit var getFavoritesNewsUseCase: GetFavoritesNewsUseCase

    init {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Before
    fun setup() {
        getFavoritesNewsUseCase = GetFavoritesNewsUseCase(newsRepository)
    }

    @After
    fun tearDown() {}


    @Test
    fun `when call execute should return livedata with list emited`() = runBlocking {
        //given
        val expectedResponse = MocksNews.mocks
        val expectedLiveData = liveDataBuilder {
            emit(expectedResponse)
        }

        //when
        coEvery { newsRepository.getFavoritesNews() } returns expectedLiveData

        //then
        val favoritesNewsLiveData = getFavoritesNewsUseCase.execute()
        assertEquals(expectedResponse, favoritesNewsLiveData.value)
    }

}