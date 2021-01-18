package com.base.viewModel.adapter.splite_line;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.base.viewModel.adapter.BaseRecyclerViewAdapter;
import com.base.viewModel.BaseViewHolder;
import com.base.utils.ClickCheckedUtil;

import java.util.List;

public class FixedItemDecoration extends RecyclerView.ItemDecoration {

    //全局的rect
    protected Rect mOutRect = null;
    protected Point mOutPoint = null;
    //是否横向(此处只是作为方向的状态保存,确保切换方向时可以正常处理)
    protected boolean mIsHorizontal = false;
    //是否第一次进行渲染加载
    private boolean mIsFirstDecoration = true;

    //用于缓存recycleView中的子view的位置
    private int mChildPosition = 0;

    private BaseRecyclerViewAdapter adapter;

    private RecyclerView parent;

    //缓存View
    // protected SparseArrayCompat<Object> mViewCacheMap = null;

    //private List<Integer> list_itemType;//类型集合

    //即绘制其副本到界面上作为stick header view
    private BaseViewHolder headerView;

    // private BaseVM baseVM;

    private Object bean_posi;//当前显示的bean
    private int fixed_Posi;//当前悬浮的位置

    /**
     * 创建固定头部的itemDecoration
     */
    public FixedItemDecoration() {
        mOutRect = new Rect();
        mOutPoint = new Point();
    }

    private boolean hasStickHeader(int position) {
        if (position == -1) return false;
        return adapter.getItemViewType(position) == adapter.getTypeFixed();
    }

    public int getFixedPosition() {
        return fixed_Posi;
    }

    public BaseViewHolder getFixedViewHolder() {
        return headerView;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, final RecyclerView.State state) {
        //在绘制完整个recycleView之后绘制,绘制的结果将显示在所有Item上面
        super.onDrawOver(c, parent, state);

        if (adapter == null && parent.getAdapter() instanceof BaseRecyclerViewAdapter)
            adapter = (BaseRecyclerViewAdapter) parent.getAdapter();

        if (adapter == null) return;
        else if (!adapter.isFixedHeader()) return;

        if (this.parent == null) {
            this.parent = parent;
            setOnTouchListener();
        }

        //list_itemType = adapter.getItemTypeList();//替换为现有的集合

        mIsHorizontal = isHorizontal(parent);
        int position = 0;//当前第一个的位置
        View firstView = findFirstVisibleChildView(parent);
        //当第一项HeaderView处于最高点可见的过渡期时(topOfViewCanSeen在view.top与view.bottom之间)
        if (firstView != null) {
            //说明此时的第一项headerView还没有被移出界面,将此headerView的位置作为下面处理显示的headerView
            //即绘制其副本到界面上作为stick header view
            position = parent.getChildAdapterPosition(firstView);
        } else {
            //获取第一项View,注意此处的View不一定是第一项可见的View,可能是被缓存了的View(不可见)
            firstView = parent.getChildAt(0);
            if (firstView == null) return;
            mChildPosition = 0;
            //获取对应View的位置
            position = parent.getChildAdapterPosition(firstView);
        }
        //获取需要悬浮的position
        fixed_Posi = getFixedViewPosition(position);
        if (fixed_Posi == -1) return;
        if (headerView == null) {
            headerView = adapter.onCreateViewHolder(parent, adapter.getTypeFixed());
        }

        //获取悬浮位置的数据
        Object bean = adapter.getListBean(fixed_Posi);
        //如果悬浮位置的数据，与当前缓存的数据不一样
        if (bean != bean_posi) {
            bean_posi = bean;
            //headerView.setValues(bean);
            adapter.onBindViewHolder(headerView, fixed_Posi);
        }
        //判断是否需要进行绑定数据并测量headerView
        //当第一次加载时
        //当方向切换时
        //当headerView从未被测量时
        //当上一次渲染的固定头部与当前需要渲染的固定头部不一致时(包括布局)
        if (isNeedToBindAndMeasureView(parent, headerView.itemView, mIsHorizontal, position)) {
            /** 测量工作必须在这里处理,因为默认是布局处理的布局是wrap_content,需要设置数据之后再进行测量计算工作
             * 否则如果布局中某些view是wrap_content,当不存在数据时该view大小将为0,即无法显示
             * */
            measureHeaderView(parent, headerView.itemView, mIsHorizontal);
        }

        //此处不能使用canvas.save()来保存当前的状态再用canvas.restore()回复
        //否则的话固定header不会被其它头部顶出界面,因为界面被还原了
        calculateFixedRect(mOutRect, mOutPoint);

        //在canvas中指定绘制的区域
        c.clipRect(mOutRect);
        //根据recycleView计算当前headerView开始绘制的起点X,Y
        //此处决定了headerView是否绘制完整
        this.calculateParentStartDrawPoint(mOutRect, parent, mOutPoint);
        //调整canvas的绘制起点
        c.translate(mOutRect.left, mOutRect.top);
        //将View绘制到canvas上
        headerView.itemView.draw(c);

    }

