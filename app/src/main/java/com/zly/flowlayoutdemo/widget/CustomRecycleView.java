package com.zly.flowlayoutdemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.zly.flowlayoutdemo.R;


/**
 * 自定义recycleview，主要用于实现布局，分割线
 */

public class CustomRecycleView extends RecyclerView {

    public CustomRecycleView(Context context) {
        this(context, null);
    }

    public CustomRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomRecycleView);
        int layoutManagerType = a.getInteger(R.styleable.CustomRecycleView_layoutManagerType, 0);
        int spanCount = a.getInteger(R.styleable.CustomRecycleView_spanCount, 3);
        boolean canScroll = a.getBoolean(R.styleable.CustomRecycleView_canScroll, true);
        boolean divider = a.getBoolean(R.styleable.CustomRecycleView_useDivider, false);
        int dividerColor = a.getColor(R.styleable.CustomRecycleView_dividerColor, -1);
        int dividerHeight = (int) (a.getDimension(R.styleable.CustomRecycleView_dividerHeight, 3));
        int dividerResId = a.getResourceId(R.styleable.CustomRecycleView_dividerResId, -1);
        //        int dividerMarginStart = (int)(a.getDimension(R.styleable.CustomRecycleView_dividerResId,0));
        //        int dividerMarginEnd = (int)(a.getDimension(R.styleable.CustomRecycleView_dividerResId,0));


        DividerItemDecoration.DividerDec dividerDec = new DividerItemDecoration.DividerDec(
                a.getBoolean(R.styleable.CustomRecycleView_useStartDivider, false),
                a.getBoolean(R.styleable.CustomRecycleView_useTopDivider, false),
                a.getBoolean(R.styleable.CustomRecycleView_useEndDivider, false),
                a.getBoolean(R.styleable.CustomRecycleView_useBottomDivider, false)
        );


        initLayoutManager(layoutManagerType, canScroll, spanCount);
        initDivider(divider, layoutManagerType, dividerColor, dividerHeight, dividerResId, dividerDec);
        a.recycle();
    }

    private void initLayoutManager(int layoutManagerType, boolean canScroll, int spanCount) {
        setNestedScrollingEnabled(canScroll);
        switch (layoutManagerType) {
            case 0:
                setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) {

                    @Override
                    public boolean canScrollVertically() {
                        return canScroll && super.canScrollVertically();
                    }

                    @Override
                    public void onLayoutChildren(Recycler recycler, State state) {
                        //解决java.lang.IndexOutOfBoundsException: Inconsistency detected.的bug
                        try {
                            super.onLayoutChildren(recycler, state);
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case 1:
                setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false) {

                    @Override
                    public boolean canScrollHorizontally() {
                        return canScroll && super.canScrollHorizontally();
                    }

                    @Override
                    public void onLayoutChildren(Recycler recycler, State state) {
                        try {
                            super.onLayoutChildren(recycler, state);
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void smoothScrollToPosition(RecyclerView recyclerView,
                                                       State state, final int position) {

                        LinearSmoothScroller smoothScroller =
                                new LinearSmoothScroller(recyclerView.getContext()) {
                                    // 返回：滑过1px时经历的时间(ms)。
                                    @Override
                                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                                        return 150f / displayMetrics.densityDpi;
                                    }
                                };

                        smoothScroller.setTargetPosition(position);
                        startSmoothScroll(smoothScroller);
                    }

                });
                break;
            case 2:
                setLayoutManager(new GridLayoutManager(getContext(), spanCount) {

                    @Override
                    public void onLayoutChildren(Recycler recycler, State state) {
                        try {
                            super.onLayoutChildren(recycler, state);
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case 3:
                setLayoutManager(new FlowLayoutManager() {
                    @Override
                    public void onLayoutChildren(Recycler recycler, State state) {
                        try {
                            super.onLayoutChildren(recycler, state);
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case 4:
                //不设置任何适配器，留给外部自行设置
                break;
            case 5:
                //瀑布流
                setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) {

                    @Override
                    public void onLayoutChildren(Recycler recycler, State state) {
                        try {
                            super.onLayoutChildren(recycler, state);
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
        }
    }

    /***
     * 在setLayoutManager后面调用
     * @param divider 是否需要分割线
     * @param layoutManagerType 分割线方向
     */
    private void initDivider(boolean divider, int layoutManagerType, int dividerColor, int dividerHeight, int dividerResId, DividerItemDecoration.DividerDec dividerDec) {
        if (!divider) {
            return;
        }
        DividerItemDecoration dividerItemDecoration = null;
        switch (layoutManagerType) {
            case 0:
                dividerItemDecoration = getDividerItemDecoration(DividerItemDecoration.HORIZONTAL_LIST, dividerColor, dividerHeight, dividerResId, dividerDec);
                break;
            case 1:
                dividerItemDecoration = getDividerItemDecoration(DividerItemDecoration.VERTICAL_LIST, dividerColor, dividerHeight, dividerResId, dividerDec);
                break;
            case 2:
            case 3:
            case 5:
                dividerItemDecoration = getDividerItemDecoration(DividerItemDecoration.BOTH_SET, dividerColor, dividerHeight, dividerResId, dividerDec);
                break;
            default:
                break;
        }

        if (dividerItemDecoration != null) {
            addItemDecoration(dividerItemDecoration);
        }
    }

    private DividerItemDecoration getDividerItemDecoration(int orientation, int dividerColor, int dividerHeight, int dividerResId, DividerItemDecoration.DividerDec dividerDec) {
        DividerItemDecoration dividerItemDecoration;
        if (dividerResId > 0) {
            dividerItemDecoration = new DividerItemDecoration(getContext(), orientation, dividerResId, dividerDec);
        } else if (dividerHeight > 0) {
            dividerItemDecoration = new DividerItemDecoration(getContext(), orientation, dividerHeight, dividerColor, dividerDec);
        } else {
            dividerItemDecoration = new DividerItemDecoration(getContext(), orientation, dividerDec);
        }
        return dividerItemDecoration;
    }


    /***
     * 水平平滑滚动
     */
    public void moveToPosition(int position) {
        if (!(getLayoutManager() instanceof LinearLayoutManager)) {
            return;
        }
        LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        if (position > -1 && getAdapter() != null && position < getAdapter().getItemCount()) {
            scrollToPosition(position);
            layoutManager.scrollToPositionWithOffset(position, 0);
        }
    }

    /***
     * 还有问题
     * 水平平滑滚动
     * @param targetPos 目标位置
     */
    public boolean moveToPositionH(int targetPos) {
        boolean move = false;
        if (!(getLayoutManager() instanceof LinearLayoutManager)) {
            return move;
        }
        LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();

        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int firstItem = layoutManager.findFirstVisibleItemPosition();
        int lastItem = layoutManager.findLastVisibleItemPosition();
        //然后区分情况
        if (targetPos <= firstItem) {
            //当要置顶的项在当前显示的第一个项的前面时
            smoothScrollToPosition(targetPos);
        } else if (targetPos <= lastItem) {
            //当要置顶的项已经在屏幕上显示时
            int left = getChildAt(targetPos - firstItem).getLeft();
            smoothScrollBy(left, 0);
        } else {
            //当要置顶的项在当前显示的最后一项的后面时
            smoothScrollToPosition(targetPos);
            //这里这个变量是用在RecyclerView滚动监听里面的
            move = true;
        }
        return move;

    }
}
