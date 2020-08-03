package com.cy.demo.fragments;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cy.demo.BR;
import com.cy.demo.MainActivity;
import com.cy.demo.R;
import com.cy.demo.base.BaseFragment;
import com.cy.demo.bean.BlueDeviceItem;
import com.cy.demo.bean.ModuleItem;
import com.cy.demo.blue.BTResponse;
import com.cy.demo.blue.BtPredicate;
import com.cy.demo.blue.RxBT;
import com.cy.demo.databind.BaseBindAdapter;
import com.cy.demo.databind.DataBindModule;
import com.cy.demo.viewModule.BLViewModule;
import com.github.ivbaranov.rxbluetooth.RxBluetooth;
import com.github.ivbaranov.rxbluetooth.events.ConnectionStateEvent;
import com.github.ivbaranov.rxbluetooth.events.ServiceEvent;
//import com.github.ivbaranov.rxbluetooth.predicates.BtPredicate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import duoshine.rxandroidbluetooth.BluetoothController;
import duoshine.rxandroidbluetooth.BluetoothWorker;
import duoshine.rxandroidbluetooth.bluetoothprofile.BluetoothWriteProfile;
import duoshine.rxandroidbluetooth.observable.Response;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * @创建者 CY
 * @创建时间 2020/7/29 17:35
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
public class BLFragment extends BaseFragment {

    private int REQUEST_PERMISSION_COARSE_LOCATION = 1 << 1;

    private BLViewModule blViewModule;
    private RxBT rxBluetooth;
    private BaseBindAdapter<BlueDeviceItem> bindAdapter;
//    public final static UUID UUID_SERVICE = UUID.fromString("0000fee7-0000-1000-8000-00805f9b34fb");
//    public final static UUID UUID_WRITE = UUID.fromString("000036F5-0000-1000-8000-00805f9b34fb");
//    public final static UUID UUID_READ = UUID.fromString("000036f6-0000-1000-8000-00805f9b34fb");
public final static UUID UUID_WRITE = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb");
    public final static UUID UUID_SERVICE = UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb");
    public final static UUID UUID_READ = UUID.fromString("0000ffe2-0000-1000-8000-00805f9b34fb");
    private BluetoothGattCharacteristic characteristicWrite;
    private BluetoothGattCharacteristic characteristicRead;
    private BluetoothGatt bluetoothGatt;
    private BluetoothWorker bluetoothWorker;
    public final static UUID UUID_DES2 = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

    @Override
    protected void initVariable() {
        blViewModule = getViewModule(BLViewModule.class);
        bindAdapter = new BaseBindAdapter<BlueDeviceItem>(R.layout.item_blue_device, new ArrayList<>());
    }

    @Override
    protected DataBindModule initDataBindModule() {
        return new DataBindModule(R.layout.fragment_bl)
                .addParam(BR.viewModule, blViewModule)
                .addParam(BR.rxBluetooth, rxBluetooth)
                .addParam(BR.method, this)
                .addParam(BR.adapter, bindAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSION_COARSE_LOCATION);
        } else {
            initBlueTooth();
        }
        bindAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
//                BluetoothWriteProfile
                BluetoothDevice device = bindAdapter.getData().get(position).device.get();
                Log.i(TAG,"device "+device.getAddress());


                rxBluetooth.connect(getContext(),false,device)
                        .subscribe(new Consumer<BTResponse>() {
                            @Override
                            public void accept(BTResponse btResponse) throws Exception {
                                Log.i(TAG,"connect: "+btResponse);

                                if (btResponse.code==2){
//                                    rxBluetooth.write();
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                throwable.printStackTrace();
                            }
                        });

////                bluetoothGatt=device.connectGatt(getContext(),false,bluetoothGattCallback);
//                bluetoothWorker.connect(device.getAddress())
//                        .subscribe(new Consumer<Response>() {
//                            @Override
//                            public void accept(Response response) throws Exception {
//                                Log.i(TAG, "connect " + response);
//                                if (response.getCode() == 4) {
//                                    byte[] c = new byte[16];
//                                    c[0] = 0x06;
//                                    c[1] = 0x01;
//                                    c[2] = 0x01;
//                                    c[3] = 0x01;
//                                        bluetoothWorker.writeOnce(c).subscribe(new Consumer<Response>() {
//                                            @Override
//                                            public void accept(Response response) throws Exception {
//                                                Log.i(TAG, "write " + response);
//                                            }
//                                        }, new Consumer<Throwable>() {
//                                            @Override
//                                            public void accept(Throwable throwable) throws Exception {
//                                                throwable.printStackTrace();
//                                            }
//                                        });
//                                }
//                            }
//                        }, new Consumer<Throwable>() {
//                            @Override
//                            public void accept(Throwable throwable) throws Exception {
//                                throwable.printStackTrace();
//                            }
//                        });

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean agree = true;
        for (int result : grantResults) {
            agree = agree && (result == PackageManager.PERMISSION_GRANTED);
        }
        if (!agree) {
            nav().navigateUp();
        }

        initBlueTooth();
    }

    private void initBlueTooth() {
        rxBluetooth = new RxBT(activity);
        blViewModule.isBluetoothAvailable.set(rxBluetooth.isBluetoothAvailable());
        blViewModule.isBluetoothEnabled.set(rxBluetooth.isBluetoothEnabled());

        bluetoothWorker = new BluetoothController.Builder(getContext())
                .setServiceUuid(UUID_SERVICE)
                .setWriteUuid(UUID_WRITE)
                .setNotifyUuid(UUID_READ)
                .build();

        rxBluetooth.observeBluetoothState()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .filter(BtPredicate.in(BluetoothAdapter.STATE_OFF, BluetoothAdapter.STATE_ON))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        blViewModule.isBluetoothEnabled.set(rxBluetooth.isBluetoothEnabled());
                    }
                });

        rxBluetooth.observeScanMode().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG,"scanMode :"+integer);
                    }
                });

        rxBluetooth.observeDevices()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .filter(new Predicate<BluetoothDevice>() {
                    @Override
                    public boolean test(BluetoothDevice bluetoothDevice) throws Exception {
                        return bluetoothDevice.getName() != null;
                    }
                })
                .subscribe(new Consumer<BluetoothDevice>() {
                    @Override
                    public void accept(@NonNull BluetoothDevice bluetoothDevice) throws Exception {
                        bindAdapter.addData(new BlueDeviceItem(bluetoothDevice));
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (rxBluetooth != null) {
            rxBluetooth.cancelDiscovery();
        }
    }


    public void openOrCloseBL() {
        if (!rxBluetooth.isBluetoothEnabled()) {
            rxBluetooth.enable();
        } else {
            rxBluetooth.disable();
        }
    }

    public void scan() {
        if (rxBluetooth.isDiscovering()) {
            rxBluetooth.cancelDiscovery();
        } else {
            rxBluetooth.startDiscovery();
        }
    }


}