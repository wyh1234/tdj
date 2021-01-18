package com.base.viewModel.adapter;


import android.support.annotation.NonNull;

import com.base.utils.JavaMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangkuo on 2018-09-21.
 * 需要折分的分组
 * 主要用于分组有头和尾，子项在分组中间的情况
 */
public abstract class GroupSplitRecyclerAdapter<G, SPG> extends GroupRecyclerAdapter<G> {

    public static final int TYPE_GROUP_SPLIT = 5;//折分出来的分组，或者是辅助分组

    public static final int SPLITGROUPS_ORIENTATION_TOP = 0;// 折分出来的分组 位于主分组上面
    public static final int SPLITGROUP_ORIENTATION_BOTTOM = 1; //折分出来的分组 位于主分组下面

    protected int childDefaultCount = 0;//折叠时，默认显示子项的数量

    /**
     * 只在初始化数据时调用
     *
     * @param gBean
     * @return 折分出来的分组的数据
     */
    public abstract SPG getSplitGroupBean(G gBean);

    /**
     * @return {@linkplain #SPLITGROUPS_ORIENTATION_TOP 折分出来的分组 位于主分组上面}
     * {@linkplain #SPLITGROUP_ORIENTATION_BOTTOM}  折分出来的分组 位于主分组下面
     */
    public abstract int getSplitGroupOrientation();


    /**
     * 设置 折叠时 默认显示子项的数量
     */
    public void setChildDefaultCount(int childDefaultCount) {
        this.childDefaultCount = childDefaultCount;
    }


    /**
     * 重写以便对数据源进行折分
     *
     * @param groupList
     * @param isNeedFirst 这个参数无用
     * @return
     */
    @Override
    public List initList(@NonNull List<G> groupList, boolean isNeedFirst) {
        List list1 = new ArrayList<>();
        if (groupList.isEmpty()) return list1;

        for (int i = 0; i < groupList.size(); i++) {
            G gbean = groupList.get(i);

            //如果需要的折分项位于分组的上方，则先添加折分项
            if (getSplitGroupOrientation() == SPLITGROUPS_ORIENTATION_TOP)
                list1.add(getSplitGroupBean(gbean));
                //如果需要的折分项位于分组的下方,则先添加分组
            else if (getSplitGroupOrientation() == SPLITGROUP_ORIENTATION_BOTTOM) list1.add(gbean);


            //添加子项
            List childList = getChildList(gbean);
            if (childList != null) {
                if (isFold) {
                    JavaMethod.setFieldValue(gbean, foldName, true);
                    if (childDefaultCount > 0) {
                        //如果childList 的长度大于 childDefaultCount，则截取显示，否则全部显示
                        if (childList.size() > childDefaultCount) {
                            List ll = childList.subList(0, childDefaultCount);
                            list1.addAll(ll);
                        } else list1.addAll(childList);
                    }
                } else {
                    JavaMethod.setFieldValue(gbean, foldName, false);
                    list1.addAll(childList);
                }
            }
            //如果需要的折分项位于分组的上方，则需要添加分组
            if (getSplitGroupOrientation() == SPLITGROUPS_ORIENTATION_TOP) list1.add(gbean);
                //如果需要的折分项位于分组的下方,则需要添加折分项
            else if (getSplitGroupOrientation() == SPLITGROUP_ORIENTATION_BOTTOM)
                list1.add(getSplitGroupBean(gbean));
        }
        return list1;
    }


    /**
     * 不再需要判断，直接返回true
     *
     * @param g1
     * @return
     */
    @Override
    protected boolean isNeedFirstGroup(Object g1) {
        return true;
    }

    //position 是第几个分组
    @Override
    public int getGroupIndex(int position) {
        int index = super.getGroupIndex(position);
        //如果需要的折分项位于分组的上方，则需要+1
        if (getSplitGroupOrientation() == SPLITGROUPS_ORIENTATION_TOP) index++;
        return index;
    }


    /**
     * 移除一个分组
     *
     * @param position 当前的位置
     */
    public void removeGroup(int position) {
        int start = getGroupFirstPosition(position) - 1;
        int end = getGroupLastPosition(position) + 1;
        if (end >= start) remove(start, end);
        else remove(end, start);
    }

