package com.stradivarius.charades.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stradivarius.charades.databinding.CategoryCardViewBinding
import com.stradivarius.charades.ui.game.container.ItemContainerActivity

class MainAdapter(
    private val categoryList: List<Pair<String, List<String>>>
) : RecyclerView.Adapter<MainAdapter.CategoryViewHolder>() {

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


    class CategoryViewHolder(
        private val cardViewBinding: CategoryCardViewBinding
    ) : RecyclerView.ViewHolder(cardViewBinding.root) {


        fun bind(pair: Pair<String, List<String>>) {
            val (category, entries) = pair
            cardViewBinding.apply {
                categoryCardText.text = category
                categoryCardView.setOnClickListener {
                    val intent = Intent(it.context, ItemContainerActivity::class.java).apply {
                        putExtra(MainFragment.KEY_BUNDLE, Bundle().apply {
                            putCharSequenceArray(ItemContainerActivity.KEY_ITEM_LIST, entries.toTypedArray())
                        })
                    }
                    it.context.startActivity(intent)
                }
            }
        }
    }
}