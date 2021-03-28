package com.example.headphonecontroller.permissions

import android.app.Activity
import android.util.Log
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport

private const val DEBUG_TAG = "AppPermission"

object AppPermission {

    /**
     * Wrappers available in {@link PermissionUtil}
     */
    fun request(activity: Activity, vararg permissions: String, callback: (Boolean) -> Unit) {
        when {
            permissions.isEmpty() -> {
                Log.e(DEBUG_TAG, "Cannot request permissions for an empty list")
            }
            permissions.size == 1 -> {
                requestSinglePermission(activity, permissions[0], callback)
            }
            else -> {
                requestMultiplePermissions(activity, *permissions, callback = callback)
            }
        }
    }

    private fun requestSinglePermission(
        activity: Activity,
        permission: String,
        callback: (Boolean) -> Unit
    ) {
        Dexter.withActivity(activity)
            .withPermission(permission)
            .withListener(CustomPermissionListener { success ->
                callback(success)
            })
            .check()
    }

    private fun requestMultiplePermissions(
        activity: Activity,
        vararg permission: String,
        callback: (Boolean) -> Unit
    ) {
        Dexter.withActivity(activity)
            .withPermissions(*permission)
            .withListener(object : CustomMultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    val success = (report != null && report.areAllPermissionsGranted())
                    callback.invoke(success)
                }
            })
            .check()
    }
}
