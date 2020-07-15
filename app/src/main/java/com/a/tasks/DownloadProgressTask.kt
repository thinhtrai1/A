package com.a.tasks

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.os.Environment
import com.a.utils.Constants
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class DownloadProgressTask(context: Context, private val dialog: ProgressDialog) : AsyncTask<String, Int, String>() {
    private val mFile = File.createTempFile("A_" + Constants.CURRENT_TIME, ".png", context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS))

    override fun onPreExecute() {
        super.onPreExecute()
        dialog.show()
    }

    override fun doInBackground(vararg f_url: String): String? {
        try {
            val url = URL(f_url[0])
            val connection = url.openConnection()
            connection.connect()
            val lengthOfFile = connection.contentLength
            val input = BufferedInputStream(url.openStream(), 8192)
            val output = FileOutputStream(mFile)

            val data = ByteArray(1024)

            var total: Long = 0

            var count = input.read(data)
            while (count != -1) {
                output.write(data, 0, count)
                total += count.toLong()
                publishProgress((total * 100 / lengthOfFile).toInt())
                count = input.read(data)
            }

            output.flush()
            output.close()
            input.close()
        } catch (e: Exception) { }

        return null
    }

    override fun onProgressUpdate(vararg progress: Int?) {
        if (progress[0] != null) {
            dialog.progress = progress[0]!!
        }
    }

    override fun onPostExecute(file_url: String?) {
        dialog.dismiss()
    }
}