package com.example.newsapp.register.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.common.base.BaseFragment
import com.example.newsapp.register.R
import com.example.newsapp.register.presentation.event.RegisterEvents
import com.example.newsapp.register.presentation.state.RegisterState
import com.example.newsapp.register.presentation.viewmodel.RegisterViewModel
import com.example.newsapp.security.UserTokenState
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.android.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment(R.layout.fragment_register) {

    private val registerViewModel: RegisterViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        observeViewModel()
    }

    private fun observeViewModel() = with(registerViewModel) {
        registerState.observe(viewLifecycleOwner, Observer {
            handleState(it)
        })
    }

    private fun setListeners() {
        bt_register.setOnClickListener {
            registerEvent()
        }
    }

    private fun registerEvent() {
        registerViewModel.handleEvent(
            RegisterEvents.Register(
                ed_email.text.toString(),
                ed_password.text.toString(),
                ed_name.text.toString(),
                ed_birth_date.text.toString()
            )
        )
    }

    private fun handleState(registerState: RegisterState) = when (registerState) {
        is RegisterState.Loading -> {
            showProgressDialog()
        }
        is RegisterState.RegisterSuccessfully -> {
            hideProgressDialog()
            Toast.makeText(requireContext(), "Deu bom", Toast.LENGTH_LONG).show()
        }

        is RegisterState.RegisterError -> {
            hideProgressDialog()
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
        }
    }


    private fun handleUserTokenState(userTokenState: UserTokenState) = when (userTokenState) {
        is UserTokenState.UserAuthenticated -> {

        }
        else -> {
        }
    }


}