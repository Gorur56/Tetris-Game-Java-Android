package com.firstapp.tetrisgamee.presenters;

public interface PresenterObserver<T> {
    void onNext(T t);
}
