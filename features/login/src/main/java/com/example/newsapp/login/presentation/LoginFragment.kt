package com.example.newsapp.login.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.common.base.BaseFragment
import com.example.common.navigator.NavigatorFeatures
import com.example.common.navigator.navigateFeature
import com.example.newsapp.login.R
import com.example.newsapp.login.presentation.event.LoginEvents
import com.example.newsapp.login.presentation.state.LoginState
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.viewmodel.ext.android.viewModel


class LoginFragment : BaseFragment(R.layout.fragment_login) {
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        observeToken()
        setOnClickListeners()
    }

    private fun observeViewModel() {
        loginViewModel.state.observe(viewLifecycleOwner, Observer {
            handleStates(it)
        })
    }

    private fun observeToken() {
        loginViewModel.token.observe(viewLifecycleOwner, Observer {
           checkAuthentication()
        })
    }

    private fun setOnClickListeners() {
        bt_login.setOnClickListener {
            login()
        }
        bt_register.setOnClickListener {
            goToRegister()
        }
    }

    private fun login() {
        loginViewModel.handleEvent(
            LoginEvents.Login(
                ed_email.text.toString(),
                ed_password.text.toString()
            )
        )
    }

    private fun goToRegister() {
        activity?.navigateFeature {
            navigatorFeatures = NavigatorFeatures.FEATURE_REGISTER
        }
    }

    private fun handleStates(loginState: LoginState) = when (loginState) {
        LoginState.LoginSuccessfully -> {
            hideProgressDialog()
            redirectToHome()
        }
        LoginState.LoginError -> {
            hideProgressDialog()
            showError()
        }
        LoginState.Loading -> showProgressDialog()
        LoginState.UserAuthenticated -> redirectToHome()
    }

    private fun checkAuthentication() {
        loginViewModel.handleEvent(LoginEvents.CheckAuthentication)
    }

    private fun redirectToHome() {
        activity?.navigateFeature {
            navigatorFeatures = NavigatorFeatures.FEATURE_HOME
        }
        activity?.finish()
    }

    private fun showError() {
        Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
    }
}