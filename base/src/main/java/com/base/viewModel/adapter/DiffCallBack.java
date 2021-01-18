package com.base.viewModel.adapter;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.base.annotation.AnnotationUtils;
import com.base.utils.JavaMethod;

import java.util.List;
import java.util.Map;


/**
 * 用法
 * 在将newDatas 设置给Adapter之前，先调用DiffUtil.calculateDiff()方法，计算出新老数据集转化的最小更新集，就是DiffUtil.DiffResult对象。
 * 第一个参数是DiffUtil.Callback对象，第二个参数代表是否检测Item的移动，改为false算法效率更高，按需设置，这里是true。
 * DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(mDatas, newDatas), true);
 * <p/>
 * //利用DiffUtil.DiffResult对象的dispatchUpdatesTo（）方法，传入RecyclerView的Adapter，
 * <p/>
 * diffResult.dispatchUpdatesTo(mAdapter);
 * <p/>
 * //别忘了将新数据给Adapter
 * mDatas = newDatas;
 */
public class DiffCallBack extends DiffUtil.Callback {

    private List mOldDatas, mNewDatas;

    public DiffCallBack(List mOldDatas, List mNewDatas) {
        this.mOldDatas = mOldDatas;
        this.mNewDatas = mNewDatas;
    }

    @Override
    public int getOldListSize() {
        return mOldDatas == null ? 0 : mOldDatas.size();
    }

    @Override
    public int getNewListSize() {
        return mNewDatas == null ? 0 : mNewDatas.size();
    }

    /**
     * 被DiffUtil调用，用来判断 两个对象是否是相同的Item。
     * 例如，如果你的Item有唯一的id字段，这个方法就 判断id是否相等。
     *
     * @param oldItemPosition The position of the item in the old list
     * @param newItemPosition The position of the item in the new list
     * @return 这里比较fal所有带有id的字段，有一个不相同则返回false
     */
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Object oldbean = mOldDatas.get(oldItemPosition);
        Object newBean = mNewDatas.get(newItemPosition);
        if (oldbean == null || newBean == null) return false;
        if (oldbean == newBean) return true;
        if (oldbean.getClass() != newBean.getClass()) return false;

        List<String> list = AnnotationUtils.getOnlyFieldNames(oldbean);
        if (list.isEmpty()) {
            Map<String, Object> mapOld = JavaMethod.getContainsNamesValue(oldbean, "id");
            Map<String, Object> mapNew = JavaMethod.getContainsNamesValue(newBean, "id");
            return JavaMethod.getDiffrentMap(mapNew, mapOld).size() == 0;
        } else {
            for (String s : list) {
                if (!JavaMethod.equals(JavaMethod.getFieldValue(oldbean, s), JavaMethod.getFieldValue(newBean, s))) {
                    return false;
                }
            }
            return true;
        }
    }


    /**
     * .
     * 被DiffUtil调用，用来检查 两个item是否含有相同的数据
     * DiffUtil用返回的信息（true false）来检测当前item的内容是否发生了变化
     * DiffUtil 用这个方法替代equals方法去检查是否相等。
     * 所以你可以根据你的UI去改变它的返回值
     * 例如，如果你用RecyclerView.Adapter 配合DiffUtil使用，你需要返回Item的视觉表现是否相同。
     * 这个方法仅仅在areItemsTheSame()返回true时，才调用。
     *
     * @param oldItemPosition The position of the item in the old list
     * @param newItemPosition The position of the item in the new list which replaces the oldItem
     * @return True if the contents of the items are the same or false if they are different.
     */

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        if (mOldDatas.get(oldItemPosition) == null || mNewDatas.get(newItemPosition) == null)
            return true;
        return JavaMethod.isDiffrentBean(mNewDatas.get(newItemPosition), mOldDatas.get(oldItemPosition));
    }

    /**
     * 当{@link #areItemsTheSame(int, int)} 返回true，且{@link #areContentsTheSame(int, int)} 返回false时，DiffUtils会回调此方法，
     * 去得到这个Item（有哪些）改变的payload。
     * <p/>
     * 例如，如果你用RecyclerView配合DiffUtils，你可以返回  这个Item改变的那些字段，
     * {@link android.support.v7.widget.RecyclerView.ItemAnimator ItemAnimator} 可以用那些信息去执行正确的动画
     * <p/>
     * 默认的实现是返回null
     * 返回 一个 代表着新老item的改变内容的 payload对象，
     * 定向刷新中的部分更新效率最高只是没有了ItemChange的白光一闪动画，
     *
     * @param oldItemPosition The position of the item in the old list
     * @param newItemPosition The position of the item in the new list
     */

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return JavaMethod.getDiffrentBean(mNewDatas.get(newItemPosition), mOldDatas.get(oldItemPosition));
    }
}
