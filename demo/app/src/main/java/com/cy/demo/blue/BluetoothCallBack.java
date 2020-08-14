package com.cy.demo.blue;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Context;

import java.util.LinkedList;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @创建者 CY
 * @创建时间 2020/8/5 10:28
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
public class BluetoothCallBack extends BluetoothGattCallback implements Disposable {
    private final String TAG="BluetoothCallBack";

//    private Observer<BTResponse> observer;

    private UUID serviceUuid;

    /**
     * 通知uuid
     */
    private UUID notifyUuid;

    /**
     * 写uuid
     */
    private UUID writeUuid;

    private UUID descriptors=UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

    private BluetoothGatt bluetoothGatt;

    /**
     * 写特征
     */
    private BluetoothGattCharacteristic writeCharacteristic;


    /**
     * 分包数据
     */
    private LinkedList<byte[]> linkedListBytes=new LinkedList<>();


    /**
     * 自动重发
     */
    private boolean autoNext=false;

    /**
     * 上一个包数据
     */
    private byte[] oldBytes;


    /**
     * 是否自动连接
     */
    private boolean isAutoConnect;


    /**
     * 是否在连接中
     */
    private boolean isConnect;



    private BluetoothGattService gattService;


    public BluetoothCallBack create(Observer<BTResponse> observer,UUID serviceUuid,UUID writeUuid,UUID notifyUuid,boolean isAutoConnect){
//        this.observer=observer;
        this.serviceUuid=serviceUuid;
        this.writeUuid=writeUuid;
        this.notifyUuid=notifyUuid;
        this.isAutoConnect=isAutoConnect;
        return get(observer);
    }


    private BluetoothCallBack create(){
        return get();
    }


    private BluetoothCallBack get(Observer<BTResponse> observer){
//        this.observer=observer;
//        observer.onSubscribe(this);
        return this;
    }

    private BluetoothCallBack get(){
        return this;
    }



    @Override
    public void dispose() {
        isConnect=false;
//        observer=null;
        bluetoothGatt.close();
        bluetoothGatt.disconnect();
    }

    @Override
    public boolean isDisposed() {
        return isConnect;
    }

    /**
     * 获取设备
     * @return
     */
    public BluetoothDevice getDevice(){
        return bluetoothGatt.getDevice();
    }


    /**
     * 自动连接
     */
    public void autoConnect(){
        if (isAutoConnect){
            onNext(BTResponse.STATE_CONNECTED);
            bluetoothGatt.connect();
        }else {
            bluetoothGatt.close();
        }

    }

    /**
     * 连接状态改变
     * @param gatt
     * @param status
     * @param newState
     */
    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        if (newState == BluetoothProfile.STATE_CONNECTED) {
            bluetoothGatt = gatt;
            isConnect = true;
            bluetoothGatt.discoverServices();
        } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
            disconnected();
        }
    }

    /**
     * 发现新服务
     * @param gatt
     * @param status
     */
    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        super.onServicesDiscovered(gatt, status);
    }


    /**
     * 写操作的结果
     * @param gatt
     * @param characteristic
     * @param status
     */
    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicWrite(gatt, characteristic, status);
    }


    /**
     * 特征值改变
     * @param gatt
     * @param characteristic
     */
    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicChanged(gatt, characteristic);
    }

    /**
     * 断开连接
     */
    public void disconnected(){

    }

    /**
     * 低功耗通信连接
     * @param context
     * @param device
     * @return
     */
    public Observable<BTResponse> connect(Context context, boolean autoConnect, BluetoothDevice device){
        return Observable.create(new ObservableOnSubscribe<BTResponse>() {
            @Override
            public void subscribe(ObservableEmitter<BTResponse> emitter) throws Exception {
                bluetoothGatt=device.connectGatt(context, autoConnect,BluetoothCallBack.this);
                gattService=bluetoothGatt.getService(serviceUuid);
                writeCharacteristic=gattService.getCharacteristic(writeUuid);
            }
        });
    }


    public Observable<BTResponse> write(byte[] bytes){
        return Observable.create(new ObservableOnSubscribe<BTResponse>() {
            @Override
            public void subscribe(ObservableEmitter<BTResponse> emitter) throws Exception {

            }
        });
    }

    private void onNext(int status,byte[] bytes){
//        observer.onNext(new BTResponse(status,bytes));
    }

    private void onNext(int status){
        onNext(status,null);
    }

}