package com.parcsys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.parcsys.controller.ProductCardController;
import com.parcsys.controller.ProductStoreController;
import com.parcsys.controller.adapters.ProductStoreAdapter;
import com.parcsys.controller.handlers.ChangeHandler;
import com.parcsys.controller.services.ChangeService;
import com.parcsys.interfaces.Constants;
import com.parcsys.model.Product;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: ivanPC
 * Date: 14.08.13
 * Time: 16:44
 * To change this template use File | Settings | File Templates.
 */
public class StoreActivity extends Activity {
    ProductStoreAdapter productAdapter;
    //без него почему то не отобржаеться main activity
    public Handler handler = new ChangeHandler(this) {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    productSetCheckedFalse(msg.arg2);
                    break;
                case 2:
                    productSetCheckedTrueBuyProduct(msg.arg2, activity);
                    break;
                case 3:
                    addProductAction(msg.obj);
                    break;
            }
        }
    };

    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store);
        productAdapter = new ProductStoreAdapter(this, ProductStoreController.getInstance().getProducts(), handler);
        ListView lvMain = (ListView) findViewById(R.id.lvStore);
        lvMain.setAdapter(productAdapter);
        Button addBtn = (Button) findViewById(R.id.button_add_activity);
        addBtn.setOnClickListener(new AddProductOnClickListener(this));

        sendProductHandlerRunExecute();
    }


    private class AddProductOnClickListener implements View.OnClickListener {
        Activity activity;

        public AddProductOnClickListener(Activity activity) {
            this.activity = activity;
        }

        public void onClick(View v) {
            Intent intent = getIntent();
            intent.setClass(activity, AddActivity.class);
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }

    private void productSetCheckedFalse(long idProduct) {
        ProductStoreController.getInstance().findViewProductById(idProduct).setChecked(false);
        productAdapter.notifyDataSetChanged();
    }

    private void productSetCheckedTrueBuyProduct(long idProduct, Activity activity) {
        if (ProductStoreController.getInstance().findById(idProduct) != null) {
            int number = ProductStoreController.getInstance().findById(idProduct).getNumber();
            if (number > 0) {
                number--;
                ProductStoreController.getInstance().findById(idProduct).setNumber(number);
                ProductStoreController.getInstance().findViewProductById(idProduct).setChecked(true);
                Product findProduct = ProductCardController.getInstance().findById(ProductStoreController.getInstance().findById(idProduct).getId());
                if (findProduct != null) {
                    findProduct.setNumber(findProduct.getNumber() + 1);
                } else {
                    try {
                        findProduct = ProductStoreController.getInstance().findById(idProduct).clone();
                        findProduct.setNumber(1);
                        ProductCardController.getInstance().addProduct(findProduct);
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
                Intent intent = new Intent(activity, ChangeService.class).putExtra(Constants.PARAM_TASK, Constants.TASK_BUY).putExtra(Constants.ID_PRODUCT, (int)idProduct);
                startService(intent);
                if (number == 0) {
                    ProductStoreController.getInstance().removeProductById(idProduct);
                }
                productAdapter.notifyDataSetChanged();
            }
        }
    }

    private void sendProductHandlerRunExecute() {
        Intent intent = getIntent();
        final Product product = (Product) intent.getSerializableExtra("product");
        if (product != null) {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = handler.obtainMessage(3, product);
                    handler.sendMessage(msg);
                }
            });
            t.start();
        }
    }
    //Todo допилить проверку на совпадение если есть такой объект добавить кол-во а не сам продукт
    private void addProductAction(Object obj) {
        Product product = (Product) obj;
        ProductStoreController.getInstance().addProduct(product);
        productAdapter.notifyDataSetChanged();
    }

    public void onResume() {
        super.onResume();
        productAdapter.notifyDataSetChanged();
    }
}

