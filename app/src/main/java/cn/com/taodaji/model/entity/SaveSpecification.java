package cn.com.taodaji.model.entity;

/**
 * Created by Administrator on 2018/3/4.
 */

public class SaveSpecification {

    /**
     * categoryId : 20
     * commodityId : 101
     * description : 我的测试
     * gallery : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1706251324115baab1a6.jpg
     * name : 大葱
     * nickName : WZRY
     * store : 11
     * typeId : 94
     * specs : [{'level1Unit':'斤','price':11,'stock':99999,'avgPrice':11,'avgUnit':'斤','levelType':1},{'level1Unit':'袋','price':50,'level2Unit':'斤','level2Value':5,'stock':99999,'avgPrice':10,'avgUnit':'斤','levelType':2},{'level1Unit':'箱','price':800,'level2Unit':'袋','level2Value':10,'level3Unit':'斤','level3Value':10,'stock':99999,'avgPrice':8,'avgUnit':'斤','levelType':3}]
     */

    private int categoryId;
    private int commodityId;
    private String description;
    private String gallery;
    private String name;
    private String nickName;
    private int store;
    private int typeId;
    private String specs;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGallery() {
        return gallery;
    }

    public void setGallery(String gallery) {
        this.gallery = gallery;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getStore() {
        return store;
    }

    public void setStore(int store) {
        this.store = store;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }
}
