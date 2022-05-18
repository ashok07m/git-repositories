package com.example.github.repositories.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github.repositories.databinding.FragmentMainBinding
import com.example.github.repositories.ui.adapters.RepositoryAdapter
import com.example.github.repositories.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            swipeRefresh.setOnRefreshListener { viewModel.refresh() }
            newsList.layoutManager = LinearLayoutManager(context)

            viewModel.repositories.observeForever {
                val adapter = RepositoryAdapter(it.take(20).toMutableList(), requireActivity())
                newsList.adapter = adapter
            }
        }
    }
}