package com.cy.demo.viewModule;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.github.ivbaranov.rxbluetooth.RxBluetooth;

/**
 * @创建者 CY
 * @创建时间 2020/7/30 11:12
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
public class BLViewModule extends ViewModel {
    public ObservableBoolean isBluetoothAvailable=new ObservableBoolean();
    public ObservableBoolean isBluetoothEnabled=new ObservableBoolean();
}