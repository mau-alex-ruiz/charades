package com.stradivarius.charades.data.repository

import androidx.lifecycle.LiveData
import com.stradivarius.charades.data.model.MainModel
import com.stradivarius.charades.data.repository.local.LocalStorage

class AppRepositoryImpl(
    private val local: LocalStorage
) : AppRepository {

    override var isDirty: Boolean
        get() = local.isDirty
        set(value) {local.isDirty = value}

    override fun getCategories(): LiveData<MainModel> {
        return local.getCategories()
    }

    override fun addCategory(title: String, list: String): Boolean {
        return local.addCategory(title, list)
    }

    override fun setCategories(list: List<Pair<String, List<String>>>) {
        local.setCategories(list)
    }

    override fun storeCategoriesState() = local.writeCategoriesStateToJson()

    override fun close() {
        // no-op
    }
}