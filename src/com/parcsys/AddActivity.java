package com.parcsys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.parcsys.controller.ProductStoreController;
import com.parcsys.model.Category;
import com.parcsys.model.Product;

/**
 * Created with IntelliJ IDEA.
 * User: ivanPC
 * Date: 10.09.13
 * Time: 15:29
 * To change this template use File | Settings | File Templates.
 */
public class AddActivity extends Activity {
    EditText nameText, descriptionText, numberText, priceText;
    Button btnAdd, btnCancel;
    Spinner categories;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_form);
        String[] data = Category.getCategories();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories = (Spinner) findViewById(R.id.category_form);
        categories.setAdapter(adapter);
        categories.setPrompt("Categories");
        categories.setSelection(1);
        nameText = (EditText) findViewById(R.id.name_form);
        descriptionText = (EditText) findViewById(R.id.description_form);
        numberText = (EditText) findViewById(R.id.number_form);
        priceText = (EditText) findViewById(R.id.price_form);
        btnAdd = (Button) findViewById(R.id.add_form);
        btnAdd.setOnClickListener(new AddOnClickListener(this));
        btnCancel = (Button) findViewById(R.id.cancel_form);
        btnCancel.setOnClickListener(new CancelOnClickListener(this));
    }

    private class CancelOnClickListener implements View.OnClickListener {
        Activity activity;
        public CancelOnClickListener(Activity activity) {
            this.activity = activity;
        }
        public void onClick(View view) {
            Intent intent = getIntent();
            intent.setClass(activity, MainActivity.class);
            intent.putExtra("button", "cancel");
            intent.putExtra("tab", "store");
            startActivity(intent);
        }
    }

    private class AddOnClickListener implements View.OnClickListener {
        Activity activity;
        public AddOnClickListener(Activity activity) {
            this.activity = activity;
        }
        public void onClick(View view) {
            if(isNotNullInputField()) {
                String name = nameText.getText().toString();
                String description = descriptionText.getText().toString();
                String category = categories.getSelectedItem().toString();
                int number = Integer.valueOf(numberText.getText().toString());
                float price = Float.valueOf(priceText.getText().toString());
                Product product = new Product(name, description, price, number);
                product.setId(ProductStoreController.getInstance().getLastElement().getId() + 1);
                product.addCategory(category);

                Intent intent = getIntent();
                intent.setClass(activity, MainActivity.class);
                intent.putExtra("button", "add");
                intent.putExtra("tab", "store");
                intent.putExtra("product", product);
                startActivity(intent);
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Введены не все поля", 3000);
                toast.show();
            }
        }
    }

    private boolean isNotNullInputField() {
        if (!nameText.getText().toString().equals("") && !descriptionText.getText().toString().equals("") &&
                !categories.getSelectedItem().toString().equals("") && !numberText.getText().toString().equals("")
                && !priceText.getText().toString().equals("")) {
            return true;
        }
        return false;
    }
}