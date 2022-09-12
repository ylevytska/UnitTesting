package com.example.android.unittestingapp.ui.util

class Event<T>(private val data: T) {

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) null
        else {
            hasBeenHandled = true
            data
        }
    }

    fun peekContent() = data
}