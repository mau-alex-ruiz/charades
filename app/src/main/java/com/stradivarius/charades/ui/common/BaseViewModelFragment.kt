package com.stradivarius.charades.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.stradivarius.charades.data.common.Model
import com.stradivarius.charades.ui.common.core.Di

abstract class BaseViewModelFragment<V, M, B>(
    private val viewModelClass: Class<V>,
    @LayoutRes private val layoutRes: Int
) : Fragment() where V : BaseViewModel<M>, M : Model, B : ViewDataBinding {

    protected lateinit var boundLayout: B

    protected lateinit var viewModel: V

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        boundLayout = DataBindingUtil.inflate<B>(
            LayoutInflater.from(context),
            layoutRes,
            container,
            false
        )
        boundLayout.lifecycleOwner = this
        return boundLayout.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = Di.viewModelProviderFactory().createFromFragment(viewModelClass,this)
        viewModel.observeStateChanges().observe(this as LifecycleOwner, Observer {
            onStateChanged(it)
        })
        bindViewModel(viewModel, boundLayout)
    }


    abstract fun bindViewModel(viewModel: V, boundLayout: B)

    protected open fun onStateChanged(state: State<M>) {
        // no-op
    }

}