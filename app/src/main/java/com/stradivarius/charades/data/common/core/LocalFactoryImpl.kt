package com.stradivarius.charades.data.common.core

import android.content.res.AssetManager
import com.stradivarius.charades.data.common.Local
import com.stradivarius.charades.data.repository.addcategory.local.AddCategoryLocal
import com.stradivarius.charades.data.repository.addcategory.local.AddCategoryLocalImpl
import com.stradivarius.charades.data.repository.main.local.MainLocal
import com.stradivarius.charades.data.repository.main.local.MainLocalImpl
import java.util.concurrent.ConcurrentHashMap

class LocalFactoryImpl(
    private val assetManager: AssetManager
) : LocalFactory {

    private val implCached = ConcurrentHashMap<Class<out Local>, Local>()

    override fun <R : Local> create(clazz: Class<R>): R {
        var cached = implCached[clazz]
        if (cached == null) {
            cached = when (clazz) {
                MainLocal::class.java -> MainLocalImpl(assetManager)
                AddCategoryLocal::class.java -> AddCategoryLocalImpl(assetManager)

                else -> throw IllegalArgumentException("No repository found in ${this::class}")
            }
            implCached[clazz] = cached
        }
        return cached as R
    }

}