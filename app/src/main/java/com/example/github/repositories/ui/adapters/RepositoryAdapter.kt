package com.example.github.repositories.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.github.repositories.R
import com.example.github.repositories.core.data.MAX_CHAR_COUNT
import com.example.github.repositories.core.domain.Repository
import com.example.github.repositories.databinding.ViewRepositoryItemBinding
import com.example.github.repositories.ui.utils.AppUtil
import java.util.*

class RepositoryAdapter constructor(val itemClickListener: (Repository) -> Unit) :
    ListAdapter<Repository, RepositoryAdapter.ViewHolder>(ItemsDiffCallback) {

    override fun getItemCount(): Int = currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewRepositoryItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData()
    }

    inner class ViewHolder(private val binding: ViewRepositoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData() {
            val item = currentList[adapterPosition]

            with(binding) {
                title.text = StringBuilder().append("# ${adapterPosition + 1} : ${item.full_name}")
                    .toString()
                description.text = item.description
                author.text = item.owner?.login

                loadBookmarkStatus(item, image)

                newsContainer.setOnClickListener {
                    itemClickListener(item)
                }
            }
        }
    }

    private fun loadBookmarkStatus(item: Repository, image: ImageView) {
        val icon = if (item.isBookMarked)
            R.drawable.baseline_bookmark_black_24
        else
            R.drawable.baseline_bookmark_border_black_24
        AppUtil.loadIcon(icon, image)
    }

    object ItemsDiffCallback : DiffUtil.ItemCallback<Repository>() {
        override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem == newItem
        }
    }
}