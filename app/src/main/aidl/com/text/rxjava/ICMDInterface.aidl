package com.text.rxjava;
import com.text.rxjava.ICMDCallBack;

interface ICMDInterface {
    void registerCallback(ICMDCallBack callback);   //用于UI注册ICMDCallBack
    void unRegisterCallback(); //用于UI注销ICMDCallBack

    void push(in int offset,in int length);
    void initParcelFileDescriptor(in ParcelFileDescriptor fb);

}
