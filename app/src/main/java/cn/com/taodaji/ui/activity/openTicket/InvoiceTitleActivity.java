package cn.com.taodaji.ui.activity.openTicket;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.retrofit.ResultInfoCallback;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.CustomerInvoice;
import tools.activity.SimpleToolbarActivity;

/**
 * Created by yangkuo on 2018-08-13.
 */
public class InvoiceTitleActivity extends SimpleToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
    }

    private BaseViewHolder viewHolder;
    private Button bt_submit_ok;
    private CustomerInvoice.DataBean customerInvoice;

    @Override
    protected void initMainView() {
        View view = ViewUtils.getLayoutView(this, R.layout.activity_invoice_title);
        body_scroll.addView(view);
        viewHolder = new BaseViewHolder(view, new BaseVM() {
            @Override
            protected void dataBinding() {
                putRelation(R.id.et_invoice_title, "invoiceTitle");
                putRelation(R.id.et_duty_paragraph, "taxNumber");
                putRelation(R.id.et_bankCard_name, "bankName");
                putRelation(R.id.et_bankCard_no, "bankAccount");
                putRelation(R.id.et_phone_num, "telephone");
                putRelation(R.id.et_register_address, "address");
            }
        }, null);

        TextView tv_invoice = view.findViewById(R.id.tv_invoice);

        if (PublicCache.initializtionData != null && !TextUtils.isEmpty(PublicCache.initializtionData.getTax_fee_rate()))
            tv_invoice.setText("2、每笔订单收取" + (new BigDecimal(PublicCache.initializtionData.getTax_fee_rate()).multiply(BigDecimal.valueOf(100)).intValue()) + "%票税。");

        bt_submit_ok = findViewById(R.id.bt_submit_ok);
        bt_submit_ok.setOnClickListener(v -> submit());

    }


    private void submit() {
        if (PublicCache.loginPurchase == null) return;
        Map<String, Object> map = viewHolder.getViewValues();

        String taxNum = String.valueOf(map.get("taxNumber"));
        if (taxNum.length() < 15 || taxNum.length() > 20) {
            UIUtils.showToastSafesShort("请检查税号是否输入有误");
            return;
        }

        for (String s : map.keySet()) {
            if (map.get(s) == null || "".equals(map.get(s))) {
                UIUtils.showToastSafesShort("有信息未输入");
                return;
            }
        }

        //编辑
        if (customerInvoice != null) {
            Map<String, Object> map_old = JavaMethod.transBean2Map(customerInvoice);
            Map<String, Object> map_dif = JavaMethod.getDiffrentMap(map, map_old);
            if (map_dif.size() == 0) {
                UIUtils.showToastSafesShort("没有修改任何信息");
                return;
            }
            loading();
            map_dif.put("customerId", PublicCache.loginPurchase.getEntityId());
            map_dif.put("invoiceType", 1);
            map_dif.put("entityId", customerInvoice.getEntityId());
            getRequestPresenter().customerInvoice_update(map_dif, new ResultInfoCallback() {
                @Override
                public void onSuccess(Object body) {
                    UIUtils.showToastSafesShort("发票已修改");
                    loadingDimss();
                    if (right_textView != null) right_textView.setText("编辑");
                    setTitle("发票抬头");
                    changed(false);
                }

                @Override
                public void onFailed(int errCode, String msg) {
                    super.onFailed(errCode, msg);
                    loadingDimss();
                    UIUtils.showToastSafesShort(msg);
                }
            });

        } else {
            loading();
            map.put("isActive", 1);
            map.put("customerId", PublicCache.loginPurchase.getEntityId());
            map.put("invoiceType", 1);
            getRequestPresenter().customerInvoice_create(map, new ResultInfoCallback(this) {
                @Override
                public void onSuccess(Object body) {
                    loadingDimss();
                    UIUtils.showToastSafesShort("发票已开启");
                    PublicCache.isInvoice = 1;
                    finish();
                }

                @Override
                public void onFailed(int errCode, String msg) {
                    super.onFailed(errCode, msg);
                    loadingDimss();
                    UIUtils.showToastSafesShort(msg);
                }
            });

        }
    }

    @Override
    protected void initData() {
        super.initData();
        customerInvoice = (CustomerInvoice.DataBean) getIntent().getSerializableExtra("data");
        if (customerInvoice != null) {
            right_textView.setText("编辑");
            viewHolder.setValues(customerInvoice);
            setTitle("发票抬头");
            bt_submit_ok.setVisibility(View.GONE);
            changed(false);

            title_right.setOnClickListener(v -> {
                boolean isEdit = "编辑".equals(right_textView.getText().toString());

                if (isEdit) {
                    right_textView.setText("完成");
                    setTitle("编辑发票抬头");
                    changed(true);
                } else {
                    submit();
                }

            });
        } else {
            setTitle("添加发票抬头");
        }
    }

    public void changed(boolean isEdit) {
        Map<String, List<Integer>> map = viewHolder.getBaseVM().getRelationList();

        for (String s : map.keySet()) {
            List<Integer> list = map.get(s);
            if (list != null) {
                for (Integer integer : list) {
                    View view1 = viewHolder.findViewById(integer);
                    view1.setEnabled(isEdit);
                    if (isEdit) {
                        view1.setBackgroundResource(R.drawable.z_round_rect_gray_c9);
                    } else {
                        view1.setBackgroundColor(UIUtils.getColor(R.color.white));
                    }

                    //注册地址
                    if (integer == R.id.et_register_address) {
                        if (isEdit) UIUtils.setViewHeight(view1, UIUtils.dip2px(100));
                        else UIUtils.setViewHeight(view1, ViewGroup.LayoutParams.WRAP_CONTENT);
                    }
                }
            }
        }

    }
}
