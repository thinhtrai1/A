package com.example.baseproject.api

import java.lang.Exception

class ApiException() : Exception() {
    var errorCode: String? = null
    var errorMessage: String? = null

    constructor(code: String?, message: String?) : this() {
        this.errorCode = code
        this.errorMessage = message
    }

//    companion object {
//        const val NETWORK_ERROR = -1
//        const val CONVERT_BASE_64_ERROR = -2
//        const val TOKEN_EXPIRED = 2
//        const val API_ERROR = 1
//    }
}