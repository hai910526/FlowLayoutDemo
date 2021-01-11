package com.zly.flowlayoutdemo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.zly.flowlayoutdemo.utils.AppUtils;
import com.zly.flowlayoutdemo.R;
import com.zly.flowlayoutdemo.widget.FlowLayout;

import java.util.ArrayList;
import java.util.List;

public class FlowLayoutActivity extends AppCompatActivity {

    private FlowLayout mFlowLayout;

    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);

        mFlowLayout = (FlowLayout) findViewById(R.id.flowLayout);

        loadData();

        staticAddView();

        //动态添加View
        //dynamicAddView();
    }

    private void staticAddView() {
        int padding10 = AppUtils.dp2px(10);
        mFlowLayout.setPadding(padding10, padding10, padding10, padding10);
        for (int i = 0; i < mDatas.size(); i++) {
            TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.item_search_history, mFlowLayout, false);
            String s = mDatas.get(i);
            textView.setText(s);
            mFlowLayout.addView(textView);
            textView.setOnClickListener(v -> Toast.makeText(FlowLayoutActivity.this, s, Toast.LENGTH_SHORT).show());
        }
        mFlowLayout.setMaxLines(5); //设置最大行数
    }


    private void dynamicAddView() {
        int padding6 = AppUtils.dp2px(6);
        int padding8 = AppUtils.dp2px(8);
        int padding10 = AppUtils.dp2px(10);
        int padding12 = AppUtils.dp2px(12);
        mFlowLayout.setPadding(padding10, padding10, padding10, padding10);
        mFlowLayout.setHorizontalSpacing(padding8); //水平间距
        mFlowLayout.setVerticalSpacing(padding10);  //竖直边距

        for (int i = 0; i < mDatas.size(); i++) {
            String key = mDatas.get(i);
            TextView textView = new TextView(this);
            textView.setText(key);

            textView.setTextColor(Color.parseColor("#333333"));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            textView.setPadding(padding12, padding6, padding12, padding6);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.shape_search_lable_bg);
            mFlowLayout.addView(textView);

            textView.setOnClickListener(v -> Toast.makeText(FlowLayoutActivity.this, key, Toast.LENGTH_SHORT).show());
        }

        mFlowLayout.setMaxLines(5); //设置最大行数
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