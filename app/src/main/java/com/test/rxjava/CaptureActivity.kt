package com.test.rxjava

import android.graphics.Bitmap
import android.widget.Toast
import com.google.zxing.Result
import com.google.zxing.client.android.CaptureActivity

class CaptureActivity : CaptureActivity() {

    override fun handleDecode(rawResult: Result?, barcode: Bitmap?, scaleFactor: Float) {
        super.handleDecode(rawResult, barcode, scaleFactor)
        Toast.makeText(this, rawResult?.text, Toast.LENGTH_LONG).show()
        //500毫秒后重新开始扫描
        restartPreviewAfterDelay(500)
    }

}