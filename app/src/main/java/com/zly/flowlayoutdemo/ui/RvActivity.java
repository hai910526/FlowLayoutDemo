package com.zly.flowlayoutdemo.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.zly.flowlayoutdemo.R;
import com.zly.flowlayoutdemo.adapter.CommomRvAdapter;
import com.zly.flowlayoutdemo.adapter.CommomRvViewHolder;
import com.zly.flowlayoutdemo.widget.FlowLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class RvActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        loadData();

        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        mRecyclerView.setLayoutManager(flowLayoutManager);

        mRecyclerView.setAdapter(new CommomRvAdapter<String>(this, mDatas, R.layout.item_rv) {
            @Override
            protected void fillData(CommomRvViewHolder holder, int position, String s) {
                holder.setText(R.id.tv_label, s);
            }
        });

    }

    private void loadData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            if (i % 2 == 0) {
                mDatas.add("数据" + i);
            } else {
                mDatas.add("数据数据数据" + i);
            }
        }
    }
}