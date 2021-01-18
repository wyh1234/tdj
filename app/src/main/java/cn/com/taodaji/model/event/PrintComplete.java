package cn.com.taodaji.model.event;

/**
 * 打印完成
 */
public class PrintComplete {
    String itemIds;

    public PrintComplete(String itemIds) {
        this.itemIds = itemIds;
    }

    public String getItemIds() {
        return itemIds;
    }

    public void setItemIds(String itemIds) {
        this.itemIds = itemIds;
    }
}
