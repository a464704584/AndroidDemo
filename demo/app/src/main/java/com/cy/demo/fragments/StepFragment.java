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

import com.cy.demo.R;
import com.cy.demo.base.BaseFragment;
import com.cy.demo.databind.DataBindModule;

import java.util.List;

import duoshine.rxandroidbluetooth.bluetoothprofile.BluetoothWriteProfile;

/**
 * @创建者 CY
 * @创建时间 2020/8/3 9:49
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
public class StepFragment extends BaseFragment implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;
    @Override
    protected void initVariable() {

    }

    @Override
    protected DataBindModule initDataBindModule() {
        return new DataBindModule(R.layout.fragment_step);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

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
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_STEP_COUNTER){
            Log.e(TAG, "onSensorChanged: 当前步数：" + event.values[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}