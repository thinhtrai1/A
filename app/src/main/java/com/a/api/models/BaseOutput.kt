package com.a.api.models

open class BaseOutput {
    var status: Boolean = false
    val error: Error? = null

    inner class Error {
        val code: String? = null
        val message: String? = null
    }
}