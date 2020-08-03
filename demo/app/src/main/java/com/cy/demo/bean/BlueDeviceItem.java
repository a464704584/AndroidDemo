package com.cy.demo.bean;

import android.bluetooth.BluetoothDevice;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

/**
 * @创建者 CY
 * @创建时间 2020/7/30 14:29
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
public class BlueDeviceItem extends ViewModel {
    public final ObservableField<BluetoothDevice> device=new ObservableField<>();

    public BlueDeviceItem(BluetoothDevice device) {
        this.device.set(device);
    }
}