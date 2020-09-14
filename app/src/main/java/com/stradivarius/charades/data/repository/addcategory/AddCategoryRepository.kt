package com.stradivarius.charades.data.repository.addcategory

import androidx.lifecycle.LiveData
import com.stradivarius.charades.data.common.Repository

interface AddCategoryRepository : Repository {

    fun addCategory(title: String, list: String): LiveData<Boolean>

}