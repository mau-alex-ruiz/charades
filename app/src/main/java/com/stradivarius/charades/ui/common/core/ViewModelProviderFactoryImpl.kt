package com.stradivarius.charades.ui.common.core

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stradivarius.charades.data.common.Model
import com.stradivarius.charades.ui.addcategory.AddCategoryViewModel
import com.stradivarius.charades.ui.addcategory.AddCategoryViewModelImpl
import com.stradivarius.charades.ui.common.BaseViewModel
import com.stradivarius.charades.ui.game.container.ItemContainerViewModel
import com.stradivarius.charades.ui.game.container.ItemContainerViewModelImpl
import com.stradivarius.charades.ui.game.item.ItemViewModel
import com.stradivarius.charades.ui.game.item.ItemViewModelImpl
import com.stradivarius.charades.ui.main.MainViewModel
import com.stradivarius.charades.ui.main.MainViewModelImpl

class ViewModelProviderFactoryImpl(
    private val viewModelFactory: ViewModelFactory
) : ViewModelProviderFactory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModelFactory.create(modelClass) as T
    }

    override fun <V : BaseViewModel<out Model>> createFromFragment(
        viewModelClass: Class<V>,
        fragment: Fragment
    ): V {
        val implClass = when (viewModelClass) {
            MainViewModel::class.java -> MainViewModelImpl::class.java
            ItemContainerViewModel::class.java -> ItemContainerViewModelImpl::class.java
            ItemViewModel::class.java -> ItemViewModelImpl::class.java
            AddCategoryViewModel::class.java -> AddCategoryViewModelImpl::class.java
            else -> throw  IllegalArgumentException("ViewModel not found in ${this::class}")
        }
        return ViewModelProvider(fragment, this).get(implClass) as V
    }

}