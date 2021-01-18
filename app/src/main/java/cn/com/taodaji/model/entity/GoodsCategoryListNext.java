package cn.com.taodaji.model.entity;

import com.base.annotation.OnlyField;

import java.io.Serializable;
import java.util.List;

public class GoodsCategoryListNext implements Serializable{

    /**
     * entityId : 351
     * createTime : 2016-09-26 11:55
     * name : 白豆干
     * unit : 斤
     * options : [2,4,6,8,10,20,30,40,50,60,80,100]
     * sortIndex : 213
     * categoryId : 19
     * parentCategoryId : 10
     * authorId : 0
     * authorType : 0
     * parentCategoryName : null
     * description : null
     */
    @OnlyField
    private int entityId;
    private String createTime;
    private String name;
    private String unit;
    private int sortIndex;
    private int categoryId;
    private int parentCategoryId;
    private int authorId;
    private int authorType;
    private Object parentCategoryName;
    private Object description;
    private List<Integer> options;

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(int parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getAuthorType() {
        return authorType;
    }

    public void setAuthorType(int authorType) {
        this.authorType = authorType;
    }

    public Object getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(Object parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public List<Integer> getOptions() {
        return options;
    }

    public void setOptions(List<Integer> options) {
        this.options = options;
    }
}

