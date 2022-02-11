package com.test.rxjava.fragment

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.test.rxjava.BaseFragment
import com.test.rxjava.databinding.FragmentSample6Binding

class Sample6Fragment : BaseFragment() {

    val TAG = Sample6Fragment::class.java.simpleName

    private var bluetoothAdapter: BluetoothAdapter? = null
    private lateinit var mBinding: FragmentSample6Binding

    private val permissions = arrayOf(
        Manifest.permission.BLUETOOTH,
        Manifest.permission.BLUETOOTH_ADMIN,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    private val launch =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {

            val iterator = it.iterator()
            iterator.forEach { item ->
                if (!item.value) {
                    Toast.makeText(context, "permission error:" + item.key, Toast.LENGTH_LONG)
                        .show()
                    return@registerForActivityResult
                }
            }

            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            //设置当前状态
            mBinding.blueTooth.isChecked = bluetoothAdapter?.isEnabled ?: false
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSample6Binding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launch.launch(permissions)
        mBinding.blueTooth.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (bluetoothAdapter?.isEnabled != true) {
                    bluetoothAdapter?.enable()
                }
            } else {
                if (bluetoothAdapter?.isEnabled == true) {
                    bluetoothAdapter?.disable()
                }
            }
        }

        mBinding.scan.setOnClickListener { scanDevice() }
    }

    fun scanDevice() {
        startScan()
    }

    fun startScan() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            bluetoothAdapter?.bluetoothLeScanner?.startScan(scanCallback)
        } else {
            bluetoothAdapter?.startLeScan(leScanner)
        }
    }

    fun stopScan() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            bluetoothAdapter?.bluetoothLeScanner?.stopScan(scanCallback)
        } else {
            bluetoothAdapter?.stopLeScan(leScanner)

        }
    }

    private val leScanner = object : BluetoothAdapter.LeScanCallback {

        override fun onLeScan(device: BluetoothDevice?, rssi: Int, scanRecord: ByteArray?) {
            Log.i(TAG, device?.name ?: "")
        }
    }

    private val scanCallback = object : ScanCallback() {
        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            super.onBatchScanResults(results)
            results?.forEach { Log.i(TAG, it.device.address + it.device?.name) }
        }

        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            Log.i(TAG, result?.device?.address + result?.device?.name)
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            if (errorCode == ScanCallback.SCAN_FAILED_APPLICATION_REGISTRATION_FAILED) {
                //扫描太多次了
            }
        }
    }
}