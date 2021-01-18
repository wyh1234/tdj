package cn.com.taodaji.ui.activity.integral;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.glide.ImageLoaderUtils;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.IntegralShopMy;
import cn.com.taodaji.model.entity.Privilegeinfo;
import cn.com.taodaji.model.entity.UserPrivilegeinfo;
import cn.com.taodaji.model.entity.VipRights;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import cn.com.taodaji.viewModel.adapter.VipLevelAdapter;
import cn.com.taodaji.viewModel.adapter.VipRightsAdapter;
import tools.activity.DataBaseActivity;
import tools.statusbar.Eyes;

public class ShopVipActivity extends DataBaseActivity implements ItemClickListener {

    private Unbinder unbinder;
    private List<UserPrivilegeinfo.DataBean.PrivilegesBean> itemList = new ArrayList<>();

    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.tv_vip)
    TextView tv_vip;
    @BindView(R.id.btn_back)
    ImageView back;
    @BindView(R.id.rv_vip_level)
    RecyclerView recyclerView;
    @BindView(R.id.rv_vip_rights)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_integral)
    TextView tv_integral;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.ll_tq)
    LinearLayout ll_tq;
    @BindView(R.id.tv_tq)
    TextView tv_tq;
    private VipLevelAdapter adapter;
    private VipRightsAdapter vipRightsAdapter;

    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.activity_shop_vip);
    }

    @Override
    protected void initView() {
        super.initView();
        unbinder = ButterKnife.bind(this);
        Eyes.setStatusBarColor(this, UIUtils.getColor(this, R.color.red_f0));
        title.setText("淘大集会员");
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ll_tq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ShopVipActivity.this, WebViewActivity.class);
                intent1.putExtra("url",PublicCache.getROOT_URL().get(2)+"tdj-user/user/integral/vipRulePage");
                startActivity(intent1);
            }
        });
        tv_tq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ShopVipActivity.this, WebViewActivity.class);
                intent1.putExtra("url",PublicCache.getROOT_URL().get(2)+"tdj-user/user/integral/privilegeRulePage");
                startActivity(intent1);
            }
        });

        initVipLevel();
        initVipRights();
    }

    @Override
    protected void initData() {
        super.initData();
        getUserAndPrivilege();
    }

    public void getUserAndPrivilege(){
        ShowLoadingDialog.showLoadingDialog(this);
        Map<String,Object> map=new HashMap<>();
        map.put("userId", PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId());
        getIntegralRequestPresenter().getUserAndPrivilege(map, new RequestCallback<UserPrivilegeinfo>(this) {
            @Override
            public void onSuc(UserPrivilegeinfo body) {
                ShowLoadingDialog.close();
                adapter.setCurrentNode(body.getData().getLevel()+1);
                if (body.getData().getLevel()==0){
                    tv_vip.setText("铜牌会员");
                }else if (body.getData().getLevel()==1){
                    tv_vip.setText("银牌会员");
                }else if (body.getData().getLevel()==2){
                    tv_vip.setText("金牌会员");
                }else if (body.getData().getLevel()==3){
                    tv_vip.setText("钻石会员");
                }else if (body.getData().getLevel()==4){
                    tv_vip.setText("至尊会员");
                }
                if (body.getData().getLevel()==4){
                    tv_integral.setText("您已是最高会员等级");

                }else {
                    tv_integral.setText("距下一等级还差"+body.getData().getUpIntegral());

                }
                if (!ListUtils.isNullOrZeroLenght(body.getData().getExpireTime())){
                    tv_time.setText("等级有效期"+body.getData().getExpireTime().substring(0,10));

                }
                if (!ListUtils.isEmpty(body.getData().getPrivileges())){
                    itemList.addAll(body.getData().getPrivileges());
                    vipRightsAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                ShowLoadingDialog.close();
                UIUtils.showToastSafe(msg);

            }
        });
    }

    private void initVipLevel() {
         adapter = new VipLevelAdapter(this, 5);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }

    private void initVipRights() {

         vipRightsAdapter = new VipRightsAdapter(itemList,this);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        vipRightsAdapter.setItemClickListener(this);
        mRecyclerView.setAdapter(vipRightsAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(View v, int position) {
      /*  if (itemList.get(position).getLinkType().equals("1")){
            //H5页面

        }*/
    }
}