    private void setOnTouchListener() {
        parent.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                switch (e.getAction()) {
                    case MotionEvent.ACTION_UP:
                        //点击的位置坐标
                        float x = e.getX();
                        float y = e.getY();

                        //固定头部的区域
                        Rect rect = new Rect();
                        //绘制头部的偏移量
                        Point point = new Point();
                        //计算当前头部区域和偏移量大小
                        calculateFixedRect(rect, point);

                        //如果处在点击区域，则拦截该点击事件
                        if (x <= rect.right && y <= rect.bottom) {
                            //一秒内只能执行一次点击
                            if (ClickCheckedUtil.onClickChecked(1000) && headerView != null) {
                                //对点击事件进行分发
                                List<Integer> onclick_list = headerView.getOnClickResIdList();
                                for (int i = 0; i < onclick_list.size(); i++) {
                                    int resId = onclick_list.get(i);
                                    View view = headerView.findViewById(resId);
                                    //获取控件的区域
                                    Rect rect_view = new Rect();
                                    view.getDrawingRect(rect_view);
//                                    Rect rect_view = DensityUtil.getViewRectOnScreen(view);
                                    //对控件的区域进行偏移量计算
                                    updateViewDrawRect(rect_view, point);
                                    //找到分发的控件
                                    if (x >= rect_view.left && x <= rect_view.right && y >= rect_view.top && y <= rect_view.bottom) {
                                        if (fixed_Posi > -1) {
//                                            adapter.onItemClick(view, 0, fixed_Posi, bean_posi);
                                            headerView.onClickEvent(view, 0, fixed_Posi);
                                        }
                                    }
                                }

                            }
                            return true;
                        }
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    //计算头部需要显示的区域
    public void calculateFixedRect(Rect mOutRect, Point mOutPoint) {
        if (parent == null || headerView == null) return;
        //计算当前headerView需要绘制的区域
        this.calculateViewDrawRect(mOutRect, parent, headerView.itemView, mIsHorizontal);

        //计算当前headerView是否受到其它View的影响(有可能下一个headerView正在替换当前headerView的位置)
        //并返回受到影响的偏移量
        this.calculateViewDrawRectInflunceByOtherView(mOutPoint, mChildPosition, mOutRect, parent, adapter, mIsHorizontal);
        //更新当前绘制区域的偏移量
        //此处决定了headerView是否显示完全(可能整个绘制区域只是headerView的一部分)
        this.updateViewDrawRect(mOutRect, mOutPoint);
    }

    public int getFixedViewPosition(int position) {
        if (position == -1 || adapter == null) return -1;
        for (int i = position; i >= 0; i--) {
            if (adapter.getItemViewType(i) == adapter.getTypeFixed()) return i;
        }
        return -1;
    }

    /**
     * 更新当前需要绘制View的区域
     *
     * @param outRect 原始需要绘制的区域(没有任何偏移量时的绘制区域)
     * @param offset  偏移量数据,来自方法 {@link #calculateViewDrawRectInflunceByOtherView(Point outPoint, int childPosition, Rect normalRect, RecyclerView parent, BaseRecyclerViewAdapter adapter, boolean isHorizontal)}
     */
    public void updateViewDrawRect(Rect outRect, Point offset) {
        //获取原始区域的宽高
        int width = outRect.width();
        int height = outRect.height();
        //将宽高处理偏移量
        width += offset.x;
        height += offset.y;
        //重新计算其绘制区域(一般为缩小了)
        //此处是改变绘制区域的大小而不是调整绘制区域的位置
        int newRight = outRect.left + width;
        int newBottom = outRect.top + height;
        outRect.set(outRect.left, outRect.top, newRight, newBottom);
    }

    /**
     * 固定头部view是否需要重新测量数据,当布局方向改变或者本身未被测量时将返回true
     *
     * @param measureView  需要被测量的view
     * @param isHorizontal 布局方向
     * @param newPosition
     * @return
     */
    protected boolean isNeedToBindAndMeasureView(RecyclerView parent, View measureView, boolean isHorizontal, int newPosition) {
        boolean isNeed = true;
        //第一次渲染加载,必定进行测量
        if (mIsFirstDecoration) {
            mIsHorizontal = isHorizontal;
            //取消第一次加载标识
            mIsFirstDecoration = false;
        } else if (mIsHorizontal != isHorizontal) {
            //当前处理的布局方向与上一次处理的布局方向不同
            //重新测量加载
            mIsHorizontal = isHorizontal;
        }
        //当前view未被测量过
        else {
            //被加载过的情况下,不再需要进行绑定数据及测量
            //否则返回true进行绑定数据及测量
            isNeed = false;
        }
        //不管如何处理,最终必定会将当前渲染的位置保存起来
        // mLastDecorationPosition = newPosition;
        return isNeed;
    }

    /**
     * 计算stick header view绘制的区域
     *
     * @param outRect      用于存放计算后的数据
     * @param parent
     * @param headerView   需要绘制的stick header view,此view决定了头部绘制的宽高
     * @param isHorizontal
     */
    protected void calculateViewDrawRect(Rect outRect, RecyclerView parent, View headerView, boolean isHorizontal) {
        //以下所有坐标都以recycleView为基础,是相对于RecycleView的坐标

        //获取可开始绘制的位置
        int drawLeft = parent.getPaddingLeft();

        int drawTop = 0;
        // if (parent.getClipToPadding())
        drawTop = parent.getPaddingTop();

        int drawRight = 0;
        int drawBottom = 0;

        //若横向布局
        if (isHorizontal) {
            //宽根据view处理
            //高填充整个parent
            drawRight = drawLeft + headerView.getWidth();
            drawBottom = drawTop + (parent.getHeight() - parent.getPaddingBottom() - parent.getPaddingTop());
        } else {
            //竖向布局
            //宽填充整个parent
            //高根据view处理
            drawRight = drawLeft + (parent.getWidth() - parent.getPaddingLeft() - parent.getPaddingRight());
            drawBottom = drawTop + headerView.getHeight();
        }

        //获取headerView的layout参数
        ViewGroup.LayoutParams params = headerView.getLayoutParams();
        //判断headerView是否存在margin
        ViewGroup.MarginLayoutParams marginParams = null;
        if (params instanceof ViewGroup.MarginLayoutParams) {
            //存在margin时,绘制时的区域需要去除margin的部分
            marginParams = (ViewGroup.MarginLayoutParams) params;
            drawLeft += marginParams.leftMargin;
            drawTop += marginParams.topMargin;
            drawRight -= marginParams.rightMargin;
        }
        //设置绘制的区域
        outRect.set(drawLeft, drawTop, drawRight, drawBottom);
    }

    /**
     * 计算recycleView开始绘制stick header view的位置
     *
     * @param outRect 暂存变量,没有任何意义,只是用于保存返回的数据;可以是新创建的也可以是任何一个不再存储有用数据的rect
     * @param parent
     * @param offset  偏移量数据,来自方法 {@link #( Point , int, Rect , RecyclerView , RecyclerView.State, boolean)}
     */
    protected void calculateParentStartDrawPoint(Rect outRect, RecyclerView parent, Point offset) {
        //计算正常情况下的绘制起点位置
//        int drawLeft = parent.getLeft() + parent.getPaddingLeft();
//        int drawTop = parent.getTop() + parent.getPaddingTop();
        int drawLeft = parent.getPaddingLeft();
        int drawTop = parent.getPaddingTop();
        outRect.set(drawLeft, drawTop, 0, 0);
        //更新偏移量
        outRect.offset(offset.x, offset.y);
    }

    /**
     * 计算headerView的宽高
     *
     * @param parent
     * @param headerView
     * @param isHorizontal
     */
    protected void measureHeaderView(RecyclerView parent, View headerView, boolean isHorizontal) {
        //不存在layoutparams的view添加默认布局参数
        if (headerView.getLayoutParams() == null) {
            headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        int widthSpec;
        int heightSpec;

        //分横向及竖向进行测量处理
        if (!isHorizontal) {
            widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
            heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);
        } else {
            widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.UNSPECIFIED);
            heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.EXACTLY);
        }

        //测量并计算headerView宽高
        int childWidth = ViewGroup.getChildMeasureSpec(widthSpec, parent.getPaddingLeft() + parent.getPaddingRight(), headerView.getLayoutParams().width);
        int childHeight = ViewGroup.getChildMeasureSpec(heightSpec, parent.getPaddingTop() + parent.getPaddingBottom(), headerView.getLayoutParams().height);

        //测量并设置headerView宽高
        headerView.measure(childWidth, childHeight);

        //刷新layout布局
        headerView.layout(0, 0, headerView.getMeasuredWidth(), headerView.getMeasuredHeight());
    }

