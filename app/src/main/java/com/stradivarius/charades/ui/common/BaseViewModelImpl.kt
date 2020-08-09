package com.stradivarius.charades.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stradivarius.charades.data.common.Model

abstract class BaseViewModelImpl<M : Model> : ViewModel(), BaseViewModel<M> {

    private val stateChanges = MutableLiveData<State<M>>()

    override fun init() {
        // no-op
    }

    override fun close() {
        onCleared()
    }

    protected fun stateChanged(state: State<M>) {
        stateChanges.postValue(state)
    }

    override fun observeStateChanges(): LiveData<State<M>> {
        return stateChanges
    }

}