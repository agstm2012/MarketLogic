package com.parcsys.controller.dialogs;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.parcsys.R;
import com.parcsys.controller.ProductCardController;
import com.parcsys.controller.ProductStoreController;
import com.parcsys.controller.adapters.ProductCardAdapter;
import com.parcsys.model.Product;

/**
 * Created with IntelliJ IDEA.
 * User: ivanPC
 * Date: 10.09.13
 * Time: 14:32
 * To change this template use File | Settings | File Templates.
 */
public class EditDialog extends DialogFragment implements View.OnClickListener {
    Button cancel, edit;
    EditText numberText;
    TextView status;
    Product product;
    ProductCardAdapter productCardAdapter;

    public EditDialog(Product product, ProductCardAdapter productCardAdapter) {
        this.product = product;
        this.productCardAdapter = productCardAdapter;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Edit");
        View v = inflater.inflate(R.layout.edit_number, null);
        cancel = (Button) v.findViewById(R.id.cancel_dialog);
        cancel.setOnClickListener(this);
        edit = (Button) v.findViewById(R.id.edit_dialog);
        edit.setOnClickListener(this);
        status = (TextView) v.findViewById(R.id.status);
        numberText = (EditText) v.findViewById(R.id.change);
        numberText.setText(String.valueOf(product.getNumber()));
        return v;
    }

    public void setNewNumber(int newNumber) {
        numberText.setText(String.valueOf(newNumber));
        status.setText("Был произведено обновление продукта");
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_dialog:
                dismiss();
                break;
            case R.id.edit_dialog:
                status.setText("");
                if (!numberText.getText().toString().equals("")) {
                    int number = Integer.valueOf(numberText.getText().toString());
                    if (isLow(product, number)) {
                        int storeProductNumberAdd = product.getNumber() - number;
                        Product findStoreProduct = ProductStoreController.getInstance().findById(product.getId());
                        Product findCardProduct = ProductCardController.getInstance().findById(product.getId());
                        if (findStoreProduct != null) {
                            if (number != 0) {
                                findStoreProduct.setNumber(findStoreProduct.getNumber() + storeProductNumberAdd);
                                findCardProduct.setNumber(number);
                            } else {
                                findStoreProduct.setNumber(findStoreProduct.getNumber() + storeProductNumberAdd);
                                ProductCardController.getInstance().removeProductById(product.getId());
                            }
                        } else {
                            try {
                                findStoreProduct = findCardProduct.clone();
                                findStoreProduct.setNumber(storeProductNumberAdd);
                                findCardProduct.setNumber(number);
                                ProductStoreController.getInstance().addProduct(findStoreProduct);
                                if (number == 0) {
                                    ProductCardController.getInstance().removeProductById(product.getId());
                                }
                            } catch (CloneNotSupportedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (isHigh(product, number)) {
                        Product findStoreProduct = ProductStoreController.getInstance().findById(product.getId());
                        Product findCardProduct = ProductCardController.getInstance().findById(product.getId());
                        if (findStoreProduct != null) {
                            int getNumber = number - product.getNumber();
                            if (getNumber <= findStoreProduct.getNumber()) {
                                int cardProductNumberAdd = number - findCardProduct.getNumber();
                                if((findStoreProduct.getNumber() - cardProductNumberAdd) == 0)  {
                                    findCardProduct.setNumber(findCardProduct.getNumber() + cardProductNumberAdd);
                                    ProductStoreController.getInstance().removeProductById(product.getId());
                                } else {
                                    cardProductNumberAdd = number - findCardProduct.getNumber();
                                    findCardProduct.setNumber(findCardProduct.getNumber() + cardProductNumberAdd);
                                    findStoreProduct.setNumber(findStoreProduct.getNumber() - cardProductNumberAdd);
                                }
                            } else {
                                status.setText("Не хватает продуктов для проведения операции.");
                            }
                        } else {
                            status.setText("Такой товар закончился.");
                        }
                    }
                }
                if(status.getText().toString().equals("")) {
                    dismiss();
                }
                break;
        }
    }

    private boolean isLow(Product product, int number) {
        if (product.getNumber() > number) {
            return true;
        }
        return false;
    }

    private boolean isHigh(Product product, int number) {
        if (product.getNumber() < number) {
            return true;
        }
        return false;
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        productCardAdapter.notifyDataSetChanged();
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
