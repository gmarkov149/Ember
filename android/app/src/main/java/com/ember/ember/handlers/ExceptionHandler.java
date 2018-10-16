package com.ember.ember.handlers;

import android.app.Activity;

public class ExceptionHandler implements java.lang.Thread.UncaughtExceptionHandler {

    private final Activity context;

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

    }

    public ExceptionHandler(Activity context) {
        this.context = context;
    }
}
