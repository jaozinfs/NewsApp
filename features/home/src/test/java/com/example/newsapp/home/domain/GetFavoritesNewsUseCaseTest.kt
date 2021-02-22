package com.example.newsapp.home.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.newsapp.home.data.MocksNews
import com.example.newsapp.home.domain.usecases.GetFavoritesNewsUseCase
import com.example.newsapp.test.TestCoroutinesRule
import com.example.newsapp.test.extensions.liveDataBuilder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.experimental.ExperimentalTypeInference

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
    fun tearDown() {
    }


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
    class Test {

        fun main() {
            listBuilder {
                add("Hello world")
            }
        }



        @OptIn(ExperimentalTypeInference::class)
        fun <T> listBuilder(@BuilderInference scope: ListBuilderScope<T>.() -> Unit) : List<T>{
            val mutbList = mutableListOf<T>()
            scope.invoke(ListBuilderScopeImpl(mutbList))
            return mutbList
        }



        
        @OptIn(ExperimentalTypeInference::class)
        class ListBuilderScopeImpl<T>(
            private val parent: MutableList<T>
        ) : ListBuilderScope<T >{
            override fun add(element: T){
                parent.add(element)
            }
        }


        interface ListBuilderScope<T>{
            fun add(element: T)
        }


    }

}