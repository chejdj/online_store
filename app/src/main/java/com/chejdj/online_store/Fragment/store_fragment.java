package com.chejdj.online_store.Fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chejdj.online_store.MyApplication;
import com.chejdj.online_store.R;
import com.chejdj.online_store.adapter.Store_adapter;
import com.chejdj.online_store.tools.Goods;
import com.chejdj.online_store.tools.OnItemClickListener;
import com.chejdj.online_store.view.rectange_radius_imageview;
import com.squareup.leakcanary.RefWatcher;


import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class store_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private RecyclerView recyclerView;
    private List<Goods> goodsList = new ArrayList<Goods>();
    private SwipeRefreshLayout layout;
    private Store_adapter adapter;
    private Dialog dialog;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            layout.setRefreshing(false);
            adapter.notifyDataSetChanged();
            recyclerView.setClickable(true);
        }
    };

    public store_fragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("store_fragment", "开始创建storfragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.store);
        layout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Store_adapter(goodsList, getActivity());
        recyclerView.setAdapter(adapter);
        SwipeListener listener = new SwipeListener();
        layout.setOnRefreshListener(listener);
        listener.onRefresh();
        adapter.setOnClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                if (dialog == null) {
                    dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_design);
                }
                Goods goods = goodsList.get(position);
                Glide.with(getActivity()).load(goods.getPicture().getFileUrl()).into((rectange_radius_imageview) dialog.findViewById(R.id.good_pic));
                ((TextView) dialog.findViewById(R.id.good_contacts)).setText("联系人 " + goods.getContacts());
                ((TextView) dialog.findViewById(R.id.good_price)).setText("价格 " + goods.getPrice());
                ((TextView) dialog.findViewById(R.id.good_descri)).setText("描述 " + goods.getDiscrible());
                dialog.show();
            }
        });
        return view;
    }

    class SwipeListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            recyclerView.setClickable(false);
            layout.setRefreshing(true);
            query();
        }
    }

    public void query() {
        Log.e("store_fragment", "query");
        new Thread() {
            @Override
            public void run() {
                BmobQuery<Goods> query = new BmobQuery<Goods>();
                query.setLimit(8);
                query.findObjects(new FindListener<Goods>() {
                    @Override
                    public void done(List<Goods> list, BmobException e) {
                        goodsList.clear();
                        Log.e("strore_fragment", "等待回调卡死");
                        if (e == null) {
                            for (int i = 0; i < list.size(); i++) {
                                goodsList.add(list.get(i));
                            }
                            Log.e("store_fragment", "goodsList的大小为" + goodsList.size());
                            Message msg = handler.obtainMessage();
                            handler.sendMessage(msg);
                        } else {
                            Log.e("store_fragment error", e.toString());
                        }
                    }
                });
            }
        }.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher watcher= MyApplication.getRefWatcher(getActivity());
        watcher.watch(this);
    }
}
