package com.zly.flowlayoutdemo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zly.flowlayoutdemo.R;
import com.zly.flowlayoutdemo.adapter.CommomRvAdapter;
import com.zly.flowlayoutdemo.adapter.CommomRvViewHolder;
import com.zly.flowlayoutdemo.widget.CustomRecycleView;
import com.zly.flowlayoutdemo.widget.FlowLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class CustomRvActivity extends AppCompatActivity {

    private CustomRecycleView mCrv;

    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_rv);

        mCrv = (CustomRecycleView) findViewById(R.id.crv);
        loadData();

        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        mCrv.setLayoutManager(flowLayoutManager);

        mCrv.setAdapter(new CommomRvAdapter<String>(this, mDatas, R.layout.item_rv) {
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