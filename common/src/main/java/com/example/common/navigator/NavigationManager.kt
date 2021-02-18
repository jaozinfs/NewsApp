package com.example.common.navigator

import android.content.Intent
import androidx.fragment.app.FragmentActivity


typealias NavigationID = Int

sealed class NavigatorFeatures(val classPackage: String) {
    object FEATURE_REGISTER :
        NavigatorFeatures("com.example.newsapp.register.presentation.view.RegisterActivity")

    object FEATURE_HOME : NavigatorFeatures("com.example.newsapp.home.presentation.HomeActivity")
    object FEATURE_LOGIN : NavigatorFeatures("com.example.newsapp.login.presentation.LoginActivity")
}

private const val packageName = "com.example.newsapp"

data class NavigationFeatureBuilder(var navigatorFeatures: NavigatorFeatures? = null)

fun FragmentActivity.navigateFeature(scope: NavigationFeatureBuilder.() -> Unit) {
    val builder = NavigationFeatureBuilder()
    scope.invoke(builder)
    builder.navigatorFeatures?.apply {
        val intent = Intent()
        intent.setClassName(
            packageName,
            classPackage
        )
        startActivity(intent)
    }

}