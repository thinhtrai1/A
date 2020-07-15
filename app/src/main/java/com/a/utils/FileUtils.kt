package com.a.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import java.io.*
import kotlin.math.pow
import kotlin.math.sqrt

object FileUtils {

    fun createFile(context: Context): File? {
        return try {
            File.createTempFile("A_" + Constants.CURRENT_TIME, ".png", context.getExternalFilesDir(Environment.DIRECTORY_PICTURES))
        } catch (e: IOException) {
            null
        }
    }

    @Throws(IOException::class)
    fun getFile(context: Context, bitmap: Bitmap): File {
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "A_" + Constants.CURRENT_TIME + ".png")
        file.createNewFile()

        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos)
        val bitmapData = bos.toByteArray()

        FileOutputStream(file).apply {
            write(bitmapData)
            flush()
            close()
        }

        return file
    }

    fun decodeImage(context: Context, uri: Uri, requiredSize: Int): File? {
        try {
            val inputStream = context.contentResolver.openInputStream(uri)
            var options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream?.close()

            var scale = 1
            while (options.outWidth * options.outHeight * (1 / scale.toDouble().pow(2.0)) > requiredSize) {
                scale++
            }
            var resultBitmap: Bitmap
            if (scale > 1) {
                scale--
                options = BitmapFactory.Options()
                options.inSampleSize = scale
                resultBitmap = BitmapFactory.decodeStream(inputStream, null, options)!!

                val height = resultBitmap.height
                val width = resultBitmap.width

                val y = sqrt(requiredSize / (width.toDouble() / height))
                val x = y / height * width

                val scaledBitmap = Bitmap.createScaledBitmap(resultBitmap, x.toInt(), y.toInt(), true)

                resultBitmap.recycle()
                resultBitmap = scaledBitmap
                System.gc()
            } else {
                resultBitmap = BitmapFactory.decodeStream(inputStream)
            }
            inputStream!!.close()

            return getFile(context, resultBitmap)
        } catch (e: IOException) {
            return null
        }
    }
}