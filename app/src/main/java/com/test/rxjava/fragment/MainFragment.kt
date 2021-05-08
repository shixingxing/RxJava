package com.test.rxjava.fragment

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.PermissionChecker
import androidx.navigation.Navigation
import com.test.rxjava.BaseFragment
import com.test.rxjava.CameraXActivity
import com.test.rxjava.CaptureActivity
import com.test.rxjava.databinding.FragmentMainBinding

class MainFragment : BaseFragment() {

    companion object {
        const val REQUEST_CAMERA = 1000
    }

    private lateinit var mBinding: FragmentMainBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentMainBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mBinding.sample1.setOnClickListener {
//            Navigation.findNavController(v).navigate(MainFragmentDirections.sample1Action())
            context?.let {
                if (PermissionChecker.checkSelfPermission(it, Manifest.permission.CAMERA) != PermissionChecker.PERMISSION_GRANTED) {
                    requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA)
                } else {
                    startActivity(Intent(it, CaptureActivity::class.java))
                }
            }

        }
        mBinding.sample2.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(MainFragmentDirections.sample2Action())
        }
        mBinding.sample3.setOnClickListener { v ->
//            Navigation.findNavController(v).navigate(MainFragmentDirections.sample3Action())
            context?.let {
                startActivity(Intent(it, CameraXActivity::class.java))
            }
        }
        mBinding.sample4.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(MainFragmentDirections.sample4Action())
        }
        mBinding.sample5.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(MainFragmentDirections.sample5Action())
        }
        mBinding.sample6.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(MainFragmentDirections.sample6Action())
        }
        mBinding.sample7.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(MainFragmentDirections.sample7Action())
        }
        mBinding.sample8.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(MainFragmentDirections.sample8Action())
        }
        mBinding.sample9.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(MainFragmentDirections.sample9Action())
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA) {
            for (result in grantResults) {
                if (result != PermissionChecker.PERMISSION_GRANTED) {
                    Toast.makeText(context, "Permission Error", Toast.LENGTH_LONG).show()
                    return
                }
            }
            startActivity(Intent(context, CaptureActivity::class.java))
        }
    }
}