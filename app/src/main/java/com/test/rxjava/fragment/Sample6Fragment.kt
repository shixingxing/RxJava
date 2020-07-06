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
import androidx.core.content.PermissionChecker
import com.test.rxjava.BaseFragment
import com.test.rxjava.R
import kotlinx.android.synthetic.main.fragment_sample6.*

class Sample6Fragment : BaseFragment() {

    val TAG = Sample6Fragment::class.java.simpleName

    lateinit var bluetoothAdapter: BluetoothAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sample6, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        blue_tooth.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                if (!bluetoothAdapter.isEnabled) {
                    bluetoothAdapter.enable()
                }
            } else {
                if (bluetoothAdapter.isEnabled) {
                    bluetoothAdapter.disable()
                }
            }
        }

        scan.setOnClickListener { scanDevice() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (PermissionChecker.checkSelfPermission(requireActivity(), Manifest.permission.BLUETOOTH)
                == PermissionChecker.PERMISSION_GRANTED && PermissionChecker.checkSelfPermission(requireActivity(), Manifest.permission.BLUETOOTH_ADMIN)
                == PermissionChecker.PERMISSION_GRANTED && PermissionChecker.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PermissionChecker.PERMISSION_GRANTED) {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            //设置当前状态
            blue_tooth.isChecked = bluetoothAdapter.isEnabled
        } else {
            //还没有权限
            requestPermissions(arrayOf(Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_FINE_LOCATION), 2333)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 2333) {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            //设置当前状态
            blue_tooth.isChecked = bluetoothAdapter.isEnabled
        }
    }

    fun scanDevice() {
        startScan()
    }

    fun startScan() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            bluetoothAdapter.bluetoothLeScanner.startScan(scanCallback)
        } else {
            bluetoothAdapter.startLeScan(leScanner)

        }
    }

    fun stopScan() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            bluetoothAdapter.bluetoothLeScanner.stopScan(scanCallback)
        } else {
            bluetoothAdapter.stopLeScan(leScanner)

        }
    }

    private val leScanner = object : BluetoothAdapter.LeScanCallback {

        override fun onLeScan(device: BluetoothDevice?, rssi: Int, scanRecord: ByteArray?) {
            Log.i(TAG, device?.name)
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