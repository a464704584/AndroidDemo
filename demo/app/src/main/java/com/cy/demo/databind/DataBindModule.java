package com.cy.demo.databind;

import android.util.Log;
import android.util.SparseArray;

import androidx.lifecycle.ViewModel;

/**
 * @创建者 CY
 * @创建时间 2020/7/29 11:00
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
public class DataBindModule {
    private Integer layout;

    private Integer viewModuleId;

    private ViewModel viewModel;

    private SparseArray params;

    public DataBindModule(Integer layout) {
        this.layout=layout;
        params=new SparseArray();
    }


    public Integer getLayout() {
        return layout;
    }

    public Integer getViewModuleId() {
        return viewModuleId;
    }

    public ViewModel getViewModel() {
        return viewModel;
    }

    public SparseArray getParams() {
        return params;
    }

    public DataBindModule addParam(int key, Object value) {
        if (params.get(key) == null) {
            params.put(key, value);
        }
        Log.i("ttttttttttt",""+params.toString());
        return this;
    }

}