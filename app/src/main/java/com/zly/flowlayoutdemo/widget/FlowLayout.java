package com.zly.flowlayoutdemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.zly.flowlayoutdemo.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Cerated by xiaoyehai
 * Create date : 2021/1/11 15:02
 * description :自定义流式布局(可指定显示行数)
 */
public class FlowLayout extends LinearLayout {

    /**
     * 默认间距
     */
    public static final int DEFAULT_SPACING = AppUtils.dp2px(10);

    /**
     * 横向间隔
     */
    private int mHorizontalSpacing = DEFAULT_SPACING;

    /**
     * 纵向间隔
     */
    private int mVerticalSpacing = DEFAULT_SPACING;

    /**
     * 是否需要布局，只用于第一次
     */
    boolean mNeedLayout = true;

    /**
     * 每一行是否平分空间:将剩余空间平均分配给每个子控件
     */
    private boolean isAverageInRow = false;

    /**
     * 当前行已用的宽度，由子View宽度加上横向间隔
     */
    private int mUsedWidth = 0;

    /**
     * 行的集合
     */
    private final List<Line> mLines = new ArrayList<>();

    /**
     * 行对象
     */
    private Line mLine = null;

    /**
     * 最大的行数
     */
    private int mMaxLinesCount = Integer.MAX_VALUE;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 设置横向间隔
     *
     * @param spacing
     */
    public void setHorizontalSpacing(int spacing) {
        if (mHorizontalSpacing != spacing) {
            mHorizontalSpacing = spacing;
            requestLayoutInner();
        }
    }

    /**
     * 设置纵向间隔
     *
     * @param spacing
     */
    public void setVerticalSpacing(int spacing) {
        if (mVerticalSpacing != spacing) {
            mVerticalSpacing = spacing;
            requestLayoutInner();
        }
    }

    /**
     * 设置最大行数
     *
     * @param count
     */
    public void setMaxLines(int count) {
        if (mMaxLinesCount != count) {
            mMaxLinesCount = count;
            requestLayoutInner();
        }
    }

    /**
     * 每一行是否平分空间
     *
     * @param isAverageInRow
     */
    public void setIsAverageInRow(boolean isAverageInRow) {
        if (isAverageInRow != isAverageInRow) {
            this.isAverageInRow = isAverageInRow;
            requestLayoutInner();
        }
    }

