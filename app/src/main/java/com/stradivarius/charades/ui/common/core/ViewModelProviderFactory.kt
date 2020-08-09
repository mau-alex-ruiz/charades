package com.stradivarius.charades.ui.common.core

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.stradivarius.charades.data.common.Model
import com.stradivarius.charades.ui.common.BaseViewModel

interface ViewModelProviderFactory : ViewModelProvider.Factory {

    fun <V : BaseViewModel<out Model>> createFromFragment(viewModelClass: Class<V>, fragment: Fragment) : V

}