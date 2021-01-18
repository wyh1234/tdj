package com.base.viewModel.adapter;


import android.support.annotation.NonNull;

import com.base.utils.JavaMethod;

import java.util.ArrayList;
import java.util.List;

public abstract class GroupRecyclerAdapter<G> extends BaseRecyclerViewAdapter {

    public static final int TYPE_GROUP = 4;//分组

    protected boolean isFold = false;   //是否全部折叠，默认不折叠

    public abstract List getChildList(G gBean);

    public int getPageNO(int position, int ps) {
        if (ps == 0) ps = 1;
        int indexG = getGroupIndex(position);
        return indexG / ps + 1;
    }

    public int getPageStartPos(int ppn, int ps) {
        int indexG = (ppn - 1) * ps;

        int index = -1;
        for (int i = getHeaderCount(); i <= getLastPosition(); i++) {
            if (getItemViewType(i) == TYPE_GROUP) {
                index++;
                if (index == indexG) {
                    return getGroupFirstPosition(i) - 1;
                }
            }
        }

        return 0;
    }

    /**
     * @param listG 分组的数据源未拆分
     */
    public void setGroupList(List<G> listG) {
        List lst = initList(listG, true);
        if (lst == null || lst.size() == 0) {
            remove(getFirstPosition(), getLastPosition());
        } else {
            list.subList(getFirstPosition(), getLastPosition() + 1).clear();
            list.addAll(getFirstPosition(), lst);
            notifyDataSetChanged();
        }
    }

    /**
     * @param lst   是以分组的数据源
     * @param toTop
     */
    @Override
    public void setList(List lst, boolean... toTop) {
        setGroupList(lst);
    }

    public List<G> getGroupList() {
        List<G> lls = new ArrayList<>();
        for (Object o : getList()) {
            if (concludeItemViewType(o) == TYPE_GROUP) {
                lls.add((G) o);
            }
        }
        return lls;
    }

    /**
     * 加载更多
     * 如果，数据源是以子项为基返回，分组是移动端加上去的，
     * 故在加载更多时需要判断，添加进来的第一个分组是要新开一个分组，还是本身就是前面一个分组
     *
     * @param newList
     */
    @Override
    public void loadMore(List newList) {
        if (newList == null || newList.size() == 0) return;
        super.loadMore(initList(newList, isNeedFirstGroup(newList.get(0))));
    }

    /**
     * 分组的 局部刷新
     * 带有分组，局部刷新则以分组为单位刷新，因为传过来的数据为分组数据
     *
     * @param newList     转换好的数据源
     * @param detectMoves
     * @param startPos    如果不传则为全部
     */
    @Override
    public void rangeUpdate(@NonNull List newList, boolean detectMoves, int... startPos) {
        int start = 0;
        boolean isNeedFirst;
        if (startPos.length > 0) {
            start = getGroupFirstPosition(startPos[0]) - 1;
            if (newList.size() == 0) isNeedFirst = true;
            else isNeedFirst = isNeedFirstGroup(newList.get(0));
        } else {
            isNeedFirst = true;
        }
        if (start < 0) start = 0;

        super.rangeUpdate(initList(newList, isNeedFirst), detectMoves, start);
    }

    /**
     * 转化分组的数据源为真实数据源
     *
     * @param groupList
     * @param isNeedFirst =true表示是第一个分组，新的分组，表现为需要添加一个新的分组
     * @return 真实的数据源
     */
    public List initList(@NonNull List<G> groupList, boolean isNeedFirst) {
        List list1 = new ArrayList<>();
        if (groupList.isEmpty()) return list1;

        for (int i = 0; i < groupList.size(); i++) {
            G gbean = groupList.get(i);
            //如果不是第一组，或 isNeedFirst=true 则先添加分组，然后再添加子项
            if (i > 0 || isNeedFirst) list1.add(gbean);

            //需要折叠的不显示子项
            if (isFold) {
                JavaMethod.setFieldValue(gbean, foldName, true);
            } else {
                JavaMethod.setFieldValue(gbean, foldName, false);
                List childList = getChildList(gbean);
                if (childList != null) list1.addAll(childList);
            }
        }
        return list1;
    }

    /**
     * @param g1
     * @return true表示不和上一个分组合并，或者是第一个分组，新的分组
     */
    protected boolean isNeedFirstGroup(Object g1) {
        return !compareLastG(g1);
    }

