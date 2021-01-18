package cn.com.taodaji.ui.activity.employeeManagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base.retrofit.RequestCallback;
import com.base.utils.DialogUtils;
import com.base.utils.UIUtils;
import com.base.utils.UserNameUtill;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.AddSubuserBean;
import cn.com.taodaji.model.entity.ShopEmployeeList;
import cn.com.taodaji.model.event.DeleteCustomeEvent;
import cn.com.taodaji.model.event.EmpoleeStoreList;
import cn.com.taodaji.model.event.SelectShopOrPositionEvent;
import cn.com.taodaji.model.presenter.RequestPresenter;
import tools.activity.SimpleToolbarActivity;

public class AddEmployeeActivity extends SimpleToolbarActivity implements View.OnClickListener {

    private View mainView;
    private LinearLayout selectShop,selectPosition;
    private TextView selectedShop,selectedPosition;
    private EditText employeeName,employeePhone;
    private Button confirm;

    private List<String> shopArray = new ArrayList<>();
    private ArrayList<ShopEmployeeList> shopEmployeeLists = new ArrayList<>();
    private String[] positionArray = {"管理员","店长","财务","厨师","员工"};
    private int position=-1, role=-1;
    private final String[] positionInfo = {
            "管理员：","所有权限(同创建人)\n",
            "(当为多个门店管理员时可创建总经办)\n",
            "店长：","所有权限(包括员工添加、编辑、删除)\n",
            "财务：","充值、付款、对账\n",
            "厨师：","下单、售后、退押、评价\n",
            "员工：","无任何操作权限"
    };
    private final String[] addSuccess = {"已添加成功，请提醒员工下载淘大集App，", "按手机短信提示信息操作。"};
    private final int[] index ={1,4,6,8,10};
    private final int[] tips ={0};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        loading("请稍后");
        RequestPresenter.getInstance().getEmpStoreList(PublicCache.loginPurchase.getLoginUserId(), new RequestCallback<EmpoleeStoreList>() {
            @Override
            protected void onSuc(EmpoleeStoreList body) {
                loadingDimss();
                for (EmpoleeStoreList.DataBean.ListBean bean : body.getData().getList()){
                    shopArray.add(bean.getEnterpriseCode());
                    ShopEmployeeList employeeList = new ShopEmployeeList();
                    employeeList.setPid(bean.getParentCustomerEntityId());
                    employeeList.setChain(bean.getEmpFlag()==1);
                    employeeList.setId(bean.getCustomerEntityId());
                    shopEmployeeLists.add(employeeList);
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                loadingDimss();
                UIUtils.showToastSafesShort(msg);
            }
        });
    }

    @Override
    public void titleSetting(Toolbar toolbar){
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("添加员工");
        right_icon.setImageResource(R.mipmap.position_information);
        right_icon_text.setText("说明");
        right_onclick_line.setOnClickListener(this);
    }

    @Override
    public void initMainView(){
        mainView = getLayoutView(R.layout.activity_add_employee);
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
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SelectShopOrPositionEvent event) {
        if (event.isFlag()){
            selectedShop.setText(event.getCurrentSelected());
            selectedShop.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            position = event.getPosition();
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
                DialogUtils.getInstance(AddEmployeeActivity.this).getTipsWithButtonDialog("职位说明",positionInfo,index).show();
                break;
            case R.id.rl_select_shop:
                Intent intent = new Intent(AddEmployeeActivity.this, PopupBottomActivity.class);
                intent.putExtra("title","选择门店");
                intent.putExtra("itemList",shopArray.toArray(new String[shopArray.size()]));
                intent.putExtra("flag",true);
                startActivity(intent);
                break;
            case R.id.rl_select_position:
                Intent intent1 = new Intent(AddEmployeeActivity.this, PopupBottomActivity.class);
                intent1.putExtra("title","员工职位");
                intent1.putExtra("itemList",positionArray);
                intent1.putExtra("flag",false);
                startActivity(intent1);
                break;
            case R.id.btn_add_confirm:
                loading("请稍候");
                if (!UserNameUtill.isPhoneNO(employeePhone.getText().toString().trim())){
                    UIUtils.showToastSafesShort("电话格式不正确");
                    loadingDimss();
                    return;
                }
                if (position>=0&&role>=0&&!employeeName.getText().toString().trim().equals("")){
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("parentId", shopEmployeeLists.get(position).getPid());
                    if (shopEmployeeLists.get(position).isChain){
                        map.put("isG", 1);
                        map.put("customerEntityId",shopEmployeeLists.get(position).getId());
                    }else {
                        map.put("isG", 0);
                    }
                    map.put("subAccountNo", employeePhone.getText().toString().trim());
                    map.put("role", role);
                    map.put("realName", employeeName.getText().toString().trim());
                    map.put("workName",employeeName.getText().toString().trim());

                RequestPresenter.getInstance().addSubUser(map, new RequestCallback<AddSubuserBean>() {
                    @Override
                    protected void onSuc(AddSubuserBean body) {
                        loadingDimss();
                        EventBus.getDefault().post(new DeleteCustomeEvent(2,body.getMsg()));
                        //DialogUtils.getInstance(AddEmployeeActivity.this).getTipsWithButtonDialog("提示",addSuccess,tips).show();
                        finish();
                    }

                    @Override
                    public void onFailed(int errCode, String msg) {
                        loadingDimss();
                        EventBus.getDefault().post(new DeleteCustomeEvent(3,msg));
                        //UIUtils.showToastSafesShort(msg);
                    }
                });}
                else {
                    loadingDimss();
                    UIUtils.showToastSafesShort("有内容为空");
                }
                break;
                default:
                    break;
        }
    }
}
