package com.example.github.repositories.ui.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github.repositories.R
import com.example.github.repositories.core.domain.Owner
import com.example.github.repositories.core.domain.Repository
import com.example.github.repositories.core.domain.User
import com.example.github.repositories.databinding.FragmentUserBinding
import com.example.github.repositories.ui.adapters.RepositoryAdapter
import com.example.github.repositories.ui.base.BaseFragment
import com.example.github.repositories.ui.base.ViewStateResult
import com.example.github.repositories.ui.details.DetailFragment
import com.example.github.repositories.ui.utils.AppUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {

    private val viewModel: UserViewModel by viewModels()
    private val safeArgs: UserFragmentArgs by navArgs()
    private val user: Owner by lazy { safeArgs.extrasOwner }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repositoryAdapter = RepositoryAdapter(::onItemClicked)
        fetchUser()

        with(binding) {

            viewModel.userViewStateResult.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is ViewStateResult.Success<*> -> {
                        when (val data = state.data) {
                            is User -> {
                                detail.text =
                                    getString(R.string.label_twitter_handle) + ": ${data.twitter_username}"
                                fetchRepos(data.repos_url)
                            }
                        }
                    }
                    is ViewStateResult.Error -> {
                        showError(
                            view = binding.coordinatorLayout,
                            message = state.errorMsg,
                            action = { fetchUser() })
                    }
                }
            }
            viewModel.viewStateResult.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is ViewStateResult.Loading -> {
                        if (state.isLoading) {
                            idlingResource?.setIdleState(false)
                            viewProgress.progressBar.visibility = View.VISIBLE
                        } else {
                            viewProgress.progressBar.visibility = View.GONE
                        }
                    }
                    is ViewStateResult.Success<*> -> {
                        when (val data = state.data) {
                            is List<*> -> {
                                repositoryAdapter.submitList(data as List<Repository>)
                                idlingResource?.setIdleState(true)
                            }
                        }
                    }
                    is ViewStateResult.Error -> {
                        showError(
                            view = binding.coordinatorLayout,
                            message = state.errorMsg,
                            action = {
                                fetchRepos(viewModel.getReposUrl())
                            })
                    }
                }
            }

            reposList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = repositoryAdapter
            }
            title.text = user.login
            AppUtil.loadImageFromUri(user.avatar_url, imageThumb)
        }
    }

    /**
     * Navigates to [DetailFragment]
     */
    private fun onItemClicked(repository: Repository) {
        val action = UserFragmentDirections.actionUserFragmentToDetailFragment(repository)
        findNavController().navigate(action)
    }

    private fun fetchRepos(url: String?) {
        viewModel.fetchRepositories(url)
    }

    private fun fetchUser() {
        viewModel.fetchUser(user.login)
    }
}