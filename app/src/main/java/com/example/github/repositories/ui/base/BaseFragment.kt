package com.example.github.repositories.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.github.repositories.GitReposApp
import com.example.github.repositories.R
import com.example.github.repositories.ui.idlingResource.SimpleIdlingResource
import com.google.android.material.snackbar.Snackbar

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    protected val idlingResource: SimpleIdlingResource? by lazy { (activity?.applicationContext as GitReposApp).getIdlingResource() as SimpleIdlingResource? }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun showError(
        view: View,
        message: String,
        actionText: String = getString(R.string.label_retry),
        action: (View) -> Unit
    ) {
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction(actionText, action)
            .show()
    }
}