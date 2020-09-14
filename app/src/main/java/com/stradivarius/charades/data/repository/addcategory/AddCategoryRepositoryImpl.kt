package com.stradivarius.charades.data.repository.addcategory

import androidx.lifecycle.LiveData
import com.stradivarius.charades.data.repository.addcategory.local.AddCategoryLocal

class AddCategoryRepositoryImpl(
    private val local: AddCategoryLocal
) : AddCategoryRepository {

    override fun addCategory(title: String, list: String): LiveData<Boolean> {
        return local.addCategory(title, list)
    }

    override fun close() {
        // no-op
    }
}