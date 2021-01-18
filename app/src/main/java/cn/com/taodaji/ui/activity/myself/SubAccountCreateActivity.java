package cn.com.taodaji.ui.activity.myself;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;

import cn.com.taodaji.model.entity.SubUserCreate;

import java.util.HashMap;
import java.util.Map;

import com.base.retrofit.RequestCallback;
import tools.activity.SimpleToolbarActivity;
import com.base.utils.UIDataProcessing;
import com.base.utils.UIUtils;
import com.base.utils.UserNameUtill;
import com.base.utils.ViewUtils;

public class SubAccountCreateActivity extends SimpleToolbarActivity implements View.OnClickListener {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("添加子账号");
    }

    private View mainView;
    //private EditText editText3;
    // private EditText editText4;
    private String parentId;

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_myself_sub_account_create);
        body_scroll.addView(mainView);
        //  editText3 = ViewUtils.findViewById(mainView, R.id.editText3);
        //  editText4 = ViewUtils.findViewById(mainView, R.id.editText4);
    }

    @Override
    protected void initData() {
        //   parentId = getIntent().getStringExtra("parentId");

    }

    @Override
    public void onClick(View view) {
        final Map<String, Object> map = new HashMap<>();
        if (!UIDataProcessing.getChildControlsValue(mainView, map)) {
            return;
        }

        for (Object key : map.keySet()) {
            Object v = map.get(key);
            if (key.equals("subAccountNo")) {
                if (!UserNameUtill.isPhoneNO(v.toString())) {
                    UIUtils.showToastSafesShort("手机号码格式不正确");
                    return;
                }

            } else if (key.equals("role") && v.toString().compareTo("3") == 0 && PublicCache.loginPurchase.getEmpRole() != 0) {
                UIUtils.showToastSafe("您没有权限创建管理员");
                return;
            }

        }


        loading();
        map.put("parentId", PublicCache.loginPurchase.getEntityId());
        map.put("isActive", 1);
        // map.put("subPassword", "123456");

        getRequestPresenter().subUserCreate(map, new RequestCallback<SubUserCreate>() {


            @Override
            public void onSuc(SubUserCreate body) {
                loadingDimss();
                AlertDialog.Builder builder = ViewUtils.showDialog(getBaseActivity(), "提示", "创建的子账号已发送到该用户手机短信中，请通知该用户按短信提示激活该账户。");
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        setResult(RESULT_OK);
                        finish();
                    }
                });
                builder.create().show();
            }

            @Override
            public void onFailed(int subUserCreate, String msg) {
                loadingDimss();
                AlertDialog.Builder builder = ViewUtils.showDialog(getBaseActivity(), "错误提示", msg);
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
    }

}
