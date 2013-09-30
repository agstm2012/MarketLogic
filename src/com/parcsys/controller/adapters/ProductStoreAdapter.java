package com.parcsys.controller.adapters;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.parcsys.R;
import com.parcsys.controller.ProductStoreController;
import com.parcsys.controller.listeners.BuyProductListener;
import com.parcsys.model.Product;
import com.parcsys.model.UpdateView;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ivanPC
 * Date: 09.09.13
 * Time: 10:05
 * To change this template use File | Settings | File Templates.
 */
public class ProductStoreAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    List<Product> objects;
    Handler handler;

    public ProductStoreAdapter(Context context, List<Product> products, Handler handler) {
        this.context = context;
        this.objects = products;
        this.handler = handler;
        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item, parent, false);
        }
        Product product = getProduct(position);
        UpdateView updateView = ProductStoreController.getInstance().findViewByPosition(position);

        ((TextView) view.findViewById(R.id.name)).setText(product.getName());
        ((TextView) view.findViewById(R.id.price)).setText(product.getPrice() + "");
        ((TextView) view.findViewById(R.id.number)).setText(String.valueOf(product.getNumber()));
        ((TextView) view.findViewById(R.id.description)).setText(product.getDescription());
        ((TextView) view.findViewById(R.id.categories)).setText(product.getCategory());
        ((Button) view.findViewById(R.id.button)).setEnabled(updateView.isChecked());
        ((Button) view.findViewById(R.id.button)).setText("Buy");
        ((Button) view.findViewById(R.id.button)).setOnClickListener(new BuyProductListener(product.getId(), handler));
        return view;
    }


}
