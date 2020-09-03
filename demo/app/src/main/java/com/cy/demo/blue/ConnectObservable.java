package com.cy.demo.blue;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.text.TextUtils;

import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @创建者 CY
 * @创建时间 2020/8/14 10:43
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
public class ConnectObservable extends Observable<BTResponse> implements Disposable {
    private Context context;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothGattServer gattServer;
    private BluetoothGatt gatt;
    private String  address;
    private UUID serviceUuid;
    private UUID writeUuid;
    private UUID notifyUuid;
    private Boolean isAutoConnect=false;

    private ConnectObservable(Context context, BluetoothAdapter bluetoothAdapter, String address, UUID serviceUuid, UUID writeUuid, UUID notifyUuid) {
        this.context = context;
        this.bluetoothAdapter = bluetoothAdapter;
        this.address = address;
        this.serviceUuid = serviceUuid;
        this.writeUuid = writeUuid;
        this.notifyUuid = notifyUuid;
    }


    public static ConnectObservable  create(Context context, BluetoothAdapter bluetoothAdapter, String address, UUID serviceUuid, UUID writeUuid, UUID notifyUuid) {
        return new ConnectObservable(context.getApplicationContext(),bluetoothAdapter,address,serviceUuid,writeUuid,notifyUuid);
    }

    ConnectObservable auto(){
        isAutoConnect=true;
        return this;
    }

    @Override
    protected void subscribeActual(Observer<? super BTResponse> observer) {
        if (TextUtils.isEmpty(address)){
            observer.onError(new RuntimeException("没有地址"));
            return;
        }

        if (bluetoothAdapter==null){
            observer.onError(new RuntimeException("bluetoothAdapter is null"));
            return;
        }

        if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_OFF) {
            observer.onError(new RuntimeException("蓝牙未开启"));
            return;
        }
//
//        ConnectObserver connectObserver=new ConnectObserver(observer);
//        observer.onSubscribe(connectObserver);

        BluetoothManager mBluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        gattServer = mBluetoothManager.openGattServer(context, new BluetoothGattServerCallback() {
            @Override
            public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    observer.onNext(new BTResponse(BTResponse.STATE_CONNECTED));
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    observer.onNext(new BTResponse(BTResponse.STATE_DISCONNECTED));
                }
            }
        });
        BleGattCallbackObservable callback = new BleGattCallbackObservable();
        BluetoothDevice remoteDevice = bluetoothAdapter.getRemoteDevice(address);
        gatt = remoteDevice.connectGatt(context, isAutoConnect, callback);

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean isDisposed() {
        return false;
    }
//
//    private class ConnectObserver implements Disposable, Observer<BTResponse> {
//
//
//        ConnectObserver(Observer<? super BTResponse> observer){
//            this.observer=observer;
//        }
//
//        private Observer<? super BTResponse> observer;
//
//
//        private Disposable upDisposable;
//
//        /**
//         * 用于取消连接
//         */
//        private BluetoothGatt bluetoothGatt;
//
//        /**
//         * dispose时要执行close 否则内存泄漏
//         */
//        private BluetoothGattServer gattServer;
//
//
//        @Override
//        public void onSubscribe(Disposable d) {
//            upDisposable=d;
//        }
//
//        @Override
//        public void onNext(BTResponse btResponse) {
//            observer.onNext(btResponse);
//        }
//
//        @Override
//        public void onError(Throwable e) {
//            observer.onError(e);
//        }
//
//        @Override
//        public void onComplete() {
//            observer.onComplete();
//        }
//
//        @Override
//        public void dispose() {
//            //释放资源
//            if (gattServer!=null){
//                gattServer.close();
//            }
//            if (bluetoothGatt!=null){
//                bluetoothGatt.disconnect();
//                bluetoothGatt.close();
//            }
//
//            if (upDisposable!=null){
//                upDisposable.dispose();
//            }
//        }
//
//        @Override
//        public boolean isDisposed() {
//            if (upDisposable==null){
//                return false;
//            }
//            return upDisposable.isDisposed();
//        }
//
//        public void setBluetoothGatt(BluetoothGatt bluetoothGatt) {
//            this.bluetoothGatt = bluetoothGatt;
//        }
//
//        public void setGattServer(BluetoothGattServer gattServer) {
//            this.gattServer = gattServer;
//        }
//    }
}