package com.a.api

import com.a.tasks.BaseTask

interface ApiListener<Output> {
    fun onConnectionOpen(task: BaseTask<Output>) { }
    fun onConnectionSuccess(task: BaseTask<Output>, data: Output)
    fun onConnectionError(task: BaseTask<Output>, exception: Exception)
}