    private void requestLayoutInner() {
        AppUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                requestLayout();
            }
        });

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取自定义控件宽度
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingRight() - getPaddingLeft();

        //获取自定义控件高度
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();

        //获取自定义控件的宽高测量模式
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        restoreLine();// 还原数据，以便重新记录

        //获取子控件数量
        final int count = getChildCount();

        //测量每个子控件的大小，决定什么时候需要换行
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }

            //如果父控件是确定模式，子控件就包裹内容，否则子控件模式和父控件一样
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(sizeWidth,
                    modeWidth == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : modeWidth);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(sizeHeight,
                    modeHeight == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : modeHeight);

            //测量子控件
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);

            //如果当前行对象为空，初始化一个行对象
            if (mLine == null) {
                mLine = new Line();
            }

            //获取子控件宽度
            int childWidth = child.getMeasuredWidth();

            mUsedWidth += childWidth;//  //当前已使用宽度增加一个子控件

            //是否超出边界
            if (mUsedWidth <= sizeWidth) { //没有超出边界
                mLine.addView(child);// //给当前行添加一个子控件
                mUsedWidth += mHorizontalSpacing;// 加上间隔
                if (mUsedWidth >= sizeWidth) {   //增加水平间距后，超出边界，需要换行
                    if (!newLine()) {
                        //创建行失败，表示已经100行，不能在创建了，结束循环，不再添加
                        break;
                    }
                }
            } else {///超出边界
                if (mLine.getViewCount() == 0) { //1.当前没有控件，一添加控件就超出边界(子控件很长)
                    mLine.addView(child);//强制添加到当前行
                    if (!newLine()) {// 换行
                        break;
                    }

                } else {
                    //2.当前有控件，一添加控件就超出边界
                    //先还行，再添加
                    if (!newLine()) {// 换行
                        break;
                    }
                    // 在新的一行，不管是否超过长度，先加上去，因为这一行一个child都没有，所以必须满足每行至少有一个child
                    mLine.addView(child);
                    mUsedWidth += childWidth + mHorizontalSpacing;
                }
            }
        }

        //保存最后一行到集合
        if (mLine != null && mLine.getViewCount() > 0 && !mLines.contains(mLine)) {
            // 由于前面采用判断长度是否超过最大宽度来决定是否换行，则最后一行可能因为还没达到最大宽度，所以需要验证后加入集合中
            mLines.add(mLine);
        }

        //控件整体宽度
        int totalWidth = MeasureSpec.getSize(widthMeasureSpec);

        // 控件整体高度
        int totalHeight = 0;

        final int linesCount = mLines.size();
        for (int i = 0; i < linesCount; i++) {// 加上所有行的高度
            totalHeight += mLines.get(i).mHeight;
        }

        totalHeight += mVerticalSpacing * (linesCount - 1);// 加上所有间隔的高度
        totalHeight += getPaddingTop() + getPaddingBottom();// 加上padding

        //根据最新宽高测量整体布局的大小
        // 设置布局的宽高，宽度直接采用父view传递过来的最大宽度，而不用考虑子view是否填满宽度，因为该布局的特性就是填满一行后，再换行
        // 高度根据设置的模式来决定采用所有子View的高度之和还是采用父view传递过来的高度
        setMeasuredDimension(totalWidth, resolveSize(totalHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!mNeedLayout || changed) {// 没有发生改变就不重新布局
            mNeedLayout = false;
            int left = getPaddingLeft();// 获取最初的左上点
            int top = getPaddingTop();

            //遍历所以行对象，设置每行位置
            final int linesCount = mLines.size();
            for (int i = 0; i < linesCount; i++) {
                final Line oneLine = mLines.get(i);
                oneLine.layoutView(left, top);// 布局每一行
                top += oneLine.mHeight + mVerticalSpacing;// 更新top值，为下一行的top赋值
            }
        }
    }

    /**
     * 还原所有数据
     */
    private void restoreLine() {
        mLines.clear();
        mLine = new Line();
        mUsedWidth = 0;
    }

    /**
     * 换行方法
     */
    private boolean newLine() {
        mLines.add(mLine); //把上一行添加到集合
        if (mLines.size() < mMaxLinesCount) {
            //如果可以继续添加行
            mLine = new Line();
            mUsedWidth = 0; //宽度清零
            return true;
        }
        return false;
    }

    // ==========================================================================
    // Inner/Nested Classes
    // ==========================================================================

    /**
     * 代表着一行，封装了一行所占高度，该行子View的集合，以及所有View的宽度总和
     */
    class Line {
        int mWidth = 0;// 该行中所有的子View累加的宽度
        int mHeight = 0;// 该行中所有的子View中高度最高的那个子View的高度

        /**
         * 一行子控件的集合
         */
        List<View> views = new ArrayList<View>();

        /**
         * 添加一个子控件
         *
         * @param view
         */
        public void addView(View view) {// 往该行中添加一个
            views.add(view);
            mWidth += view.getMeasuredWidth();
            int childHeight = view.getMeasuredHeight();
            mHeight = mHeight < childHeight ? childHeight : mHeight;// 高度等于一行中最高的View
        }

        /**
         * 获取当前行子控件的个数
         *
         * @return
         */
        public int getViewCount() {
            return views.size();
        }

        /**
         * 摆放行对象
         *
         * @param l
         * @param t
         */
        public void layoutView(int l, int t) {// 布局
            int left = l;
            int top = t;
            int count = getViewCount();

            // 总宽度
            int layoutWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();

            // 剩余的宽度，是除了View和间隙的剩余空间
            //将剩余空间平均分配给每个子控件
            int surplusWidth = layoutWidth - mWidth - mHorizontalSpacing * (count - 1);

            if (surplusWidth >= 0) {// 剩余空间
                // 采用float类型数据计算后四舍五入能减少int类型计算带来的误差
                int splitSpacing = (int) (surplusWidth / count + 0.5); //平均每个控件分配的大小

                for (int i = 0; i < count; i++) {

                    final View view = views.get(i);
                    int childWidth = view.getMeasuredWidth();
                    int childHeight = view.getMeasuredHeight();

                    // 当控件比较矮时，需要居中展示
                    // 计算出每个View的顶点，是由最高的View和该View高度的差值除以2
                    int topOffset = (int) ((mHeight - childHeight) / 2.0 + 0.5);
                    if (topOffset < 0) {
                        topOffset = 0;
                    }
                    // 把剩余空间平均到每个View上
                    if (isAverageInRow) {
                        childWidth = childWidth + splitSpacing;
                    }
                    view.getLayoutParams().width = childWidth;
                    if (isAverageInRow) {
                        if (splitSpacing > 0) {// View的长度改变了，需要重新measure
                            int widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
                            int heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
                            view.measure(widthMeasureSpec, heightMeasureSpec);
                        }
                    }
                    // 布局View
                    view.layout(left, top + topOffset, left + childWidth, top + topOffset + childHeight);
                    left += childWidth + mHorizontalSpacing; // 为下一个View的left赋值
                }
            } else {
                if (count == 1) {
                    //没有剩余空间
                    //这个控件很长，占满整行
                    View view = views.get(0);
                    view.layout(left, top, left + view.getMeasuredWidth(), top + view.getMeasuredHeight());
                } else {
                    // 走到这里来，应该是代码出问题了，目前按照逻辑来看，是不可能走到这一步
                }
            }
        }
    }
}
