package cn.com.taodaji.ui.activity.employeeManagement;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.EmployeesListBean;
import cn.com.taodaji.model.entity.ShopAddressItem;
import cn.com.taodaji.model.entity.ShopEmployeeItem;
import cn.com.taodaji.model.entity.ShopEmployeeList;
import cn.com.taodaji.model.event.DeleteCustomeEvent;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.viewModel.adapter.ChainShopAdapter;
import cn.com.taodaji.viewModel.adapter.EmployeesManagementAdapter;
import tools.activity.SimpleToolbarActivity;

public class EmployeesManagementActivity extends SimpleToolbarActivity implements View.OnClickListener {

    private View mainView;
    private RecyclerView recyclerView;
    private Button addEmployee;
    private EmployeesManagementAdapter adapter;
    private ArrayList<MultiItemEntity> data = new ArrayList<>();
    private ArrayList<ShopEmployeeList> shopList = new ArrayList<>();
    public int shopNumber=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void titleSetting(Toolbar toolbar){
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("员工管理");
        right_icon_text.setText("创建总部");
        right_icon.setVisibility(View.VISIBLE);
        right_icon.setImageResource(R.mipmap.create_headquarters);
        right_onclick_line.setOnClickListener(this);
    }

    @Override
    public void initMainView(){
        mainView = getLayoutView(R.layout.activity_employees_management);
        body_other.addView(mainView);


        recyclerView = ViewUtils.findViewById(mainView,R.id.rv_shop_employees_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EmployeesManagementAdapter(data);
        recyclerView.setAdapter(adapter);


        addEmployee = ViewUtils.findViewById(mainView,R.id.btn_add_new_employee);
        addEmployee.setOnClickListener(this);

        iniData();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        iniData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DeleteCustomeEvent event) {
        switch (event.getFlag()) {
            case 0:
                UIUtils.showToastSafesShort(event.getMsg());
                break;
            case 1:
                UIUtils.showToastSafesShort(event.getMsg());
                iniData();
                break;
            case 2:
                UIUtils.showToastSafesShort(event.getMsg());
                break;
            case 3:
                UIUtils.showToastSafesShort(event.getMsg());
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_add_new_employee:
                if (PublicCache.loginPurchase.getEmpRole()==0||PublicCache.loginPurchase.getEmpRole()==3||PublicCache.loginPurchase.getEmpRole()==4){
                Intent intent1 = new Intent(this,AddEmployeeActivity.class);
                startActivity(intent1);}
                else {
                    UIUtils.showToastSafesShort("您没有权限进行该操作");
                }
                break;
            case R.id.right_onclick_line:
                if (PublicCache.loginPurchase.getEmpRole()==0||PublicCache.loginPurchase.getEmpRole()==3){
                Intent intent = new Intent(this,CreateHeadquartersActivity.class);
                intent.putExtra("title","");
                startActivity(intent);}
                else {
                    UIUtils.showToastSafesShort("您没有权限进行该操作");
                }
                break;
                default:break;
        }
    }

    public void iniData() {
        data.clear();
        shopList.clear();
        shopNumber=0;
        RequestPresenter.getInstance().getEmployeesList(PublicCache.loginPurchase.getLoginUserId(), new RequestCallback<EmployeesListBean>() {
            @Override
            protected void onSuc(EmployeesListBean body) {
                for (int k=0;k<body.getData().getGmoList().size();k++){
                    ShopEmployeeList employeeList = new ShopEmployeeList();
                    employeeList.setId(body.getData().getGmoList().get(k).getCustomerEntityId());
                    employeeList.setTitle(body.getData().getGmoList().get(k).getEnterpriseCode());
                    employeeList.setEmployeesNum(body.getData().getGmoList().get(k).getEmpCount());
                    employeeList.setChain(true);
                    employeeList.setOwnStores(body.getData().getGmoList().get(k).getOwnedStores());
                    shopList.add(employeeList);
                    data.add(employeeList);
                    shopNumber++;
                }
                for (int i = 0; i < body.getData().getList().size(); i++) {
                    ShopEmployeeList employeeList = new ShopEmployeeList();
                    employeeList.setId(body.getData().getList().get(i).getCustomerEntityId());
                    employeeList.setTitle(body.getData().getList().get(i).getEnterpriseCode());
                    employeeList.setEmployeesNum(body.getData().getList().get(i).getEmpCount());
                    employeeList.setChain(false);
                    if (body.getData().getList().get(i).getEmpFlag() == 0){
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
                        item.setRole(body.getData().getList().get(i).getSubInfoList().get(j).getRole());
                        employeeList.addSubItem(item);
                    }
                    }
                    shopList.add(employeeList);
                    data.add(employeeList);
                    shopNumber++;
                }
                if (shopNumber<=1){
                    right_onclick_line.setVisibility(View.INVISIBLE);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}
