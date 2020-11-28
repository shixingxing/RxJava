package com.test.rxjava

import android.graphics.Bitmap
import com.google.zxing.Result
import com.google.zxing.client.android.CaptureActivity

class CaptureActivity : CaptureActivity() {

    override fun handleDecode(rawResult: Result?, barcode: Bitmap?, scaleFactor: Float) {
        super.handleDecode(rawResult, barcode, scaleFactor)
    }

}