    /**
     * 计算headerView绘制区域是否被其它View影响,此方法用于处理其它headerItem正在替换当前stick header View的情况
     * 计算顶出上一个headerView的偏移量
     *
     * @param outPoint
     * @param childPosition
     * @param normalRect    正常的绘制区域,这里指的是在不考虑任何偏移量时当前headerView的绘制区域
     * @param parent
     * @param adapter       用于获取recycleView缓存的View总数
     * @param isHorizontal  布局方向
     * @return
     */
    protected Point calculateViewDrawRectInflunceByOtherView(Point outPoint, int childPosition, Rect normalRect, RecyclerView parent, BaseRecyclerViewAdapter adapter, boolean isHorizontal) {
        int childCount = adapter.getItemCount();
        //当有且只有一项时,不可能存在偏移量,不处理
        if (childCount <= 1) {
            return new Point(0, 0);
        }
        //childPosition是前面查找到的第一项可见childView,+1是从其后开始查找最近的一个headerView
        //这是因为第一项View可能是一个headerView,也可能是某一组分组中的子项,若是上一个分组的子项,则此时需要显示的还是该分组的headerView,否则需要显示下一个分组的header
        //从第二项开始查找是为了查找最近的一个headerView
        //该headerView可能是当前正在替换旧headerView的头部,也可能是远未达到顶部的headerView
        View itemView = this.searchFirstHeaderView(childPosition + 1, adapter.getItemCount(), parent);
        //根据布局方向计算并返回固定头部的偏移量
        return calculateOffsetAccordingOrientation(outPoint, itemView, normalRect, parent, isHorizontal);
    }

