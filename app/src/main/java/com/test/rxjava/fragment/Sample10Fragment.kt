package com.test.rxjava.fragment

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.test.rxjava.BaseFragment
import com.test.rxjava.StartActivityForResultActivity
import com.test.rxjava.databinding.FragmentSample10Binding

class Sample10Fragment : BaseFragment() {

    private var binding: FragmentSample10Binding? = null

//    private val launcher = registerForActivityResult(Contract()) {
//        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
//    }

    private val launcher2 =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val code = it.resultCode
            val data = it.data
            if (code == Activity.RESULT_OK) {
                Toast.makeText(context, data?.getStringExtra("output"), Toast.LENGTH_LONG).show()
            }
        }

    private val launcher3 = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        Toast.makeText(context, "result:" + it, Toast.LENGTH_LONG).show()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSample10Binding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.startActivity?.setOnClickListener {
            launch()
        }

        binding?.requestPermissions?.setOnClickListener {
            requestPermissions()
        }
    }

    private fun launch() {

//        launcher.launch("abc")

        launcher2.launch(Intent(context, StartActivityForResultActivity::class.java).apply {
            putExtra("input", "abc")
        })
    }

    private fun requestPermissions() {
        launcher3.launch(Manifest.permission.CAMERA)
    }

    class Contract : ActivityResultContract<String, String>() {
        override fun createIntent(context: Context, input: String): Intent {
            return Intent(context, StartActivityForResultActivity::class.java).apply {
                putExtra("input", input)
            }
        }

        /** Convert result obtained from [Activity.onActivityResult] to O  */
        override fun parseResult(resultCode: Int, intent: Intent?): String {
            if (resultCode == Activity.RESULT_OK) {
                return intent?.getStringExtra("output") ?: ""
            }

            return ""
        }

    }
}