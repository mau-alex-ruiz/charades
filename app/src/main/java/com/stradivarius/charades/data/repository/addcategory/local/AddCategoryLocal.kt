package com.stradivarius.charades.data.repository.addcategory.local

import androidx.lifecycle.LiveData
import com.stradivarius.charades.data.common.Local

interface AddCategoryLocal : Local {

    fun addCategory(title: String, list: String): LiveData<Boolean>

}