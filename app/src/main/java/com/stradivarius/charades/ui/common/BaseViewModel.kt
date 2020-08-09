package com.stradivarius.charades.ui.common

import androidx.lifecycle.LiveData
import com.stradivarius.charades.data.common.Model

interface BaseViewModel<M : Model> : AutoCloseable {

    fun init()

    fun observeStateChanges() : LiveData<State<M>>

}