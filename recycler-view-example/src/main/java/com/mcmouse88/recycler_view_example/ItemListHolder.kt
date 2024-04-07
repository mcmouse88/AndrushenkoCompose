package com.mcmouse88.recycler_view_example

import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.mcmouse88.recycler_view_example.databinding.ItemListBinding

class ItemListHolder(
    private val binding: ItemListBinding,
    private val onItemDeleteClick: (id: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: SimpleItem) {
        with(binding) {
            tvTitle.text = item.title
            ivDelete.isVisible = !item.inProgress
            progress.isVisible = item.inProgress

            ivDelete.setOnClickListener { onItemDeleteClick.invoke(item.id) }
        }
    }

    fun updateInProgress() {
        binding.progress.isVisible = true
        binding.ivDelete.isInvisible = true
    }
}