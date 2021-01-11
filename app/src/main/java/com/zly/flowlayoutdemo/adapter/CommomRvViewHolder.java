package com.zly.flowlayoutdemo.adapter;

import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Cerated by xiaoyehai
 * Create date : 2021/1/11 17:45
 * description :
 */
public class CommomRvViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViewArray;

    public CommomRvViewHolder(View itemView) {
        super(itemView);
        mViewArray = new SparseArray<>();
    }

    /**
     * 通过id得到view
     *
     * @param viewId
     * @param <V>
     * @return
     */
    public <V extends View> V getView(int viewId) {
        View view = mViewArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViewArray.put(viewId, view);
        }
        return (V) view;
    }

    /**
     * 设置文字
     *
     * @param viewId
     * @param text
     * @return
     */
    public CommomRvViewHolder setText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    /**
     * 通过网址设置图片
     *
     * @param viewId
     * @param imgUrl
     * @return
     */
    public CommomRvViewHolder setImageFromUrl(int viewId, String imgUrl) {
        ImageView imageView = getView(viewId);
        //Glide.with(imageView.getContext()).load(imgUrl).into(imageView);
        return this;
    }

    /**
     * 通过资源来设置图片资源
     *
     * @param viewId
     * @param resId
     * @return
     */
    public CommomRvViewHolder setImageFromRes(int viewId, int resId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resId);
        return this;
    }
}
