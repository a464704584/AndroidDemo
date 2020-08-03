package com.cy.demo.blue;

import android.bluetooth.BluetoothDevice;

/**
 * @创建者 CY
 * @创建时间 2020/8/3 16:03
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
public class BondStateEvent {

    private int state;
    private int previousState;
    private BluetoothDevice bluetoothDevice;

    public BondStateEvent(int state, int previousState, BluetoothDevice bluetoothDevice) {
        this.state = state;
        this.previousState = previousState;
        this.bluetoothDevice = bluetoothDevice;
    }

    public int getState() {
        return state;
    }

    public int getPreviousState() {
        return previousState;
    }

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BondStateEvent that = (BondStateEvent) o;

        if (state != that.state) return false;
        if (previousState != that.previousState) return false;
        return !(bluetoothDevice != null ? !bluetoothDevice.equals(that.bluetoothDevice)
                : that.bluetoothDevice != null);
    }

    @Override public int hashCode() {
        int result = state;
        result = 31 * result + previousState;
        result = 31 * result + (bluetoothDevice != null ? bluetoothDevice.hashCode() : 0);
        return result;
    }

    @Override public String toString() {
        return "BondStateEvent{" +
                "state=" + state +
                ", previousState=" + previousState +
                ", bluetoothDevice=" + bluetoothDevice +
                '}';
    }
} 