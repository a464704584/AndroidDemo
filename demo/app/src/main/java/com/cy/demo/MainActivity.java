package com.cy.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cy.demo.BR;
import com.cy.demo.R;
import com.cy.demo.base.BaseActivity;
import com.cy.demo.bean.ModuleItem;
import com.cy.demo.databind.BaseBindAdapter;
import com.cy.demo.databind.DataBindModule;
import com.cy.demo.databinding.ItemModuleBinding;
import com.cy.demo.viewModule.MainViewModule;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class MainActivity extends BaseActivity {

    @Override
    protected void initVariable() {

    }

    @Override
    protected DataBindModule initDataBindModule() {
        return new DataBindModule(R.layout.activity_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}