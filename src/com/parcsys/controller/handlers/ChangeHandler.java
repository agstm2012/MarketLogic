package com.parcsys.controller.handlers;

import android.app.Activity;
import android.os.Handler;

/**
 * Created with IntelliJ IDEA.
 * User: ivanPC
 * Date: 12.09.13
 * Time: 14:25
 * To change this template use File | Settings | File Templates.
 */
public class ChangeHandler extends Handler {
    public Activity activity;
    public ChangeHandler(Activity activity) {
        this.activity = activity;
    }
}
