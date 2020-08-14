package com.cy.demo.fragments;

import com.cy.demo.R;
import com.cy.demo.base.BaseFragment;
import com.cy.demo.databind.DataBindModule;

/**
 * @创建者 CY
 * @创建时间 2020/8/11 11:09
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
public class UI1Fragment extends BaseFragment {
    @Override
    protected void initVariable() {

    }

    @Override
    protected DataBindModule initDataBindModule() {
        return new DataBindModule(R.layout.fragment_ui_1);
    }
}