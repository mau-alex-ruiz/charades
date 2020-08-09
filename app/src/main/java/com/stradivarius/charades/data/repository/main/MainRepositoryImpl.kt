package com.stradivarius.charades.data.repository.main

import com.stradivarius.charades.data.model.MainModel
import com.stradivarius.charades.data.repository.main.local.MainLocal

class MainRepositoryImpl(
    private val local: MainLocal
) : MainRepository {

    override fun getCategories(): MainModel {
        return local.getCategories()
    }

    override fun close() {
        // no-op
    }
}