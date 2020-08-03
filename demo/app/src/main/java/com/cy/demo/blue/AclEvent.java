package com.cy.demo.blue;

import android.bluetooth.BluetoothDevice;

/**
 * @创建者 CY
 * @创建时间 2020/8/3 16:02
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
public class AclEvent {

    private String action;
    private BluetoothDevice bluetoothDevice;

    public AclEvent(String action, BluetoothDevice bluetoothDevice) {
        this.action = action;
        this.bluetoothDevice = bluetoothDevice;
    }

    public String getAction() {
        return action;
    }

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AclEvent that = (AclEvent) o;

        if (action != null && !action.equals(that.action)) return false;
        return !(bluetoothDevice != null ? !bluetoothDevice.equals(that.bluetoothDevice)
                : that.bluetoothDevice != null);
    }

    @Override public int hashCode() {
        int result = action != null ? action.hashCode() : 0;
        result = 31 * result + (bluetoothDevice != null ? bluetoothDevice.hashCode() : 0);
        return result;
    }

    @Override public String toString() {
        return "AclEvent{" +
                "action=" + action +
                ", bluetoothDevice=" + bluetoothDevice +
                '}';
    }
} 