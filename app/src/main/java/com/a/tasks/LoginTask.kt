package com.a.tasks

import com.a.api.HttpApi
import com.a.api.models.LoginOutput
import com.example.baseproject.api.ApiListener

class LoginTask(private val input: HashMap<String, String>, listener: ApiListener<Any>): BaseTask<Any>(listener) {
    override fun callApiMethod(): Any {
        val data =  mHttpApi.doHttpPost(getFullUrl(HttpApi.LOGIN), mGson.toJson(input))
        return mGson.fromJson(data, LoginOutput::class.java)
    }
}