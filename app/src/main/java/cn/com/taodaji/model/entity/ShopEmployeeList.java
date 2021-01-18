package cn.com.taodaji.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

import cn.com.taodaji.viewModel.adapter.ChainShopAdapter;

/**
 * Created by zhaowenlong on 2019/3/5.
 */
public class ShopEmployeeList extends AbstractExpandableItem<ShopEmployeeItem> implements MultiItemEntity, Parcelable {
    public String title;
    public int employeesNum;
    public boolean isChain;
    public boolean isDetail;
    public String markCode;
    public String ownStores;
    public int id;
    public int pid;

    public ShopEmployeeList(String title, int employeesNum, boolean isChain) {
        this.title = title;
        this.employeesNum = employeesNum;
        this.isChain = isChain;
    }

    public ShopEmployeeList() { }



    @Override
    public int getItemType() {
        return ChainShopAdapter.TYPE_LEVEL_1;
    }

    @Override
    public int getLevel() {
        return 1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEmployeesNum() {
        return employeesNum;
    }

    public void setEmployeesNum(int employeesNum) {
        this.employeesNum = employeesNum;
    }

    public boolean isChain() {
        return isChain;
    }

    public void setChain(boolean chain) {
        isChain = chain;
    }

    public boolean isDetail() {
        return isDetail;
    }

    public void setDetail(boolean detail) {
        isDetail = detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarkCode() {
        return markCode;
    }

    public void setMarkCode(String markCode) {
        this.markCode = markCode;
    }

    public String getOwnStores() {
        return ownStores;
    }

    public void setOwnStores(String ownStores) {
        this.ownStores = ownStores;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(employeesNum);
        dest.writeByte((byte) (isChain ? 1 : 0));
        dest.writeByte((byte) (isDetail ? 1 : 0));
        dest.writeString(markCode);
        dest.writeString(ownStores);
        dest.writeInt(id);
        dest.writeInt(pid);
    }

    public static final Parcelable.Creator<ShopEmployeeList> CREATOR = new Creator<ShopEmployeeList>() {

        @Override
        public ShopEmployeeList createFromParcel(Parcel source) {
            return new ShopEmployeeList(source);
        }

        @Override
        public ShopEmployeeList[] newArray(int size) {
            return new ShopEmployeeList[size];
        }
    };

    public ShopEmployeeList(Parcel in){
        //如果元素数据是list类型的时候需要： lits = new ArrayList<?> in.readList(list);
        //否则会出现空指针异常.并且读出和写入的数据类型必须相同.如果不想对部分关键字进行序列化,可以使用transient关键字来修饰以及static修饰.
        title = in.readString();
        employeesNum = in.readInt();
        isChain = in.readByte()!= 0;
        isDetail = in.readByte()!= 0;
        markCode = in.readString();
        ownStores = in.readString();
        id = in.readInt();
        pid = in.readInt();
    }
}
