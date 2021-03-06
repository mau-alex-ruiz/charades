package com.stradivarius.charades.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.stradivarius.charades.databinding.CategoryCardViewBinding
import com.stradivarius.charades.ui.category.edit.EditCategoryActivity
import com.stradivarius.charades.ui.game.container.ItemContainerActivity
import com.stradivarius.charades.ui.utils.itemtouchhelper.ItemTouchHelperAdapter
import java.util.Collections.swap

class MainAdapter(
    private var categoryList: List<Pair<String, List<String>>>,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<MainAdapter.CategoryViewHolder>(),
    ItemTouchHelperAdapter {

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
            ItemTouchHelper.START -> {
                EditCategoryActivity.startActivity(
                    viewHolder.itemView.context,
                    categoryList[viewHolder.adapterPosition].first,
                    categoryList[viewHolder.adapterPosition].second
                )
                notifyItemChanged(viewHolder.adapterPosition)
            }
            ItemTouchHelper.END -> {
                ItemContainerActivity.startActivity(
                    viewHolder.itemView.context,
                    categoryList[viewHolder.adapterPosition].second.toTypedArray())
                notifyItemChanged(viewHolder.adapterPosition)
            }
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                swap(categoryList, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                swap(categoryList, i, i - 1)
            }
        }
        if (fromPosition != toPosition) {
            viewModel.setCategories(categoryList)
            viewModel.isDirty = true
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    fun updateDataSet(categoryList: List<Pair<String, List<String>>>) {
        this.categoryList = categoryList
        notifyDataSetChanged()
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