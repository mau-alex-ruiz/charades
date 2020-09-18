package com.stradivarius.charades.ui.category.edit

import com.stradivarius.charades.ui.category.base.CategoryViewModel

interface EditCategoryViewModel : CategoryViewModel {

    fun init(categoryTitle: String, categoryList: List<String>)

}