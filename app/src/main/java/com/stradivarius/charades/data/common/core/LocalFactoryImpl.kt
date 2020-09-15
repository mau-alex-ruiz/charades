package com.stradivarius.charades.data.common.core

import android.content.res.AssetManager
import com.stradivarius.charades.data.repository.local.LocalStorage
import com.stradivarius.charades.data.repository.local.LocalStorageImpl
import java.util.concurrent.ConcurrentHashMap

class LocalFactoryImpl(
    private val assetManager: AssetManager
) : LocalFactory {

    private val implCached = ConcurrentHashMap<Class<out com.stradivarius.charades.data.common.Local>, com.stradivarius.charades.data.common.Local>()

    override fun <R : com.stradivarius.charades.data.common.Local> create(clazz: Class<R>): R {
        var cached = implCached[clazz]
        if (cached == null) {
            cached = when (clazz) {
                LocalStorage::class.java -> LocalStorageImpl(
                    assetManager
                )

                else -> throw IllegalArgumentException("No repository found in ${this::class}")
            }
            implCached[clazz] = cached
        }
        return cached as R
    }

}