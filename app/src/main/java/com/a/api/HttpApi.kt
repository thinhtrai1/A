package com.a.api

import android.text.TextUtils
import com.a.api.models.LoginOutput
import com.example.baseproject.api.ApiException
import com.a.utils.Constants
import com.a.utils.SharedPreferenceHelper
import com.example.baseproject.utils.StringUtils
import com.google.gson.Gson
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection
import java.util.ArrayList
import java.util.HashMap
import java.util.LinkedHashMap

class HttpApi {
    private var mToken: String? = null

    companion object {
        private const val CHARSET = "UTF-8"
        private const val CONNECT_TIME_OUT = 25000
        private const val READ_TIME_OUT = 25000
        private const val LINE_FEED = "\r\n"
        private const val BOUNDARY = "*****"
        private const val TWO_HYPHENS = "--"

        const val TASK_WS = "http://124.47.187.247:82/api/"
        const val LOGIN = "login/"
        const val GET_PROFILE = "user/get"
    }

    private fun createHeaderWithAuthorization(): Map<String, String>? {
        return if (!mToken.isNullOrEmpty()) {
            val map = LinkedHashMap<String, String>()
            map["Content-Type"] = "application/json"
            //            map["Content-Type"] = "application/x-www-form-urlencoded"
            map["Accept"] = "application/json"
            map["X-Authorization"] = "Bearer $mToken"
            map["Authorization"] = "Bearer $mToken"
            map
        } else null
    }

    fun setCredentials(token: String?) {
        mToken = token
    }

    fun doHttpPost(requestUrl: String, jsonObject: String): String {
        var connection = prepareConnection(requestUrl)
        connection.requestMethod = "POST"
        connection.doOutput = true
        connection.doInput = true
        connection.setRequestProperty("Content-Type", "application/json; charset=$CHARSET")
        connection.setRequestProperty(
            "Content-Length",
            "" + jsonObject.toByteArray().size.toString()
        )
        connection.addHeaderFields()
        PrintWriter(OutputStreamWriter(connection.outputStream, CHARSET), true).apply {
            append(jsonObject)
            flush()
            close()
        }
        return try {
            executeHttpRequest(connection)
        } catch (e: Exception) {
            if (e is ApiException && e.errorCode == Constants.TOKEN_EXPIRED && getNewSession()) {
                connection = prepareConnection(requestUrl)
                connection.requestMethod = "POST"
                connection.doOutput = true
                connection.doInput = true
                connection.setRequestProperty("Content-Type", "application/json; charset=$CHARSET")
                connection.setRequestProperty(
                    "Content-Length",
                    "" + jsonObject.toByteArray().size.toString()
                )
                connection.addHeaderFields()
                PrintWriter(OutputStreamWriter(connection.outputStream, CHARSET), true).apply {
                    append(jsonObject)
                    flush()
                    close()
                }
                executeHttpRequest(connection)
            } else {
                throw e
            }
        }
    }

    fun doHttpPut(requestUrl: String, jsonObject: String): String {
        var connection = prepareConnection(requestUrl)
        connection.requestMethod = "PUT"
        connection.doOutput = true
        connection.doInput = true
        connection.setRequestProperty("Content-Type", "application/json; charset=$CHARSET")
        connection.setRequestProperty(
            "Content-Length", "" + jsonObject.toByteArray().size.toString()
        )
        connection.addHeaderFields()
        PrintWriter(OutputStreamWriter(connection.outputStream, CHARSET), true).apply {
            append(jsonObject)
            flush()
            close()
        }
        return try {
            executeHttpRequest(connection)
        } catch (e: Exception) {
            if (e is ApiException && e.errorCode == Constants.TOKEN_EXPIRED && getNewSession()) {
                connection = prepareConnection(requestUrl)
                connection.requestMethod = "PUT"
                connection.doOutput = true
                connection.doInput = true
                connection.setRequestProperty("Content-Type", "application/json; charset=$CHARSET")
                connection.setRequestProperty(
                    "Content-Length", "" + jsonObject.toByteArray().size.toString()
                )
                connection.addHeaderFields()
                PrintWriter(OutputStreamWriter(connection.outputStream, CHARSET), true).apply {
                    append(jsonObject)
                    flush()
                    close()
                }
                executeHttpRequest(connection)
            } else {
                throw e
            }
        }
    }

