package com.parcsys.controller;


import com.parcsys.model.Category;
import com.parcsys.model.Product;
import com.parcsys.model.UpdateView;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ivanPC
 * Date: 27.08.13
 * Time: 15:27
 * To change this template use File | Settings | File Templates.
 */
public class ProductStoreController {
    private static List<Product> products = new ArrayList<Product>();
    final private static List<UpdateView> updateViews = new ArrayList<UpdateView>();

    private static class InstanceHolder {
        private static final ProductStoreController instance = new ProductStoreController();
    }

    public static ProductStoreController getInstance() {
        return InstanceHolder.instance;
    }

    private ProductStoreController() {
        testData();
    }

    private List<Product> generateData() {
        List<Product> generateListProducts = new ArrayList<Product>();
        for (int i = 1; i <= 20; i++) {
            Product product = new Product("Product " + i, "Product " + (i * 10), i + 1, i + 1);
            product.setId(i - 1);
            product.addCategory(Category.Home());
            generateListProducts.add(product);
        }
        return generateListProducts;
    }

    private void testData() {
        for (int i = 1; i <= 20; i++) {
            Product product = new Product("Product " + i, "Product " + (i * 10), i + 1, i + 1);
            product.setId(i - 1);
            product.addCategory(Category.Home());
            products.add(product);
        }
        setUpdateViews(products);
    }

    public synchronized void addProduct(Product product) {
        updateViews.add(new UpdateView(product, true));
        products.add(product);
    }

    public synchronized void removeProductByPosition(int position) {
        updateViews.remove(position);
        products.remove(position);
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
        Iterator<UpdateView> updateViewIterator = updateViews.iterator();
        while (updateViewIterator.hasNext()) {
            UpdateView updateView = updateViewIterator.next();
            if(updateView.getProduct().getId() == id) {
                updateViewIterator.remove();
                break;
            }
        }
    }

    public synchronized Product findById(long id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public synchronized UpdateView findViewProductById(long id) {
        for(UpdateView updateView:updateViews) {
            if(updateView.getProduct().getId() == id) {
                return updateView;
            }
        }
        return null;
    }

    public synchronized Product findByPosition(int position) {
        return products.get(position);
    }

    public synchronized UpdateView findViewByPosition(int position) {
        return updateViews.get(position);
    }

    public synchronized List<Product> getProducts() {
        return products;
    }

    public synchronized void setUpdateViews(List<Product> products) {
        for(Product product:products) {
            updateViews.add(new UpdateView(product, true));
        }
    }

    public synchronized Product getLastElement() {
        Product lastProduct = products.get(products.size() - 1);
        return lastProduct;
    }
}
