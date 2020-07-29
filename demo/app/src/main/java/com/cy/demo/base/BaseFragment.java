package com.cy.demo.base;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.cy.demo.databind.DataBindModule;

/**
 * @创建者 CY
 * @创建时间 2020/7/29 17:26
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
public abstract class BaseFragment extends Fragment {
    protected AppCompatActivity activity;


    private DataBindModule dataBindModule;

    private ViewDataBinding viewDataBinding;
    private ViewModelProvider viewModelProvider;



    protected abstract void initVariable();
    protected abstract DataBindModule initDataBindModule();


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity= (AppCompatActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                NavHostFragment.findNavController(BaseFragment.this)
                        .popBackStack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBindModule=initDataBindModule();
        viewDataBinding= DataBindingUtil.inflate(inflater, dataBindModule.getLayout(), container, false);
        viewDataBinding.setLifecycleOwner(this);
        SparseArray params=dataBindModule.getParams();
        for (int i=0;i<params.size();i++){
            viewDataBinding.setVariable(params.keyAt(i),params.valueAt(i));
        }
        return viewDataBinding.getRoot();
    }

    protected <T extends ViewModel>T getViewModule(Class<T> c){
        if (viewModelProvider==null){
            viewModelProvider=new ViewModelProvider(this);
        }
        return viewModelProvider.get(c);
    }


    protected NavController nav() {
        return NavHostFragment.findNavController(this);
    }

} 