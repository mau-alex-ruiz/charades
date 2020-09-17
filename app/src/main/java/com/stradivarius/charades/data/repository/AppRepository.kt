package com.stradivarius.charades.data.repository

import androidx.lifecycle.LiveData
import com.stradivarius.charades.data.common.Repository
import com.stradivarius.charades.data.model.MainModel

interface AppRepository : Repository {

    var isDirty: Boolean

    fun getCategories(): LiveData<MainModel>

    fun addCategory(title: String, list: String): Boolean

    fun setCategories(list: List<Pair<String, List<String>>>)

    fun storeCategoriesState()

}