package com.parcsys.controller.listeners;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import com.parcsys.CardActivity;
import com.parcsys.controller.ProductStoreController;
import com.parcsys.controller.adapters.ProductStoreAdapter;
import com.parcsys.controller.services.ChangeService;
import com.parcsys.interfaces.Constants;

import java.util.concurrent.TimeUnit;


/**
 * Created with IntelliJ IDEA.
 * User: ivanPC
 * Date: 09.09.13
 * Time: 16:05
 * To change this template use File | Settings | File Templates.
 */
public class BuyProductListener implements View.OnClickListener {
    Handler handler;
    long idProduct;

    public BuyProductListener(long idProduct, Handler handler) {
        this.idProduct = idProduct;
        this.handler = handler;
    }
    public void onClick(View view) {
        Thread buyThread = new Thread(new Runnable() {
            public void run() {
                Message msg = handler.obtainMessage(1, 0, (int) idProduct);
                handler.sendMessage(msg);
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                msg = handler.obtainMessage(2, 0, (int) idProduct);
                handler.sendMessage(msg);
            }
        });
        buyThread.start();
    };
}



