package com.mcmouse88.recycler_view_example

import android.content.res.Resources
import android.graphics.Rect
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mcmouse88.recycler_view_example.databinding.ItemListBinding

class MainAdapter(
    private val onItemDeleteClick: (id: Int) -> Unit
) : ListAdapter<SimpleItem, ItemListHolder>(MainDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListBinding.inflate(inflater, parent, false)
        return ItemListHolder(binding, onItemDeleteClick)
    }

    override fun onBindViewHolder(holder: ItemListHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: ItemListHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            holder.updateInProgress()
        } else {
            onBindViewHolder(holder, position)
        }
    }

    private class MainDiffUtil : DiffUtil.ItemCallback<SimpleItem>() {

        override fun areItemsTheSame(oldItem: SimpleItem, newItem: SimpleItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SimpleItem, newItem: SimpleItem): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: SimpleItem, newItem: SimpleItem): Any? {
            return if (oldItem.inProgress != newItem.inProgress) true
            else super.getChangePayload(oldItem, newItem)
        }
    }
}

class HorizontalItemDecorator : RecyclerView.ItemDecoration() {

    private val dpMargin = 16.dp

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = dpMargin
        outRect.right = dpMargin
    }
}

class VerticalItemDecoration : RecyclerView.ItemDecoration() {

    private val paddingTop = 16.dp
    private val space = 8.dp
    private val paddingBottom = 32.dp

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val adapter = parent.adapter ?: return
        val viewHolder = parent.getChildViewHolder(view)
        val currentPosition = parent.getChildAdapterPosition(view).takeIf {
            it != RecyclerView.NO_POSITION
        } ?: viewHolder.oldPosition

        when (currentPosition) {
            0 -> {
                outRect.top = paddingTop
                outRect.bottom = space
            }

            adapter.itemCount - 1 -> {
                outRect.top = space
                outRect.bottom = paddingBottom
            }

            else -> {
                outRect.top = space
                outRect.bottom = space
            }
        }
    }
}

private inline val Int.dp: Int
    inline get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()