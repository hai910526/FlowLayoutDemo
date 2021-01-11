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
import com.zly.flowlayoutdemo.ui.FlexboxLayoutActivity;
import com.zly.flowlayoutdemo.ui.FlexboxRvActivity;
import com.zly.flowlayoutdemo.ui.FlowLayoutActivity;
import com.zly.flowlayoutdemo.ui.RvActivity;
import com.zly.flowlayoutdemo.ui.ZFlowLayoutActivity;
import com.zly.flowlayoutdemo.widget.ZFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.btn_01:
                startActivity(new Intent(this, FlowLayoutActivity.class));
                break;
            case R.id.btn_02:
                startActivity(new Intent(this, ZFlowLayoutActivity.class));
                break;
            case R.id.btn_03:
                startActivity(new Intent(this, RvActivity.class));
                break;
            case R.id.btn_04:
                startActivity(new Intent(this, FlexboxLayoutActivity.class));
                break;
            case R.id.btn_05:
                startActivity(new Intent(this, FlexboxRvActivity.class));
                break;
        }
    }
}