package com.stradivarius.charades.data.common.core

import com.stradivarius.charades.data.common.Repository
import com.stradivarius.charades.data.repository.AppRepository
import com.stradivarius.charades.data.repository.AppRepositoryImpl
import com.stradivarius.charades.data.repository.local.LocalStorage
import java.util.concurrent.ConcurrentHashMap

class RepositoryFactoryImpl(
    private val localFactory: LocalFactory
) : RepositoryFactory {

    private val implCached = ConcurrentHashMap<Class<out Repository>, Repository>()

    override fun <R : Repository> create(clazz: Class<R>): R {
        var cached = implCached[clazz]
        if (cached == null) {
            cached = when (clazz) {
                AppRepository::class.java -> AppRepositoryImpl(
                    localFactory.create(LocalStorage::class.java)
                )

                else -> throw IllegalArgumentException("No repository found in ${this::class}")
            }
            implCached[clazz] = cached
        }
        return cached as R
    }

}