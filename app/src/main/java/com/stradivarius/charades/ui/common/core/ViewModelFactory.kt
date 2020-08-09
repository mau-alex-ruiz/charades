package com.stradivarius.charades.ui.common.core

import androidx.lifecycle.ViewModel
import com.stradivarius.charades.data.common.Model
import com.stradivarius.charades.ui.common.BaseViewModel

interface ViewModelFactory {

    fun <V> create(clazz: Class<V>): BaseViewModel<out Model>

}