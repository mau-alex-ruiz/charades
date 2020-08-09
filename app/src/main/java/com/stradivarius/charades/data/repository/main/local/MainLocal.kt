package com.stradivarius.charades.data.repository.main.local

import com.stradivarius.charades.data.common.Local
import com.stradivarius.charades.data.model.MainModel

interface MainLocal : Local {

    fun getCategories(): MainModel

}