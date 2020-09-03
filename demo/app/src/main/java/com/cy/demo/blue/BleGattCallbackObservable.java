package com.cy.demo.blue;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.LinkedList;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * @创建者 CY
 * @创建时间 2020/8/17 11:37
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
public class BleGattCallbackObservable extends BluetoothGattCallback implements Disposable {


    /**
     * downstream
     */
    private Observable<BTResponse> observer;

    /**
     * 服务uuid
     */
    private UUID serviceUuid;

    /**
     * 写uuid
     */
    private UUID writeUuid;

    /**
     * 通知uuid
     */
    private UUID notifyUuid;


    /**
     * UUID的描述符
     */
    private UUID descriptors= UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

    /**
     *GATT client  无法用于取消一个正在连接的任务 因为他是在连接成功时赋值的
     */
    private BluetoothGatt bluetoothGatt;

    /**
     * 写操作特征
     */
    private BluetoothGattCharacteristic writeCharacteristic;

    /**
     * 指令queue
     * note:不会维护多条指令  只会维护单条指令的多包
     */
    private LinkedList<byte[]> linked;

    /**
     * 自动发送下一包  默认非自动 只有用户调用了自动发送的方法才会触发 断开连接 或新的连接建立后恢复默认值
     */
    private boolean autoNext = false;

    /**
     * 上一包数据 用来重发
     */
    private byte[] oldNext;

    /**
     * function
     * ByteArray：远程设备的返回包 由外部来处理
     * Boolean：是否继续发送 由外部来处理
     */
    private Function<byte[], Integer> function;

    /**
     * 是否自动连接自动断开连接的设备  用户手动断开的连接不会自动重连
     */
    private boolean isAutoConnect =true;



    /**
     * 是否连接中
     */
    private boolean isConnect = false;


    public BleGattCallbackObservable(){
        linked=new LinkedList<>();
    }







    @Override
    public void dispose() {

    }

    @Override
    public boolean isDisposed() {
        return false;
    }
}