package com.cy.demo.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @创建者 CY
 * @创建时间 2020/8/10 14:24
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
@Entity(tableName = "step")
public class StepBean {
    @PrimaryKey
    @NonNull
    private String date;
    private int num;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "StepBean{" +
                "date='" + date + '\'' +
                ", num=" + num +
                '}';
    }


}
