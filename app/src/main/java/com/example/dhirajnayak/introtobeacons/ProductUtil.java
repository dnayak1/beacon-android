package com.example.dhirajnayak.introtobeacons;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dhirajnayak on 9/13/17.
 */

public class ProductUtil {
    static public class ProductJSONParser{
        static ArrayList<Product> parseProducts(String in) throws JSONException {
            ArrayList<Product> productsArrayList=new ArrayList<>();
            JSONObject root=new JSONObject(in);
            JSONArray jsonArrayProduct=root.getJSONArray("result");
            for(int i=0;i<jsonArrayProduct.length();i++){
                JSONObject jsonObjectProduct=jsonArrayProduct.getJSONObject(i);
                Product product=new Product();
                product.setName(jsonObjectProduct.getString("name"));
                product.setRegion(jsonObjectProduct.getString("region"));
                product.setPhoto(jsonObjectProduct.getString("photo"));
                product.setPrice(jsonObjectProduct.getDouble("price"));
                product.setDiscount(jsonObjectProduct.getDouble("discount"));
                productsArrayList.add(product);
            }
            return productsArrayList;
        }
    }
}