    fun doHttpPut(requestUrl: String, params: Map<String, String>): String {
        val urlParams = StringUtils.getParamsRequest(params)
        val postDataBytes = urlParams.toByteArray()

        var connection = prepareConnection(requestUrl)
        connection.requestMethod = "PUT"
        connection.doOutput = true
        connection.doInput = true
        connection.setRequestProperty("Accept", "*/*")
        connection.setRequestProperty(
            "Content-Type",
            "application/x-www-form-urlencoded; charset=$CHARSET"
        )
        connection.setRequestProperty("Content-Length", postDataBytes.size.toString())
        connection.addHeaderFields()
        if (urlParams.isNotEmpty()) {
            connection.outputStream.write(postDataBytes)
            //            PrintWriter writer = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), CHARSET), true);
            //            writer.append(urlParams);
            //            writer.flush();
            //            writer.close();
        }
        return try {
            executeHttpRequest(connection)
        } catch (e: Exception) {
            if (e is ApiException && e.errorCode == Constants.TOKEN_EXPIRED && getNewSession()) {
                connection = prepareConnection(requestUrl)
                connection.requestMethod = "PUT"
                connection.doOutput = true
                connection.doInput = true
                connection.setRequestProperty("Accept", "*/*")
                connection.setRequestProperty(
                    "Content-Type",
                    "application/x-www-form-urlencoded; charset=$CHARSET"
                )
                connection.setRequestProperty("Content-Length", postDataBytes.size.toString())
                connection.addHeaderFields()
                if (urlParams.isNotEmpty()) {
                    connection.outputStream.write(postDataBytes)
                    //            PrintWriter writer = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), CHARSET), true);
                    //            writer.append(urlParams);
                    //            writer.flush();
                    //            writer.close();
                }
                executeHttpRequest(connection)
            } else {
                throw e
            }
        }
    }

    fun doHttpPatch(requestUrl: String, jsonObject: String): String {
        var connection = prepareConnection(requestUrl)
        connection.requestMethod = "PATCH"
        connection.doOutput = true
        connection.doInput = true
        connection.setRequestProperty("Content-Type", "application/json; charset=$CHARSET")
        connection.setRequestProperty(
            "Content-Length",
            "" + jsonObject.toByteArray().size.toString()
        )
        connection.addHeaderFields()
        PrintWriter(OutputStreamWriter(connection.outputStream, CHARSET), true).apply {
            append(jsonObject)
            flush()
            close()
        }
        return try {
            executeHttpRequest(connection)
        } catch (e: Exception) {
            if (e is ApiException && e.errorCode == Constants.TOKEN_EXPIRED && getNewSession()) {
                connection = prepareConnection(requestUrl)
                connection.requestMethod = "PATCH"
                connection.doOutput = true
                connection.doInput = true
                connection.setRequestProperty("Content-Type", "application/json; charset=$CHARSET")
                connection.setRequestProperty(
                    "Content-Length",
                    "" + jsonObject.toByteArray().size.toString()
                )
                connection.addHeaderFields()
                PrintWriter(OutputStreamWriter(connection.outputStream, CHARSET), true).apply {
                    append(jsonObject)
                    flush()
                    close()
                }
                executeHttpRequest(connection)
            } else {
                throw e
            }
        }
    }

    fun doHttpPost(requestUrl: String, params: Map<String, String?>): String {
        val urlParams = StringUtils.getParamsRequest(params)
        val postDataBytes = urlParams.toByteArray()

        var connection = prepareConnection(requestUrl)
        connection.requestMethod = "POST"
        connection.doOutput = true
        connection.doInput = true
        connection.setRequestProperty("Accept", "*/*")
        connection.setRequestProperty(
            "Content-Type",
            "application/x-www-form-urlencoded; charset=$CHARSET"
        )
        connection.setRequestProperty("Content-Length", postDataBytes.size.toString())
        connection.addHeaderFields()
        if (urlParams.isNotEmpty()) {
            connection.outputStream.write(postDataBytes)
            //            PrintWriter writer = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), CHARSET), true);
            //            writer.append(urlParams);
            //            writer.flush();
            //            writer.close();
        }
        return try {
            executeHttpRequest(connection)
        } catch (e: Exception) {
            if (e is ApiException && e.errorCode == Constants.TOKEN_EXPIRED && getNewSession()) {
                connection = prepareConnection(requestUrl)
                connection.requestMethod = "POST"
                connection.doOutput = true
                connection.doInput = true
                connection.setRequestProperty("Accept", "*/*")
                connection.setRequestProperty(
                    "Content-Type",
                    "application/x-www-form-urlencoded; charset=$CHARSET"
                )
                connection.setRequestProperty("Content-Length", postDataBytes.size.toString())
                connection.addHeaderFields()
                if (urlParams.isNotEmpty()) {
                    connection.outputStream.write(postDataBytes)
                    //            PrintWriter writer = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), CHARSET), true);
                    //            writer.append(urlParams);
                    //            writer.flush();
                    //            writer.close();
                }
                executeHttpRequest(connection)
            } else {
                throw e
            }
        }
    }

