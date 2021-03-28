package com.example.headphonecontroller.permissions

import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class CustomPermissionListener(val callback: (Boolean) -> Unit) : PermissionListener {

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        callback(true)
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        callback(false)
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken
    ) {
        token.continuePermissionRequest()
    }
}
