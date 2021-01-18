package cn.com.taodaji.ui.activity.wallet;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.SimpleBankCardAdapter;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.AccountByCustomerId;
import cn.com.taodaji.model.entity.AccountByStoreId;
import cn.com.taodaji.model.entity.AccountByStoreId_Resu;
import cn.com.taodaji.model.entity.DefaultAccountSet;

import com.base.viewModel.adapter.OnItemClickListener;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;
import com.base.entity.ResultInfo;
import com.base.retrofit.RequestCallback;

import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.ui.activity.ocr.RealNameAuthenticationActivity;
import tools.activity.SimpleToolbarActivity;

import com.base.utils.CustomerData;
import com.base.utils.DensityUtil;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;


public class MyBankCardListActivity extends SimpleToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("我的提现账户");
    }

    private SimpleBankCardAdapter simpleBankCardAdapter;
    private boolean isDefault = false;
    private View footer_view2;//绑定支付宝
    private View footer_view;//绑定银行卡
    private CustomerData<Integer, String, String> bank = PublicCache.getBank();

    @Override
    protected void initMainView() {
        View mainView = getLayoutView(R.layout.activity_myself_my_bank_card);
        body_other.addView(mainView);

        RecyclerView recyclerView = ViewUtils.findViewById(mainView, R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(DensityUtil.dp2px(10), R.color.white));

        footer_view = ViewUtils.getFragmentView(recyclerView, R.layout.item_myself_my_bank_card_footer);
        footer_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PublicCache.loginSupplier != null && PublicCache.loginSupplier.getIsAuth().equals("0")) {
                    UIUtils.showToastSafesShort("添加银行卡前必须先实名认证");
                    //认证页面
                    Intent intent = new Intent(MyBankCardListActivity.this, RealNameAuthenticationActivity.class);
                    startActivity(intent);
                } else MyBankCardAddActivity.startActivity(MyBankCardListActivity.this);
            }
        });
        simpleBankCardAdapter = new SimpleBankCardAdapter();

        if (PublicCache.loginPurchase != null) {
            footer_view2 = ViewUtils.getFragmentView(recyclerView, R.layout.item_myself_my_bank_card_footer);
            TextView text = ViewUtils.findViewById(footer_view2, R.id.text);
            text.setText("绑定支付宝");
            footer_view2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlipayAccountBindingActivity.startActivity(MyBankCardListActivity.this);
                }
            });
            simpleBankCardAdapter.setItemClickListener(new OnItemClickListener() {
                @Override
                public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                    if (onclickType == 0) {
                        AccountByCustomerId.DataBean dataBean = (AccountByCustomerId.DataBean) bean;
                        if (isDefault) {
                            RequestPresenter.getInstance().customerFinance_setDefaultAccount(dataBean.getEntityId(), PublicCache.loginPurchase.getEntityId(), new RequestCallback<ResultInfo>() {
                                @Override
                                public void onSuc(ResultInfo body) {
                                    finish();
                                }

                                @Override
                                public void onFailed(int resultInfo, String msg) {

                                }
                            });
                        } else MyBankCardDetailActivity.startActivity(view.getContext(), dataBean);
                        return true;
                    }
                    return false;
                }
            });
            simpleBankCardAdapter.addFooterView(footer_view2);
            simpleBankCardAdapter.addFooterView(footer_view);
            recyclerView.setAdapter(simpleBankCardAdapter);
        } else if (PublicCache.loginSupplier != null) {
            simpleBankCardAdapter.setItemClickListener(new OnItemClickListener() {
                @Override
                public boolean onItemClick(View view, int onclickType, int position, Object itemBean) {
                    if (onclickType == 0) {
                        AccountByStoreId bean = (AccountByStoreId) itemBean;
                        if (isDefault) {
                            RequestPresenter.getInstance().setDefaultAccount(bean.getEntityId(), bean.getStoreId(), new RequestCallback<DefaultAccountSet>() {
                                @Override
                                public void onSuc(DefaultAccountSet body) {
                                    finish();
                                }

                                @Override
                                public void onFailed(int defaultAccountSet, String msg) {

                                }
                            });
                        } else MyBankCardDetailActivity.startActivity(view.getContext(), bean);
                        return true;
                    } else return false;
                }
            });
            //simpleBankCardAdapter.addFooterView(footer_view);
            recyclerView.setAdapter(simpleBankCardAdapter);
        }

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (PublicCache.loginSupplier != null) {
            loading();
            getRequestPresenter().getAccountByStoreId(PublicCache.loginSupplier.getStore(), new RequestCallback<AccountByStoreId_Resu>() {
                @Override
                public void onSuc(AccountByStoreId_Resu body) {
                    List<AccountByStoreId> list = body.getData();
                    if (list != null && list.size() > 0) {
                        for (AccountByStoreId accountByStoreId : list) {
                            int bankType = bank.idOfValue(accountByStoreId.getBankName());
                            accountByStoreId.setBankType(bankType);
                            if (bankType != 0) {
                                String acc = accountByStoreId.getAccountNo();
                                String h = acc.substring(0, 4);
                                String f = acc.substring(acc.length() - 4);
                                acc = h + "***********" + f;
                                accountByStoreId.setAccountNo(acc);
                            }
                        }


                        simpleBankCardAdapter.setList(list);
                        if (simpleBankCardAdapter.isHasFooter(footer_view)) {
                            simpleBankCardAdapter.removeFooter(footer_view);
                        }
                        if (list.size() > 0) simpleBankCardAdapter.moveToPosition(0);
                    } else {
                        simpleBankCardAdapter.clearAll();
                        simpleBankCardAdapter.addFooterView(footer_view);
                    }

                    loadingDimss();

                }

                @Override
                public void onFailed(int accountByStoreId_resu, String msg) {
                    loadingDimss();
                }
            });
        } else if (PublicCache.loginPurchase != null) {
            getRequestPresenter().getAccountByCustomerId(PublicCache.loginPurchase.getEntityId(), new RequestCallback<AccountByCustomerId>() {
                @Override
                public void onSuc(AccountByCustomerId body) {
                    List<AccountByCustomerId.DataBean> list = body.getData();
                    for (AccountByCustomerId.DataBean dataBean : list) {
                        int bankType = bank.idOfValue(dataBean.getBankName());
                        if (bankType == 0) {
                            simpleBankCardAdapter.removeFooter(footer_view2);
                        } else {
                            String acc = dataBean.getAccountNo();
                            String h = acc.substring(0, 4);
                            String f = acc.substring(acc.length() - 4);
                            acc = h + "***********" + f;
                            dataBean.setAccountNo(acc);
                            simpleBankCardAdapter.removeFooter(footer_view);
                        }
                        dataBean.setBankType(bankType);
                    }
                    simpleBankCardAdapter.notifyDataSetChanged(list);

                }

                @Override
                public void onFailed(int accountByCustomerId, String msg) {

                }
            });


        }

    }

    @Override
    protected void initData() {

    }


    //选择提现银行卡入口
    @Subscribe(sticky = true)
    public void onEvent(Boolean ben) {
        //ben==true,点击事件变为 设置默认银行卡
        EventBus.getDefault().removeStickyEvent(ben);
        isDefault = ben;
        if (isDefault) simple_title.setText("设置提现账户");
    }

    public static void startActivity(Context context, Boolean isDefault) {
        //  if (PublicCache.loginSupplier == null) return;
        if (isDefault) EventBus.getDefault().postSticky(true);
        Intent intent = new Intent(context, MyBankCardListActivity.class);
        context.startActivity(intent);
    }

}
