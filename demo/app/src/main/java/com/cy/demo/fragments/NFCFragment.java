package com.cy.demo.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cy.demo.BR;
import com.cy.demo.R;
import com.cy.demo.base.BaseFragment;
import com.cy.demo.base.BaseMainFragment;
import com.cy.demo.databind.DataBindModule;
import com.cy.demo.viewModule.NFCViewModule;

/**
 * @创建者 CY
 * @创建时间 2020/7/31 14:26
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
public class NFCFragment extends BaseFragment {
    private NFCViewModule nfcViewModule;

    private NfcAdapter adapter;

    private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();

            Log.i(TAG,""+action);
            int state = intent.getIntExtra(NfcAdapter.EXTRA_ADAPTER_STATE,
                    NfcAdapter.STATE_OFF);
            switch (state){
                case NfcAdapter.STATE_ON:
                    nfcViewModule.text.set("NFC 打开");
                    break;
                case NfcAdapter.STATE_OFF:
                    nfcViewModule.text.set("NFC 关闭");
                    break;
            }
        }
    };

    @Override
    protected void initVariable() {
        nfcViewModule=getViewModule(NFCViewModule.class);
        nfcViewModule.text.set("NFC");
    }

    @Override
    protected DataBindModule initDataBindModule() {
        return new DataBindModule(R.layout.fragment_nfc)
                .addParam(BR.viewModule,nfcViewModule);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter=NfcAdapter.getDefaultAdapter(activity);
        if (adapter==null){
            nfcViewModule.text.set("不支持NFC");
        }else {
            IntentFilter intentFilter=new IntentFilter();
            intentFilter.addAction(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED);
            intentFilter.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
            intentFilter.addAction(NfcAdapter.ACTION_TAG_DISCOVERED);
            intentFilter.addAction(NfcAdapter.ACTION_TECH_DISCOVERED);
            intentFilter.addAction(NfcAdapter.ACTION_TRANSACTION_DETECTED);

            getContext().registerReceiver(broadcastReceiver,intentFilter);



            if (!adapter.isEnabled()){
                nfcViewModule.text.set("NFC 未打卡");
            }else {
                nfcViewModule.text.set("NFC 已打开");
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(broadcastReceiver);
    }
}