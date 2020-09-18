package com.stradivarius.charades.ui.category.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.stradivarius.charades.data.repository.AppRepository
import com.stradivarius.charades.ui.category.base.BaseCategoryViewModelImpl
import com.stradivarius.charades.ui.common.RepoStatus
import com.stradivarius.charades.ui.common.State

class EditCategoryViewModelImpl(
    private val repository: AppRepository
) : BaseCategoryViewModelImpl(repository), EditCategoryViewModel {

    override val submitFormMap: LiveData<RepoStatus<Unit>>
        get() = Transformations.map(submitFormListener) {
            it?.let { (title, list) ->
                repository.editCategory(title, list)
            }
        }

    override fun init(categoryTitle: String, categoryList: List<String>) {
        this.currentTitle.set(categoryTitle)
        this.currentList.set(categoryList.sorted().joinToString(separator = "\n"))
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