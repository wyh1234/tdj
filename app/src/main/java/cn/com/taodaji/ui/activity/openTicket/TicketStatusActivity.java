package cn.com.taodaji.ui.activity.openTicket;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.utils.DialogUtils;

import java.math.BigDecimal;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.CustomerInvoice;
import tools.activity.SimpleToolbarActivity;

public class TicketStatusActivity extends SimpleToolbarActivity implements View.OnClickListener {
    private DialogUtils alertDialog;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("专票信息");
    }


    private LinearLayout ll_invoice_title;

    private TextView txt_close_ticket, txt_ticket_top, txt_ticket_status;


    private CustomerInvoice.DataBean dataBean;

    @Override
    protected void initMainView() {
        View mainView = getLayoutView(R.layout.activity_ticket_status);
        body_other.addView(mainView);

        ll_invoice_title = mainView.findViewById(R.id.ll_invoice_title);


        txt_close_ticket = ViewUtils.findViewById(mainView, R.id.txt_close_ticket);
        txt_ticket_status = mainView.findViewById(R.id.txt_ticket_status);
        txt_ticket_top = mainView.findViewById(R.id.txt_ticket_top);

        txt_close_ticket.setOnClickListener(this);

    }

    private void init() {
        switch (PublicCache.isInvoice) {
            //isInvoice 0-无发票 1-启用 2-关闭
            case 0:
                ll_invoice_title.setVisibility(View.GONE);
                txt_close_ticket.setText("需要专票");
                break;
            case 1:
                ll_invoice_title.setVisibility(View.VISIBLE);
                right_textView.setText("开票信息");
                txt_close_ticket.setText("关闭开票");
                title_right.setOnClickListener(TicketStatusActivity.this);
                break;
            case 2:
                ll_invoice_title.setVisibility(View.VISIBLE);
                right_textView.setText("开票信息");
                txt_close_ticket.setText("恢复开票");
                title_right.setOnClickListener(TicketStatusActivity.this);
                break;
        }
        loading();
        if (PublicCache.loginPurchase != null)
            getRequestPresenter().customerInvoice_findOne(PublicCache.loginPurchase.getEntityId(), new RequestCallback<CustomerInvoice>() {
                @Override
                protected void onSuc(CustomerInvoice body) {
                    dataBean = body.getData();
                    loadingDimss();
                    if (body.getData() != null) {
                        ll_invoice_title.setVisibility(View.VISIBLE);
                        txt_ticket_top.setText(body.getData().getInvoiceTitle());
                        if (body.getData().getIsActive() == 0) {
                            txt_ticket_status.setText("已关闭");
                            PublicCache.isInvoice = 2;
                            txt_close_ticket.setText("恢复开票");
                        } else {
                            txt_ticket_status.setText("已开启");
                            PublicCache.isInvoice = 1;
                            txt_close_ticket.setText("关闭开票");
                        }

                    }
                }

                @Override
                public void onFailed(int errCode, String msg) {
                    super.onFailed(errCode, msg);
                    loadingDimss();
                }
            });
        else loadingDimss();
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_close_ticket:
                //需要专票
                if (PublicCache.isInvoice == 0) {
                    Intent intent = new Intent(this, InvoiceTitleActivity.class);
                    startActivity(intent);
                } else {
                    if (dataBean == null || PublicCache.loginPurchase == null) return;

                    if (PublicCache.initializtionData == null) return;

                    int isActive = dataBean.getIsActive() == 0 ? 1 : 0;


                    if (isActive == 0) {

                        DialogUtils.getInstance(this).getSimpleDialog(UIUtils.getString(R.string.close_ticket_tips)).setPositiveButton("确定关闭", (dialog, which) -> {
                            loading();
                            getRequestPresenter().customerInvoice_updateStatus(dataBean.getEntityId(), PublicCache.loginPurchase.getEntityId(), isActive, new ResultInfoCallback() {
                                @Override
                                public void onSuccess(Object body) {
                                    UIUtils.showToastSafesShort("发票已关闭");
                                    PublicCache.isInvoice = 2;
                                    init();
                                }

                                @Override
                                public void onFailed(int errCode, String msg) {
                                    super.onFailed(errCode, msg);
                                    UIUtils.showToastSafesShort(msg);
                                    loadingDimss();
                                }
                            });

                        }, R.color.orange_yellow_ff7d01).show();
                    } else {
                        int ss = new BigDecimal(PublicCache.initializtionData.getTax_fee_rate()).multiply(BigDecimal.valueOf(100)).intValue();
                        DialogUtils.getInstance(this).getSimpleDialog(String.format(UIUtils.getString(R.string.open_ticket_tips), String.valueOf(ss + "%"), 1000, (10 * ss), (1000 + 10 * ss))).setPositiveButton("确定开启", (dialog, which) -> {
                            loading();
                            getRequestPresenter().customerInvoice_updateStatus(dataBean.getEntityId(), PublicCache.loginPurchase.getEntityId(), isActive, new ResultInfoCallback() {
                                @Override
                                public void onSuccess(Object body) {
                                    UIUtils.showToastSafesShort("发票已开启");
                                    PublicCache.isInvoice = 1;
                                    init();
                                }

                                @Override
                                public void onFailed(int errCode, String msg) {
                                    super.onFailed(errCode, msg);
                                    UIUtils.showToastSafesShort(msg);
                                    loadingDimss();
                                }
                            });

                        }, R.color.orange_yellow_ff7d01).show();
                    }
                }


                break;
            case R.id.title_right:
                if (dataBean == null) return;
                Intent intent = new Intent(this, InvoiceTitleActivity.class);
                intent.putExtra("data", dataBean);
                startActivity(intent);
                break;
        }

    }
}
