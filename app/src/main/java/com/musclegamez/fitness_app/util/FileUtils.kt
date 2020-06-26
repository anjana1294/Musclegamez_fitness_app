package com.musclegamez.fitness_app.util
import android.util.Base64
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException

/**
 * Created by root on 28/11/19.
 */
object FileUtils {

    fun convertFileToBase64EncodedString(filePath: String): String {
        try {
            return Base64.encodeToString(convertFileToByteArray(filePath), Base64.DEFAULT)
        } catch (e: Exception) {
            Timber.e(e)
        }
        return ""
    }

    fun convertFileToByteArray(filePath: String): ByteArray {
        val file = File(filePath.replace("file:", ""))
        try {
            val fis = FileInputStream(file)
            val bos = ByteArrayOutputStream()
            val buf = ByteArray(1024)
            try {
                var readNum: Int
                while ((fis.read(buf)) != -1) {
                    bos.write(buf, 0, fis.read(buf))
                }
            } catch (e: IOException) {
                Timber.e(e)
            }

            return bos.toByteArray()
        } catch (e: Exception) {
            Timber.e(e)
            return byteArrayOf()
        }

    }
}