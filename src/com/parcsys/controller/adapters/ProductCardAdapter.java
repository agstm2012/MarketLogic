package com.parcsys.controller.adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.parcsys.R;
import com.parcsys.controller.ProductCardController;
import com.parcsys.controller.dialogs.EditDialog;
import com.parcsys.model.Product;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ivanPC
 * Date: 09.09.13
 * Time: 16:23
 * To change this template use File | Settings | File Templates.
 */
public class ProductCardAdapter extends BaseAdapter{
    Context context;
    LayoutInflater layoutInflater;
    List<Product> objects;
    EditDialog editDialog;
    FragmentManager fragmentManager;
    long currentId = -1;

    public ProductCardAdapter(Context context, List<Product> products, FragmentManager fragmentManager) {
        this.context = context;
        this.objects = products;
        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.fragmentManager = fragmentManager;
    }

    public int getCount() {
        return objects.size();
    }

    public Object getItem(int position) {
        return objects.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public Product getProduct(int position) {
        return ((Product) getItem(position));
    }

    public void setTextDialog(long id) {
        if(id == currentId && currentId != -1) {
            editDialog.setNewNumber(ProductCardController.getInstance().findById(id).getNumber());
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item, parent, false);
        }
        Product product = getProduct(position);

        ((TextView) view.findViewById(R.id.name)).setText(product.getName());
        ((TextView) view.findViewById(R.id.price)).setText(product.getPrice() + "");
        ((TextView) view.findViewById(R.id.number)).setText(String.valueOf(product.getNumber()));
        ((TextView) view.findViewById(R.id.description)).setText(product.getDescription());
        ((TextView) view.findViewById(R.id.categories)).setText(product.getCategory());
        ((Button) view.findViewById(R.id.button)).setText("Edit");
        ((Button) view.findViewById(R.id.button)).setOnClickListener(new CreateEditDialog(product, this));
        return view;
    }

    public class CreateEditDialog implements View.OnClickListener {
        Product product;
        ProductCardAdapter productCardAdapter;
        public CreateEditDialog(Product product, ProductCardAdapter productCardAdapter) {
            this.product = product;
            this.productCardAdapter = productCardAdapter;
        }
        public void onClick(View view) {
            editDialog = new EditDialog(product, productCardAdapter);
            editDialog.show(fragmentManager, "dialog1");
            currentId = product.getId();
        }
    }
}
