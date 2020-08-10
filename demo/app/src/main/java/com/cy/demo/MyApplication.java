package com.cy.demo;

import android.app.Application;

import androidx.room.Room;

import com.cy.demo.room.BaseDB;

/**
 * @创建者 CY
 * @创建时间 2020/8/10 15:09
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
public class MyApplication extends Application {
    private static BaseDB db;

    @Override
    public void onCreate() {
        super.onCreate();
        db = Room.databaseBuilder(getApplicationContext(), BaseDB.class, "db_0")
                .allowMainThreadQueries()
                .build();
    }

    public static BaseDB getDb() {
        return db;
    }
}