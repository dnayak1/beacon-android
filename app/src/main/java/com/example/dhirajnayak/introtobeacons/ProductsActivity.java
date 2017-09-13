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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        new ProductAsyncTask(this).execute("http://13.59.212.226:5000/api/allProducts");
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