    fun doHttpGet(requestUrl: String, params: Map<String, String>?): String {
        var urlParams: String? = null
        if (params != null) urlParams = StringUtils.getParamsRequest(params)
        val queryUrl = if (TextUtils.isEmpty(urlParams)) requestUrl else "$requestUrl?$urlParams"
        var connection = prepareConnection(queryUrl)
        connection.requestMethod = "GET"
        connection.addHeaderFields()
        return try {
            executeHttpRequest(connection)
        } catch (e: Exception) {
            if (e is ApiException && e.errorCode == Constants.TOKEN_EXPIRED && getNewSession()) {
                connection = prepareConnection(queryUrl)
                connection.requestMethod = "GET"
                connection.addHeaderFields()
                executeHttpRequest(connection)
            } else {
                throw e
            }
        }
    }

    fun doHttpGet(requestUrl: String): String {
        var connection = prepareConnection(requestUrl)
        connection.requestMethod = "GET"
        connection.addHeaderFields()
        return try {
            executeHttpRequest(connection)
        } catch (e: Exception) {
            if (e is ApiException && e.errorCode == Constants.TOKEN_EXPIRED && getNewSession()) {
                connection = prepareConnection(requestUrl)
                connection.requestMethod = "GET"
                connection.addHeaderFields()
                executeHttpRequest(connection)
            } else {
                throw e
            }
        }
    }

    fun doHttpMultipart2(
        requestUrl: String,
        params: Map<String, String>?,
        files: Map<String, File>?
    ): String {
        var connection = prepareConnection(requestUrl)
        connection.requestMethod = "POST"
        connection.doInput = true
        connection.doOutput = true
        connection.setRequestProperty("Connection", "Keep-Alive")
        connection.setRequestProperty("Cache-Control", "no-cache")
        connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=$BOUNDARY")
        connection.addHeaderFields()
        val outputStream = connection.outputStream
        val writer = PrintWriter(OutputStreamWriter(outputStream, CHARSET), true)
        if (params != null) addFormFields(writer, params)
        if (files != null) addFileParts(writer, outputStream, files)
        writer.append(TWO_HYPHENS + BOUNDARY + TWO_HYPHENS).append(LINE_FEED)
        writer.flush()
        writer.close()
        return try {
            executeHttpRequest(connection)
        } catch (e: Exception) {
            if (e is ApiException && e.errorCode == Constants.TOKEN_EXPIRED && getNewSession()) {
                connection = prepareConnection(requestUrl)
                connection.requestMethod = "POST"
                connection.doInput = true
                connection.doOutput = true
                connection.setRequestProperty("Connection", "Keep-Alive")
                connection.setRequestProperty("Cache-Control", "no-cache")
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=$BOUNDARY")
                connection.addHeaderFields()
                val output = connection.outputStream
                val write = PrintWriter(OutputStreamWriter(output, CHARSET), true)
                if (params != null) addFormFields(write, params)
                if (files != null) addFileParts(write, output, files)
                write.append(TWO_HYPHENS + BOUNDARY + TWO_HYPHENS).append(LINE_FEED)
                write.flush()
                write.close()
                executeHttpRequest(connection)
            } else {
                throw e
            }
        }
    }

