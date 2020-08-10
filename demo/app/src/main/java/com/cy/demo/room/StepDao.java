package com.cy.demo.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @创建者 CY
 * @创建时间 2020/8/10 14:43
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
@Dao
public interface StepDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(StepBean stepBean);

    @Query("SELECT * FROM step WHERE date=:primary")
    StepBean selectByPrimary(String primary);


    @Query("SELECT * FROM step")
    Flowable<List<StepBean>> list();


}
