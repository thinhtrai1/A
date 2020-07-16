package com.a.tasks

import com.a.api.HttpApi
import com.a.api.models.BaseOutput
import com.a.api.ApiListener

class GetProfileTask(listener: ApiListener<Any>): BaseTask<Any>(listener) {
    override fun callApiMethod(): Any {
        val data = mHttpApi.doHttpGet(getFullUrl(HttpApi.GET_PROFILE))
        return mGson.fromJson(data, BaseOutput::class.java)
    }
}