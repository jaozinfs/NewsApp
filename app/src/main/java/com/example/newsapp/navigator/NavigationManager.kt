package com.example.newsapp.navigator

import androidx.navigation.NavController
import com.example.common.R

typealias NavigationID = Int


data class NavigationFeature(var id: NavigationID? = null)

fun NavController.navigateFeature(scope: NavigationFeature.() -> Unit) {
    val navigationFeature = NavigationFeature()
    scope.invoke(navigationFeature)
    navigationFeature.id ?: return
    navigate(navigationFeature.id!!)
}