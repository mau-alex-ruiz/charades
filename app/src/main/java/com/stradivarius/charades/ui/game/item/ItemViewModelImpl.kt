package com.stradivarius.charades.ui.game.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stradivarius.charades.data.model.Item
import com.stradivarius.charades.ui.common.BaseViewModelImpl

class ItemViewModelImpl : BaseViewModelImpl<Item>(),
    ItemViewModel {

    private val item = MutableLiveData<String>()

    override fun init(item: String) {
        this.item.postValue(item)
    }

    override fun observeItem(): LiveData<String> = item

}