package com.test.rxjava.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import com.byox.drawview.enums.DrawingCapture
import com.test.rxjava.BaseFragment
import com.test.rxjava.databinding.FragmentSample8Binding
import com.test.rxjava.dialog.SaveBitmapDialog

public class Sample8Fragment : BaseFragment() {

    // CONSTANTS
    private val STORAGE_PERMISSIONS = 1000
    private lateinit var mBinding: FragmentSample8Binding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentSample8Binding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.save.setOnClickListener {
            requestPermissions()
        }
    }

    private fun saveDraw() {
        val saveBitmapDialog: SaveBitmapDialog = SaveBitmapDialog.newInstance()
        val createCaptureResponse: Array<Any> = mBinding.drawView.createCapture(DrawingCapture.BITMAP)
        saveBitmapDialog.setPreviewBitmap(createCaptureResponse[0] as Bitmap)
        saveBitmapDialog.setPreviewFormat(createCaptureResponse[1].toString())
        saveBitmapDialog.setOnSaveBitmapListener(object : SaveBitmapDialog.OnSaveBitmapListener {
            override fun onSaveBitmapCompleted() {
                Toast.makeText(activity, "Capture saved succesfully!", Toast.LENGTH_LONG).show()
            }

            override fun onSaveBitmapCanceled() {
                Toast.makeText(activity, "Capture saved canceled.", Toast.LENGTH_LONG).show()
            }
        })
        saveBitmapDialog.show(childFragmentManager, "saveBitmap")
    }

    private fun requestPermissions() {
        if (activity == null) {
            return
        }
        if (PermissionChecker.checkSelfPermission(requireActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    STORAGE_PERMISSIONS)
        } else {
            saveDraw()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSIONS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveDraw()
            } else {
                Toast.makeText(activity, "permission error", Toast.LENGTH_LONG).show()
            }
        }
    }
}