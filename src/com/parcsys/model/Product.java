package com.parcsys.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: ivanPC
 * Date: 16.08.13
 * Time: 11:58
 * To change this template use File | Settings | File Templates.
 */
public class Product implements Serializable, Cloneable{
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private String description;
    private float price;
    private String category;
    private int number;

    public Product(String name, String description, float price, int number) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.number = number;
    }

    public void addCategory(String category) {
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String toString() {
        return id + " " + name + " " + description + " " + price + " " + number + " " + category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        if (id != product.id) return false;
        if (number != product.number) return false;
        if (Float.compare(product.price, price) != 0) return false;
        if (description != null ? !description.equals(product.description) : product.description != null) return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        result = 31 * result + number;
        return result;
    }

    public Product clone() throws CloneNotSupportedException {
        return (Product)super.clone();
    }
}
