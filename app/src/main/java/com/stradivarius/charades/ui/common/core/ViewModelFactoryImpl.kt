package com.stradivarius.charades.ui.common.core

import com.stradivarius.charades.data.common.Model
import com.stradivarius.charades.data.common.core.RepositoryFactory
import com.stradivarius.charades.data.repository.AppRepository
import com.stradivarius.charades.ui.addcategory.AddCategoryViewModelImpl
import com.stradivarius.charades.ui.common.BaseViewModel
import com.stradivarius.charades.ui.game.container.ItemContainerViewModelImpl
import com.stradivarius.charades.ui.game.item.ItemViewModelImpl
import com.stradivarius.charades.ui.main.MainViewModelImpl

class ViewModelFactoryImpl(
    private val repositoryFactory: RepositoryFactory
) : ViewModelFactory {

    override fun <V> create(clazz: Class<V>): BaseViewModel<out Model> {
        return when (clazz) {
            MainViewModelImpl::class.java -> MainViewModelImpl(
                repositoryFactory.create(AppRepository::class.java)
            )
            ItemContainerViewModelImpl::class.java -> ItemContainerViewModelImpl()
            ItemViewModelImpl::class.java -> ItemViewModelImpl()
            AddCategoryViewModelImpl::class.java -> AddCategoryViewModelImpl(
                repositoryFactory.create(AppRepository::class.java)
            )
            else -> throw IllegalArgumentException("Unrecognized viewmodel class in ${this::class}")
        }
    }

}