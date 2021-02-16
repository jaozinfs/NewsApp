package com.example.newsapp.navigator

import androidx.navigation.NavController
import com.example.newsapp.R


typealias NavigationID = Int

object NavigatorFeatures {
    const val FEATURE_REGISTER: NavigationID = R.id.navigation_register
}

data class NavigationFeature(var id: NavigationID? = null)

fun NavController.navigateFeature(scope: NavigationFeature.() -> Unit) {
    val navigationFeature = NavigationFeature()
    scope.invoke(navigationFeature)
    navigationFeature.id ?: return
    navigate(navigationFeature.id!!)
}