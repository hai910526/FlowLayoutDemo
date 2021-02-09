package com.zly.flowlayoutdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.internal.FlowLayout;
import com.zly.flowlayoutdemo.ui.CustomRvActivity;
import com.zly.flowlayoutdemo.ui.FlexboxLayoutActivity;
import com.zly.flowlayoutdemo.ui.FlexboxRvActivity;
import com.zly.flowlayoutdemo.ui.FlowLayoutActivity;
import com.zly.flowlayoutdemo.ui.RvActivity;
import com.zly.flowlayoutdemo.ui.ZFlowLayoutActivity;
import com.zly.flowlayoutdemo.widget.ZFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.btn_01: //自定义流式布局FlowLayout(可指定显示行数)
                startActivity(new Intent(this, FlowLayoutActivity.class));
                break;
            case R.id.btn_02: //自定义流式布局ZFlowLayout(可以设置展开和收起按钮)
                startActivity(new Intent(this, ZFlowLayoutActivity.class));
                break;
            case R.id.btn_03: //使用RecyclerView实现流式布局(FlowLayoutManager)
                startActivity(new Intent(this, RvActivity.class));
                break;
            case R.id.btn_04: //FlexboxLayout实现流式布局
                startActivity(new Intent(this, FlexboxLayoutActivity.class));
                break;
            case R.id.btn_05: //FlexboxLayout和RecyclerView结合使用实现流式布局
                startActivity(new Intent(this, FlexboxRvActivity.class));
                break;
            case R.id.btn_06: //CustomRecycleView实现流式布局
                startActivity(new Intent(this, CustomRvActivity.class));
                break;
        }
    }
}