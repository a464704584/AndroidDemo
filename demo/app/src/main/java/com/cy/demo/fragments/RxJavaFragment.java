package com.cy.demo.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cy.demo.BR;
import com.cy.demo.R;
import com.cy.demo.base.BaseFragment;
import com.cy.demo.databind.DataBindModule;
import com.cy.demo.viewModule.RxViewModule;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * @创建者 CY
 * @创建时间 2020/8/11 10:44
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
public class RxJavaFragment extends BaseFragment {
    private RxViewModule viewModule;
    @Override
    protected void initVariable() {
        viewModule=getViewModule(RxViewModule.class);
    }

    @Override
    protected DataBindModule initDataBindModule() {
        return new DataBindModule(R.layout.fragment_rxjava)
                .addParam(BR.viewModule,viewModule)
                .addParam(BR.method,this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);






    }

    public void click(){
        Log.i(TAG,""+viewModule.text.get());
    }
}