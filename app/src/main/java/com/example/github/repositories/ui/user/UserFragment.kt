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
import com.example.github.repositories.databinding.FragmentUserBinding
import com.example.github.repositories.ui.adapters.RepositoryAdapter
import com.example.github.repositories.ui.base.BaseFragment
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
        viewModel.fetchUser(user.login)

        with(binding) {

            viewModel.user.observe(viewLifecycleOwner) { dto ->
                detail.text = getString(R.string.label_twitter_handle) + ": ${dto.twitter_username}"
                viewModel.fetchRepositories(dto.repos_url)
            }

            viewModel.repositories.observeForever {
                repositoryAdapter.submitList(it)
            }

            list.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = repositoryAdapter
            }
            title.text = user.login
            AppUtil.loadImageFromUri(user.avatar_url, image)
        }
    }

    /**
     * Navigates to [DetailFragment]
     */
    private fun onItemClicked(repository: Repository) {
        val action = UserFragmentDirections.actionUserFragmentToDetailFragment(repository)
        findNavController().navigate(action)
    }
}