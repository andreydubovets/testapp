package com.andreydubovets.testapp

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat


class StoragePermissionsManager(private val activity: Activity) {
    private var onPermissionGranted: () -> Unit = {}

    fun onRequestPermissionsResult(grantResults: IntArray) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) when {
            grantResults[0] == PackageManager.PERMISSION_GRANTED -> onPermissionGranted()
        }
    }

    fun requestPermissionsIfNeeded(onPermissionGranted: () -> Unit): Boolean {
        this.onPermissionGranted = onPermissionGranted
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return when (activity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                PackageManager.PERMISSION_GRANTED -> handlePermissionsGranted()
                PackageManager.PERMISSION_DENIED -> handlePermissionsNotGranted()
                else -> handlePermissionsNotGranted()
            }
        } else true
    }

    private fun handlePermissionsNotGranted(): Boolean {
        ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSIONS_REQUEST_CODE)
        return false
    }

    private fun handlePermissionsGranted(): Boolean {
        this.onPermissionGranted()
        return true
    }

    companion object {
        const val PERMISSIONS_REQUEST_CODE = 1
    }
}