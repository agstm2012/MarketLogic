package com.parcsys.model;

/**
 * Created with IntelliJ IDEA.
 * User: ivanPC
 * Date: 30.08.13
 * Time: 12:08
 * To change this template use File | Settings | File Templates.
 */
public class UpdateView {
    Product product;
    boolean checked;

    public UpdateView(Product product, boolean checked) {
        this.product = product;
        this.checked = checked;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
