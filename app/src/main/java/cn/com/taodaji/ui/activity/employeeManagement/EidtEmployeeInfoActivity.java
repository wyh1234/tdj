package cn.com.taodaji.ui.activity.employeeManagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.retrofit.RequestCallback;
import com.base.utils.DialogUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.EidtEmployeeInfo;
import cn.com.taodaji.model.entity.ShopEmployeeItem;
import cn.com.taodaji.model.entity.UpdateCustomerBean;
import cn.com.taodaji.model.event.SelectShopOrPositionEvent;
import cn.com.taodaji.model.presenter.RequestPresenter;
import tools.activity.SimpleToolbarActivity;

public class EidtEmployeeInfoActivity extends SimpleToolbarActivity implements View.OnClickListener {
    private View mainView;
    private LinearLayout selectShop,selectPosition;
    private TextView selectedShop,selectedPosition;
    private EditText employeeName,employeePhone;
    private Button confirm;
    private String markCode;
    private int pid =-1,role=-1,id=-1,flag=-1,entityId=-1;
    private ShopEmployeeItem item = new ShopEmployeeItem();

    private String[] positionArray = {"管理员", "店长","财务","厨师","员工"};
    private final String[] positionInfo = {
            "管理员：","所有权限\n",
            "(当为多个门店管理员时可创建总经办)\n",
            "店长：","所有权限\n",
            "财务：","充值、付款、对账\n",
            "厨师：","下单、售后、退押、评价\n",
            "员工：","无任何操作权限"
    };
    private final int[] index ={1,4,6,8,10};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void titleSetting(Toolbar toolbar){
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("编辑员工信息");
        right_icon.setImageResource(R.mipmap.position_information);
        right_icon_text.setText("说明");
        right_onclick_line.setOnClickListener(this);
    }

    @Override
    public void initMainView(){
        mainView = getLayoutView(R.layout.activity_eidt_employee_info);
        body_other.addView(mainView);

        selectShop = ViewUtils.findViewById(mainView,R.id.rl_select_shop);
        selectPosition = ViewUtils.findViewById(mainView,R.id.rl_select_position);
        selectShop.setOnClickListener(this);
        selectPosition.setOnClickListener(this);

        selectedShop = ViewUtils.findViewById(mainView,R.id.tv_selected_shop_title);
        selectedPosition = ViewUtils.findViewById(mainView,R.id.tv_selected_position);

        employeeName = ViewUtils.findViewById(mainView,R.id.et_employee_real_name);
        employeePhone = ViewUtils.findViewById(mainView,R.id.et_employee_phone_number);

        confirm = ViewUtils.findViewById(mainView,R.id.btn_add_confirm);
        confirm.setOnClickListener(this);

        item = getIntent().getParcelableExtra("edit");
        markCode = item.getMarkCode();

        loading("请稍后");
        RequestPresenter.getInstance().getEidtEmployeeInfo(item.getId(), item.getPid(), new RequestCallback<EidtEmployeeInfo>() {
            @Override
            protected void onSuc(EidtEmployeeInfo body) {
                loadingDimss();
                selectedShop.setText(body.getData().getEnterpriseCode());
                employeeName.setText(body.getData().getWorkName());
                employeePhone.setText(body.getData().getPhone());
                role = body.getData().getRole();
                pid = body.getData().getParentCustomerEntityId();
                id = body.getData().getEmployeesEntityId();
                entityId = body.getData().getCustomerEntityId();
                flag = body.getData().getFlag();//flag=1时员工是主账户，不能修改”所属门店“ 是2的时候可以改
                switch (role){
                    //角色 0：管理员、1：厨师、2：财务、3：子管理员、 4：店长、5：员工
                    case 0:
                        selectedPosition.setText("管理员");
                        break;
                    case 1:
                        selectedPosition.setText("厨师");
                        break;
                    case 2:
                        selectedPosition.setText("财务");
                        break;
                    case 3:
                        selectedPosition.setText("管理员");
                        break;
                    case 4:
                        selectedPosition.setText("店长");
                        break;
                    case 5:
                        selectedPosition.setText("员工");
                        break;
                    default:break;
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                loadingDimss();
                UIUtils.showToastSafesShort("获取数据失败");
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SelectShopOrPositionEvent event) {
        if (event.isFlag()){
            selectedShop.setText(event.getCurrentSelected());
            selectedShop.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            pid = event.getPosition();
            markCode = event.getMarkCode();
        }else {
            selectedPosition.setText(event.getCurrentSelected());
            selectedPosition.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            role = event.getPosition();
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
            case R.id.right_onclick_line:
                DialogUtils.getInstance(EidtEmployeeInfoActivity.this).getTipsWithButtonDialog("职位说明",positionInfo,index).show();
                break;
            case R.id.rl_select_shop:
                if (flag==1){
                    UIUtils.showToastSafesShort("主账号无法修改门店");
                    break;
                }
                Intent intent = new Intent(EidtEmployeeInfoActivity.this, SelecteShopActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_select_position:
                Intent intent1 = new Intent(EidtEmployeeInfoActivity.this, PopupBottomActivity.class);
                intent1.putExtra("title","员工职位");
                intent1.putExtra("itemList",positionArray);
                intent1.putExtra("flag",false);
                startActivity(intent1);
                break;
            case R.id.btn_add_confirm:
                if (employeeName.getText().toString().trim().equals("")){
                    UIUtils.showToastSafesShort("所有信息为必填");
                    break;
                }
                HashMap<String,Object> map = new HashMap<>();
                map.put("parentId", pid);
                map.put("entityId", entityId);
                map.put("markCode",markCode);
                map.put("role",role);
                map.put("workName",employeeName.getText().toString().trim());
                map.put("employeesEntityId",id);
                loading("请稍后");
                RequestPresenter.getInstance().updateCustomer(map, new RequestCallback<UpdateCustomerBean>() {
                    @Override
                    protected void onSuc(UpdateCustomerBean body) {
                        loadingDimss();
                        UIUtils.showToastSafesShort("编辑成功");
                        finish();
                    }

                    @Override
                    public void onFailed(int errCode, String msg) {
                        loadingDimss();
                        UIUtils.showToastSafesShort("编辑失败");
                    }
                });
                break;
            default:
                break;
        }
    }
}