    fun doHttpMultipart(
        requestUrl: String,
        params: Map<String, String>?,
        files: Map<String, File>?
    ): String {
        var connection = prepareConnection(requestUrl)
        connection.requestMethod = "POST"
        connection.doOutput = true
        connection.doInput = true
        connection.setRequestProperty("Connection", "Keep-Alive")
        connection.setRequestProperty("Cache-Control", "no-cache")
        connection.setRequestProperty("Accept", "*/*")
        connection.setRequestProperty(
            "Content-Type",
            "multipart/form-data; charset=$CHARSET; boundary=$BOUNDARY"
        )
        connection.addHeaderFields()
        val outputStream = connection.outputStream
        val dataStream = DataOutputStream(outputStream)
        if (params != null) addFormFields(dataStream, params)
        if (files != null && files.isNotEmpty()) addFileParts(dataStream, outputStream, files)
        dataStream.writeBytes(TWO_HYPHENS + BOUNDARY + TWO_HYPHENS + LINE_FEED)
        dataStream.flush()
        dataStream.close()
        return try {
            executeHttpRequest(connection)
        } catch (e: Exception) {
            if (e is ApiException && e.errorCode == Constants.TOKEN_EXPIRED && getNewSession()) {
                connection = prepareConnection(requestUrl)
                connection.requestMethod = "POST"
                connection.doOutput = true
                connection.doInput = true
                connection.setRequestProperty("Connection", "Keep-Alive")
                connection.setRequestProperty("Cache-Control", "no-cache")
                connection.setRequestProperty("Accept", "*/*")
                connection.setRequestProperty(
                    "Content-Type",
                    "multipart/form-data; charset=$CHARSET; boundary=$BOUNDARY"
                )
                connection.addHeaderFields()
                val output = connection.outputStream
                val data = DataOutputStream(output)
                if (params != null) addFormFields(data, params)
                if (files != null && files.isNotEmpty()) addFileParts(
                    data,
                    output,
                    files
                )
                data.writeBytes(TWO_HYPHENS + BOUNDARY + TWO_HYPHENS + LINE_FEED)
                data.flush()
                data.close()
                executeHttpRequest(connection)
            } else {
                throw e
            }
        }
    }

    fun doHttpMultipartPut(
        requestUrl: String,
        params: Map<String, String>?,
        files: Map<String, File>?
    ): String {
        var connection = prepareConnection(requestUrl)
        connection.requestMethod = "PUT"
        connection.doOutput = true
        connection.doInput = true
        connection.setRequestProperty("Connection", "Keep-Alive")
        connection.setRequestProperty("Cache-Control", "no-cache")
        connection.setRequestProperty("Accept", "*/*")
        connection.setRequestProperty(
            "Content-Type",
            "multipart/form-data; charset=$CHARSET; boundary=$BOUNDARY"
        )
        connection.addHeaderFields()
        val outputStream = connection.outputStream
        val dataStream = DataOutputStream(outputStream)
        if (params != null) addFormFields(dataStream, params)
        if (files != null && files.isNotEmpty()) addFileParts(dataStream, outputStream, files)
        dataStream.writeBytes(TWO_HYPHENS + BOUNDARY + TWO_HYPHENS + LINE_FEED)
        dataStream.flush()
        dataStream.close()
        return try {
            executeHttpRequest(connection)
        } catch (e: Exception) {
            if (e is ApiException && e.errorCode == Constants.TOKEN_EXPIRED && getNewSession()) {
                connection = prepareConnection(requestUrl)
                connection.requestMethod = "PUT"
                connection.doOutput = true
                connection.doInput = true
                connection.setRequestProperty("Connection", "Keep-Alive")
                connection.setRequestProperty("Cache-Control", "no-cache")
                connection.setRequestProperty("Accept", "*/*")
                connection.setRequestProperty(
                    "Content-Type",
                    "multipart/form-data; charset=$CHARSET; boundary=$BOUNDARY"
                )
                connection.addHeaderFields()
                val output = connection.outputStream
                val data = DataOutputStream(output)
                if (params != null) addFormFields(data, params)
                if (files != null && files.isNotEmpty()) addFileParts(
                    data,
                    output,
                    files
                )
                data.writeBytes(TWO_HYPHENS + BOUNDARY + TWO_HYPHENS + LINE_FEED)
                data.flush()
                data.close()
                executeHttpRequest(connection)
            } else {
                throw e
            }
        }
    }

