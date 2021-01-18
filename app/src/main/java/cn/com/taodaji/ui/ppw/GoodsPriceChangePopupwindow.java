package cn.com.taodaji.ui.ppw;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.GoodsUpdate;
import com.base.retrofit.RequestCallback;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.viewModel.vm.goods.GoodsShopListVM;
import cn.com.taodaji.model.entity.GoodsInformation;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.base.viewModel.BaseViewHolder;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public abstract class GoodsPriceChangePopupwindow extends BasePopupWindow implements View.OnClickListener, PopupResultInterface {
    private GoodsInformation beam;
    private BaseViewHolder viewHolder;

    public GoodsPriceChangePopupwindow(View mainView, GoodsInformation bean) {
        super(mainView);
        this.beam = bean;
        viewHolder.setValues(bean);
    }

    private View view;
    private Map<String, Object> mapd;
    private EditText goods_price, stock_Edit;

    @Override
    public View createContextView(View mainView) {
        view = ViewUtils.getLayoutView(mainView.getContext(), R.layout.popup_window_goods_price_change);
        goods_price = ViewUtils.findViewById(view, R.id.goods_price);
        stock_Edit = ViewUtils.findViewById(view, R.id.stock);

        viewHolder = new BaseViewHolder(view, new GoodsShopListVM(), null);
        Button popupWindow_close = (Button) view.findViewById(R.id.popupWindow_close);
        view.findViewById(R.id.goods_price_change_OK).setOnClickListener(this);
        popupWindow_close.setOnClickListener(this);
        return view;
    }

    @Override
    public boolean isTransparent() {
        return false;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.popupWindow_close:
                dismiss();
                break;
            case R.id.goods_price_change_OK:

                String price = goods_price.getText().toString();
                String stock = stock_Edit.getText().toString();
                if (price.length() == 0 || stock.length() == 0) {
                    UIUtils.showToastSafesShort("价格或库存不能为空");
                    return;
                }
                if (new BigDecimal(price).compareTo(BigDecimal.ZERO) <= 0 || BigDecimal.ZERO.compareTo(new BigDecimal(stock)) >= 0) {
                    UIUtils.showToastSafesShort("价格或库存不能为0");
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("stock", new BigDecimal(stock).intValue());
                map.put("price", new BigDecimal(price));
                mapd = JavaMethod.getDiffrentMap(map, JavaMethod.transBean2Map(beam));
                if (mapd.size() == 0) {
                    UIUtils.showToastSafesShort("没有任何修改，无需提交");
                    return;
                }
                mapd.put("categoryId", beam.getCategoryId());
                mapd.put("entityId", beam.getEntityId());
                mapd.put("store", beam.getStore());
                mapd.put("nickName", beam.getNickName());
                RequestPresenter.getInstance().goodsUpdate(mapd, new RequestCallback<GoodsUpdate>() {
                    @Override
                    public void onSuc(GoodsUpdate body) {
                        UIUtils.showToastSafesShort("修改成功");
                        setResult(mapd);
                        dismiss();
                    }

                    @Override
                    public void onFailed(int goodsUpdate, String msg) {
                        UIUtils.showToastSafesShort(msg);
                        dismiss();
                    }
                });
                break;
        }
    }
}
