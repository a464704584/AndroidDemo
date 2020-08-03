package com.cy.demo.blue;

import java.util.Arrays;

/**
 * @创建者 CY
 * @创建时间 2020/8/3 17:48
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
public class BTResponse {
    private int STATE_DISCONNECTED = 0;
    /** The profile is in connecting state */
    private int STATE_CONNECTING = 1;
    /** The profile is in connected state */
    private int STATE_CONNECTED = 2;
    /** The profile is in disconnecting state */
    private int STATE_DISCONNECTING = 3;


    public int code=-1;
    public byte[] bytes;

    public BTResponse(int code) {
        this(code,null);
    }

    public BTResponse(int code, byte[] bytes) {
        this.code = code;
        this.bytes = bytes;
    }

    @Override
    public String toString() {
        return "BTResponse{" +
                "code=" + code +
                ", bytes=" + Arrays.toString(bytes) +
                '}';
    }
}