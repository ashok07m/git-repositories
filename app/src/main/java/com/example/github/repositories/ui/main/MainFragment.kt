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
        val repositoryAdapter = RepositoryAdapter(::onItemClicked)

        with(binding) {
            swipeRefresh.setOnRefreshListener { viewModel.fetchItems(isForceFetch = true) }

            newsList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = repositoryAdapter
            }

            viewModel.repositories.observe(viewLifecycleOwner) {
                repositoryAdapter.submitList(it)
            }
        }
    }
}