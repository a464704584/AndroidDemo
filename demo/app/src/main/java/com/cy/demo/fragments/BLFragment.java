package com.cy.demo.fragments;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cy.demo.BR;
import com.cy.demo.R;
import com.cy.demo.base.BaseFragment;
import com.cy.demo.base.BaseMainFragment;
import com.cy.demo.bean.BlueDeviceItem;
import com.cy.demo.blue.BTResponse;
import com.cy.demo.blue.BluetoothCallBack;
import com.cy.demo.blue.BtPredicate;
import com.cy.demo.blue.RxBT;
import com.cy.demo.databind.BaseBindAdapter;
import com.cy.demo.databind.DataBindModule;
import com.cy.demo.viewModule.BLViewModule;
//import com.github.ivbaranov.rxbluetooth.predicates.BtPredicate;

import java.util.ArrayList;
import java.util.UUID;
import duoshine.rxandroidbluetooth.BluetoothWorker;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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
    private RxBT rxbt;
    private BaseBindAdapter<BlueDeviceItem> bindAdapter;

    private BluetoothWorker bluetoothWorker;
    public final static UUID UUID_SERVICE = UUID.fromString("0000fee7-0000-1000-8000-00805f9b34fb");
    public final static UUID UUID_WRITE = UUID.fromString("000036F5-0000-1000-8000-00805f9b34fb");
    public final static UUID UUID_READ = UUID.fromString("000036f6-0000-1000-8000-00805f9b34fb");
    private Disposable disposable;
//    BluetoothController
    @Override
    protected void initVariable() {
//        bluetoothWorker=BluetoothController.Builder
        blViewModule = getViewModule(BLViewModule.class);
        bindAdapter = new BaseBindAdapter<BlueDeviceItem>(R.layout.item_blue_device, new ArrayList<>());
    }

    @Override
    protected DataBindModule initDataBindModule() {
        return new DataBindModule(R.layout.fragment_bl)
                .addParam(BR.viewModule, blViewModule)
                .addParam(BR.rxBluetooth, rxbt)
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
                if (disposable!=null&&!disposable.isDisposed()){
                    disposable.dispose();
                }


                BluetoothDevice device = bindAdapter.getData().get(position).device.get();
                Log.i(TAG,"device "+device.getAddress());
               disposable=rxbt.connect(device.getAddress())
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribeOn(Schedulers.computation())
                       .subscribe(btResponse -> {
                           Log.i(TAG,""+btResponse.toString());
                       },throwable -> {
                           throwable.printStackTrace();
                       });
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
        rxbt = new RxBT.Builder(getContext())
                .setServiceUUID(UUID_SERVICE)
                .setWriteUUID(UUID_WRITE)
                .setNotifyUUID(UUID_READ)
                .build();
        blViewModule.isBluetoothAvailable.set(rxbt.isBluetoothAvailable());
        blViewModule.isBluetoothEnabled.set(rxbt.isBluetoothEnabled());

        rxbt.observeBluetoothState()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .filter(BtPredicate.in(BluetoothAdapter.STATE_OFF, BluetoothAdapter.STATE_ON))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        blViewModule.isBluetoothEnabled.set(rxbt.isBluetoothEnabled());
                    }
                });

        rxbt.observeDevices()
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
        if (disposable!=null&&!disposable.isDisposed()){
            disposable.dispose();
        }

        if (rxbt != null) {
            rxbt.cancelDiscovery();
        }
    }
    

    public void openOrCloseBL() {
        if (!rxbt.isBluetoothEnabled()) {
            rxbt.enable();
        } else {
            rxbt.disable();
        }
    }

    public void scan() {
        if (rxbt.isDiscovering()) {
            rxbt.cancelDiscovery();
        } else {
            rxbt.startDiscovery();
        }
    }


}