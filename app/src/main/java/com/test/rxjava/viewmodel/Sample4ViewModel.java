package com.test.rxjava.viewmodel;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.MemoryFile;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.test.rxjava.service.CMDService;
import com.test.rxjava.utils.NIOClient;
import com.test.rxjava.utils.RxUtil;
import com.text.rxjava.ICMDCallBack;
import com.text.rxjava.ICMDInterface;

import java.io.IOException;

import io.reactivex.ObservableEmitter;
import io.reactivex.observers.DisposableObserver;


public class Sample4ViewModel extends MyObservable {

    private ICMDInterface cmdInterface;

    private DisposableObserver observer;


    private static final int MEMORY_FILE_SIZE = 10 * 1024 * 1024;
    private MemoryFile memoryFile;
    private int offSet = 0;

    public Sample4ViewModel(Context context) {
        super(context);
    }


    public String getStatusText() {
        return "";
    }

    public void onClickBind(View view) {
        bindService(context, serviceConnection);
//        startCmd();

        RxUtil.io(null, new RxUtil.RxTask() {
            @Override
            public Object doSth(ObservableEmitter emitter, Object... object) {
                startSocket();
                return null;
            }

            @Override
            public void onNext(Object value) {

            }
        });
    }

    public void onClickUnBind(View view) {
        unBindService(context, serviceConnection);
//        stopCmd();
        stopSocket();
    }

    private void startCmd() {
        observer = RxUtil.io(null, new RxUtil.RxTask() {
            @Override
            public Object doSth(ObservableEmitter emitter, Object... object) {

                while (true) {
                    if (emitter.isDisposed()) {
                        return null;
                    }

                    if (cmdInterface != null) {
                        byte[] bytes = new byte[2 * 1024 * 1024];

                        bytes[0] = 50;
                        try {
                            memoryFile.writeBytes(bytes, 0, offSet, bytes.length);
                            cmdInterface.push(offSet, bytes.length);

                            Log.i("TTT", "write:" + bytes.length + "offset:" + offSet);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }

                        offSet = offSet + bytes.length;
                        if (MEMORY_FILE_SIZE - offSet < bytes.length) {
                            offSet = 0;
                        }

                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }

                return null;
            }

            @Override
            public void onNext(Object value) {
                Log.i("TTT", "onComplete");
            }
        });
    }

    private void stopCmd() {
        if (observer != null) {
            observer.dispose();
        }
    }

    /**
     * 绑定服务
     */
    private void bindService(Context context, ServiceConnection serviceConnection) {
        if (context == null || null == serviceConnection) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(context, CMDService.class);
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 解绑服务
     *
     * @param context
     */
    private void unBindService(Context context, ServiceConnection serviceConnection) {
        if (context == null || null == serviceConnection) {
            return;
        }
        try {
            // 当需要多次调用doSomething()方法的时候，如果直接bindService是会报错的
            context.unbindService(serviceConnection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            cmdInterface = ICMDInterface.Stub.asInterface(service);
            try {
                cmdInterface.registerCallback(new CMDCallBack());
            } catch (RemoteException e) {
                e.printStackTrace();
            }

//            try {
//                memoryFile = new MemoryFile("test_memory", MEMORY_FILE_SIZE);
//                Method method = MemoryFile.class.getDeclaredMethod("getFileDescriptor");
//                FileDescriptor des = (FileDescriptor) method.invoke(memoryFile);
//                ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.dup(des);
//                cmdInterface.initParcelFileDescriptor(parcelFileDescriptor);
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            try {
                if (null != cmdInterface) {
                    cmdInterface.unRegisterCallback();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            cmdInterface = null;
        }
    };


    class CMDCallBack extends ICMDCallBack.Stub {

        @Override
        public void callback(int value) throws RemoteException {

        }
    }

    @Override
    public void destroy() {
        super.destroy();
        if (observer != null) {
            observer.dispose();
        }
    }

    NIOClient client;

    private void startSocket() {

        client = new NIOClient();
        try {
            client.initClient(2333);
            client.listen();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void stopSocket() {

        if (client != null) {
            client.close();
        }
    }


}
