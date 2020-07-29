package com.cy.demo.fragments;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cy.demo.BR;
import com.cy.demo.R;
import com.cy.demo.base.BaseFragment;
import com.cy.demo.bean.ModuleItem;
import com.cy.demo.databind.BaseBindAdapter;
import com.cy.demo.databind.DataBindModule;
import com.cy.demo.viewModule.MainViewModule;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 CY
 * @创建时间 2020/7/29 17:26
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
public class MainFragment extends BaseFragment {
    private MainViewModule mainViewModule;
    private BaseBindAdapter<ModuleItem> bindAdapter;


    @Override
    protected void initVariable() {
        mainViewModule=getViewModule(MainViewModule.class);
        bindAdapter=new BaseBindAdapter<ModuleItem>(R.layout.item_module,getModules());
    }

    @Override
    protected DataBindModule initDataBindModule() {
        return new DataBindModule(R.layout.fragment_main)
                .addParam(BR.adapter,bindAdapter);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ModuleItem moduleItem=bindAdapter.getData().get(position);
                nav().navigate(moduleItem.navId);
            }
        });
    }

    private List<ModuleItem> getModules(){
        List<ModuleItem> data=new ArrayList<>();
        data.add(new ModuleItem("蓝牙",R.id.action_mainFragment_to_BLFragment));
        return data;
    }

}