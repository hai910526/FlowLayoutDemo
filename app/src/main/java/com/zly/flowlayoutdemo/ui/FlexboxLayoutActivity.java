package com.zly.flowlayoutdemo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.zly.flowlayoutdemo.R;
import com.zly.flowlayoutdemo.utils.AppUtils;

public class FlexboxLayoutActivity extends AppCompatActivity {

    private FlexboxLayout mFlexboxLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexbox_layout);

        mFlexboxLayout = (FlexboxLayout) findViewById(R.id.flexbox_layout);


        //动态添加
        // dynamicAddView();


    }

    private void dynamicAddView() {

        mFlexboxLayout.removeAllViews();

        // 通过代码向FlexboxLayout添加View
        for (int i = 0; i < 10; i++) {
            TextView textView = new TextView(this);
            textView.setBackground(getResources().getDrawable(R.drawable.label_bg_shape));
            textView.setText("散文" + i);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(AppUtils.dp2px(15), 0, AppUtils.dp2px(15), 0);
            textView.setTextColor(getResources().getColor(R.color.text_color));

            FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    AppUtils.dp2px(40));
            textView.setLayoutParams(layoutParams);
            mFlexboxLayout.addView(textView);

            //可以通过FlexboxLayout.LayoutParams 设置子元素支持的属性

            ViewGroup.LayoutParams params = textView.getLayoutParams();
            if (params instanceof FlexboxLayout.LayoutParams) {
                FlexboxLayout.LayoutParams lp = (FlexboxLayout.LayoutParams) params;
                //设置子元素的长度为它父容器长度的百分比
                //lp.setFlexBasisPercent(0.5f);
            }
        }
    }

}