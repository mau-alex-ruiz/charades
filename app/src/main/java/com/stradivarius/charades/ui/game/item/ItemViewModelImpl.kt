package com.stradivarius.charades.ui.game.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stradivarius.charades.data.model.ItemModel
import com.stradivarius.charades.ui.common.BaseViewModelImpl

class ItemViewModelImpl : BaseViewModelImpl<ItemModel>(),
    ItemViewModel {

    private val item = MutableLiveData<String>()

    override fun init(item: String) {
        this.item.postValue(item)
    }

    override fun observeItem(): LiveData<String> = item

}