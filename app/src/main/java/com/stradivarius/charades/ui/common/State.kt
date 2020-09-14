package com.stradivarius.charades.ui.common

sealed class State<M>(val model: M? = null) {

    internal class Loaded<M>(model: M? = null) : State<M>(model)

    internal class Success<M>(model: M? = null): State<M>(model)

    internal class Error<M>(model: M? = null): State<M>(model)

}