package com.cy.demo.blue;

import android.bluetooth.BluetoothProfile;

/**
 * @创建者 CY
 * @创建时间 2020/8/3 16:01
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
public class ServiceEvent {
    public enum State {
        CONNECTED, DISCONNECTED
    }

    private ServiceEvent.State state;
    private int profileType;
    private BluetoothProfile bluetoothProfile;

    public ServiceEvent(ServiceEvent.State state, int profileType, BluetoothProfile bluetoothProfile) {
        this.state = state;
        this.profileType = profileType;
        this.bluetoothProfile = bluetoothProfile;
    }

    public ServiceEvent.State getState() {
        return state;
    }

    public int getProfileType() {
        return profileType;
    }

    public BluetoothProfile getBluetoothProfile() {
        return bluetoothProfile;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceEvent that = (ServiceEvent) o;

        if (profileType != that.profileType) return false;
        if (state != that.state) return false;
        return !(bluetoothProfile != null ? !bluetoothProfile.equals(that.bluetoothProfile)
                : that.bluetoothProfile != null);
    }

    @Override public int hashCode() {
        int result = state.hashCode();
        result = 31 * result + profileType;
        result = 31 * result + (bluetoothProfile != null ? bluetoothProfile.hashCode() : 0);
        return result;
    }

    @Override public String toString() {
        return "ServiceEvent{" +
                "state=" + state +
                ", profileType=" + profileType +
                ", bluetoothProfile=" + bluetoothProfile +
                '}';
    }
} 