package com.zly.flowlayoutdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Cerated by xiaoyehai
 * Create date : 2021/1/11 17:45
 * description :
 */
public abstract class CommomRvAdapter<T> extends RecyclerView.Adapter<CommomRvViewHolder> {

    private Context context;

    private List<T> list;

    private int layoutId;

    private OnItemClickListener mOnItemClickListener;

    private OnItemLongClickListener mOnItemLongClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;

    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }


    public CommomRvAdapter(Context context, List<T> list, int layoutId) {
        this.context = context;
        this.list = list;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public CommomRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, layoutId, null);
        return new CommomRvViewHolder(view);
    }

    /**
     * 将onBindViewHolder方法的第一个参数ViewHolder显式地指明为CommonAdapter.BaseViewHolder,不然会报错
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull CommomRvViewHolder holder, final int position) {
        // TODO: 2016/11/4 填充界面
        fillData(holder, position, list.get(position));
        setListener(holder, position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    protected abstract void fillData(CommomRvViewHolder holder, int position, T t);

    private void setListener(@NonNull CommomRvViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, position);
                }
            }
        });


        //item长按事件
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener != null) {
                    mOnItemLongClickListener.onItemLongClick(v, position);
                }
                //返回值决定了是否消费当前的事件，如果消费了（true），就不会传递到后面的单击事件中
                return true;
            }
        });
    }


    /**
     * 点击事件接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
}
