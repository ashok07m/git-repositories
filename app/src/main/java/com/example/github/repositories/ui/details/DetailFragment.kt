package com.example.github.repositories.ui.details

import android.os.Bundle
import android.view.View
import com.example.github.repositories.R
import com.example.github.repositories.data.source.local.LocalDataStore
import com.example.github.repositories.data.source.remote.RepositoryDTO
import com.example.github.repositories.databinding.FragmentDetailBinding
import com.example.github.repositories.ui.base.BaseFragment
import com.example.github.repositories.ui.user.UserFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment(private val repository: RepositoryDTO) :
    BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            title.text = repository.name
            detail.text =
                "Created by " + repository.owner?.login + ", at " + repository.created_at
            Picasso.get().load(repository.owner?.avatar_url).into(image)
            description.text = repository.description
            url.text = repository.html_url

            image.apply {

                setImageResource(
                    if (LocalDataStore.instance.getBookmarks().contains(repository))
                        R.drawable.baseline_bookmark_black_24
                    else
                        R.drawable.baseline_bookmark_border_black_24
                )

                setOnClickListener {
                    val isBookmarked = LocalDataStore.instance.getBookmarks().contains(repository)
                    LocalDataStore.instance.bookmarkRepo(repository, !isBookmarked)
                    setImageResource(if (!isBookmarked) R.drawable.baseline_bookmark_black_24 else R.drawable.baseline_bookmark_border_black_24)
                }

            }
            detail.setOnClickListener {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .add(android.R.id.content, UserFragment(repository.owner!!))
                    .addToBackStack("user")
                    .commit()
            }
        }
    }
}