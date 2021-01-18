package cn.com.taodaji.model.event;

/**
 * Created by Administrator on 2017-11-08.
 */

public class ADInfoEvent {
    private int goodsCount;

    public ADInfoEvent(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }
}
