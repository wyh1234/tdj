package cn.com.taodaji.model.entity;

public class ClassifyPopuWindowInfo {
    private String categoryName;
    private String categoryId;
    private int type;

    public ClassifyPopuWindowInfo(String categoryName, String categoryId, int type) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.type = type;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
