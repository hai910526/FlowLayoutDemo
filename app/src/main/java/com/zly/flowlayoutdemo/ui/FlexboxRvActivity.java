package com.zly.flowlayoutdemo.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.zly.flowlayoutdemo.R;
import com.zly.flowlayoutdemo.adapter.CommomRvAdapter;
import com.zly.flowlayoutdemo.adapter.CommomRvViewHolder;
import com.zly.flowlayoutdemo.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class FlexboxRvActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexbox_rv_layout);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);

        //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal。
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);//主轴为水平方向，起点在左端。

        //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列。
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);//按正常方向换行

        //justifyContent 属性定义了项目在主轴上的对齐方式。
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);//交叉轴的起点对齐。

        mRecyclerView.setLayoutManager(flexboxLayoutManager);

        loadData();

        CommomRvAdapter<String> adapter = new CommomRvAdapter<String>(this, mDatas, R.layout.item_rv) {
            @Override
            protected void fillData(CommomRvViewHolder holder, int position, String s) {
                holder.setText(R.id.tv_label, s);
            }
        };
        mRecyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener((view, position) -> {
            Toast.makeText(this, mDatas.get(position), Toast.LENGTH_SHORT).show();
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