package com.abb.rockpaperscissor.game;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by Abarajithan
 */
public class GameBus {

    private static final Subject<Object> subject = PublishSubject.create().toSerialized();

    private GameBus() {
    }

    public static void publish(Object object) {
        subject.onNext(object);
    }

    public static <T> Observable<T> listen(Class<T> clazz) {
        return subject.ofType(clazz);
    }

}
