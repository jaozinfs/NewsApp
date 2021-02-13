package com.example.newsapp.extensions

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.example.newsapp.utils.CommonDialogFragment

open class BaseFragment(private val layoutId: Int) : Fragment(layoutId) {
    private val dialog = CommonDialogFragment()

    fun showProgressDialog() {
        dialog.show(parentFragmentManager, null)
    }

    fun hideProgressDialog() {
        dialog.dismiss()
    }
}