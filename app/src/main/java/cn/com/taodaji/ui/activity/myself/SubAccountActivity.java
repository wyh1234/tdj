package cn.com.taodaji.ui.activity.myself;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.OnOrOffSubUser;

import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;

import cn.com.taodaji.model.entity.SubAccount;
import cn.com.taodaji.model.entity.SubAccount_Resu;
import cn.com.taodaji.model.entity.SubUserDelete;
import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import cn.com.taodaji.common.PublicCache;

import java.util.HashMap;
import java.util.Map;

import tools.activity.SimpleToolbarActivity;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;


public class SubAccountActivity extends SimpleToolbarActivity implements View.OnClickListener {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("子账号管理");
        right_textView.setText("创建账号");
        right_textView.setOnClickListener(this);
        right_textView.setTextColor(UIUtils.getColor(R.color.white));
        right_textView.setPadding(30, 10, 30, 10);
        right_textView.setTextSize(14);
//        right_textView.setBackgroundResource(R.drawable.z_round_rect_white);

    }

    private SingleRecyclerViewAdapter myRecyclerViewAdapter;


    @Override
    protected void initMainView() {
        RecyclerView recyclerView = getLayoutView(R.layout.t_recyclerview);
        body_other.addView(recyclerView);
        body_other.setBackgroundColor(UIUtils.getColor(R.color.gray_f2));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, UIUtils.dip2px(10), R.layout.t_split_line));
        myRecyclerViewAdapter = new SingleRecyclerViewAdapter() {
            @Override
            public void initBaseVM() {
                putBaseVM(TYPE_CHILD, new BaseVM() {
                    @Override
                    protected void dataBinding() {
                        putRelation(R.id.username, "username");
                        putRelation(R.id.isActive, "isActive");
                        putRelation(R.id.name, "realname");

                    }

                    @Override
                    protected void addOnclick() {
                        putViewOnClick(R.id.onOrOffSubUser);
                        putViewOnClick(R.id.deleteSubUser);
                    }
                });


            }

            @Override
            public View onCreateHolderView(ViewGroup parent, int viewType) {
                return ViewUtils.getFragmentView(parent, R.layout.item_myself_sub_account);
            }

            @Override
            public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
                super.onBindViewHolderCustomer(holder, position);

                SubAccount bean = (SubAccount) getListBean(position);
                if (bean == null) return;

                CheckedTextView isActive = holder.findViewById(R.id.isActive);
                Button onOrOffSubUser = holder.findViewById(R.id.onOrOffSubUser);
                TextView role_text = holder.findViewById(R.id.role_text);
                TextView item_id = holder.findViewById(R.id.item_id);
                item_id.setText(String.valueOf(position + 1));
                role_text.setText(PublicCache.getRoleType().getValueOfKey(bean.getRole()));

                if (bean.getHasVerfify() == 0) {
                    isActive.setText("未激活");
                    isActive.setChecked(false);
                    onOrOffSubUser.setText("重新发送");
                } else {
                    onOrOffSubUser.setVisibility(View.VISIBLE);
                    if (bean.getIsActive() == 0) {
                        isActive.setText("已停用");
                        onOrOffSubUser.setText("启用");
                    } else {
                        isActive.setText("使用中");
                        onOrOffSubUser.setText("停用");
                    }
                }

            }

            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object itemBean) {

                if (onclickType == 0) {

                    SubAccount bean = (SubAccount) itemBean;

                    Map<String, Object> map = new HashMap<>();

                    switch (view.getId()) {
                        case R.id.onOrOffSubUser:

                            if (bean.getHasVerfify() == 0) {
                                loading();
                                getRequestPresenter().sendSmsToSubUser(bean.getEntityId(), new ResultInfoCallback<Object>() {
                                    @Override
                                    public void onSuccess(Object body) {
                                        loadingDimss();
                                        UIUtils.showToastSafesShort("已发送成功");
                                    }

                                    @Override
                                    public void onFailed(int objectResultInfo, String msg) {
                                        UIUtils.showToastSafesShort(msg);
                                        loadingDimss();
                                    }
                                });
                            } else {
                                map.put("entityId", bean.getEntityId());
                                map.put("isActive", bean.getIsActive() == 0 ? 1 : 0);
                                loading();
                                getRequestPresenter().onOrOffSubUser(map, new RequestCallback<OnOrOffSubUser>() {
                                    @Override
                                    public void onSuc(OnOrOffSubUser body) {
                                        loadingDimss();
                                        initData();
                                    }

                                    @Override
                                    public void onFailed(int onOrOffSubUser, String msg) {
                                        UIUtils.showToastSafesShort(msg);
                                        loadingDimss();
                                    }
                                });
                            }
                            return true;
                        case R.id.deleteSubUser:
                            if (bean.getRole() == 3 && PublicCache.loginPurchase.getEmpRole() != 0) {
                                UIUtils.showToastSafe("您没有权限删除管理员");
                                return true;
                            }
                            loading();
                            getRequestPresenter().subUserDelete(bean.getEntityId(), bean.getFlag(), bean.getMarkCode(), new RequestCallback<SubUserDelete>() {
                                @Override
                                public void onSuc(SubUserDelete body) {
                                    initData();
                                    loadingDimss();
                                }

                                @Override
                                public void onFailed(int subUserDelete, String msg) {
                                    UIUtils.showToastSafesShort(msg);
                                    loadingDimss();
                                }
                            });
                            return true;
                    }
                }
                return super.onItemClick(view, onclickType, position, itemBean);
            }
        };
        recyclerView.setAdapter(myRecyclerViewAdapter);
    }


    @Override
    protected void initData() {
        if (PublicCache.loginPurchase == null) return;
        getRequestPresenter().getSubUserList(PublicCache.loginPurchase.getMarkCode(), new RequestCallback<SubAccount_Resu>() {
            @Override
            public void onSuc(SubAccount_Resu body) {
                myRecyclerViewAdapter.setList(body.getData());
            }

            @Override
            public void onFailed(int subAccount_resu, String msg) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, SubAccountCreateActivity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            initData();
        }
    }
}
