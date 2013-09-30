package com.parcsys.controller.datawork;

import com.parcsys.model.Product;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ivanPC
 * Date: 09.09.13
 * Time: 12:29
 * To change this template use File | Settings | File Templates.
 */
//Adapter interface
public interface DataManager {
    public List<Product> readData();
    public void createData(List<Product> products);
    public void updateData(List<Product> products);
}
