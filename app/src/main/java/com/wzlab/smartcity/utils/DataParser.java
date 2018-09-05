package com.wzlab.smartcity.utils;


/**
 * Created by wzlab on 2018/7/12.
 */

public class DataParser {

    public static String getData(String s, String defaultValue){
        if(s == null || s.equals("") || s.equals("null") || s.equals(" ")){
            return defaultValue;
        }else {
            return s;
        }
    }

}
