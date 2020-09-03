package com.cy.demo.blue;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.MainThreadDisposable;
import io.reactivex.annotations.NonNull;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;
import static android.os.Build.VERSION.SDK_INT;

/**
 * @创建者 CY
 * @创建时间 2020/8/3 15:53
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
public class RxBT {
    private final UUID UUID_DES2 = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

//    private BluetoothGatt gatt;

    private BluetoothAdapter bluetoothAdapter;
    private Context context;

    private UUID serviceUuid;

    /**
     * 通知uuid
     */
    private UUID notifyUuid;

    /**
     * 写uuid
     */
    private UUID writeUuid;

    private RxBT(Context context, UUID serviceUuid, UUID notifyUuid, UUID writeUuid) {
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.context = context;
        this.serviceUuid = serviceUuid;
        this.notifyUuid = notifyUuid;
        this.writeUuid = writeUuid;
    }

    /**
     * 该设备是否支持蓝牙
     *
     * @return
     */
    public boolean isBluetoothAvailable() {
        return bluetoothAdapter != null;
    }

    /**
     * 蓝牙是否打开
     *
     * @return
     */
    public boolean isBluetoothEnabled() {
        return bluetoothAdapter.isEnabled();
    }




    /**
     * 打开系统的蓝牙页面
     *
     * @param activity
     * @param requestCode
     */
    public void enableBluetooth(Activity activity, int requestCode) {
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent, requestCode);
        }
    }

    /**
     * 启动蓝牙
     *
     * @return
     */
    public boolean enable() {
        return bluetoothAdapter.enable();
    }

    /**
     * 关闭蓝牙
     *
     * @return
     */
    public boolean disable() {
        return bluetoothAdapter.disable();
    }


    /**
     * 获取已经配对的蓝牙集合
     *
     * @return
     */
    @Nullable
    public Set<BluetoothDevice> getBondedDevices() {
        return bluetoothAdapter.getBondedDevices();
    }


    /**
     * 发现蓝牙
     *
     * @return
     */
    public boolean startDiscovery() {
        return bluetoothAdapter.startDiscovery();
    }

    /**
     * 是否正在发现蓝牙
     *
     * @return
     */
    public boolean isDiscovering() {
        return bluetoothAdapter.isDiscovering();
    }

    /**
     * 停止发现蓝牙
     *
     * @return
     */
    public boolean cancelDiscovery() {

        return bluetoothAdapter.cancelDiscovery();
    }

    /**
     * 暴露设备 对其他蓝牙可见
     *
     * @param activity
     * @param requestCode
     */
    public void enableDiscoverability(Activity activity, int requestCode) {
        enableDiscoverability(activity, requestCode, -1);
    }

    /**
     * 暴露设备 对其他蓝牙可见
     *
     * @param activity
     * @param requestCode
     * @param duration    最大120秒
     */
    public void enableDiscoverability(Activity activity, int requestCode, int duration) {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        if (duration >= 0) {
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, duration);
        }
        activity.startActivityForResult(discoverableIntent, requestCode);
    }

    /**
     * 观察发现的设备
     * @return
     */
    public Observable<BluetoothDevice> observeDevices() {
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        return Observable.create(new ObservableOnSubscribe<BluetoothDevice>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<BluetoothDevice> emitter) throws Exception {
                BroadcastReceiver receiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String action = intent.getAction();
                        if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                            emitter.onNext(device);
                        }
                    }
                };

                context.registerReceiver(receiver, filter);

                emitter.setDisposable(new MainThreadDisposable() {
                    @Override
                    protected void onDispose() {
                        context.unregisterReceiver(receiver);
                    }
                });
            }
        });
    }


    /**
     * 观察设备扫描状态 状态（开始/结束）
     * @return
     */
    public Observable<String> observeDiscovery() {
        final IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<String> emitter)
                    throws Exception {
                BroadcastReceiver receiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        emitter.onNext(intent.getAction());
                    }
                };

                context.registerReceiver(receiver, filter);

                emitter.setDisposable(new MainThreadDisposable() {
                    @Override
                    protected void onDispose() {
                        context.unregisterReceiver(receiver);
                    }
                });
            }
        });
    }


    /**
     * 观察蓝牙打开关闭状态
     * {@link BluetoothAdapter#STATE_OFF},
     * {@link BluetoothAdapter#STATE_TURNING_ON},
     * {@link BluetoothAdapter#STATE_ON},
     * {@link BluetoothAdapter#STATE_TURNING_OFF},
     *
     */
    public Observable<Integer> observeBluetoothState() {
        final IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);

        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Integer> emitter)
                    throws Exception {
                final BroadcastReceiver receiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                                BluetoothAdapter.ERROR);
                        emitter.onNext(state);
                    }
                };

                context.registerReceiver(receiver, filter);

                emitter.setDisposable(new MainThreadDisposable() {
                    @Override
                    protected void onDispose() {
                        context.unregisterReceiver(receiver);
                    }
                });
            }
        });
    }

    /**
     * 获取扫描状态
     * {@link BluetoothAdapter#SCAN_MODE_NONE}  该蓝牙不能扫描以及被扫描,
     * {@link BluetoothAdapter#SCAN_MODE_CONNECTABLE} 该蓝牙可以扫描其他蓝牙设备,
     * {@link BluetoothAdapter#SCAN_MODE_CONNECTABLE_DISCOVERABLE} 该蓝牙可以扫描其他设备，也可以被扫描
     */
    public Observable<Integer> observeScanMode() {
        final IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);

        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Integer> emitter)
                    throws Exception {
                final BroadcastReceiver receiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        emitter.onNext(bluetoothAdapter.getScanMode());
                    }
                };

                context.registerReceiver(receiver, filter);

                emitter.setDisposable(new MainThreadDisposable() {
                    @Override
                    protected void onDispose() {
                        context.unregisterReceiver(receiver);
                    }
                });
            }
        });
    }

    /**
     * Observes connection to specified profile. See also {@link BluetoothProfile.ServiceListener}.
     *
     * @param bluetoothProfile bluetooth profile to connect to. Can be either {@link
     *                         BluetoothProfile#HEALTH},{@link BluetoothProfile#HEADSET}, {@link BluetoothProfile#A2DP},
     *                         {@link BluetoothProfile#GATT} or {@link BluetoothProfile#GATT_SERVER}.
     * @return RxJava Observable with {@link ServiceEvent}
     */
    public Observable<ServiceEvent> observeBluetoothProfile(final int bluetoothProfile) {
        return Observable.create(new ObservableOnSubscribe<ServiceEvent>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<ServiceEvent> emitter)
                    throws Exception {
                if (!bluetoothAdapter.getProfileProxy(context, new BluetoothProfile.ServiceListener() {
                    @Override
                    public void onServiceConnected(int profile, BluetoothProfile proxy) {
                        emitter.onNext(new ServiceEvent(ServiceEvent.State.CONNECTED, profile, proxy));
                    }

                    @Override
                    public void onServiceDisconnected(int profile) {
                        emitter.onNext(new ServiceEvent(ServiceEvent.State.DISCONNECTED, profile, null));
                    }
                }, bluetoothProfile)) {
                    emitter.onError(new GetProfileProxyException());
                }
            }
        });
    }

    /**
     * Close the connection of the profile proxy to the Service.
     *
     * <p> Clients should call this when they are no longer using the proxy obtained from {@link
     * #observeBluetoothProfile}.
     * <p>Profile can be one of {@link BluetoothProfile#HEALTH},{@link BluetoothProfile#HEADSET},
     * {@link BluetoothProfile#A2DP}, {@link BluetoothProfile#GATT} or {@link
     * BluetoothProfile#GATT_SERVER}.
     *
     * @param profile the Bluetooth profile
     * @param proxy   profile proxy object
     */
    public void closeProfileProxy(int profile, BluetoothProfile proxy) {
        bluetoothAdapter.closeProfileProxy(profile, proxy);
    }

    /**
     * 获取蓝牙连接状态
     */
    public Observable<ConnectionStateEvent> observeConnectionState() {
        final IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);

        return Observable.create(new ObservableOnSubscribe<ConnectionStateEvent>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<ConnectionStateEvent> emitter)
                    throws Exception {
                final BroadcastReceiver receiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        int status = intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE,
                                BluetoothAdapter.STATE_DISCONNECTED);
                        int previousStatus =
                                intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_CONNECTION_STATE,
                                        BluetoothAdapter.STATE_DISCONNECTED);
                        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                        emitter.onNext(new ConnectionStateEvent(status, previousStatus, device));
                    }
                };

                context.registerReceiver(receiver, filter);

                emitter.setDisposable(new MainThreadDisposable() {
                    @Override
                    protected void onDispose() {
                        context.unregisterReceiver(receiver);
                    }
                });
            }
        });
    }

    /**
     * 观察配对状态
     */
    public Observable<BondStateEvent> observeBondState() {
        final IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);

        return Observable.create(new ObservableOnSubscribe<BondStateEvent>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<BondStateEvent> emitter)
                    throws Exception {
                final BroadcastReceiver receiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        int state =
                                intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.BOND_NONE);
                        int previousState = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE,
                                BluetoothDevice.BOND_NONE);
                        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                        emitter.onNext(new BondStateEvent(state, previousState, device));
                    }
                };

                context.registerReceiver(receiver, filter);

                emitter.setDisposable(new MainThreadDisposable() {
                    @Override
                    protected void onDispose() {
                        context.unregisterReceiver(receiver);
                    }
                });
            }
        });
    }


    public ConnectObservable connect(String address){
        return ConnectObservable.create(context,bluetoothAdapter,address,serviceUuid,writeUuid,notifyUuid);
    }




    /**
     * Opens {@link BluetoothServerSocket}, listens for a single connection request, releases socket
     * and returns a connected {@link BluetoothSocket} on successful connection. Notifies observers
     * with {@link IOException} {@code onError()}.
     *
     * @param name service name for SDP record
     * @param uuid uuid for SDP record
     * @return observable with connected {@link BluetoothSocket} on successful connection
     * @deprecated use {{@link #connectAsServer(String, UUID)}} instead
     */
    @Deprecated
    public Observable<BluetoothSocket> observeBluetoothSocket(final String name, final UUID uuid) {
        return connectAsServer(name, uuid).toObservable();
    }

    /**
     * Create connection to {@link BluetoothDevice} and returns a connected {@link BluetoothSocket}
     * on successful connection. Notifies observers with {@link IOException} via {@code onError()}.
     *
     * @param bluetoothDevice bluetooth device to connect
     * @param uuid            uuid for SDP record
     * @return observable with connected {@link BluetoothSocket} on successful connection
     * @deprecated use {{@link #connectAsClient(BluetoothDevice, UUID)}} instead
     */
    @Deprecated
    public Observable<BluetoothSocket> observeConnectDevice(final BluetoothDevice bluetoothDevice,
                                                            final UUID uuid) {
        return connectAsClient(bluetoothDevice, uuid).toObservable();
    }

    /**
     * Opens {@link BluetoothServerSocket}, listens for a single connection request, releases socket
     * and returns a connected {@link BluetoothSocket} on successful connection. Notifies observers
     * with {@link IOException} {@code onError()}.
     *
     * @param name service name for SDP record
     * @param uuid uuid for SDP record
     * @return Single with connected {@link BluetoothSocket} on successful connection
     */
    public Single<BluetoothSocket> connectAsServer(final String name, final UUID uuid) {
        return Single.create(new SingleOnSubscribe<BluetoothSocket>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<BluetoothSocket> emitter) {
                try {
                    BluetoothServerSocket bluetoothServerSocket =
                            bluetoothAdapter.listenUsingRfcommWithServiceRecord(name, uuid);
                    try {
                        emitter.onSuccess(bluetoothServerSocket.accept());
                    } finally {
                        bluetoothServerSocket.close();
                    }
                } catch (IOException e) {
                    emitter.onError(e);
                }
            }
        });
    }

    /**
     * Create connection to {@link BluetoothDevice} and returns a connected {@link BluetoothSocket}
     * on successful connection. Notifies observers with {@link IOException} via {@code onError()}.
     *
     * @param bluetoothDevice bluetooth device to connect
     * @param uuid            uuid for SDP record
     * @return Single with connected {@link BluetoothSocket} on successful connection
     */
    public Single<BluetoothSocket> connectAsClient(final BluetoothDevice bluetoothDevice,
                                                   final UUID uuid) {
        return Single.create(new SingleOnSubscribe<BluetoothSocket>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<BluetoothSocket> emitter) {
                BluetoothSocket bluetoothSocket = null;
                try {
                    bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
                    bluetoothSocket.connect();
                    emitter.onSuccess(bluetoothSocket);
                } catch (IOException e) {
                    if (bluetoothSocket != null) {
                        try {
                            bluetoothSocket.close();
                        } catch (IOException suppressed) {
                            if (SDK_INT >= 19) {
                                e.addSuppressed(suppressed);
                            }
                        }
                    }
                    emitter.onError(e);
                }
            }
        });
    }

    /**
     * Create connection to {@link BluetoothDevice} via createRfcommSocket and returns a connected {@link BluetoothSocket}
     * on successful connection.
     * Note: createRfcommSocket is not public API and hence this might break in the future.
     * Notifies observers with {@link IOException} or any reflection related exception via {@code onError()}.
     *
     * @param bluetoothDevice bluetooth device to connect
     * @param channel         RFCOMM channel to connect to
     * @return Single with connected {@link BluetoothSocket} on successful connection
     */
    public Single<BluetoothSocket> connectAsClient(final BluetoothDevice bluetoothDevice,
                                                   final int channel) {
        return Single.create(new SingleOnSubscribe<BluetoothSocket>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<BluetoothSocket> emitter) {
                BluetoothSocket bluetoothSocket = null;
                try {
                    bluetoothSocket = createSocket(bluetoothDevice, channel);
                    bluetoothSocket.connect();
                    emitter.onSuccess(bluetoothSocket);
                } catch (IOException e) {
                    if (bluetoothSocket != null) {
                        try {
                            bluetoothSocket.close();
                        } catch (IOException suppressed) {
                            if (SDK_INT >= 19) {
                                e.addSuppressed(suppressed);
                            }
                        }
                    }
                    emitter.onError(e);
                }
            }
        });
    }

    /**
     * Observes ACL broadcast actions from {@link BluetoothDevice}. Possible broadcast ACL action
     * values are:
     * {@link BluetoothDevice#ACTION_ACL_CONNECTED},
     * {@link BluetoothDevice#ACTION_ACL_DISCONNECT_REQUESTED},
     * {@link BluetoothDevice#ACTION_ACL_DISCONNECTED}
     *
     * @return RxJava Observable with {@link AclEvent}
     */
    public Observable<AclEvent> observeAclEvent() {
        final IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);

        return Observable.create(new ObservableOnSubscribe<AclEvent>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<AclEvent> emitter)
                    throws Exception {
                final BroadcastReceiver receiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String action = intent.getAction();
                        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                        emitter.onNext(new AclEvent(action, device));
                    }
                };

                context.registerReceiver(receiver, filter);

                emitter.setDisposable(new MainThreadDisposable() {
                    @Override
                    protected void onDispose() {
                        context.unregisterReceiver(receiver);
                    }
                });
            }
        });
    }

    /**
     * Performs a service discovery and fetches a list of UUIDs that can be used to connect to {@link BluetoothDevice}
     *
     * @param bluetoothDevice bluetooth device to connect
     * @return RxJava Observable with an array of Device UUIDs that can be used to connect to the device
     */

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    public Observable<Parcelable[]> observeFetchDeviceUuids(final BluetoothDevice bluetoothDevice) {
        final IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_UUID);

        return Observable.create(new ObservableOnSubscribe<Parcelable[]>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Parcelable[]> emitter) {
                final BroadcastReceiver receiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        Parcelable[] uuids = intent.getParcelableArrayExtra(BluetoothDevice.EXTRA_UUID);
                        if (uuids != null) {
                            emitter.onNext(uuids);
                        }
                        emitter.onComplete();
                    }
                };

                context.registerReceiver(receiver, filter);

                emitter.setDisposable(new MainThreadDisposable() {
                    @Override
                    protected void onDispose() {
                        context.unregisterReceiver(receiver);
                    }
                });

                bluetoothDevice.fetchUuidsWithSdp();
            }
        });
    }

    public static class Builder{

        private Context mContext;
        private UUID serviceUuid;

        /**
         * 通知uuid
         */
        private UUID notifyUuid;

        /**
         * 写uuid
         */
        private UUID writeUuid;

        public Builder(Context context){
            mContext=context;
        }

        public Builder setServiceUUID(UUID uuid){
            serviceUuid=uuid;
            return this;
        }

        public Builder setNotifyUUID(UUID uuid){
            notifyUuid=uuid;
            return this;
        }


        public Builder setWriteUUID(UUID uuid){
            serviceUuid=uuid;
            return this;
        }


        public RxBT build(){
            return new RxBT(mContext,serviceUuid,notifyUuid,writeUuid);

        }



    }


    private static void close(final Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignored) {
                // Ignored.
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static BluetoothSocket createSocket(
            final BluetoothDevice device, final int channel) {
        try {
            Method method = BluetoothDevice.class.getMethod("createRfcommSocket", Integer.TYPE);
            return (BluetoothSocket) method.invoke(device, channel);
        } catch (final NoSuchMethodException e) {
            throw new UnsupportedOperationException(e);
        } catch (final InvocationTargetException e) {
            throw new UnsupportedOperationException(e);
        } catch (final IllegalAccessException e) {
            throw new UnsupportedOperationException(e);
        }
    }
} 