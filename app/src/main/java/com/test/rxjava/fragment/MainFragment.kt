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
import com.test.rxjava.CaptureActivity
import com.test.rxjava.R
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {

    companion object {
        const val REQUEST_CAMERA = 1000
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sample1.setOnClickListener { v ->
//            Navigation.findNavController(v).navigate(MainFragmentDirections.sample1Action())
            context?.let {
                if (PermissionChecker.checkSelfPermission(it, Manifest.permission.CAMERA) != PermissionChecker.PERMISSION_GRANTED) {
                    requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA)
                } else {
                    startActivity(Intent(it, CaptureActivity::class.java))
                }
            }

        }
        sample2.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(MainFragmentDirections.sample2Action())
        }
        sample3.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(MainFragmentDirections.sample3Action())
        }
        sample4.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(MainFragmentDirections.sample4Action())
        }
        sample5.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(MainFragmentDirections.sample5Action())
        }
        sample6.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(MainFragmentDirections.sample6Action())
        }
        sample7.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(MainFragmentDirections.sample7Action())
        }
        sample8.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(MainFragmentDirections.sample8Action())
        }
        sample9.setOnClickListener { v ->
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