    fun doHttpMultipartImages(
        requestUrl: String,
        params: Map<String, String>?,
        files: ArrayList<File>?
    ): String {
        var connection = prepareConnection(requestUrl)
        connection.requestMethod = "POST"
        connection.doOutput = true
        connection.doInput = true
        connection.setRequestProperty("Connection", "Keep-Alive")
        connection.setRequestProperty("Cache-Control", "no-cache")
        connection.setRequestProperty("Accept", "*/*")
        connection.setRequestProperty(
            "Content-Type",
            "multipart/form-data; charset=$CHARSET; boundary=$BOUNDARY"
        )
        connection.addHeaderFields()
        val outputStream = connection.outputStream
        val dataStream = DataOutputStream(outputStream)
        if (params != null) addFormFields(dataStream, params)
        if (files != null && files.size > 0) addFileImagesParts(dataStream, outputStream, files)
        dataStream.writeBytes(TWO_HYPHENS + BOUNDARY + TWO_HYPHENS + LINE_FEED)
        dataStream.flush()
        dataStream.close()
        return try {
            executeHttpRequest(connection)
        } catch (e: Exception) {
            if (e is ApiException && e.errorCode == Constants.TOKEN_EXPIRED && getNewSession()) {
                connection = prepareConnection(requestUrl)
                connection.requestMethod = "POST"
                connection.doOutput = true
                connection.doInput = true
                connection.setRequestProperty("Connection", "Keep-Alive")
                connection.setRequestProperty("Cache-Control", "no-cache")
                connection.setRequestProperty("Accept", "*/*")
                connection.setRequestProperty(
                    "Content-Type",
                    "multipart/form-data; charset=$CHARSET; boundary=$BOUNDARY"
                )
                connection.addHeaderFields()
                val output = connection.outputStream
                val data = DataOutputStream(output)
                if (params != null) addFormFields(data, params)
                if (files != null && files.size > 0) addFileImagesParts(
                    data,
                    output,
                    files
                )
                data.writeBytes(TWO_HYPHENS + BOUNDARY + TWO_HYPHENS + LINE_FEED)
                data.flush()
                data.close()
                executeHttpRequest(connection)
            } else {
                throw e
            }
        }
    }

    private fun prepareConnection(requestUrl: String): HttpURLConnection {
        val url = URL(requestUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.setRequestProperty("connection", "close")
        connection.connectTimeout = CONNECT_TIME_OUT
        connection.readTimeout = READ_TIME_OUT
        connection.useCaches = false
        return connection
    }

    private fun executeHttpRequest(connection: HttpURLConnection): String {
        val response = StringBuilder()
        val status = connection.responseCode
        if (status.toString() == Constants.TOKEN_EXPIRED) {
            throw ApiException(status.toString(), "")
        }
        try {
            val reader =
                BufferedReader(InputStreamReader(if (status == HttpURLConnection.HTTP_OK) connection.inputStream else connection.errorStream))
            for (line in reader.readLines()) {
                response.append(line)
            }
            reader.close()
            connection.disconnect()
        } catch (ex: Exception) {
            throw ApiException(
                status.toString(),
                "Error: " + ex.message + ". Server returned non-OK status: " + status
            )
        }
        return response.toString()
    }

    private fun HttpURLConnection.addHeaderFields() {
        createHeaderWithAuthorization()?.let {
            for (entry in it.entries) {
                setRequestProperty(entry.key, entry.value)
            }
        }
    }

    private fun addFormFields(writer: PrintWriter, params: Map<String, String>) {
        for (entry in params.entries) {
            addFormField(writer, entry.key, entry.value)
        }
    }

    private fun addFormFields(stream: DataOutputStream, params: Map<String, String>) {
        for (entry in params.entries) {
            addFormField(stream, entry.key, entry.value)
        }
    }

    private fun addFileParts(
        writer: PrintWriter,
        outputStream: OutputStream,
        files: Map<String, File>
    ) {
        for (entry in files.entries) {
//            if (entry.value != null && entry.value.exists())
            addFilePart(writer, outputStream, entry.key, entry.value)
        }
    }

    private fun addFileParts(
        stream: DataOutputStream,
        outputStream: OutputStream,
        files: Map<String, File>
    ) {
        for (entry in files.entries) {
//            if (entry.value != null && entry.value.exists())
            addFilePart(stream, outputStream, entry.key, entry.value)
        }
    }

    private fun addFileImagesParts(
        stream: DataOutputStream,
        outputStream: OutputStream,
        images: ArrayList<File>
    ) {
        for (item in images) {
            addFilePart(stream, outputStream, "ImageFiles", item)
        }
    }

    private fun addFormField(writer: PrintWriter, name: String, value: String) {
        writer.append(TWO_HYPHENS + BOUNDARY).append(LINE_FEED)
        writer.append("Content-Disposition: form-data; name=\"$name\"").append(LINE_FEED)
        writer.append("Content-Type: text/plain; charset=$CHARSET").append(LINE_FEED)
        writer.append(LINE_FEED)
        writer.append(value).append(LINE_FEED)
        writer.flush()
    }

    private fun addFormField(dataStream: DataOutputStream, name: String, value: String?) {
        if (value != null && value != "null") {
            dataStream.writeBytes(TWO_HYPHENS + BOUNDARY + LINE_FEED)
            dataStream.writeBytes("Content-Disposition: form-data; name=\"$name\"$LINE_FEED")
            dataStream.writeBytes(LINE_FEED)
            dataStream.write(value.toByteArray(charset(CHARSET)))
            dataStream.writeBytes(LINE_FEED)
            dataStream.flush()
        }
    }

    private fun addFilePart(
        stream: DataOutputStream,
        outputStream: OutputStream,
        fieldName: String,
        uploadFile: File
    ) {
        val fileName = uploadFile.name
        stream.writeBytes(TWO_HYPHENS + BOUNDARY + LINE_FEED)
        stream.writeBytes("Content-Disposition: form-data; name=\"$fieldName\"; filename=\"$fileName\"$LINE_FEED")
        stream.writeBytes("Content-Type: " + URLConnection.guessContentTypeFromName(fileName))
        stream.writeBytes(LINE_FEED)
        stream.writeBytes("Content-Transfer-Encoding: binary$LINE_FEED")
        stream.writeBytes(LINE_FEED)
        stream.flush()

        val inputStream = FileInputStream(uploadFile)
        val buffer = ByteArray(4096)
        while (inputStream.read(buffer) != -1) {
            outputStream.write(buffer, 0, inputStream.read(buffer))
        }
        outputStream.flush()
        inputStream.close()

        stream.writeBytes(LINE_FEED)
        stream.flush()
    }

    private fun addFilePart(
        writer: PrintWriter,
        outputStream: OutputStream,
        fieldName: String,
        uploadFile: File
    ) {
        val fileName = uploadFile.name
        writer.append("--$BOUNDARY").append(LINE_FEED)
        writer.append("Content-Disposition: form-data; name=\"$fieldName\"; filename=\"$fileName\"")
            .append(LINE_FEED)
        writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(fileName))
            .append(LINE_FEED)
        writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED)
        writer.append(LINE_FEED)
        writer.flush()

