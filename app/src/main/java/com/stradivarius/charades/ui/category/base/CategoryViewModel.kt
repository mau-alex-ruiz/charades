package com.stradivarius.charades.ui.category.base

import androidx.databinding.ObservableField
import com.stradivarius.charades.data.model.CategoryModel
import com.stradivarius.charades.ui.common.BaseViewModel

interface CategoryViewModel : BaseViewModel<CategoryModel> {

    fun currentTitle(): ObservableField<String>

    fun currentList(): ObservableField<String>

    fun submitForm()

}