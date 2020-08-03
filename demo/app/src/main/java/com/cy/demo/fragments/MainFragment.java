package com.cy.demo.fragments;

import android.os.Bundle;
import android.util.Log;
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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

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


        byte[] c = new byte[16];
        c[0] = 0x06;
        c[1] = 0x01;
        c[2] = 0x01;
        c[3] = 0x01;

        byte [] a=Encrypt(c);
        Log.i(TAG,"aaa:"+new BigInteger(c).toString(16));
        Log.i(TAG,"aaa:"+new BigInteger(a).toString(16));

    }

    public byte[] Encrypt(byte[] sSrc) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(new byte[]{43,11,32,33,11,58,119, (byte) 200,43,11,32,33,11,58,119, (byte) 200}, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/NOPadding");
            IvParameterSpec iv = new IvParameterSpec(new byte[]{43,11,32,33,11,58,119, (byte) 200,43,11,32,33,11,58,119, (byte) 200});//使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
//            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(sSrc);
            return encrypted;
        } catch (Exception ex) {
            return null;
        }
    }
    private List<ModuleItem> getModules(){
        List<ModuleItem> data=new ArrayList<>();
        data.add(new ModuleItem("蓝牙",R.id.action_mainFragment_to_BLFragment));
        data.add(new ModuleItem("NFC",R.id.action_mainFragment_to_NFCFragment));
        data.add(new ModuleItem("步数",R.id.action_mainFragment_to_stepFragment));
        return data;
    }

}