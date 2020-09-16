package com.stradivarius.charades.ui.addcategory

import androidx.databinding.ObservableField
import com.stradivarius.charades.data.model.AddCategoryModel
import com.stradivarius.charades.ui.common.BaseViewModel

interface AddCategoryViewModel : BaseViewModel<AddCategoryModel> {

    fun currentTitle(): ObservableField<String>

    fun currentList(): ObservableField<String>

    fun submitForm()

}