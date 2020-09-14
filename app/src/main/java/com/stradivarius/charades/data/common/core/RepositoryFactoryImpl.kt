package com.stradivarius.charades.data.common.core

import com.stradivarius.charades.data.common.Repository
import com.stradivarius.charades.data.repository.addcategory.AddCategoryRepository
import com.stradivarius.charades.data.repository.addcategory.AddCategoryRepositoryImpl
import com.stradivarius.charades.data.repository.addcategory.local.AddCategoryLocal
import com.stradivarius.charades.data.repository.main.MainRepository
import com.stradivarius.charades.data.repository.main.MainRepositoryImpl
import com.stradivarius.charades.data.repository.main.local.MainLocal
import java.util.concurrent.ConcurrentHashMap

class RepositoryFactoryImpl(
    private val localFactory: LocalFactory
) : RepositoryFactory {

    private val implCached = ConcurrentHashMap<Class<out Repository>, Repository>()

    override fun <R : Repository> create(clazz: Class<R>): R {
        var cached = implCached[clazz]
        if (cached == null) {
            cached = when (clazz) {
                MainRepository::class.java -> MainRepositoryImpl(
                    localFactory.create(MainLocal::class.java)
                )
                AddCategoryRepository::class.java -> AddCategoryRepositoryImpl(
                    localFactory.create(AddCategoryLocal::class.java)
                )

                else -> throw IllegalArgumentException("No repository found in ${this::class}")
            }
            implCached[clazz] = cached
        }
        return cached as R
    }

}