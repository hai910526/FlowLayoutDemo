package com.zly.flowlayoutdemo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zly.flowlayoutdemo.R;
import com.zly.flowlayoutdemo.widget.ZFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class ZFlowLayoutActivity extends AppCompatActivity {

    private ZFlowLayout mZFlowLayout;

    private List<String> mDatas;

    private List<View> mViewList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_z_flow_layout);

        mZFlowLayout = (ZFlowLayout) findViewById(R.id.zFlowLayout);

        loadData();

        //ZFlowLayout的使用：可添加展开和收起按钮
        initZFlowLayout();
    }


    private void initZFlowLayout() {
        mViewList.clear();
        for (int i = 0; i < mDatas.size(); i++) {
            TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.item_search_history, mZFlowLayout, false);
            textView.setText(mDatas.get(i));
            mViewList.add(textView);
        }
        mZFlowLayout.setChildren(mViewList);

        mZFlowLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mZFlowLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int lineCount = mZFlowLayout.getLineCount();  //行数
                int twoLineViewCount = mZFlowLayout.getTwoLineViewCount();  //前两行里面view的个数
                int expandLineViewCount = mZFlowLayout.getExpandLineViewCount(); ///展开时显示view的个数
                if (lineCount > 2) {  //默认展示2行，其余折叠收起，最多展示5行
                    initIvClose(twoLineViewCount, expandLineViewCount);
                }
            }
        });

        mZFlowLayout.setOnTagClickListener((view, position) -> {
            //点击了
            Toast.makeText(this, mDatas.get(position), Toast.LENGTH_SHORT).show();
        });

    }

    private void initIvClose(int twoLineViewCount, int expandLineViewCount) {
        mViewList.clear();
        for (int i = 0; i < twoLineViewCount; i++) {
            TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.item_search_history, mZFlowLayout, false);
            textView.setText(mDatas.get(i));
            mViewList.add(textView);
        }

        //展开按钮
        ImageView imageView = (ImageView) LayoutInflater.from(this).inflate(R.layout.item_search_history_img, mZFlowLayout, false);
        imageView.setImageResource(R.mipmap.search_close);
        imageView.setOnClickListener(v -> {
            initIvOpen(twoLineViewCount, expandLineViewCount);

        });
        mViewList.add(imageView);
        mZFlowLayout.setChildren(mViewList);
        mZFlowLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mZFlowLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int lineCount = mZFlowLayout.getLineCount();
                int twoLineViewCount = mZFlowLayout.getTwoLineViewCount();
                if (lineCount > 2) {
                    initIvClose(twoLineViewCount - 1, mZFlowLayout.getExpandLineViewCount());
                }
            }
        });
    }

    private void initIvOpen(int twoLineViewCount, int expandLineViewCount) {
        mViewList.clear();

        /*for (int i = 0; i < mDatas.size(); i++) {
            TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.item_search_history, mZFlowLayout, false);
            textView.setText(mDatas.get(i));
            mViewList.add(textView);
        }*/

        for (int i = 0; i < expandLineViewCount; i++) {
            TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.item_search_history, mZFlowLayout, false);
            textView.setText(mDatas.get(i));
            mViewList.add(textView);
        }

        //收起按钮
        ImageView imageView = (ImageView) LayoutInflater.from(this).inflate(R.layout.item_search_history_img, mZFlowLayout, false);
        imageView.setImageResource(R.mipmap.search_open);
        imageView.setOnClickListener(v -> initIvClose(twoLineViewCount, expandLineViewCount));
        mViewList.add(imageView); //不需要的话可以不添加
        mZFlowLayout.setChildren(mViewList);
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