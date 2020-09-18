package com.stradivarius.charades.ui.common

sealed class RepoStatus<T>(data: T? = null) {

    class Success<T>(data: T? = null) : RepoStatus<T>(data)

    class Error<T>(data: T? = null) : RepoStatus<T>(data)

    class Loading<T>(data: T? = null) : RepoStatus<T>(data)

    fun isSuccess(): Boolean = this is Success

    fun isError(): Boolean = this is Error

    fun isLoading(): Boolean = this is Loading

}