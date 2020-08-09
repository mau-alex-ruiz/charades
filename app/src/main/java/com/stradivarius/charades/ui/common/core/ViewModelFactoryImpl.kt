package com.stradivarius.charades.ui.common.core

import com.stradivarius.charades.data.common.Model
import com.stradivarius.charades.data.common.core.RepositoryFactory
import com.stradivarius.charades.data.repository.main.MainRepository
import com.stradivarius.charades.ui.common.BaseViewModel
import com.stradivarius.charades.ui.main.MainViewModelImpl

class ViewModelFactoryImpl(
    private val repositoryFactory: RepositoryFactory
) : ViewModelFactory {

    override fun <V> create(clazz: Class<V>): BaseViewModel<out Model> {
        return when (clazz) {
            MainViewModelImpl::class.java -> MainViewModelImpl(
                repositoryFactory.create(MainRepository::class.java)
            )
            else -> throw IllegalArgumentException("Unrecognized viewmodel class in ${this::class}")
        }
    }

}