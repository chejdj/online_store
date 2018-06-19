package com.chejdj.online_store.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chejdj.online_store.R;
import com.chejdj.online_store.tools.Goods;
import com.chejdj.online_store.tools.OnItemClickListener;

import java.util.List;

public class Store_adapter extends RecyclerView.Adapter<Store_adapter.ViewHolder> {
    private final List<Goods> goodsList;
    private final Context mcontext;
    private OnItemClickListener listener;

    public Store_adapter(List<Goods> goodsList, Context mcontext) {
        this.goodsList = goodsList;
        this.mcontext = mcontext;
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Goods goods = goodsList.get(position);
        holder.goods_name.setText(goods.getName());
        holder.goods_pri.setText(goods.getPrice());
        Glide.with(mcontext).load(goods.getPicture().getFileUrl()).into(holder.goods_pic);
        holder.goods_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView goods_name;
        final TextView goods_pri;
        final ImageView goods_pic;

        public ViewHolder(View itemView) {
            super(itemView);
            goods_name = itemView.findViewById(R.id.store_name);
            goods_pic = itemView.findViewById(R.id.store_pic);
            goods_pri = itemView.findViewById(R.id.store_price);
        }
    }
}
