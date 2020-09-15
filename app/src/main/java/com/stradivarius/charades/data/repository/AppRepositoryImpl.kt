package com.stradivarius.charades.data.repository

import androidx.lifecycle.LiveData
import com.stradivarius.charades.data.model.MainModel
import com.stradivarius.charades.data.repository.local.LocalStorage

class AppRepositoryImpl(
    private val local: LocalStorage
) : AppRepository {

    override fun getCategories(): LiveData<MainModel> {
        return local.getCategories()
    }

    override fun addCategory(title: String, list: String): Boolean {
        return local.addCategory(title, list)
    }

    override fun close() {
        // no-op
    }
}