package com.andreydubovets.app

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.support.media.ExifInterface
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel


class FileManager {

    fun createQulixFolder() {
        val file = File(QULIX_DIRECTORY_PATH)

        file.exists().takeIf { !it }?.let {
            if (!file.mkdirs()) {
                Log.e("qulix", "Directory not created")
            }
        }
    }

    fun copyImageAndChangeData(context: Context, uri: Uri) {
        val source = uriToPath(context, uri)
        val destinationFile = File(QULIX_DIRECTORY_PATH, File(source).name)
        copyFile(source, QULIX_DIRECTORY_PATH)
        updateFileMetaData(destinationFile.path)
    }

    private fun uriToPath(context: Context, uri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(uri, proj, null, null, null)
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } finally {
            if (cursor != null) {
                cursor.close()
            }
        }
    }

    private fun copyFile(srcDir: String, dstDir: String) {
        try {
            val src = File(srcDir)
            val dst = File(dstDir, src.name)

            copyFile(src, dst)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @Throws(IOException::class)
    private fun copyFile(sourceFile: File, destFile: File) {
        if (!destFile.parentFile.exists())
            destFile.parentFile.mkdirs()

        if (!destFile.exists()) {
            destFile.createNewFile()
        }

        var source: FileChannel? = null
        var destination: FileChannel? = null

        try {
            source = FileInputStream(sourceFile).channel
            destination = FileOutputStream(destFile).channel
            destination!!.transferFrom(source, 0, source!!.size())
        } finally {
            if (source != null) {
                source.close()
            }
            if (destination != null) {
                destination.close()
            }
        }
    }

    fun updateFileMetaData(filePath: String) = try {
        val exifInterface = ExifInterface(filePath)
        exifInterface.setAttribute(ExifInterface.TAG_DATETIME, DATA_DATETIME)
        exifInterface.saveAttributes()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    fun getUriListFromQulixFolder(): List<Uri> {
        return File(QULIX_DIRECTORY_PATH).listFiles()
                .filter { it.isFile }
                .map { Uri.fromFile(it) }
                .toList()
    }

    companion object {
        private const val FOLDER_NAME = "qulix"
        private const val PICTURES_FOLDER_NAME = "Pictures"

        val QULIX_DIRECTORY_PATH = (
                Environment.getExternalStorageDirectory().toString()
                + File.separator
                + PICTURES_FOLDER_NAME
                + File.separator
                + FOLDER_NAME
                )

        const val DATA_DATETIME = "2018-05-14 15:00:00"
    }
}