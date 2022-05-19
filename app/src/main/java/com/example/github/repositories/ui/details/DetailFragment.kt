package com.example.github.repositories.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.github.repositories.R
import com.example.github.repositories.core.domain.Repository
import com.example.github.repositories.databinding.FragmentDetailBinding
import com.example.github.repositories.ui.utils.AppUtil
import com.example.github.repositories.ui.base.BaseFragment
import com.example.github.repositories.ui.user.UserFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val viewModel: DetailsViewModel by viewModels()
    private val safeArgs: DetailFragmentArgs by navArgs()
    private val repository: Repository by lazy { safeArgs.extrasRepository }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            title.text = repository.name
            detail.text =
                getString(R.string.label_created_by) + " ${repository.owner?.login}, ${getString(R.string.label_at)}  ${repository.created_at}"

            description.text = repository.description
            url.text = repository.html_url

            image.setOnClickListener {
                viewModel.toggleBookmarkStatus(repository)
            }

            detail.setOnClickListener {
                onItemClicked(repository)
            }
        }

        with(viewModel) {
            isRepositoryBookmarked(repository)

            bookmarkStatus.observe(viewLifecycleOwner) { status ->
                loadBookmarkIcon(status)
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

    /**
     * Navigates to [UserFragment]
     */
    private fun onItemClicked(repository: Repository) {
        repository.owner?.let {
            val action =
                DetailFragmentDirections.actionDetailFragmentToUserFragment(repository.owner)
            findNavController().navigate(action)
        }
    }
}