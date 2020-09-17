package com.stradivarius.charades.ui.main

import androidx.lifecycle.Observer
import com.stradivarius.charades.data.model.MainModel
import com.stradivarius.charades.data.repository.AppRepository
import com.stradivarius.charades.ui.common.BaseViewModelImpl
import com.stradivarius.charades.ui.common.State

class MainViewModelImpl(
    private val repository: AppRepository
) : BaseViewModelImpl<MainModel>(), MainViewModel {

    override var isDirty: Boolean
        get() = repository.isDirty
        set(value) {repository.isDirty = value}
    private val mainModel = repository.getCategories()
    private val categoriesObserver = Observer<MainModel> {
        it?.also { model ->
            stateChanged(State.Loaded(model))
        }
    }

    override fun init() {
        mainModel.observeForever(categoriesObserver)
    }

    override fun setCategories(list: List<Pair<String, List<String>>>) {
        repository.setCategories(list)
    }

    override fun storeOrderState() {
        repository.storeCategoriesState()
    }

    override fun close() {
        mainModel.removeObserver(categoriesObserver)
    }

}