    //属于哪一组的position
    @Override
    public int getGroupPosition(int position) {
        //如果需要的折分项位于分组的上方，则需要向后寻找
        if (getSplitGroupOrientation() == SPLITGROUPS_ORIENTATION_TOP) {
            for (int i = position; i <= getLastPosition(); i++) {
                if (getItemViewType(i) == TYPE_GROUP) return i;
            }
        }
        //如果需要的折分项位于分组的下方，则需要向前寻找
        else if (getSplitGroupOrientation() == SPLITGROUP_ORIENTATION_BOTTOM) {
            for (int i = position; i >= 0; i--) {
                if (getItemViewType(i) == TYPE_GROUP) return i;
            }
        }
        //找不到返回-1
        return -1;
    }

    /**
     * 返回当前分组的最后一个子项的position,
     * 如果没有子项，返回的是当前分组的开始位置
     * 故如果没有子项{@link #getGroupFirstPosition(int)}必然大于 {@linkplain #getGroupLastPosition(int)}
     *
     * @param position
     * @return
     */
    @Override
    public int getGroupLastPosition(int position) {
        //如果需要的折分项位于分组的上方
        if (getSplitGroupOrientation() == SPLITGROUPS_ORIENTATION_TOP) {
            for (int i = position; i <= getLastPosition(); i++) {
                if (getItemViewType(i) == TYPE_GROUP) return i - 1;
            }
        }
        //如果需要的折分项位于分组的下方
        else if (getSplitGroupOrientation() == SPLITGROUP_ORIENTATION_BOTTOM) {
            for (int i = position; i <= getLastPosition(); i++) {
                if (getItemViewType(i) == TYPE_GROUP_SPLIT) return i - 1;
            }
        }
        //找不到返回-1
        return -1;
    }

    /**
     * 返回当前分组的第一个子项的位置
     * 如果没有子项，返回的是当前分组的结束位置  开始位置为   @return -1
     * <p>
     * 故如果没有子项{@link #getGroupFirstPosition(int)}必然大于 {@linkplain #getGroupLastPosition(int)}
     *
     * @param position
     * @return
     */
    //
    public int getGroupFirstPosition(int position) {
        //如果需要的折分项位于分组的上方
        if (getSplitGroupOrientation() == SPLITGROUPS_ORIENTATION_TOP) {
            for (int i = position; i >= 0; i--) {
                if (getItemViewType(i) == TYPE_GROUP_SPLIT) return i + 1;
            }
        }
        //如果需要的折分项位于分组的下方
        else if (getSplitGroupOrientation() == SPLITGROUP_ORIENTATION_BOTTOM) {
            for (int i = position; i >= 0; i--) {
                if (getItemViewType(i) == TYPE_GROUP) return i + 1;
            }
        }
        //找不到返回-1
        return -1;
    }


    @Override
    public void setFold(int position) {
        //设置分组选中
        update(position, foldName, true);

        int startPosition = getGroupFirstPosition(position);
        int lastPosition = getGroupLastPosition(position);

        if (lastPosition >= startPosition) {
            if (childDefaultCount > 0) {
                if (lastPosition - startPosition + 1 > childDefaultCount) {
                    remove(startPosition + childDefaultCount, lastPosition);
                }
            }
        }
    }

    @Override
    public void setSpread(int position) {
        update(position, foldName, false);

        int groupPosition = getGroupPosition(position);

        if (groupPosition == -1) return;

        G gbean = getListBeans(groupPosition);

        List list = getChildList(gbean);

        if (list != null) {

            int startPosition = getGroupFirstPosition(position);

            int lastPosition = getGroupLastPosition(position);

            int pos;//插入的位置
            List showList = new ArrayList(); //需要插入的数据

            //如果没有子项目，插入的位置是该分组结束的位置ps:  在没有子项目时getGroupFirstPosition(position);就是结束位置
            if (startPosition > lastPosition) {
                pos = startPosition;
                showList.addAll(list);
            } else {
                pos = lastPosition + 1;
                //当前分组中真实存在的数量
                int real_count = lastPosition - startPosition + 1;
                if (list.size() > real_count) {
                    showList.addAll(list.subList(real_count, list.size()));
                }
            }

            if (showList.size() > 0) {
                //如果是最后一个
                if (position == getLastPosition()) {
                    insert(showList, pos);
                    moveToPosition(getLastPosition());
                } else insert(showList, pos);
            }

        }
    }


    @Override
    protected boolean isFullSpanType(int type) {
        return type == TYPE_GROUP_SPLIT || super.isFullSpanType(type);
    }


}
