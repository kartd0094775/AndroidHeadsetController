package com.example.headphonecontroller.permissions

import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

interface CustomMultiplePermissionsListener : MultiplePermissionsListener {

    override fun onPermissionsChecked(report: MultiplePermissionsReport?)

    override fun onPermissionRationaleShouldBeShown(
        request: List<PermissionRequest>,
        token: PermissionToken
    ) {
        token.continuePermissionRequest()
    }
}
