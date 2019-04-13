package com.test.rxjava.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;

import com.test.rxjava.utils.RxUtil;
import com.text.rxjava.ICMDCallBack;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import androidx.annotation.Nullable;
import io.reactivex.ObservableEmitter;
import io.reactivex.observers.DisposableObserver;

public class CMDService extends Service {

    public static final int SERVICE_DEFAULT = 0;
    public static final int SERVICE_BINDING = 1;
    public static final int SERVICE_STARTING = 2;
    public static final int SERVICE_DESTROY = 3;

    private int mServiceState = SERVICE_DEFAULT;


    private ICMDCallBack callBack;

    ParcelFileDescriptor pd;


    Selector selector;
    ServerSocketChannel socketChannel;
    DisposableObserver disposableObserver;

    /**
     * Return the communication channel to the service.  May return null if
     * clients can not bind to the service.  The returned
     * {@link IBinder} is usually for a complex interface
     * that has been <a href="{@docRoot}guide/components/aidl.html">described using
     * aidl</a>.
     *
     * <p><em>Note that unlike other application components, calls on to the
     * IBinder interface returned here may not happen on the main thread
     * of the process</em>.  More information about the main thread can be found in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html">Processes and
     * Threads</a>.</p>
     *
     * @param intent The Intent that was used to bind to this service,
     *               as given to {@link Context#bindService
     *               Context.bindService}.  Note that any extras that were included with
     *               the Intent at that point will <em>not</em> be seen here.
     * @return Return an IBinder through which clients can call on to the
     * service.
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mServiceState = SERVICE_BINDING;
        return new ICMDInterface().asBinder();
    }

    /**
     * Called by the system when the service is first created.  Do not call this method directly.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mServiceState = SERVICE_DEFAULT;

        try {
            selector = Selector.open();

            socketChannel = ServerSocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.socket().bind(new InetSocketAddress(2333));
            socketChannel.register(selector, SelectionKey.OP_ACCEPT);

            disposableObserver = RxUtil.io(null, new RxUtil.RxTask() {
                @Override
                public Object doSth(ObservableEmitter emitter, Object... object) {
                    startSelector(selector);
                    return null;
                }

                @Override
                public void onNext(Object value) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Called by the system to notify a Service that it is no longer used and is being removed.  The
     * service should clean up any resources it holds (threads, registered
     * receivers, etc) at this point.  Upon return, there will be no more calls
     * in to this Service object and it is effectively dead.  Do not call this method directly.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mServiceState = SERVICE_DESTROY;
        callBack = null;

        try {
            if (socketChannel != null) {
                socketChannel.close();
            }
            if (selector != null) {
                selector.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void startSelector(Selector selector) {
        int loopCount = 0;
        while (true) {
            // Selector轮询注册来的Channel, 阻塞到至少有一个通道在你注册的事件上就绪了。
            int n = 0;
            try {
                n = selector.select();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (n == 0) {
                continue;
            }
            System.out.println("loopCount:" + loopCount);
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                // 获取SelectionKey

                SelectionKey selectionKey = iterator.next();
                SocketChannel socketChannel = null;
                SelectableChannel selectableChannel = selectionKey.channel();

                // 每一步都要判断selectionKey.isValid()，避免断开连接产生的java.nio.channels.CancelledKeyException
                if (selectionKey.isValid() && selectionKey.isAcceptable()) {
                    Log.i("TTTT", "selectionKey isAcceptable");
                    acceptClient(selectionKey, (ServerSocketChannel) selectableChannel);
                }
                if (selectionKey.isValid() && selectionKey.isReadable()) {
                    // a channel is ready for reading
                    Log.i("TTTT", "selectionKey isReadable");
                    socketChannel = (SocketChannel) selectableChannel;// 返回为之创建此键的通道。
//                    readMsg(selectionKey, socketChannel);
                }
                if (selectionKey.isValid() && selectionKey.isWritable()) {
                    // a channel is ready for writing
                    Log.i("TTTT", "selectionKey isWritable");
                    socketChannel = (SocketChannel) selectableChannel;
//                    writeMsg(selectionKey, socketChannel);
                }
                iterator.remove();
            }
            loopCount++;
        }
    }


    private void acceptClient(SelectionKey selectionKey, ServerSocketChannel serverChannel) {
        // 此方法返回的套接字通道（如果有）将处于阻塞模式。
        try {
            SocketChannel socketChannel = serverChannel.accept();
            socketChannel.configureBlocking(false);

            // 向Selector注册Channel，设置读取为感兴趣操作，此类操作将会在下一次选择器select操作时被交付。同时附加byteBuffer对象作为数据传递的容器
            socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                System.out.println("connected from:" + socketChannel.getLocalAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
            selectionKey.cancel();
        }
    }

    /**
     * Called when all clients have disconnected from a particular interface
     * published by the service.  The default implementation does nothing and
     * returns false.
     *
     * @param intent The Intent that was used to bind to this service,
     *               as given to {@link Context#bindService
     *               Context.bindService}.  Note that any extras that were included with
     *               the Intent at that point will <em>not</em> be seen here.
     * @return Return true if you would like to have the service's
     * {@link #onRebind} method later called when new clients bind to it.
     */
    @Override
    public boolean onUnbind(Intent intent) {
        mServiceState = SERVICE_DEFAULT;
        return super.onUnbind(intent);
    }

