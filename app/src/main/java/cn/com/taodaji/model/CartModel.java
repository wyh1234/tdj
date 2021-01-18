package cn.com.taodaji.model;


import android.support.v4.util.SparseArrayCompat;
import android.util.Log;

import com.orm.SugarRecord;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.event.CartEvent;
import cn.com.taodaji.model.event.Login_in;
import cn.com.taodaji.model.event.Login_out;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;

public class CartModel {

    private static CartModel cartModel = null;
    private static List<CartGoodsBean> goods_cart = new ArrayList<>();
    private int entityId;


    //        productId   index    value
    private SparseArrayCompat<CartGoodsBean> goods_select = new SparseArrayCompat<>();

    private CartModel() {
        initData();
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }


    //是否可以添加检查，当前的site和登录后的site_login如果一样才可以添加
    public static boolean addChecked() {
        return PublicCache.site == PublicCache.site_login;
    }


    public void initData() {

        if (PublicCache.loginPurchase != null) {
            entityId = PublicCache.loginPurchase.getEntityId();
        } else if (PublicCache.loginSupplier != null) {
            entityId = PublicCache.loginSupplier.getEntityId();
        } else entityId = -1;

        goods_cart.clear();
        goods_select.clear();
//        List<CartGoodsBean> ss = SugarRecord.listAll(CartGoodsBean.class, "status,store_status");
        List<CartGoodsBean> goods = SugarRecord.find(CartGoodsBean.class, "entity_id =?", new String[]{String.valueOf(entityId)}, null, "status,store_status", null);

        for (CartGoodsBean bean : goods) {
            goods_select.put(bean.getSpecId(), bean);
        }
        goods_cart.addAll(goods);

        //如果数量不相同说明goods_cart中存在重复的数据,以goods_select里面的数据为准
        if (goods_cart.size() != goods_select.size()) {
            //清楚掉多的数据
            for (int i = goods_cart.size() - 1; i >= 0; i--) {
                int index = goods_select.indexOfValue(goods_cart.get(i));
                if (index == -1) {
                    goods_cart.get(i).delete();
                    goods_cart.remove(i);
                }
            }
        }
    }


    @Override
    protected void finalize() throws Throwable {
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
        super.finalize();
    }

    public static synchronized CartModel getInstance() {
        if (cartModel == null) {
            cartModel = new CartModel();
        }
        return cartModel;
    }

    public List<CartGoodsBean> getCartList() {
        return goods_cart;
    }

    //接收商品数量变化事件
    @Subscribe(priority = 999)
    public void onMessageEvent(CartEvent event) {
        CartGoodsBean cgb = event.getData();

        Log.d("CartEvent", cgb.getProductName() + cgb.getProductId()+cgb.getStoreId());
        if (cgb.getProductQty() == 0) remove(cgb);
        else {
            //如果是添加商品
            if (goods_select.indexOfKey(cgb.getSpecId()) < 0) {
                goods_cart.add(cgb);
                goods_select.put(cgb.getSpecId(), cgb);
                cgb.setSelected(true);
                save(cgb);
            } else {
                cgb.setSelected(true);
                cgb.update();
            }

        }

    }

    private void save(CartGoodsBean cgb) {
        cgb.setEntityId(entityId);
        //删除数据库存在多余数据
        CartGoodsBean.deleteAll(CartGoodsBean.class, "spec_id =?", String.valueOf(cgb.getSpecId()));
        cgb.save();
    }


    @Subscribe
    public void onMessageEvent(Login_in event) {
        initData();
    }

    @Subscribe
    public void onMessageEvent(Login_out event) {
        initData();
    }

    public CartGoodsBean getDataBean(int specId) {
        return goods_select.get(specId, null);
    }

    public int getPosition(int specId) {
        for (int i = 0; i < goods_cart.size(); i++) {
            if (goods_cart.get(i).getSpecId() == specId) {
                return i;
            }
        }
        return -1;
    }

    public void remove(final CartGoodsBean cartGoodsBean) {
        goods_cart.remove(cartGoodsBean);
        goods_select.remove(cartGoodsBean.getSpecId());
        cartGoodsBean.delete();
    }

    public int getCount() {
        int count = 0;
        for (CartGoodsBean bean : goods_cart) {
            if (bean.getStatus() == 1 && bean.getStoreStatus() == 0) count += 1;
        }
        return count;
    }

    public int getSelectCount() {
        int count = 0;
        int size = goods_cart.size();
        for (int i = 0; i < size; i++) {
            if (goods_cart.get(i).getSelected() && goods_cart.get(i).getStatus() == 1 && goods_cart.get(i).getStoreStatus() == 0) {
                count += 1;
            }
        }
        return count;
    }


    public int getCount(int specId) {
        CartGoodsBean cgb = getDataBean(specId);
        return cgb != null ? cgb.getProductQty() : 0;
    }

    public BigDecimal getPriceSum() {
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < goods_cart.size(); i++) {
            sum = sum.add(getPrices(i));
        }
        return sum.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getSelectPriceSum() {
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < goods_cart.size(); i++) {
            if (goods_cart.get(i).getSelected()) {
                sum = sum.add(getPrices(i));
            }
        }
        return sum.setScale(2, BigDecimal.ROUND_HALF_UP);
    }


    public BigDecimal getPrice(int specId) {
        if (getDataBean(specId) == null) return BigDecimal.ZERO;
        CartGoodsBean cgb = getDataBean(specId);
        if (cgb.getProductPrice() == null) return BigDecimal.ZERO;
        return cgb.getProductPrice().multiply(BigDecimal.valueOf(cgb.getProductQty())).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal getPrices(int index) {
        if (index == -1 || goods_cart.get(index).getStatus() != 1 || goods_cart.get(index).getStoreStatus() != 0)
            return BigDecimal.ZERO;
        return goods_cart.get(index).getProductPrice().multiply(BigDecimal.valueOf(goods_cart.get(index).getProductQty())).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

}
