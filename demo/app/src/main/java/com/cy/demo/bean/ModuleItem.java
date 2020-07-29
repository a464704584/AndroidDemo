package com.cy.demo.bean;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @创建者 CY
 * @创建时间 2020/7/29 13:08
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
public class ModuleItem extends ViewModel {
    public final ObservableField<String> title=new ObservableField<>();
    public Integer navId;

    public ModuleItem(String title,Integer navId) {
        this.navId = navId;
        this.title.set(title);
    }
}