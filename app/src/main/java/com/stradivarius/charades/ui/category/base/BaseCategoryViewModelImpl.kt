package com.stradivarius.charades.ui.category.base

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.stradivarius.charades.data.model.CategoryModel
import com.stradivarius.charades.data.repository.AppRepository
import com.stradivarius.charades.ui.common.BaseViewModelImpl
import com.stradivarius.charades.ui.common.RepoStatus
import com.stradivarius.charades.ui.common.State

abstract class BaseCategoryViewModelImpl(
    private val repository: AppRepository
) : BaseViewModelImpl<CategoryModel>(), CategoryViewModel {

    protected val currentTitle = ObservableField<String>()
    protected val currentList = ObservableField<String>()

    protected val submitFormListener = MutableLiveData<Pair<String, String>>()
    protected abstract val submitFormMap: LiveData<RepoStatus<Unit>>

    private val submitFormObserver = Observer<RepoStatus<Unit>?> {
        it?.also {
            when {
                it.isError() -> stateChanged(State.Error(State.ErrorTypes.Critical()))

                it.isSuccess() -> stateChanged(State.Success())
            }
        }
    }

    override fun init() {
        super.init()
        submitFormMap.observeForever(submitFormObserver)
    }

    override fun currentTitle(): ObservableField<String> = currentTitle

    override fun currentList(): ObservableField<String> = currentList

    override fun close() {
        super.close()
        submitFormMap.removeObserver(submitFormObserver)
    }

}