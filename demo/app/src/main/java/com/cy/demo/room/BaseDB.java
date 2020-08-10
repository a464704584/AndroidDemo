package com.cy.demo.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * @创建者 CY
 * @创建时间 2020/8/10 14:43
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
@Database(entities = {StepBean.class}, version = 1 ,exportSchema = false)
public abstract class BaseDB extends RoomDatabase {
    public abstract StepDao getStepDao();
} 