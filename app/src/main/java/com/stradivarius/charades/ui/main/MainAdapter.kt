package com.stradivarius.charades.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.stradivarius.charades.databinding.CategoryCardViewBinding
import com.stradivarius.charades.ui.game.container.ItemContainerActivity
import com.stradivarius.charades.ui.utils.ItemTouchHelperAdapter

class MainAdapter(
    private val categoryList: List<Pair<String, List<String>>>
) : RecyclerView.Adapter<MainAdapter.CategoryViewHolder>(), ItemTouchHelperAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        with(CategoryCardViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )) {
            return CategoryViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        when(direction) {
            ItemTouchHelper.END -> {
                ItemContainerActivity.startActivity(
                    viewHolder.itemView.context,
                    categoryList[viewHolder.adapterPosition].second.toTypedArray())
                notifyItemChanged(viewHolder.adapterPosition)
            }
        }
    }

    class CategoryViewHolder(
        private val cardViewBinding: CategoryCardViewBinding
    ) : RecyclerView.ViewHolder(cardViewBinding.root) {

        fun bind(pair: Pair<String, List<String>>) {
            val (category, entries) = pair
            cardViewBinding.apply {
                categoryCardText.text = category
                categoryCardView.setOnClickListener {
                    ItemContainerActivity.startActivity(it.context, entries.toTypedArray())
                }
            }
        }
    }
}