package com.text.rxjava;
import com.text.rxjava.ICMDCallBack;
// Declare any non-default types here with import statements

interface ICMDInterface {
    void registerCallback(ICMDCallBack callback);   //用于UI注册ICMDCallBack
    void unRegisterCallback(); //用于UI注销ICMDCallBack
//    void resetLiveStream();//重置直播流

    void push(in int offset,in int length);
    void initParcelFileDescriptor(in ParcelFileDescriptor fb);

}
