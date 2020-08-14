package com.cy.demo;

import org.junit.Test;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    Object result;
    @Test
    public void addition_isCorrect() {

        Single<String> stringSingle=Single.create(emitter -> {
            emitter.onSuccess("123123");
        });

        stringSingle.subscribe((s, throwable) -> {
            System.out.println(s);
        });

        stringSingle.cache();

    }
}