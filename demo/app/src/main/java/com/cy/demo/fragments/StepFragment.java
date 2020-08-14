package com.cy.demo.fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cy.demo.BR;
import com.cy.demo.MyApplication;
import com.cy.demo.R;
import com.cy.demo.base.BaseFragment;
import com.cy.demo.base.BaseMainFragment;
import com.cy.demo.databind.DataBindModule;
import com.cy.demo.room.BaseDB;
import com.cy.demo.room.StepBean;
import com.cy.demo.room.StepDao;
import com.cy.demo.viewModule.StepViewModule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @创建者 CY
 * @创建时间 2020/8/3 9:49
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
public class StepFragment extends BaseFragment implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;

    private StepViewModule stepViewModule;

    private BaseDB db;
    private StepDao stepDao;
    private StepBean stepBean;
    private Disposable disposable;
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");

    @Override
    protected void initVariable() {
        stepViewModule=getViewModule(StepViewModule.class);
    }

    @Override
    protected DataBindModule initDataBindModule() {
        return new DataBindModule(R.layout.fragment_step)
                .addParam(BR.viewModule,stepViewModule);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        db= MyApplication.getDb();
        stepDao=db.getStepDao();
        Log.i(TAG,"onViewCreated");
        disposable=stepDao.list().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<StepBean>>() {
                    @Override
                    public void accept(List<StepBean> stepBeans) throws Exception {
                        Log.i(TAG,"asdasdasd");
                        for (StepBean item:stepBeans){
                            Log.i(TAG,"item"+item);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });

    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (sensorManager!=null){
            sensorManager.unregisterListener(this);
        }
        stepDao.insert(stepBean);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_STEP_COUNTER){
            if (stepBean==null){
                stepBean=stepDao.selectByPrimary(simpleDateFormat.format(new Date()));
                if (stepBean==null){
                    stepBean=new StepBean();
                    stepBean.setDate(simpleDateFormat.format(new Date()));
                    stepDao.insert(stepBean);
                }
            }else {
                stepBean.setNum(stepBean.getNum()+1);
            }
            stepViewModule.stepTotalNum.set(stepBean.getNum());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onStop() {
        super.onStop();
        if (!disposable.isDisposed()){
            disposable.dispose();
        }
    }
}