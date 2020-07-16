package com.a.api

import java.lang.Exception

class ApiException() : Exception() {
    var errorCode: String? = null
    var errorMessage: String? = null

    constructor(code: String?, message: String?) : this() {
        this.errorCode = code
        this.errorMessage = message
    }
}