package cn.com.taodaji.ui.activity.employeeManagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.ChainShopList;
import cn.com.taodaji.model.entity.ShopEmployeeItem;
import cn.com.taodaji.model.entity.ShopEmployeeList;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.viewModel.adapter.ChainShopAdapter;
import tools.activity.SimpleToolbarActivity;

public class ChainShopActivity extends SimpleToolbarActivity implements View.OnClickListener {

    private View mainView;
    private RecyclerView recyclerView;
    private ChainShopAdapter adapter;
    private List<MultiItemEntity> data = new ArrayList<>();
    private String title="";
    private int pid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void titleSetting(Toolbar toolbar){
        setStatusBarColor();
        setToolBarColor();
        right_icon.setImageResource(R.mipmap.edit_headquarters);
        right_icon_text.setText("编辑总部");
        right_icon.setVisibility(View.VISIBLE);
        right_onclick_line.setOnClickListener(this);
        title = getIntent().getStringExtra("title");
        simple_title.setText(title);
    }

    @Override
    public void initMainView(){
        mainView = getLayoutView(R.layout.activity_chain_shop);
        body_other.addView(mainView);

        recyclerView = ViewUtils.findViewById(mainView,R.id.rv_chain_shop);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChainShopAdapter(data);
        recyclerView.setAdapter(adapter);
        iniData();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        iniData();
    }

    @Override
    protected void onResume(){
        super.onResume();
        iniData();
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.right_onclick_line:
                if (PublicCache.loginPurchase.getEmpRole()==0||PublicCache.loginPurchase.getEmpRole()==3){
                    Intent intent = new Intent(ChainShopActivity.this,CreateHeadquartersActivity.class);
                    intent.putExtra("title",title);
                    intent.putExtra("pid",pid);
                    startActivity(intent);
                    finish();
                }else {
                    UIUtils.showToastSafesShort("您没有权限进行该操作");
                }
                break;
                default:break;
        }
    }

    public void iniData(){
        RequestPresenter.getInstance().getChainList(PublicCache.loginPurchase.getLoginUserId(), getIntent().getStringExtra("own"), new RequestCallback<ChainShopList>() {
            @Override
            protected void onSuc(ChainShopList body) {
                data.clear();
                for (int i = 0; i < body.getData().getList().size(); i++) {
                    ShopEmployeeList employeeList = new ShopEmployeeList();
                    employeeList.setId(body.getData().getList().get(i).getCustomerEntityId());
                    employeeList.setTitle(body.getData().getList().get(i).getEnterpriseCode());
                    employeeList.setEmployeesNum(body.getData().getList().get(i).getEmpCount());
                    employeeList.setPid(body.getData().getList().get(i).getParentCustomerEntityId());
                    employeeList.setChain(body.getData().getList().get(i).getEmpFlag()==1);
                    if (body.getData().getList().get(i).getEmpFlag()==1){
                        pid = body.getData().getList().get(i).getParentCustomerEntityId();
                    }
                    for (int j = 0; j < body.getData().getList().get(i).getSubInfoList().size(); j++) {
                        ShopEmployeeItem item = new ShopEmployeeItem();
                        item.setCreator(body.getData().getList().get(i).getSubInfoList().get(j).getIsCreater().equals("Y"));
                        item.setPosition(body.getData().getList().get(i).getSubInfoList().get(j).getRole());
                        item.setName(body.getData().getList().get(i).getSubInfoList().get(j).getWorkName());
                        item.setPhone(body.getData().getList().get(i).getSubInfoList().get(j).getPhone());
                        item.setPid(body.getData().getList().get(i).getSubInfoList().get(j).getParentCustomerEntityId());
                        item.setId(body.getData().getList().get(i).getSubInfoList().get(j).getCustomerEntityId());
                        item.setLeader(body.getData().getList().get(i).getSubInfoList().get(j).getIsLeader().equals("Y"));
                        item.setEnterpriseCode(body.getData().getList().get(i).getSubInfoList().get(j).getEnterpriseCode());
                        item.setMarkCode(body.getData().getList().get(i).getSubInfoList().get(j).getMarkCode());
                        employeeList.addSubItem(item);
                    }
                    data.add(employeeList);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafesShort(msg);
            }
        });
    }
}
