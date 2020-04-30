package com.test.rxjava.im;

public class ConnectUtils {

    public static final int REPEAT_TIME = 5;//表示重连次数
    public static final String HOST = "127.0.0.1";//表示IP地址
    public static final int PORT = 3347;//表示端口号
    public static final int IDLE_TIME = 10;//客户端10s内没有向服务端发送数据
    public static final int TIMEOUT = 5;//设置连接超时时间,超过5s还没连接上便抛出异常


}
