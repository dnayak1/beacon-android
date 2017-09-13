package com.example.dhirajnayak.introtobeacons;

import android.os.AsyncTask;
import android.speech.tts.Voice;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by dhirajnayak on 9/13/17.
 */

public class ProductAsyncTask extends AsyncTask<String,Void,ArrayList<Product>>{

    IData activity;

    public ProductAsyncTask(IData activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<Product> doInBackground(String... strings) {
        try {
            URL url=new URL(strings[0]);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int statusCode=connection.getResponseCode();
            if(statusCode== HttpsURLConnection.HTTP_OK){
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder=new StringBuilder();
                String line=bufferedReader.readLine();
                while ((line!=null)){
                    stringBuilder.append(line);
                    line=bufferedReader.readLine();
                }
                return ProductUtil.ProductJSONParser.parseProducts(stringBuilder.toString());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Product> products) {
        super.onPostExecute(products);
        activity.setupData(products);
    }

    static public interface IData{
        public void setupData(ArrayList<Product> products);
    }
}
