package com.example.github.repositories.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github.repositories.GitReposApp
import com.example.github.repositories.core.domain.Repository
import com.example.github.repositories.databinding.FragmentMainBinding
import com.example.github.repositories.ui.adapters.RepositoryAdapter
import com.example.github.repositories.ui.base.BaseFragment
import com.example.github.repositories.ui.base.ViewStateResult
import com.example.github.repositories.ui.idlingResource.SimpleIdlingResource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repositoryAdapter = RepositoryAdapter(::onItemClicked)
        viewModel.fetchItems()
        with(binding) {
            swipeRefresh.setOnRefreshListener { fetchRepos() }

            reposList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = repositoryAdapter
            }

            viewModel.viewStateResult.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is ViewStateResult.Loading -> {
                        if (state.isLoading) {
                            idlingResource?.setIdleState(false)
                            viewProgress.progressBar.visibility = View.VISIBLE
                        } else {
                            swipeRefresh.isRefreshing = false
                            viewProgress.progressBar.visibility = View.GONE
                        }
                    }
                    is ViewStateResult.Success<*> -> {
                        val data = state.data
                        if (data is List<*>) {
                            repositoryAdapter.submitList(data as List<Repository>)
                            idlingResource?.setIdleState(true)
                        }
                    }
                    is ViewStateResult.Error -> {
                        showError(
                            view = binding.coordinatorLayout,
                            message = state.errorMsg,
                            action = { fetchRepos() })
                    }
                }
            }
        }
    }

    /**
     * Navigates to [DetailFragment]
     */
    private fun onItemClicked(repository: Repository) {
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(repository)
        findNavController().navigate(action)
    }

    private fun fetchRepos() {
        viewModel.fetchItems(isForceFetch = true)
    }
}