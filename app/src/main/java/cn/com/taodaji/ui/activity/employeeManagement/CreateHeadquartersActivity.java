package cn.com.taodaji.ui.activity.employeeManagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.CreateHeadquartersBean;
import cn.com.taodaji.model.entity.EmployeesListBean;
import cn.com.taodaji.model.entity.GmoEditStore;
import cn.com.taodaji.model.entity.ShopAddressItem;
import cn.com.taodaji.model.entity.ShopEmployeeItem;
import cn.com.taodaji.model.entity.ShopEmployeeList;
import cn.com.taodaji.model.event.EmpoleeStoreList;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.viewModel.adapter.ChooseAssociatedShopAdapter;
import tools.activity.SimpleToolbarActivity;

public class CreateHeadquartersActivity extends SimpleToolbarActivity {
    private View mainView;
    private EditText editText;
    private Button button;
    private RecyclerView recyclerView;
    private ChooseAssociatedShopAdapter adapter;
    private List<ShopAddressItem> itemList = new ArrayList<>();
    private ArrayList<ShopEmployeeList> shopEmployeeLists = new ArrayList<>();
    private int entityId=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void titleSetting(Toolbar toolbar){
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("创建总部");
    }

    @Override
    public void initMainView(){
        mainView = getLayoutView(R.layout.activity_create_headquarters);
        body_other.addView(mainView);
        button = ViewUtils.findViewById(mainView,R.id.btn_confirm);
        editText = ViewUtils.findViewById(mainView,R.id.et_headquarters_title);
        recyclerView = ViewUtils.findViewById(mainView,R.id.rv_choose_associated_shop);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        adapter = new ChooseAssociatedShopAdapter(itemList,this);
        recyclerView.setAdapter(adapter);
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                adapter.notifyItemChanged(position);
            }
        });


        if (!getIntent().getStringExtra("title").equals("")) {
            editText.setText(getIntent().getStringExtra("title"));
            button.setText("确定编辑");
            simple_title.setText("编辑总部");
            entityId =getIntent().getIntExtra("pid",0);
            initEditGmoData();
        }else {
            iniData();
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().equals("")){
                    UIUtils.showToastSafesShort("总部名称必填");
                    return;
                }
                StringBuffer sb= new StringBuffer();
                int i =0;
                for (ShopAddressItem item :itemList){
                    if (!item.isCurrent()){
                        sb.append(item.getShopId());
                        sb.append(",");
                        i ++;
                    }
                }
                if (i<2){
                    UIUtils.showToastSafesShort("至少选择两个门店");
                    return;
                } else {
                    loading("请稍后");
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("entityId",entityId);
                    map.put("gmoName",editText.getText().toString().trim());
                    map.put("customerEntityId", PublicCache.loginPurchase.getLoginUserId());
                    map.put("relationCustomerEntityId",sb.toString());
                    RequestPresenter.getInstance().createHeadquarters(map, new RequestCallback<CreateHeadquartersBean>() {
                        @Override
                        protected void onSuc(CreateHeadquartersBean body) {
                            loadingDimss();
                            UIUtils.showToastSafesShort("编辑成功");
                            finish();
                        }

                        @Override
                        public void onFailed(int errCode, String msg) {
                            loadingDimss();
                            UIUtils.showToastSafesShort(msg);
                        }
                    });
                }
            }
        });

    }

    public void iniData(){
        itemList.clear();
        RequestPresenter.getInstance().getEmployeesList(PublicCache.loginPurchase.getLoginUserId(), new RequestCallback<EmployeesListBean>() {
            @Override
            protected void onSuc(EmployeesListBean body) {
                for (int i = 0; i < body.getData().getList().size(); i++) {
                    ShopAddressItem item = new ShopAddressItem();
                    item.setTitle(body.getData().getList().get(i).getEnterpriseCode());
                    item.setShopId(body.getData().getList().get(i).getCustomerEntityId());
                    itemList.add(item);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafesShort(msg);
            }
        });
    }

    public void initEditGmoData(){
        itemList.clear();
        RequestPresenter.getInstance().getEidtGmoList(PublicCache.loginPurchase.getLoginUserId(), entityId, new RequestCallback<GmoEditStore>() {
            @Override
            protected void onSuc(GmoEditStore body) {
                if (body.getData().getList()!=null){
                    for (int i = 0; i < body.getData().getList().size(); i++) {
                        ShopAddressItem item = new ShopAddressItem();
                        item.setTitle(body.getData().getList().get(i).getEnterpriseCode());
                        item.setShopId(body.getData().getList().get(i).getCustomerEntityId());
                        item.setCurrent(body.getData().getList().get(i).getFlag()==1);
                        itemList.add(item);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafesShort(msg);
            }
        });
    }
}
