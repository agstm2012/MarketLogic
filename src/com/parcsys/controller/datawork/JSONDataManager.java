package com.parcsys.controller.datawork;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.parcsys.model.Product;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ivanPC
 * Date: 09.09.13
 * Time: 12:30
 * To change this template use File | Settings | File Templates.
 */
public class JSONDataManager implements DataManager{
    public String path;

    public JSONDataManager(String path, List<Product> products) {
        this.path = path;
        File file = new File(path);
        if (!file.exists()) {
            createData(products);
            readData();
        } else {
            readData();
        }
    }

    public List<Product> readData() {
        List<Product> readingProducts = null;
        try {
            StringBuilder builder = new StringBuilder();
            File file = new File(path);
            InputStream is = new FileInputStream(file);
            byte[] buffer = new byte[is.available()];
            while (is.read(buffer) != -1) {
                String string = new String(buffer);
                builder.append(string);
            }
            is.close();
            Type type = new TypeToken<List<Product>>() {
            }.getType();
            readingProducts = new Gson().fromJson(builder.toString(), type);
            //Todo setUpdateViews(readingProducts);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readingProducts;
    }

    public void createData(List<Product> products) {
        try {
            Gson gson = new Gson();
            File file = new File(path);
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(gson.toJson(products));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateData(List<Product> products) {
        try {
            Gson gson = new Gson();
            File file = new File(path);
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(gson.toJson(products));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPath() {
        return path;
    }
}
