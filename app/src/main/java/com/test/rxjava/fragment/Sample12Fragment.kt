package com.test.rxjava.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.provider.Telephony.Sms
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.test.rxjava.BaseFragment
import com.test.rxjava.R
import com.test.rxjava.databinding.FragmentSample12Binding
import com.test.rxjava.model.SmsDto

class Sample12Fragment : BaseFragment() {

    private var binding: FragmentSample12Binding? = null

    private val permissions = arrayOf(
        Manifest.permission.READ_SMS,
        Manifest.permission.RECEIVE_SMS,
    )

    private val launch = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {

        val iterator = it.iterator()
        iterator.forEach { item ->
            if (!item.value) {
                Toast.makeText(context, "permission error:" + item.key, Toast.LENGTH_LONG).show()
                return@registerForActivityResult
            }
        }

        startQuery()
    }

    private val adapter = Adapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSample12Binding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launch.launch(permissions)

        binding?.recycler?.layoutManager = LinearLayoutManager(context)
        binding?.recycler?.adapter = adapter

        adapter.setOnItemClickListener { _, _, position ->
            val item = adapter.getItem(position)
            startEdit(item)
        }
    }

    @SuppressLint("Range")
    private fun startQuery() {
        val cr = context?.contentResolver
        val projection = arrayOf(Sms._ID, Sms.ADDRESS, Sms.PERSON, Sms.BODY, Sms.DATE)
        val cursor: Cursor? = cr?.query(Sms.CONTENT_URI, projection, null, null, Sms.DEFAULT_SORT_ORDER)

        val list = mutableListOf<SmsDto>()
        while (cursor?.moveToNext() == true) {
            val id = cursor.getString(cursor.getColumnIndex(projection[0]))
            val address = cursor.getString(cursor.getColumnIndex(projection[1]))
            val body = cursor.getString(cursor.getColumnIndex(projection[3]))

            list.add(SmsDto(id, address, body))

        }

        cursor?.close()
        adapter.setNewInstance(list)

    }

    private fun startEdit(item: SmsDto) {


    }
}

class Adapter : BaseQuickAdapter<SmsDto, BaseViewHolder> {
    constructor() : super(R.layout.layout_sms_item)

    override fun convert(holder: BaseViewHolder, item: SmsDto) {
        val showStr = item.address + ":" + item.body
        holder.setText(R.id.sms_body, showStr)
    }
}