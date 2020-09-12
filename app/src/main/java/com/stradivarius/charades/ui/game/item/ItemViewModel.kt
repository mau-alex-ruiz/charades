package com.stradivarius.charades.ui.game.item

import androidx.lifecycle.LiveData
import com.stradivarius.charades.data.model.Item
import com.stradivarius.charades.ui.common.BaseViewModel

interface ItemViewModel : BaseViewModel<Item> {

    fun init(item: String)

    fun observeItem(): LiveData<String>

}