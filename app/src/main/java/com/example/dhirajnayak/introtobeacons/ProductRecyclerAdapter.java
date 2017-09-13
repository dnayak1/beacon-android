package com.example.dhirajnayak.introtobeacons;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by dhirajnayak on 9/13/17.
 */

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ProductRecyclerViewHolder> {
    ArrayList<Product> arrayListProduct=new ArrayList<>();
    Context mContext;
    //private IProductListener productListener;

    public ProductRecyclerAdapter(Context mContext, ArrayList<Product> arrayListProduct) {
        this.arrayListProduct = arrayListProduct;
        this.mContext = mContext;
        //this.productListener = productListener;

    }



    @Override
    public ProductRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        View view= layoutInflater.inflate(R.layout.list_item_layout,parent,false);
        ProductRecyclerAdapter.ProductRecyclerViewHolder productRecyclerViewHolder=new ProductRecyclerAdapter.ProductRecyclerViewHolder(view);
        return productRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(ProductRecyclerViewHolder holder, int position) {
        final Product product=arrayListProduct.get(position);
        String imgUrl="https://s3.us-east-2.amazonaws.com/productsdhiraj/Ass2Products/"+product.getPhoto();
        Picasso.with(mContext).load(imgUrl).into(holder.imageViewProduct);
        holder.textViewRegion.setText(product.getRegion());
        holder.textViewName.setText(product.getName());
        holder.textViewPrice.setText(product.getPrice().toString());
        holder.textViewDiscount.setText(product.getDiscount().toString());
    }



    @Override
    public int getItemCount() {
        return arrayListProduct.size();
    }

    public static class ProductRecyclerViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewProduct;
        TextView textViewPrice;
        TextView textViewName;
        TextView textViewDiscount;
        TextView textViewRegion;

        public ProductRecyclerViewHolder(View itemView) {
            super(itemView);
            imageViewProduct= (ImageView) itemView.findViewById(R.id.imageView);
            textViewName= (TextView) itemView.findViewById(R.id.textViewName);
            textViewDiscount= (TextView) itemView.findViewById(R.id.textViewDiscount);
            textViewPrice= (TextView) itemView.findViewById(R.id.textViewPrice);
            textViewRegion= (TextView) itemView.findViewById(R.id.textViewRegion);
        }
    }

}
