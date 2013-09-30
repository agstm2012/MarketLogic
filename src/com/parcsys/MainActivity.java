package com.parcsys;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.Toast;
import com.parcsys.model.Product;

public class MainActivity extends TabActivity {
    static boolean started = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Intent intent = getIntent();

        TabHost tabHost = getTabHost();
        TabHost.TabSpec tabSpec1 = null;
        TabHost.TabSpec tabSpec2 = null;

        if (started) {
            String currentTab = intent.getStringExtra("tab");
            String pushButton = intent.getStringExtra("button");
            createTabs(tabHost, tabSpec1, tabSpec2, currentTab, pushButton);
        } else {
            createTabs(tabHost, tabSpec1, tabSpec2, "", "");
            started = true;
        }
    }

    private void createTabs(TabHost tabHost, TabHost.TabSpec tabSpec1, TabHost.TabSpec tabSpec2, String currentTab, String pushButton) {
        Intent storeIntent = new Intent(this, StoreActivity.class);
        Intent cardIntent = new Intent(this, CardActivity.class);
        if(pushButton.equals("add")) {
            Intent intent = getIntent();
            Product product = (Product) intent.getSerializableExtra("product");
            storeIntent.putExtra("product", product);
        }
        tabSpec1 = tabHost.newTabSpec("store");
        tabSpec1.setIndicator("Хранилище", getResources().getDrawable(R.drawable.tab_icon_selector_store));
        tabSpec1.setContent(storeIntent);
        tabHost.addTab(tabSpec1);
        tabSpec2 = tabHost.newTabSpec("card");
        tabSpec2.setIndicator("Корзина", getResources().getDrawable(R.drawable.tab_icon_selector_card));
        tabSpec2.setContent(cardIntent);
        tabHost.addTab(tabSpec2);

        if(currentTab.equals("")) {
            tabHost.setCurrentTabByTag("store");
        } else {
            tabHost.setCurrentTabByTag(currentTab);
            Toast toast = Toast.makeText(getApplicationContext(), pushButton, 3000);
            toast.show();
        }
    }
}
