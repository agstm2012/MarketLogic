package com.parcsys.controller.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.parcsys.interfaces.Constants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 9/1/13
 * Time: 5:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChangeService extends Service {
    ExecutorService es;

    public void onCreate() {
        super.onCreate();
        es = Executors.newCachedThreadPool();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        int task = intent.getIntExtra(Constants.PARAM_TASK, 0);
        int id = intent.getIntExtra(Constants.ID_PRODUCT, 0);
        if (Constants.TASK_BUY == task) {
            WorkerBuy workerBuy = new WorkerBuy(startId, task, id);
            es.execute(workerBuy);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    class WorkerBuy implements Runnable {
        int startId;
        int task;
        int id;
        public WorkerBuy(int startId, int task, int id) {
            this.startId = startId;
            this.task = task;
            this.id = id;
        }
        public void run() {
            Intent intent = new Intent(Constants.BROADCAST_ACTION);
            intent.putExtra(Constants.PARAM_TASK, task);
            intent.putExtra(Constants.PARAM_STATUS, Constants.STATUS_FINISH);
            intent.putExtra("id", id);
            sendBroadcast(intent);
            stop();
        }
        public void stop() {
            stopSelfResult(startId);
        }
    }
}