    /**
     * 判断当前的childView是否可见且在第一项位置,用于更精确处理固定头部显示的数据
     *
     * @param childView    recycleView缓存的子view
     * @param parent
     * @param isHorizontal 是否横向布局
     * @return
     */
    protected boolean isViewCanSeenAtFirstPosition(View childView, RecyclerView parent, boolean isHorizontal) {
        if (childView == null) {
            return false;
        }
        int edgeOfCanSeen = isHorizontal ? parent.getPaddingLeft() : parent.getPaddingTop();
        int edgeOfStart = isHorizontal ? childView.getLeft() : childView.getTop();
        int edgeOfEnd = isHorizontal ? childView.getRight() : childView.getBottom();

        return edgeOfStart <= edgeOfCanSeen && edgeOfEnd > edgeOfCanSeen;
    }

    /**
     * 根据布局方向计算偏移量
     *
     * @param outPoint
     * @param headerView   当前第一项可见view后的第一个headerView,来自 {@link #searchFirstHeaderView(int, int, RecyclerView)}
     * @param rect         当前固定头部需要绘制的完整区域(不考虑其它view的影响)
     * @param parent
     * @param isHorizontal 布局方向
     * @return
     */
    protected Point calculateOffsetAccordingOrientation(Point outPoint, View headerView, Rect rect, RecyclerView parent, boolean isHorizontal) {
        int offsetX = 0;
        int offsetY = 0;
        if (headerView != null) {
            //根据布局方向确定可见边缘为左边界还是上边界
            if (isHorizontal) {
                //横向布局处理
                if (headerView.getLeft() < rect.right && headerView.getLeft() > rect.left) {
                    offsetX = rect.right - headerView.getLeft();
                }
            } else {
                if (headerView.getTop() < rect.bottom && headerView.getTop() > rect.top) {
                    //如果查找得到的headerView已经在替换当前的stick headerView
                    //计算出需要处理的偏移量,否则不处理(即不存在偏移量,返回0)
                    offsetY = rect.bottom - headerView.getTop();
                }
            }
        }
        if (outPoint == null) {
            outPoint = new Point();
        }
        outPoint.set(offsetX * -1, offsetY * -1);
        //偏移量是负值,因为绘制区域将向上或者向左移动出界面
        return outPoint;
    }

