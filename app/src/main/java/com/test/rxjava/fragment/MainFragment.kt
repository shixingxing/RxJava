package com.test.rxjava.fragment

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.Navigation
import com.test.rxjava.BaseFragment
import com.test.rxjava.CameraXActivity
import com.test.rxjava.CaptureActivity
import com.test.rxjava.databinding.FragmentMainBinding

class MainFragment : BaseFragment() {

    private val launchCameraCheck =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                startActivity(Intent(activity, CaptureActivity::class.java))
            } else {
                Toast.makeText(activity, "Permission Error", Toast.LENGTH_LONG).show()
            }
        }

    private lateinit var mBinding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMainBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.sample1.setOnClickListener {
            launchCameraCheck.launch(Manifest.permission.CAMERA)
        }
        mBinding.sample2.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(MainFragmentDirections.sample2Action())
        }
        mBinding.sample3.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(MainFragmentDirections.sample3Action())
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
        mBinding.sample10.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(MainFragmentDirections.sample10Action())
        }
        mBinding.sample11.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(MainFragmentDirections.sample11Action())
        }
        mBinding.sample12.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(MainFragmentDirections.sample12Action())
        }
        mBinding.sample13.setOnClickListener { v ->
            context?.let {
                startActivity(Intent(it, CameraXActivity::class.java))
            }
        }
    }

}