package com.stradivarius.charades.ui.addcategory

import androidx.databinding.ObservableField
import com.stradivarius.charades.data.model.AddCategory
import com.stradivarius.charades.data.repository.AppRepository
import com.stradivarius.charades.ui.common.BaseViewModelImpl
import com.stradivarius.charades.ui.common.State

class AddCategoryViewModelImpl(
    private val repository: AppRepository
) : BaseViewModelImpl<AddCategory>(), AddCategoryViewModel {

    private val currentTitle = ObservableField<String>()
    private val currentList = ObservableField<String>()

    override fun currentTitle(): ObservableField<String> = currentTitle

    override fun currentList(): ObservableField<String> = currentList

    override fun submitForm() {
        currentTitle.get()?.let { title ->
            currentList.get()?.let { list ->
                if (!title.isBlank() && !list.isBlank()) {
                    if (repository.addCategory(title, list)) {
                        stateChanged(State.Success())
                    } else {
                        stateChanged(State.Error(State.ErrorTypes.Critical()))
                    }
                } else {
                    stateChanged(State.Error(State.ErrorTypes.Warning()))
                }
            }
        } ?: stateChanged(State.Error(State.ErrorTypes.Warning()))
    }


}