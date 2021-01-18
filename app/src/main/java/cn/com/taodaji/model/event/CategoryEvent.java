package cn.com.taodaji.model.event;

public class CategoryEvent {
    private int supplierSaleType;
    private String subCategoryJson;

    public int getSupplierSaleType() {
        return supplierSaleType;
    }

    public void setSupplierSaleType(int supplierSaleType) {
        this.supplierSaleType = supplierSaleType;
    }

    public String getSubCategoryJson() {
        return subCategoryJson;
    }

    public void setSubCategoryJson(String subCategoryJson) {
        this.subCategoryJson = subCategoryJson;
    }
}
