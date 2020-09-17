package com.stradivarius.charades.ui.main

import com.stradivarius.charades.data.model.MainModel
import com.stradivarius.charades.ui.common.BaseViewModel

interface MainViewModel : BaseViewModel<MainModel> {

    var isDirty: Boolean

    fun storeOrderState()

    fun setCategories(list: List<Pair<String, List<String>>>)

}
