package com.example.common.base

import androidx.fragment.app.Fragment
import com.example.common.CommonDialogFragment

open class BaseFragment(layoutId: Int) : Fragment(layoutId) {
    private val dialog = CommonDialogFragment()

    fun showProgressDialog() {
        dialog.show(requireFragmentManager(), null)
    }

    fun hideProgressDialog() {
        dialog.dismiss()
    }
}