package com.parcsys.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 8/18/13
 * Time: 8:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class Category {
    enum names {
        home, professional, ultimate;
    }

    public static String Professional(){
        return names.professional.toString();
    }

    public static String Home(){
        return names.home.toString();
    }

    public static String Ultimate() {
        return names.ultimate.toString();
    }

    public static String[] getCategories() {
        List<String> categories = new ArrayList<String>();
        categories.add(names.home.toString());
        categories.add(names.professional.toString());
        categories.add(names.ultimate.toString());
        return convertListToString(categories);
    }

    private static String[] convertListToString(List<String> catList) {
        String[] catArr = new String[catList.size()];
        for(int i = 0; i < catList.size(); i++) {
            catArr[i] = catList.get(i);
        }
        return catArr;
    }
}
