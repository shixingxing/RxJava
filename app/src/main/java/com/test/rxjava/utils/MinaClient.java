package com.test.rxjava.utils;

import android.util.Log;

import com.test.rxjava.im.ConnectUtils;
import com.test.rxjava.im.HeartBeatHandler;
import com.test.rxjava.im.HeartBeatListener;
import com.test.rxjava.im.HeartBeatMessageFactory;
import com.test.rxjava.im.codec.FrameCodecFactory;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;

import io.reactivex.ObservableEmitter;

public class MinaClient {

    IoSession mSession;
    NioSocketConnector mSocketConnector;

    public void start() {
         mSocketConnector = new NioSocketConnector();
        //设置协议封装解析处理
        mSocketConnector.getFilterChain().addLast("protocol", new ProtocolCodecFilter(new FrameCodecFactory()));
        //设置心跳包
        KeepAliveFilter heartFilter = new KeepAliveFilter(new HeartBeatMessageFactory());
        //每 5 分钟发送一个心跳包
        heartFilter.setRequestInterval(5 * 60);
        //心跳包超时时间 10s
        heartFilter.setRequestTimeout(10);
        // 获取过滤器链
        DefaultIoFilterChainBuilder filterChain = mSocketConnector.getFilterChain();
        filterChain.addLast("encoder", new ProtocolCodecFilter(new FrameCodecFactory()));
        // 添加编码过滤器 处理乱码、编码问题
        filterChain.addLast("decoder", new ProtocolCodecFilter(new FrameCodecFactory()));
        mSocketConnector.getFilterChain().addLast("heartbeat", heartFilter);
        //设置 handler 处理业务逻辑
        mSocketConnector.setHandler(new HeartBeatHandler());
        mSocketConnector.addListener(new HeartBeatListener(mSocketConnector));
        //配置服务器地址
        InetSocketAddress mSocketAddress = new InetSocketAddress(ConnectUtils.HOST, ConnectUtils.PORT);
        RxUtil.io(new RxUtil.RxTask() {
            @Override
            public Object doSth(ObservableEmitter emitter, Object value) {
                //发起连接
                ConnectFuture mFuture = mSocketConnector.connect(mSocketAddress);
                mFuture.awaitUninterruptibly();
                mSession = mFuture.getSession();
                Log.d("", "======连接成功" + mSession.toString());
                while (true) {

                    mSession.write("你看见了吗\n");
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNext(Object value) {

            }
        });

    }

    public void stop() {
            if (mSocketConnector!=null){
                mSocketConnector.dispose();
            }
    }
}
