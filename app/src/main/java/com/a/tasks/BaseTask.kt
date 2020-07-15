package com.a.tasks

import android.os.AsyncTask
import com.a.api.HttpApi
import com.a.api.models.BaseOutput
import com.a.utils.Constants
import com.a.utils.SharedPreferenceHelper
import com.example.baseproject.api.ApiListener
import com.example.baseproject.api.ApiException
import com.google.gson.Gson

abstract class BaseTask<Output>(private val mListener: ApiListener<Output>?) :
    AsyncTask<Void, Exception, Output>() {
    private var mException: Exception? = null
    val mGson = Gson()
    val mHttpApi = HttpApi()

    override fun onPreExecute() {
        mListener?.onConnectionOpen(this)
        if (!SharedPreferenceHelper.getInstance[Constants.PREF_TOKEN].isNullOrEmpty()) {
            mHttpApi.setCredentials(SharedPreferenceHelper.getInstance[Constants.PREF_TOKEN])
        }
    }

    override fun doInBackground(vararg params: Void): Output? {
        return try {
//            callApiMethod()
            val result = callApiMethod()
            if ((result as BaseOutput).status) {
                result
            } else {
                mException = ApiException(
                    result.error?.code,
                    result.error?.message
                )
                null
            }
        } catch (e: Exception) {
            mException = e
            null
        }

    }

    override fun onPostExecute(output: Output) {
        if (mException == null) {
            mListener?.onConnectionSuccess(this, output)
        } else {
            mListener?.onConnectionError(this, mException!!)
        }
    }

    fun getFullUrl(subUrl: String) = HttpApi.TASK_WS + subUrl

    protected abstract fun callApiMethod(): Output
}