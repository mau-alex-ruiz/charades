package com.stradivarius.charades.data.repository.local

import androidx.lifecycle.LiveData
import com.stradivarius.charades.data.common.Local
import com.stradivarius.charades.data.model.MainModel

interface LocalStorage : Local {

    fun getCategories(): LiveData<MainModel>

    fun addCategory(title: String, list: String): Boolean

}