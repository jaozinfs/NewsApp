package com.example.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.common.security.TokenManager
import com.example.common.security.UserAuthentication
import org.koin.android.ext.android.inject
import timber.log.Timber


open class BaseAuthActivity(layoutId: Int) : AppCompatActivity(layoutId) {
    private val TAG = this@BaseAuthActivity::class.simpleName
    private val userTokenManager: TokenManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeToken()
    }

    private fun observeToken() {
        userTokenManager.tokenObservable.observe(this, Observer { token->
            checkAuthentication(token)
        })
    }

    private fun checkAuthentication(token: String?){
        when(userTokenManager.checkAuthenticationByToken(token)){
            is UserAuthentication.TokenNotAuthenticated ->  gotoLogin()
            is UserAuthentication.UserAuthenticated ->  autenticated()
        }
    }

    private fun gotoLogin() {
        Timber.tag(TAG).d("Sem login")
    }

    private fun autenticated(){
        Timber.tag(TAG).d("Com login")
    }
}