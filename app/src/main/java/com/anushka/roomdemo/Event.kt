package com.anushka.roomdemo

//TODO: Step 8: Create an Event class to handle the response from the database
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set //allow external read but not write

    /**
     * Returns the content and prevents its use again
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled
     */
    fun peekContent(): T = content
}