    /**
     * 判断是否属于同一个分组，例如:在加载更多时需要判断，添加是否和上一组是同一组
     *
     * @param g1 对比最后一个分组是否和g1一个组
     * @return true 表示一样的
     */
    private boolean compareLastG(Object g1) {
        for (int i = getRealCount() - 1; i >= getHeaderCount(); i--) {
            if (getItemViewType(i) == TYPE_GROUP) {
                return compareTo(list.get(i), g1);
            }
        }
        return false;
    }


    /**
     * 比较两个bean中的 @OnlyField 标注的字段值是否一样
     *
     * @param g1
     * @param g2
     * @return
     */
    private boolean compareTo(Object g1, Object g2) {
        return JavaMethod.beanEquals_OnlyField(g1, g2);
    }


    /**
     * 获取该分组的bean
     *
     * @param position 不一定是分组的position
     * @return
     */
    public G getGroupBean(int position) {
        int posg = getGroupPosition(position);
        if (posg != -1) return (G) list.get(posg);
        else return null;
    }


    //position 是第几个分组
    public int getGroupIndex(int position) {
        int index = -1;
        for (int i = getHeaderCount(); i <= position; i++) {
            if (getItemViewType(i) == TYPE_GROUP) {
                index++;
            }
        }
        return index;
    }

    //属于哪一组的position
    public int getGroupPosition(int position) {
        for (int i = position; i >= 0; i--) {
            if (getItemViewType(i) == TYPE_GROUP) return i;
        }
        return -1;
    }

    /**
     * 返回当前分组的最后一个position，
     * 如果没有子项，position必为 TYPE_GROUP，返回的是position
     * 如果没有子项 {@link #getGroupFirstPosition(int)}必然大于 {@linkplain #getGroupLastPosition(int)}
     *
     * @param position
     * @return
     */
    public int getGroupLastPosition(int position) {
        for (int i = position + 1; i <= getLastPosition(); i++) {
            if (getItemViewType(i) == TYPE_GROUP) return i - 1;
        }
        return getLastPosition();
    }

    /**
     * 返回当前分组的第一个position,
     * 如果没有子项参数 position必为 TYPE_GROUP，返回的是position+1
     * <p>
     * 如果没有子项 {@link #getGroupFirstPosition(int)}必然大于 {@linkplain #getGroupLastPosition(int)}
     *
     * @param position
     * @return
     */
    public int getGroupFirstPosition(int position) {

        if (getItemViewType(position) == TYPE_GROUP) return position + 1;

        for (int i = position - 1; i >= getFirstPosition(); i--) {
            if (getItemViewType(i) == TYPE_GROUP) return i + 1;
        }
        return getFirstPosition();
    }

    //获取第index个分组的位置，如果index超出则返回最后一个position(不是footer)
    public int getPositionForGroupIndex(int index) {
        int count = -1;
        for (int i = 0; i < getItemCount(); i++) {
            if (getItemViewType(i) == TYPE_GROUP) {
                count++;
                if (count == index) return i;
            }
        }

        //index 超出分组，则返回最后一个位置
        if (count > -1) {
            return getLastPosition();
        }
        //不存在返回-1
        return -1;
    }

    /**
     * 设置默认是否折叠
     *
     * @param fold
     */
    public void setFold(boolean fold) {
        isFold = fold;
    }


    public void setFoldChanged(int position) {
        if (isFold(position)) setSpread(position);
        else setFold(position);
    }


    //折叠
    public void setFold(int position) {
        //设置分组选中
        update(position, foldName, true);
        int startPosition = getGroupFirstPosition(position);
        int lastPosition = getGroupLastPosition(position);

        if (lastPosition >= startPosition) {
            remove(startPosition, lastPosition);
        }
    }

    //展开
    public void setSpread(int position) {
        update(position, foldName, false);
        G gbean = getListBeans(position);
        List list = getChildList(gbean);
        if (list != null) {
            //如果是最后一个
            if (position == getLastPosition()) {
                insert(list, position + 1);
//                moveToPosition(getLastPosition());
            } else insert(list, position + 1);
        }

    }

    //是否折叠
    public boolean isFold(int position) {
        if (getItemViewType(position) == TYPE_GROUP) {
            return JavaMethod.getFieldValue(getListBean(position), foldName, boolean.class);
        }
        return false;
    }


    @Override
    protected boolean isFullSpanType(int type) {
        return type == TYPE_GROUP || super.isFullSpanType(type);
    }

}
