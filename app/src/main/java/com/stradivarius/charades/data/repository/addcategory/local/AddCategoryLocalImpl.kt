package com.stradivarius.charades.data.repository.addcategory.local

import android.content.res.AssetManager
import androidx.lifecycle.LiveData

class AddCategoryLocalImpl(
    private val assetManager: AssetManager
) : AddCategoryLocal {

    override fun addCategory(title: String, list: String): LiveData<Boolean> {
        TODO()
    }

    override fun close() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}