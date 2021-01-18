package com.base.viewModel.adapter;

import android.os.SystemClock;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangkuo on 2017/10/7.
 * 不同的类型，用不同的ViewHolder
 */
public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> implements OnItemClickListener, ItemSlideHelper.Callback, ViewTreeObserver.OnGlobalLayoutListener {

    private static final int TYPE_HEADER = 100000;//头
    private static final int TYPE_FOOTER = 200000;//尾
    public static final int TYPE_CHILD = 0;//默认的项目

    private int TYPE_FIXED = 0;//用于悬浮的分组类型，例如 TYPE_FIXED=TYPE_GROUP

    private boolean isFixedHeader = false;//是否需要固定头部

    private boolean isRadio = false;//是否是单项选择

    public String foldName = "selected";//是否折叠的字段

//    private int last_select_position;//上次选中的position,isRadio=true时有用

    private boolean sideslip = false;//是否侧滑

    //存储不同类型的BaseVM  key=,TYPE_GROUP,TYPE_MIDDLE
    private SparseArrayCompat<BaseVM> baseVM_list;

    //存储头部View
    protected SparseArrayCompat<View> mHeaderViews;
    //存储尾部View
    protected SparseArrayCompat<View> mFootViews;

    //数据源
    protected List list;

    //被添加的recyclerView
    private RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;
    /**
     * {@link #onGlobalLayout()}
     */
    private int movePosition;//移动到的位置
    //点击事件
    protected OnItemClickListener itemClickListener;

    public abstract View onCreateHolderView(ViewGroup parent, int viewType);

    public abstract void initBaseVM();

    /**
     * 从中间局部刷新确定分页
     *
     * @param position
     * @param ps
     * @return
     */
    public abstract int getPageNO(int position, int ps);

    /**
     * 分页的第一个项位置
     *
     * @param ppn
     * @param ps
     * @return
     */
    public abstract int getPageStartPos(int ppn, int ps);


    //初始化数据
    public abstract void setList(List lst, boolean... toTop);

    public BaseRecyclerViewAdapter() {


        //数据源
        list = new ArrayList<>();
        //存储头部View
        mHeaderViews = new SparseArrayCompat<>();
        //存储尾部View
        mFootViews = new SparseArrayCompat<>();

        //存储不同类型的BaseVM
        baseVM_list = new SparseArrayCompat<>();

        initBaseVM();
    }

    /**
     * 设置需要选中，折叠的字段
     *
     * @param selected
     */
    public void setSelectFieldName(String selected) {
        foldName = selected;
    }

    public String getSelectFieldName() {
        return foldName;
    }

    //添加点击事件
    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public OnItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public int getTypeFixed() {
        return TYPE_FIXED;
    }

    //设置悬浮的类型
    public void setTypeFixed(int typeFixed) {
        TYPE_FIXED = typeFixed;
        setFixedHeader(true);
    }

    //是否是左滑菜单
    public void setSideslip(boolean sideslip) {
        this.sideslip = sideslip;
    }

    public void setRadio(boolean radio) {
        isRadio = radio;
    }

    public boolean isRadio() {
        return isRadio;
    }

    public boolean isFixedHeader() {
        return isFixedHeader;
    }

    private void setFixedHeader(boolean fixedHeader) {
        isFixedHeader = fixedHeader;
    }

    //推断是什么类型，默认为0,,   这个方法很重要，扩展必须重写这个方法
    public int concludeItemViewType(Object obj) {
        if (obj == null) return TYPE_CHILD;
        if (obj.getClass() == Header.class) return ((Header) obj).type;
        else if (obj.getClass() == Footer.class) return ((Footer) obj).type;
        else return TYPE_CHILD;
    }

    @Override
    public int getItemViewType(int position) {
        return concludeItemViewType(getListBean(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public int getRealCount() {
        return getItemCount() - getHeaderCount() - getFooterCount();
    }

    public Object getListBean(int position) {
        if (position >= list.size()) return null;
        if (position < 0) return null;
        return list.get(position);
    }

    public <T> T getListBeans(int position) {
        if (position >= list.size()) return null;
        if (position < 0) return null;
        return (T) list.get(position);
    }

    /**
     * @return 如果没有item 返回的是-1
     */
    public int getLastPosition() {
        return getItemCount() - getFooterCount() - 1;
    }

    public int getFirstPosition() {
        return getHeaderCount();
    }

    public int getFirstPosition(int itemType) {
        for (int i = 0; i < getItemCount(); i++) {
            if (itemType == getItemViewType(i)) return i;
        }
        return -1;
    }

    public int getLastPosition(int itemType) {
        for (int i = getItemCount() - 1; i >= 0; i--) {
            if (itemType == getItemViewType(i)) return i;
        }
        return -1;
    }

    public int getHeaderCount() {
        return mHeaderViews.size();
    }

    public int getFooterCount() {
        return mFootViews.size();
    }


    public boolean isHeaderType(int viewType) {
        return mHeaderViews.indexOfKey(viewType) > -1;
    }

    public boolean isFooterType(int viewType) {
        return mFootViews.indexOfKey(viewType) > -1;
    }

    public boolean isHasFooter() {
        return mFootViews.size() > 0;
    }

    public boolean isHasFooter(View view) {
        return mFootViews.indexOfValue(view) > -1;
    }

    public boolean isHasHeader(View view) {
        return mHeaderViews.indexOfValue(view) != -1;
    }

    public boolean isHasHeader() {
        return mHeaderViews.size() > 0;
    }

    public void removeHeard(View view) {
        if (view != null) {
            mHeaderViews.remove(TYPE_HEADER + view.hashCode());
            int pos = getFirstPosition(TYPE_HEADER + view.hashCode());
            if (pos > -1) remove(pos);
        }
    }

    public void removeFooter(View view) {
        if (view != null) {
            mFootViews.remove(TYPE_FOOTER + view.hashCode());
            int pos = getLastPosition(TYPE_FOOTER + view.hashCode());
            if (pos > -1) remove(pos);
        }
    }

    public void removeAllHeard() {
        int last = getHeaderCount() - 1;
        for (int i = last; i >= 0; i--) {
            remove(i);
        }
        mHeaderViews.clear();
    }

    public void removeAllFooter() {
        int count = getItemCount() - 1;
        for (int i = count; i >= 0; i--) {
            if (isFooterType(getItemViewType(i))) {
                remove(i);
            } else break;
        }
        mFootViews.clear();
    }

    public void addHeaderView(View view, int... position) {
        if (view == null || isHasHeader(view)) return;
        ViewUtils.removeSelfFromParent(view);
        mHeaderViews.put(TYPE_HEADER + view.hashCode(), view);
        int pos = getHeaderCount() - 1;
        //如果需要插入
        if (position.length > 0 && position[0] < pos) pos = position[0];
        insert(new Header(TYPE_HEADER + view.hashCode()), pos);
    }

    public void addFooterView(View view) {
        if (view == null || isHasFooter(view)) return;
        ViewUtils.removeSelfFromParent(view);
        mFootViews.put(TYPE_FOOTER + view.hashCode(), view);
        insert(new Footer(TYPE_FOOTER + view.hashCode()), getItemCount());
    }


    public List getList() {
        if (list.isEmpty()) return list;
        return list.subList(getFirstPosition(), getLastPosition() + 1);
    }

    public void setSelected(int position) {
        if (isRadio) {
            int last = getVM(getItemViewType(position)).last_select_position;
            if (position == last && last != 0) return;
            update(last, foldName, false);
        }
        update(position, foldName, true);
    }

    public void setSelectedCancel(int position) {
        if (!isRadio) {
            update(position, foldName, false);
        }
    }

    //清除列表
    public void clear() {
        if (getRealCount() > 0) remove(getFirstPosition(), getLastPosition());
    }

    //清楚所有，包括头和尾
    public void clearAll() {
        mHeaderViews.clear();
        mFootViews.clear();
        if (getItemCount() > 0) remove(0, getItemCount() - 1);
    }


    //加载更多
    public void loadMore(List list) {
        insert(list, getLastPosition() + 1);
    }

    public void loadMore(Object bean) {
        insert(bean, getLastPosition() + 1);
    }

    public void update(int position, List lst) {
        if (lst.isEmpty()) return;
        rangeUpdate(lst, false, position);
    }

    public void update(int position, Object bean) {
        Object old = getListBean(position);
        Map<String, Object> map = JavaMethod.getDiffrentBean(bean, old);
        update(position, map);
    }

    public void update(int position, Map<String, Object> map) {
        if (map.size() > 0) {
            JavaMethod.transMap2Bean(getListBean(position), map);
            notifyItemChanged(position, map);
        }
    }

    public void update(int position, String fieldName, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(fieldName, value);
        update(position, map);
    }

    public void move(int fromPosition, int toPosition) {
        notifyItemMoved(fromPosition, toPosition);
        Object bean = getListBean(fromPosition);
        list.add(toPosition, bean);
        //后移
        if (fromPosition < toPosition) {
            list.remove(fromPosition);
        }
        //前移到之前面后，移除出时fromPosition+1
        else {
            list.remove(fromPosition + 1);
        }
    }

    protected void insert(@NonNull List lst, int position) {

        if (getItemCount() > 0) {
            list.addAll(position, lst);
            notifyItemRangeInserted(position, lst.size());
        } else {
            list.addAll(lst);
            notifyDataSetChanged();
        }
    }

    public void insert(Object bean, int position) {
        if (getItemCount() > 0) {
            list.add(position, bean);
            notifyItemInserted(position);
        } else {
            list.add(bean);
            notifyDataSetChanged();
        }
    }

    public void remove(int position) {
        if (position < 0) return;
        notifyItemRemoved(position);
        list.remove(position);
    }

    public void remove(int startPosition, int toPosition) {
        if (startPosition < 0) startPosition = 0;
        if (toPosition < 0) return;
        notifyItemRangeRemoved(startPosition, toPosition - startPosition + 1);
        list.subList(startPosition, toPosition + 1).clear();
    }


    /**
     * 局部刷新
     *
     * @param startPos 如果不传则为全部
     */
    public void rangeUpdate(@NonNull List newList, boolean detectMoves, int... startPos) {


        int startPosition;//开始的位置

        int endPosition = getLastPosition();//结束的位置

        if (startPos.length == 0) {
            startPosition = getFirstPosition();
        } else {
            if (startPos[0] < 0) startPosition = 0;
            else startPosition = startPos[0];
        }


        if (endPosition < startPosition) {
            loadMore(newList);
        } else {

            if (newList.isEmpty()) {

                remove(startPosition, endPosition);
                return;
            }

            List oldList = list.subList(startPosition, endPosition + 1);

            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(oldList, newList), detectMoves);
            diffResult.dispatchUpdatesTo(new ListUpdateCallback() {

                @Override
                public void onInserted(int position, int count) {
                    notifyItemRangeInserted(startPosition + position, count);
                }

                @Override
                public void onRemoved(int position, int count) {
                    notifyItemRangeRemoved(startPosition + position, count);
                }

                @Override
                public void onMoved(int fromPosition, int toPosition) {
                    notifyItemMoved(startPosition + fromPosition, startPosition + toPosition);
                }

                @Override
                public void onChanged(int position, int count, Object payload) {
                    notifyItemRangeChanged(startPosition + position, count, payload);
                }
            });
            oldList.clear();
            oldList.addAll(newList);
        }


    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (isHeaderType(viewType))
            return new BaseViewHolder(mHeaderViews.get(viewType), null, null);
        else if (isFooterType(viewType))
            return new BaseViewHolder(mFootViews.get(viewType), null, null);

        else return new BaseViewHolder(onCreateHolderView(parent, viewType), viewType, this);
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (isHeaderType(getItemViewType(position)) || isFooterType(getItemViewType(position)))
            return;
        //把头和尾过滤掉
        onBindViewHolderBind(holder, position);
    }


    public void onBindViewHolder(BaseViewHolder holder, int position, List payloads) {
        if (isHeaderType(getItemViewType(position)) || isFooterType(getItemViewType(position)))
            return;
        if (!payloads.isEmpty()) {
            Map payload = (Map) payloads.get(0);
            holder.setValues(payload, getListBean(position));
            onBindViewHolderCustomer(holder, position);
        } else super.onBindViewHolder(holder, position, payloads);
    }


    //过滤掉了头和尾
    public void onBindViewHolderBind(BaseViewHolder holder, int position) {
        if (isRadio && position == 0) {
            JavaMethod.setFieldValue(getListBean(position), foldName, true);
        }
        holder.setValues(getListBean(position));
        onBindViewHolderCustomer(holder, position);
    }

    /**
     * 主要是调用onBindViewHolderCustomer
     * 在onBindViewHolder两个方法中，局部刷新可能会出现的状态
     */
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {

    }


    /**
     * 如果 TYPE_CHILD 的VM
     *
     * @param viewType
     * @return
     */
    public BaseVM getVM(int viewType) {
        return baseVM_list.get(viewType, baseVM_list.get(TYPE_CHILD));
    }

    public void putBaseVM(int itemType, BaseVM vm) {
        baseVM_list.put(itemType, vm);
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getAdapterPosition();
        int type = getItemViewType(position);
        if (isFullSpanType(type)) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                lp.setFullSpan(true);
            }
        }
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        this.recyclerView = recyclerView;
        this.layoutManager = layoutManager;
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isFullSpanType(getItemViewType(position))) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }

        //侧滑菜单
        if (sideslip)
            recyclerView.addOnItemTouchListener(new ItemSlideHelper(recyclerView.getContext(), this));
    }

    public int findFirstVisibleItemPosition() {
        if (recyclerView == null) return -1;
        //获取缓存的childView总数
        int childCount = recyclerView.getChildCount();
        //逐一遍历
        for (int i = 0; i < childCount; i++) {
            View childView = recyclerView.getChildAt(i);
            if (isViewCanSeenAtFirstPosition(childView, recyclerView, isHorizontal(recyclerView))) {
                return recyclerView.getChildAdapterPosition(childView);
            }
        }
        return -1;
    }

    public int findLastVisibleItemPosition() {
        if (recyclerView == null) return -1;
        //获取缓存的childView总数
        int childCount = recyclerView.getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            View childView = recyclerView.getChildAt(i);
            if (isViewCanSeenAtLastPosition(childView, recyclerView, isHorizontal(recyclerView))) {
                return recyclerView.getChildAdapterPosition(childView);
            }
        }
        return -1;
    }

    /**
     * 是否横向布局,由layoutManager决定
     *
     * @param parent
     * @return
     */
    public boolean isHorizontal(RecyclerView parent) {
        boolean isHorizontal = false;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        //获取layoutManager的布局方向
        if (layoutManager instanceof LinearLayoutManager) {
            isHorizontal = ((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.HORIZONTAL;
        }
        return isHorizontal;
    }

    /**
     * 判断当前的childView是否可见且在第一项位置,用于更精确处理固定头部显示的数据
     *
     * @param childView    recycleView缓存的子view
     * @param parent
     * @param isHorizontal 是否横向布局
     * @return
     */
    public boolean isViewCanSeenAtFirstPosition(View childView, RecyclerView parent, boolean isHorizontal) {
        if (childView == null) {
            return false;
        }
        int edgeOfCanSeen = isHorizontal ? parent.getPaddingLeft() : parent.getPaddingTop();
        int edgeOfStart = isHorizontal ? childView.getLeft() : childView.getTop();
        int edgeOfEnd = isHorizontal ? childView.getRight() : childView.getBottom();

        return edgeOfStart <= edgeOfCanSeen && edgeOfEnd > edgeOfCanSeen;
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

    //强制停止RecyclerView滑动方法
    public void forceStopRecyclerViewScroll() {
        recyclerView.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 0, 0, 0));
    }


    public void setTopPosition(int position) {
        if (getLastPosition() == -1) return;
        movePosition = position;
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    public void setBottomVisible() {
        if (getLastPosition() == -1) return;
        movePosition = getLastPosition();
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        moveToPosition(movePosition);
    }

    /**
     * RecyclerView 移动到当前位置，
     *
     * @param position 要跳转的位置
     *                 只对LinearLayoutManager  有效
     */
    public void moveToPosition(int position) {
        if (getItemCount() == 0 || getLastPosition() < 0) return;

        if (position > getLastPosition()) position = getLastPosition();

        if (layoutManager instanceof LinearLayoutManager) {

            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int firstItem = linearLayoutManager.findFirstVisibleItemPosition();
            int lastItem = linearLayoutManager.findLastVisibleItemPosition();

            //当要置顶的项在当前显示的第一个项的前面时
            if (position < firstItem) {
                recyclerView.smoothScrollToPosition(position);
            }
            //当要置顶的项已经在屏幕上显示时
            else if (position < lastItem) {
                if (linearLayoutManager.getOrientation() == 1) {
                    int top = recyclerView.getChildAt(position - firstItem).getTop();
                    recyclerView.smoothScrollBy(0, top);
//                    recyclerView.scrollBy(0, top);
                } else {
                    int left = recyclerView.getChildAt(position - firstItem).getLeft();
                    recyclerView.smoothScrollBy(left, 0);
//                    recyclerView.scrollBy(left, 0);
                }

            } else {
                //当要置顶的项在当前显示的最后一项的后面时
                recyclerView.smoothScrollToPosition(position);
            }
        }
    }

    public int getPosition(String fieldName, Object value) {
        for (int i = 0; i < list.size(); i++) {
            Object obj = JavaMethod.getFieldValue(getListBean(i), fieldName);
            if (obj != null) {
                if (JavaMethod.equals(obj, value)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public BaseViewHolder getViewHolder(int position) {
        if (layoutManager == null) return null;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        View view = layoutManager.findViewByPosition(position);
        if (view == null) return null;
        return (BaseViewHolder) recyclerView.getChildViewHolder(view);
    }

    public int getPosition(View view) {
        return recyclerView.getChildAdapterPosition(view);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    protected boolean isFullSpanType(int type) {
        return isHeaderType(type) || isFooterType(type);
    }

    public int getHorizontalRange(RecyclerView.ViewHolder holder) {
        if (holder.itemView instanceof LinearLayout) {
            ViewGroup viewGroup = (ViewGroup) holder.itemView;
            if (viewGroup.getChildCount() == 2) {
                return viewGroup.getChildAt(1).getLayoutParams().width;
            }
        }
        return 0;
    }

    /**
     * @param childView 未显示的是获取不到的
     * @return
     */
    public RecyclerView.ViewHolder getChildViewHolder(View childView) {
        return recyclerView.getChildViewHolder(childView);
    }

    public View findTargetView(float x, float y) {
        return recyclerView.findChildViewUnder(x, y);
    }

    public <T extends View> T getFragmentView(ViewGroup parent, @LayoutRes int layoutResid) {
        return (T) LayoutInflater.from(parent.getContext()).inflate(layoutResid, parent, false);
    }

    public static class Header {
        public int type;

        public Header(int type) {
            this.type = type;
        }
    }

    public static class Footer {
        public int type;

        public Footer(int type) {
            this.type = type;
        }
    }

    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {

        return false;
    }
}