        val inputStream = FileInputStream(uploadFile)
        val buffer = ByteArray(4096)
        while (inputStream.read(buffer) != -1) {
            outputStream.write(buffer, 0, inputStream.read(buffer))
        }
        outputStream.flush()
        inputStream.close()

        writer.append(LINE_FEED)
        writer.flush()
    }

    private fun addFilePart(
        writer: PrintWriter,
        outputStream: OutputStream,
        fieldName: String,
        uploadFile: File,
        contentType: String
    ) {
        val fileName = uploadFile.name
        writer.append("--$BOUNDARY").append(LINE_FEED)
        writer.append(("Content-Disposition: form-data; name=\"$fieldName\"; filename=\"$fileName\""))
            .append(LINE_FEED)
        writer.append("Content-Type: $contentType").append(LINE_FEED)
        //		writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
        writer.append(LINE_FEED)
        writer.flush()

        val inputStream = FileInputStream(uploadFile)
        val buffer = ByteArray(4096)
        while (inputStream.read(buffer) != -1) {
            outputStream.write(buffer, 0, inputStream.read(buffer))
        }
        outputStream.flush()
        inputStream.close()

        writer.append(LINE_FEED)
        writer.flush()
    }

    private fun getNewSession(): Boolean {
        val mPrefHelper = SharedPreferenceHelper.getInstance
        val params = HashMap<String, String?>()
        params["email"] = mPrefHelper[Constants.PREF_USERNAME]
        params["password"] = mPrefHelper[Constants.PREF_PASSWORD]
        params["accountType"] = "Normal"
        params["googleId"] = ""
        params["facebookId"] = ""
        params["deviceType"] = "Android"
        params["token"] = "ádasdsa"

        val data = doHttpPost(TASK_WS + "login", Gson().toJson(params))
        val output = Gson().fromJson(data, LoginOutput::class.java)
        if (output.status) {
            mPrefHelper[Constants.PREF_TOKEN] = output.data?.jwtToken!!
            mToken = output.data.jwtToken
            return true
        }
        return false
    }
}