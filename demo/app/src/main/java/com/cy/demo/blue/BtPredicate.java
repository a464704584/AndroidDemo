package com.cy.demo.blue;

import io.reactivex.functions.Predicate;

/**
 * @创建者 CY
 * @创建时间 2020/8/3 16:06
 * @描述 天逢门下，降魔大仙，摧魔伐恶，鹰犬当先，二将闻召，立至坛前，依律道奉令，神功帝宣，魔妖万鬼，诛专战无盖，太上圣力，浩荡无边，急急奉北帝律令
 */
public final class BtPredicate {
    /**
     * Function, which checks if current object equals single argument or one of many
     * arguments. It can be used inside filter(...) method from RxJava.
     *
     * @param arguments many arguments or single argument
     * @return Predicate function
     */
    public static <T> Predicate<T> in(final T... arguments) {
        return new Predicate<T>() {
            @Override public boolean test(T object) {
                for (T t : arguments) {
                    if (t.equals(object)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    private BtPredicate() {
        throw new AssertionError("No instances.");
    }
}