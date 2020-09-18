package com.stradivarius.charades.ui.common

sealed class State<M>(val model: M? = null) {

    class InProgress<M>(model: M? = null) : State<M>(model)

    class Loaded<M>(model: M? = null) : State<M>(model)

    class Success<M>(model: M? = null): State<M>(model)

    class Error<M>(val errorType: ErrorTypes): State<M>()

    sealed class ErrorTypes(msg: String) {

        class Warning(msg: String = "") : ErrorTypes(msg)

        class Critical(msg: String = "") : ErrorTypes(msg)

    }

}