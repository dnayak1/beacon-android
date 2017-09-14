package com.example.dhirajnayak.introtobeacons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity implements ProductAsyncTask.IData {
    RecyclerView recyclerView;
    ProductRecyclerAdapter adapter;
    LinearLayoutManager layoutManager;
    String title;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        url=getIntent().getExtras().getString("URL");
        if(url.length()<42){
            title="All Products";
        }else{
            title=url.substring(42);
        }
        setTitle(title);
        new ProductAsyncTask(this).execute(url);

    }

    @Override
    public void setupData(ArrayList<Product> products) {
        adapter=new ProductRecyclerAdapter(ProductsActivity.this,products);
        recyclerView.setAdapter(adapter);
        layoutManager=new LinearLayoutManager(ProductsActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();;
    }
}
