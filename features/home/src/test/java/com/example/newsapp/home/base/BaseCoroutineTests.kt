package com.example.newsapp.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.newsapp.home.base.TestCoroutinesRule
import org.junit.Rule

abstract class BaseCoroutineTests  {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutinesRule()


}