package com.stradivarius.charades.ui.main

import com.stradivarius.charades.data.model.MainModel
import com.stradivarius.charades.data.repository.main.MainRepository
import com.stradivarius.charades.ui.common.BaseViewModelImpl
import com.stradivarius.charades.ui.common.State

class MainViewModelImpl(
    private val repository: MainRepository
) : BaseViewModelImpl<MainModel>(), MainViewModel {

    override fun init() {
        stateChanged(State.Loaded(repository.getCategories()))
    }

    override fun close() {
        // no-op
    }

}