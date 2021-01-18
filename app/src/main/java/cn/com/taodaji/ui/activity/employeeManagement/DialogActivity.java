package cn.com.taodaji.ui.activity.employeeManagement;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.base.retrofit.RequestCallback;
import com.base.utils.DialogUtils;
import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.DeleteCustomerBean;
import cn.com.taodaji.model.entity.ShopEmployeeItem;
import cn.com.taodaji.model.event.DeleteCustomeEvent;
import tools.activity.DataBaseActivity;

public class DialogActivity extends DataBaseActivity implements View.OnClickListener {

    private Button first,second,third;//1.拨打电话 2.编辑员工信息 3.删除
    private String[] tips = {"该账号删除后，将与本店无任何关联，是否确认删除？","",""};
    private int[] index = {2};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_popwindow_bottom);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //窗口对齐屏幕宽度
        Window win = this.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;//设置对话框置顶显示
        lp.alpha = 0.8f;
        win.setAttributes(lp);
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        first = (Button) findViewById(R.id.btn_first);
        second = (Button) findViewById(R.id.btn_second);
        third = (Button) findViewById(R.id.btn_third);
        first.setOnClickListener(this);
        second.setOnClickListener(this);
        third.setOnClickListener(this);
    }

    @Override
    protected View getContentLayout() {
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = inflater.inflate(R.layout.layout_popwindow_bottom, null);
        return child;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        finish();
        return true;
    }

    @Override
    public void onClick(View view){
        ShopEmployeeItem item = getIntent().getParcelableExtra("phone");
        switch (view.getId()){
            case R.id.btn_first:
                if (item.getPhone()==null){
                    UIUtils.showToastSafesShort("该员工电话为空");
                }else {
                    SystemUtils.callPhone(DialogActivity.this, item.getPhone());
                    finish();
                }
                break;
            case R.id.btn_second:
                if (PublicCache.loginPurchase.getEmpRole() ==0||PublicCache.loginPurchase.getEmpRole()==3||PublicCache.loginPurchase.getEmpRole()==4){
                Intent intent = new Intent(DialogActivity.this,EidtEmployeeInfoActivity.class);
                intent.putExtra("edit",item);
                startActivity(intent);
                finish(); }
                else {
                    UIUtils.showToastSafesShort("您没有此操作权限");
                }
                break;
            case R.id.btn_third:
                if (PublicCache.loginPurchase.getEmpRole()==0||PublicCache.loginPurchase.getEmpRole()==3||PublicCache.loginPurchase.getEmpRole()==4) {
                    DialogUtils.getInstance(DialogActivity.this).getSimpleColorDialog("",tips,index).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            loading("请稍后");
                            HashMap<String,Object> map = new HashMap<>();
                            map.put("parentId",item.getPid());
                            map.put("entityId",item.getId());
                            getRequestPresenter().deleteCustomer(map, new RequestCallback<DeleteCustomerBean>() {
                                @Override
                                protected void onSuc(DeleteCustomerBean body) {
                                    EventBus.getDefault().post(new DeleteCustomeEvent(1,"成功"));
                                    loadingDimss();
                                }

                                @Override
                                public void onFailed(int errCode, String msg) {
                                    EventBus.getDefault().post(new DeleteCustomeEvent(0,msg));
                                    loadingDimss();
                                }
                            });
                            finish();
                        }
                    },R.color.red_dark).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }else {
                    UIUtils.showToastSafesShort("您没有此操作权限");
                }
                break;
                default:break;
        }
    }
}
