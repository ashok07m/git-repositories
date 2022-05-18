package com.example.github.repositories.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.github.repositories.R
import com.example.github.repositories.data.source.local.LocalDataStore
import com.example.github.repositories.data.source.remote.RepositoryDTO
import com.example.github.repositories.databinding.ItemBinding
import java.util.*

class RepositoryAdapter constructor(val itemClickListener: (RepositoryDTO) -> Unit) :
    ListAdapter<RepositoryDTO, RepositoryAdapter.ViewHolder>(ItemsDiffCallback) {

    override fun getItemCount(): Int = currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData()
    }

    inner class ViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData() {
            val item = currentList[adapterPosition]

            with(binding) {
                title.text =
                    "#" + (adapterPosition + 1) + ": " + item.full_name?.uppercase(Locale.US)

                description.text =
                    if (item.description?.length ?: 0 > 150) item.description?.take(150)
                        .plus("...") else item.description

                author.text = item.owner?.login

                image.setImageResource(
                    if (LocalDataStore.instance.getBookmarks().contains(item))
                        R.drawable.baseline_bookmark_black_24
                    else
                        R.drawable.baseline_bookmark_border_black_24
                )

                newsContainer.setOnClickListener {
                    itemClickListener(item)
                }
            }
        }
    }

    object ItemsDiffCallback : DiffUtil.ItemCallback<RepositoryDTO>() {
        override fun areItemsTheSame(oldItem: RepositoryDTO, newItem: RepositoryDTO): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RepositoryDTO, newItem: RepositoryDTO): Boolean {
            return oldItem == newItem
        }
    }
}