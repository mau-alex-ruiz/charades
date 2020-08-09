package com.stradivarius.charades.ui.common

sealed class State<M>(val model: M? = null) {

    internal class Loaded<M>(model: M?) : State<M>(model)

}