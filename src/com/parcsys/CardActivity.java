package com.parcsys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;
import com.parcsys.controller.ProductCardController;
import com.parcsys.controller.adapters.ProductCardAdapter;
import com.parcsys.interfaces.Constants;


/**
 * Created with IntelliJ IDEA.
 * User: ivanPC
 * Date: 14.08.13
 * Time: 16:44
 * To change this template use File | Settings | File Templates.
 */
public class CardActivity extends Activity {
    ProductCardAdapter productAdapter;
    BroadcastReceiver br;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card);
        productAdapter = new ProductCardAdapter(this, ProductCardController.getInstance().getProducts(), getFragmentManager());
        ListView lvMain = (ListView) findViewById(R.id.lvCard);
        lvMain.setAdapter(productAdapter);

        br = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                int task = intent.getIntExtra(Constants.PARAM_TASK, 0);
                int status = intent.getIntExtra(Constants.PARAM_STATUS, 0);
                int id = intent.getIntExtra("id", 0);
                if (status == Constants.STATUS_FINISH) {
                    if (task == Constants.TASK_BUY) {
                        productAdapter.setTextDialog(id);
                        productAdapter.notifyDataSetChanged();
                        //ProductCardController.getInstance().dataManager.updateData(ProductCardController.getInstance().getProducts());
                    }
                }
            }
        };
        IntentFilter intFilter = new IntentFilter(Constants.BROADCAST_ACTION);
        registerReceiver(br, intFilter);
    }

    public void onResume() {
        super.onResume();
        productAdapter.notifyDataSetChanged();
        //ProductCardController.getInstance().dataManager.updateData(ProductCardController.getInstance().getProducts());
    }


}