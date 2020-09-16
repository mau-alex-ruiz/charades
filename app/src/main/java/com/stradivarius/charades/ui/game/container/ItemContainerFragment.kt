package com.stradivarius.charades.ui.game.container

import com.stradivarius.charades.R
import com.stradivarius.charades.data.model.ItemModel
import com.stradivarius.charades.databinding.ItemContainerLayoutBinding
import com.stradivarius.charades.ui.common.BaseViewModelFragment

class ItemContainerFragment
    : BaseViewModelFragment<ItemContainerViewModel, ItemModel, ItemContainerLayoutBinding>(
    ItemContainerViewModel::class.java,
    R.layout.item_container_layout
) {

    override fun bindViewModel(
        viewModel: ItemContainerViewModel,
        boundLayout: ItemContainerLayoutBinding
    ) {

    }

}