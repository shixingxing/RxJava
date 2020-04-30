package com.test.rxjava.utils;

import android.util.Log;

import com.test.rxjava.im.ConnectUtils;
import com.test.rxjava.im.ServiceHandler;
import com.test.rxjava.im.codec.FrameCodecFactory;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.net.InetSocketAddress;

public class MinaServer {

    private static final String TAG = MinaServer.class.getSimpleName();

    IoAcceptor acceptor;

    public void start() {
        try {
            // 创建一个非阻塞的server端的Socket
            acceptor = new NioSocketAcceptor();
            // 为接收器设置管理服务
            acceptor.setHandler(new ServiceHandler());
            acceptor.getFilterChain().addLast("encoder", new ProtocolCodecFilter(new FrameCodecFactory()));
            // 自定义的编解码器
            acceptor.getFilterChain().addLast("decoder", new ProtocolCodecFilter(new FrameCodecFactory()));
            // 设置读取数据的换从区大小
            acceptor.getSessionConfig().setReadBufferSize(2048);
            // 读写通道10秒内无操作进入空闲状态
            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30);
            // 绑定端口
            acceptor.bind(new InetSocketAddress(ConnectUtils.PORT));
            Log.i(TAG, "服务器启动成功... 端口号：" + ConnectUtils.PORT);
        } catch (Exception e) {
            Log.i(TAG, "服务器启动异常..." + e);
        }
    }

    public void stop() {
        if (acceptor != null) {
            acceptor.unbind(new InetSocketAddress(ConnectUtils.PORT));
        }
    }
}
