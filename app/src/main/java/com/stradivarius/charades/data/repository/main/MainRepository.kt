package com.stradivarius.charades.data.repository.main

import com.stradivarius.charades.data.common.Repository
import com.stradivarius.charades.data.model.MainModel

interface MainRepository : Repository {

    fun getCategories(): MainModel

}