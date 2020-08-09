package com.stradivarius.charades.ui.common.core

import android.content.res.AssetManager
import com.stradivarius.charades.data.common.core.LocalFactory
import com.stradivarius.charades.data.common.core.LocalFactoryImpl
import com.stradivarius.charades.data.common.core.RepositoryFactory
import com.stradivarius.charades.data.common.core.RepositoryFactoryImpl

object Di {

    var isInitialized = false
    private var _viewModelProviderFactory : ViewModelProviderFactory? = null
    private var _repositoryFactory : RepositoryFactory? = null
    private var _localFactory : LocalFactory? = null

    fun init(assetManager: AssetManager) {
        if (!isInitialized) {
            _localFactory = LocalFactoryImpl(assetManager)
            _repositoryFactory = RepositoryFactoryImpl(localfactory())
            _viewModelProviderFactory = ViewModelProviderFactoryImpl(
                ViewModelFactoryImpl(repositoryFactory())
            )
            isInitialized = true
        }
    }

    fun viewModelProviderFactory() : ViewModelProviderFactory {
        return requireNotNull(_viewModelProviderFactory) {
            "ViewModelFactory is null in ${this::class}"
        }
    }

    fun repositoryFactory() : RepositoryFactory {
        return requireNotNull(_repositoryFactory) {
            "RepositoryFactory is null in ${this::class}"
        }
    }

    fun localfactory() : LocalFactory {
        return requireNotNull(_localFactory) {
            "LocalFactory is null in ${this::class}"
        }
    }

    fun destroy() {
        _viewModelProviderFactory = null
        _repositoryFactory = null
        _localFactory = null
        isInitialized = false
    }

}