    /**
     * Called when new clients have connected to the service, after it had
     * previously been notified that all had disconnected in its
     * {@link #onUnbind}.  This will only be called if the implementation
     * of {@link #onUnbind} was overridden to return true.
     *
     * @param intent The Intent that was used to bind to this service,
     *               as given to {@link Context#bindService
     *               Context.bindService}.  Note that any extras that were included with
     *               the Intent at that point will <em>not</em> be seen here.
     */
    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        mServiceState = SERVICE_BINDING;
    }

    /**
     * Called by the system every time a client explicitly starts the service by calling
     * {@link Context#startService}, providing the arguments it supplied and a
     * unique integer token representing the start request.  Do not call this method directly.
     *
     * <p>For backwards compatibility, the default implementation calls
     * {@link #onStart} and returns either {@link #START_STICKY}
     * or {@link #START_STICKY_COMPATIBILITY}.
     *
     * <p class="caution">Note that the system calls this on your
     * service's main thread.  A service's main thread is the same
     * thread where UI operations take place for Activities running in the
     * same process.  You should always avoid stalling the main
     * thread's event loop.  When doing long-running operations,
     * network calls, or heavy disk I/O, you should kick off a new
     * thread, or use {@link AsyncTask}.</p>
     *
     * @param intent  The Intent supplied to {@link Context#startService},
     *                as given.  This may be null if the service is being restarted after
     *                its process has gone away, and it had previously returned anything
     *                except {@link #START_STICKY_COMPATIBILITY}.
     * @param flags   Additional data about this start request.
     * @param startId A unique integer representing this specific request to
     *                start.  Use with {@link #stopSelfResult(int)}.
     * @return The return value indicates what semantics the system should
     * use for the service's current started state.  It may be one of the
     * constants associated with the {@link #START_CONTINUATION_MASK} bits.
     * @see #stopSelfResult(int)
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mServiceState != SERVICE_BINDING) {
            mServiceState = SERVICE_STARTING;
        }
        return super.onStartCommand(intent, flags, startId);
    }


    class ICMDInterface extends com.text.rxjava.ICMDInterface.Stub {
        @Override
        public void registerCallback(ICMDCallBack callback) throws RemoteException {
            CMDService.this.callBack = callback;
        }

        @Override
        public void unRegisterCallback() throws RemoteException {
            callBack = null;
        }

        @Override
        public void push(int offset, int length) throws RemoteException {

            if (pd != null) {
                Log.i("TTT", "read:" + length + "offset:" + offset);

                try {
                    byte[] data = new byte[length];
//                    ParcelFileDescriptor.AutoCloseInputStream inputStream = new ParcelFileDescriptor.AutoCloseInputStream(pd);
                    FileInputStream fileInputStream = new FileInputStream(pd.getFileDescriptor());

                    fileInputStream.read(data, 0, length);
                    fileInputStream.close();
                    Log.i("TTT", String.valueOf(data[0]));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void initParcelFileDescriptor(ParcelFileDescriptor fb) throws RemoteException {
            pd = fb;
        }
    }
}
