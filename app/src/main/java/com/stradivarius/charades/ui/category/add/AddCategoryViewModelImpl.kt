package com.stradivarius.charades.ui.category.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.stradivarius.charades.data.repository.AppRepository
import com.stradivarius.charades.ui.category.base.BaseCategoryViewModelImpl
import com.stradivarius.charades.ui.common.RepoStatus
import com.stradivarius.charades.ui.common.State

class AddCategoryViewModelImpl(
    private val repository: AppRepository
) : BaseCategoryViewModelImpl(repository), AddCategoryViewModel {

    override val submitFormMap: LiveData<RepoStatus<Unit>>
        get() = Transformations.map(submitFormListener) {
            it?.let { (title, list) ->
            repository.addCategory(title, list)
        }
    }

    override fun submitForm() {
        currentTitle.get().also { currentTitle ->
            currentList.get().also { currentList ->
                if (!currentTitle.isNullOrBlank() && !currentList.isNullOrBlank()) {
                    stateChanged(State.InProgress())
                    submitFormListener.postValue(currentTitle to currentList)
                } else {
                    stateChanged(State.Error(State.ErrorTypes.Warning()))
                }
            }
        }
    }

}