package com.cy.demo.base;

import android.os.Bundle;
import android.util.SparseArray;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.cy.demo.R;
import com.cy.demo.databind.DataBindModule;

/**
 * @创建者 CY
 * @创建时间 2020/7/29 10:55
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected final String TAG= getClass().getSimpleName();

    private DataBindModule dataBindModule;

    private ViewDataBinding viewDataBinding;
    private ViewModelProvider viewModelProvider;



    protected abstract void initVariable();
    protected abstract DataBindModule initDataBindModule();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
        dataBindModule=initDataBindModule();
        viewDataBinding=DataBindingUtil.setContentView(this,dataBindModule.getLayout());
        viewDataBinding.setLifecycleOwner(this);
        SparseArray params=dataBindModule.getParams();
        for (int i=0;i<params.size();i++){
            viewDataBinding.setVariable(params.keyAt(i),params.valueAt(i));
        }
    }

    protected <T extends ViewModel>T getViewModule(Class<T> c){
        if (viewModelProvider==null){
            viewModelProvider=new ViewModelProvider(this);
        }
        return viewModelProvider.get(c);
    }
}