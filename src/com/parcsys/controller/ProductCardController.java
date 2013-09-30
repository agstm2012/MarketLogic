package com.parcsys.controller;


import android.os.Environment;
import com.parcsys.controller.datawork.DataManager;
import com.parcsys.controller.datawork.JSONDataManager;
import com.parcsys.model.Product;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ivanPC
 * Date: 29.08.13
 * Time: 14:47
 * To change this template use File | Settings | File Templates.
 */
public class ProductCardController {
    private static List<Product> products = new ArrayList<Product>();
    //public DataManager dataManager;

    private static class InstanceHolder {
        private static final ProductCardController instance = new ProductCardController();
    }

    public static ProductCardController getInstance() {
        return InstanceHolder.instance;
    }

    private ProductCardController() {
        //dataManager = new JSONDataManager(Environment.getExternalStorageDirectory() + File.separator + "buy.json");
        //dataManager.readData();
    }

    public synchronized void addProduct(Product product) {
        products.add(product);
        //dataManager.updateData(products);
    }

    public synchronized void removeProduct(int position) {
        products.remove(position);
        //dataManager.updateData(products);
    }

    public synchronized Product findById(long id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public synchronized Product findByPosition(int position) {
        return products.get(position);
    }

    public synchronized List<Product> getProducts() {
        return products;
    }

    public synchronized void removeProductById(long id) {
        Iterator<Product> productIterator = products.iterator();
        while (productIterator.hasNext()) {
            Product product = productIterator.next();
            if(product.getId() == id) {
                productIterator.remove();
                break;
            }
        }
    }
}
