package tools.shopping_anim;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.base.utils.ClickCheckedUtil;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;

import cn.com.taodaji.R;
import cn.com.taodaji.model.CartModel;
import cn.com.taodaji.model.LoginMethod;
import cn.com.taodaji.model.entity.GoodsInformation;
import cn.com.taodaji.model.entity.GoodsSpecification;
import cn.com.taodaji.model.event.CartEvent;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;
import cn.com.taodaji.ui.ppw.InputKeysetPopupWindow;

/**
 * Created by yangkuo on 2018/12/10.
 */
public class ShoppingButtonNew extends ShoppingButton {

    private AlertDialog alertDialog;

    public ShoppingButtonNew(Context context) {
        super(context);
    }

    public ShoppingButtonNew(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ShoppingButtonNew(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private CartGoodsBean cartGoodsBean;
    private GoodsInformation data;

    private boolean isShowDeleteAlertDialog = false;//是否显示删除对话框

    public void setShowDeleteAlertDialog(boolean showDeleteAlertDialog) {
        isShowDeleteAlertDialog = showDeleteAlertDialog;
    }

    public void setCartGoodsBean(CartGoodsBean cartGoodsBean) {
        this.cartGoodsBean = cartGoodsBean;
    }

    public void setCartGoodsBean(GoodsInformation data) {
        this.data = data;
    }

    private void initData() {
        if (data == null || data.getSpecs() == null) return;
        if (cartGoodsBean != null && cartGoodsBean.getProductId() == data.getEntityId()) return;

        GoodsSpecification gsbean = data.getSpecs().get(0);
        CartGoodsBean goodsBean = CartModel.getInstance().getDataBean(gsbean.getSpecId());
        if (goodsBean == null) {
            goodsBean = new CartGoodsBean();
            goodsBean.setSpecId(gsbean.getSpecId());
            goodsBean.setProductId(data.getEntityId());
            goodsBean.setProductQty(data.getProductQty());
        }
        goodsBean.setStoreId(data.getStore());
        goodsBean.setPackageName(data.getPackageName());
        goodsBean.setForegift(data.getForegift());

        goodsBean.setProductImage(data.getImage());
        goodsBean.setProductName(data.getName());
        goodsBean.setNickName(data.getNickName());
        goodsBean.setProductPrice(gsbean.getPrice());
        goodsBean.setProductUnit(gsbean.getLevel1Unit());

        goodsBean.setLevel2Value(gsbean.getLevel2Value());
        goodsBean.setLevel2Unit(gsbean.getLevel2Unit());
        goodsBean.setLevel3Value(gsbean.getLevel3Value());
        goodsBean.setLevel3Unit(gsbean.getLevel3Unit());
        goodsBean.setLevelType(gsbean.getLevelType());
        goodsBean.setAvgUnit(gsbean.getAvgUnit());
        goodsBean.setStock(gsbean.getStock());
        goodsBean.setSelected(true);

        int count_xg;
        if (data.getProductType() == 0) count_xg = data.getStock();
        else count_xg = data.getAllowPurchase() - data.getAlreadyPurchase();

        goodsBean.setStatus(data.getStatus());
        goodsBean.setCountXg(count_xg);
        goodsBean.setTypeXg(data.getProductType());
        goodsBean.setProductType(data.getProductType());

        goodsBean.setIsP(data.getIsP());
        goodsBean.setCategoryId(data.getCategoryId());
        goodsBean.setCommodityId(data.getCommodityId());
        this.cartGoodsBean = goodsBean;
    }


    @Override
    public boolean countChanged(boolean isAdd) {
        if (LoginMethod.notLoginChecked()) {
            if (ClickCheckedUtil.onClickChecked(2000)) UIUtils.showToastSafesShort("登录后才能购买商品哟~");
            return false;
        }
        if (!CartModel.addChecked()) {
            if (ClickCheckedUtil.onClickChecked(2000))
                UIUtils.showToastSafesShort("与收货地址不在同一城市，无法添加!");
            return false;
        }
        initData();
        if (cartGoodsBean != null) {

            if (data != null) cartGoodsBean.setProductQty(data.getProductQty());


            if (isAdd) {

                int count = cartGoodsBean.getProductQty();

                if (cartGoodsBean.getStock() - count <= 0) {
                    if (ClickCheckedUtil.onClickChecked(2000))
                        UIUtils.showToastSafesShort("店家货物售罄,欢迎下次光临");
                    return false;
                }

                if ((cartGoodsBean.getTypeXg() == 1||cartGoodsBean.getTypeXg() == 4 )&& cartGoodsBean.getCountXg() - count <= 0) {
                    if (ClickCheckedUtil.onClickChecked(2000))
                        UIUtils.showToastSafesShort("限购数量已达到");//+goodsBean.getCountXg()
                    return false;
                }

                cartGoodsBean.setProductQty(count + 1);

                //发送商品数量变化事件，商品详情，去挑菜等接收更改数量，CartModel接收缓存到本地
                CartGoodsBean bean = CartModel.getInstance().getDataBean(cartGoodsBean.getSpecId());
                if (bean != null) {
                    bean.setProductQty(cartGoodsBean.getProductQty());
                    EventBus.getDefault().post(new CartEvent(bean));
                } else {
                    EventBus.getDefault().post(new CartEvent(cartGoodsBean));
                }


                return true;
            } else {

                if (cartGoodsBean.getProductQty() == 1 && isShowDeleteAlertDialog) {
                    if (alertDialog == null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        View view = ViewUtils.getLayoutView(getContext(), R.layout.popup_window_simple_intput);
                        ViewUtils.findViewById(view, R.id.et_input).setVisibility(View.GONE);
                        TextView title = ViewUtils.findViewById(view, R.id.title);
                        title.setText("确认从购物车删除该商品？");
                        title.setPadding(0, 80, 0, 80);
                        builder.setView(view);
                        alertDialog = builder.create();
                        view.findViewById(R.id.no).setOnClickListener(v -> alertDialog.dismiss());
                        view.findViewById(R.id.yes).setOnClickListener(v -> {

                            //发送商品数量变化事件，商品详情，去挑菜等接收更改数量，CartModel接收缓存到本
                            CartGoodsBean bean = CartModel.getInstance().getDataBean(cartGoodsBean.getSpecId());
                            if (bean != null) {
                                bean.setProductQty(0);
                                EventBus.getDefault().post(new CartEvent(bean));
                            } else {
                                cartGoodsBean.setProductQty(0);
                                EventBus.getDefault().post(new CartEvent(cartGoodsBean));
                            }

                            if (alertDialog != null && alertDialog.isShowing())
                                alertDialog.dismiss();
                        });
                    }
                    if (alertDialog != null && !alertDialog.isShowing()) alertDialog.show();
                } else {
                    cartGoodsBean.setProductQty(cartGoodsBean.getProductQty() - 1);
                    if (cartGoodsBean.getProductQty() < 0) cartGoodsBean.setProductQty(0);
                    CartGoodsBean bean = CartModel.getInstance().getDataBean(cartGoodsBean.getSpecId());

                    if (bean != null) {
                        bean.setProductQty(cartGoodsBean.getProductQty());
                        EventBus.getDefault().post(new CartEvent(bean));
                    } else EventBus.getDefault().post(new CartEvent(cartGoodsBean));


                    return true;
                }

            }
        }

        return false;
    }

    //中间数字的点击事件
    @Override
    public void onClick(View view) {
        initData();
        if (cartGoodsBean != null) {

            if (data != null) cartGoodsBean.setProductQty(data.getProductQty());

            //发送商品数量变化事件，商品详情，去挑菜等接收更改数量，CartModel接收缓存到本地
            CartGoodsBean bean = CartModel.getInstance().getDataBean(cartGoodsBean.getSpecId());
            if (bean == null) bean = cartGoodsBean;

            InputKeysetPopupWindow inputKeysetPopupWindow = new InputKeysetPopupWindow(view.getContext(), new CartEvent(bean));
            inputKeysetPopupWindow.showAtLocation(view.getRootView(), Gravity.BOTTOM, 0, 0);
        }
    }
}
