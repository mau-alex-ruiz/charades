package com.stradivarius.charades.ui.game.item

import com.stradivarius.charades.R
import com.stradivarius.charades.data.model.Item
import com.stradivarius.charades.databinding.ItemLayoutBinding
import com.stradivarius.charades.ui.common.BaseViewModelFragment

class ItemFragment(
    private val item: String
) : BaseViewModelFragment<ItemViewModel, Item, ItemLayoutBinding>(
    ItemViewModel::class.java,
    R.layout.item_layout
) {

    override fun bindViewModel(viewModel: ItemViewModel, boundLayout: ItemLayoutBinding) {
        boundLayout.model = viewModel.apply { init(item) }
    }

}