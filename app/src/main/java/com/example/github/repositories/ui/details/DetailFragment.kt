package com.example.github.repositories.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.github.repositories.R
import com.example.github.repositories.core.data.local.LocalDataStoreImpl
import com.example.github.repositories.core.domain.Repository
import com.example.github.repositories.databinding.FragmentDetailBinding
import com.example.github.repositories.ui.AppUtil
import com.example.github.repositories.ui.base.BaseFragment
import com.example.github.repositories.ui.main.MainViewModel
import com.example.github.repositories.ui.user.UserFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class DetailFragment(private val repository: Repository) :
    BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val viewModel: DetailsViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            title.text = repository.name
            detail.text =
                "Created by " + repository.owner?.login + ", at " + repository.created_at

            description.text = repository.description
            url.text = repository.html_url

            image.setOnClickListener {
                viewModel.toggleBookmarkStatus(repository)
            }

            detail.setOnClickListener {
                onItemClicked(repository)
            }

            viewModel.run {
                isRepositoryBookmarked(repository)

                bookmarkStatus.observe(viewLifecycleOwner) { status ->
                    loadBookmarkIcon(status)
                }
            }
        }
    }

    private fun loadBookmarkIcon(status: Boolean) {
        val resource: Int = if (status)
            R.drawable.baseline_bookmark_black_24
        else
            R.drawable.baseline_bookmark_border_black_24

        AppUtil.loadIcon(resource, binding.image)
    }

    private fun onItemClicked(item: Repository) {
        item.owner?.let {
            val fragment = UserFragment(it)
            moveToFragment(fragment, "user")
        }

    }
}