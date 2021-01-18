package cn.com.taodaji.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import cn.com.taodaji.viewModel.adapter.ChainShopAdapter;

/**
 * Created by zhaowenlong on 2019/3/5.
 */
public class ShopEmployeeItem implements MultiItemEntity, Parcelable {

    public int id;
    public int pid;
    public String name;
    public int position;
    public String phone;
    public boolean isCreator;
    public boolean isLeader;
    public String enterpriseCode;
    public String markCode;
    public int role;

    public ShopEmployeeItem(String name, int position, String phone, boolean isCreator) {
        this.name = name;
        this.position = position;
        this.phone = phone;
        this.isCreator = isCreator;
    }

    public ShopEmployeeItem() {
    }

    @Override
    public int getItemType() {
        return ChainShopAdapter.TYPE_PERSON;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isCreator() {
        return isCreator;
    }

    public void setCreator(boolean creator) {
        isCreator = creator;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setLeader(boolean leader) {
        isLeader = leader;
    }

    public String getEnterpriseCode() {
        return enterpriseCode;
    }

    public void setEnterpriseCode(String enterpriseCode) {
        this.enterpriseCode = enterpriseCode;
    }

    public String getMarkCode() {
        return markCode;
    }

    public void setMarkCode(String markCode) {
        this.markCode = markCode;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(pid);
        dest.writeString(name);
        dest.writeInt(position);
        dest.writeString(phone);
        dest.writeByte((byte) (isCreator ? 1 : 0));
        dest.writeByte((byte) (isLeader ? 1 : 0));
        dest.writeString(enterpriseCode);
        dest.writeString(markCode);
        dest.writeInt(role);
    }

    public static final Parcelable.Creator<ShopEmployeeItem> CREATOR = new Creator<ShopEmployeeItem>() {

        @Override
        public ShopEmployeeItem createFromParcel(Parcel source) {
            return new ShopEmployeeItem(source);
        }

        @Override
        public ShopEmployeeItem[] newArray(int size) {
            return new ShopEmployeeItem[size];
        }
    };

    public ShopEmployeeItem(Parcel in){
        //如果元素数据是list类型的时候需要： lits = new ArrayList<?> in.readList(list);
        //否则会出现空指针异常.并且读出和写入的数据类型必须相同.如果不想对部分关键字进行序列化,可以使用transient关键字来修饰以及static修饰.
        id = in.readInt();
        pid = in.readInt();
        name = in.readString();
        position = in.readInt();
        phone = in.readString();
        isCreator = in.readByte()!= 0;
        isLeader = in.readByte()!=0;
        enterpriseCode = in.readString();
        markCode = in.readString();
        role = in.readInt();
    }
}
