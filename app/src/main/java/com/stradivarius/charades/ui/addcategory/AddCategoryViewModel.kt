package com.stradivarius.charades.ui.addcategory

import androidx.databinding.ObservableField
import com.stradivarius.charades.data.model.AddCategory
import com.stradivarius.charades.ui.common.BaseViewModel

interface AddCategoryViewModel : BaseViewModel<AddCategory> {

    fun currentTitle(): ObservableField<String>

    fun currentList(): ObservableField<String>

    fun submitForm()

}