    /**
     * 从指定位置向后查找第一个headerView
     *
     * @param beginSearchPosition 开始查找的位置,此处的位置是指 {@link RecyclerView#getChildAt(int)},不是adapter中的position
     * @param searchCount         需要查找的位置总数,一般来自 {@link RecyclerView.State#getItemCount()} ,是recycleView可见View的总数,不是adapter中item的总数
     * @param parent
     * @return 返回查找到的第一项headerView, 若查找不到返回null, 通过 {@link RecyclerView#getChildAdapterPosition(View)}可以获取当前view在adapter中的位置
     */
    protected View searchFirstHeaderView(int beginSearchPosition, int searchCount, RecyclerView parent) {
        View viewInSearch;
        int positionInSearch;
        for (int i = beginSearchPosition; i < searchCount; i++) {
            //获取指定位置的view(其实这应该是recycleView缓存的view)
            viewInSearch = parent.getChildAt(i);
            //获取其position
            positionInSearch = parent.getChildAdapterPosition(viewInSearch);
            //判断当前view是否为header
            if (hasStickHeader(positionInSearch)) {
                return viewInSearch;
            }
        }
        return null;
    }

    /**
     * 是否横向布局,由layoutManager决定
     *
     * @param parent
     * @return
     */
    protected boolean isHorizontal(RecyclerView parent) {
        boolean isHorizontal = false;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        //获取layoutManager的布局方向
        if (layoutManager instanceof LinearLayoutManager) {
            isHorizontal = ((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.HORIZONTAL;
        }
        return isHorizontal;
    }

    /**
     * 查找第一个可见的childView
     *
     * @param parent
     * @return
     */
    protected View findFirstVisibleChildView(RecyclerView parent) {

        //获取缓存的childView总数
        int childCount = parent.getChildCount();
        //逐一遍历
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            if (isViewCanSeenAtFirstPosition(childView, parent, mIsHorizontal)) {
                //查找到第一个可见childView时保存其当前的childView位置(后面需要查找此位置之后的headerView)
                mChildPosition = i;
                return childView;
            }
        }
        //若没有返回null,除非RecycleView不存在childView,不然正常情况下不会返回null
        return null;
    }

    public int findLastVisibleItemPosition(RecyclerView parent) {
        if (parent == null) return -1;
        //获取缓存的childView总数
        int childCount = parent.getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            View childView = parent.getChildAt(i);
            if (isViewCanSeenAtLastPosition(childView, parent, isHorizontal(parent))) {
                return parent.getChildAdapterPosition(childView);
            }
        }
        return -1;
    }

    /**
     * 判断当前的childView是否可见且在最后一项位置,用于更精确处理固定尾部显示的数据
     *
     * @param childView    recycleView缓存的子view
     * @param parent
     * @param isHorizontal 是否横向布局
     * @return
     */
    public boolean isViewCanSeenAtLastPosition(View childView, RecyclerView parent, boolean isHorizontal) {
        if (childView == null) {
            return false;
        }
        int edgeOfCanSeen = isHorizontal ? parent.getRight() - parent.getPaddingRight() : parent.getBottom() - parent.getPaddingBottom();
        int edgeOfStart = isHorizontal ? childView.getLeft() : childView.getTop();
        int edgeOfEnd = isHorizontal ? childView.getRight() : childView.getBottom();

        return edgeOfStart < edgeOfCanSeen && edgeOfEnd >= edgeOfCanSeen;
    }
}

