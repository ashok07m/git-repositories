package com.example.github.repositories.ui.user

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.example.github.repositories.data.source.remote.OwnerDTO
import com.example.github.repositories.databinding.FragmentUserBinding
import com.example.github.repositories.ui.adapters.RepositoryAdapter
import com.example.github.repositories.ui.base.BaseFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment(private val user: OwnerDTO) :
    BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {

    private val viewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            title.text = user.login

            Picasso.get().load(user.avatar_url.toUri()).into(image)

            viewModel.fetchUser(user.login)

            viewModel.user.observeForever {
                detail!!.text = "Twitter handle: " + it.twitter_username
                viewModel.fetchRepositories(it.repos_url!!)
            }

            viewModel.repositories.observeForever {
                list!!.adapter = RepositoryAdapter(it.toMutableList(), requireActivity())
            }
        }
    }
}