package cn.com.taodaji.ui.activity.myself;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.com.taodaji.R;

import cn.com.taodaji.model.entity.MyselftUpdateP;
import cn.com.taodaji.model.event.PersonInfoChangeEvent;
import cn.com.taodaji.ui.activity.ocr.RealNamePurchaserAuthenticationActivity;
import tools.activity.SimpleToolbarActivity;

public class IdentityManageActivity extends SimpleToolbarActivity implements View.OnClickListener {

    private TextView text_real_name, text_identity_number, text_picture_status;
    //  private String cardno = "0";//是否实名认证
    private MyselftUpdateP.DataBean bean;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("身份管理");
    }

    @Override
    protected void initMainView() {

        View mainView = getLayoutView(R.layout.activity_identity_manage);
        body_scroll.addView(mainView);

        ViewUtils.findViewById(mainView, R.id.ll_real_name).setOnClickListener(this);
        //ViewUtils.findViewById(mainView, R.id.ll_identity_number).setOnClickListener(this);
        ViewUtils.findViewById(mainView, R.id.ll_picture_status).setOnClickListener(this);

        text_real_name = ViewUtils.findViewById(mainView, R.id.text_real_name);
        text_identity_number = ViewUtils.findViewById(mainView, R.id.text_identity_number);
        text_picture_status = ViewUtils.findViewById(mainView, R.id.text_picture_status);

        bean=(MyselftUpdateP.DataBean)getIntent().getExtras().getSerializable("bean");
        initViewData();
    }

    //实名认证完成后通知
    @Subscribe(sticky = true)
    public void onEvent(PersonInfoChangeEvent event) {
        //ben==true,点击事件变为 设置默认银行卡
        EventBus.getDefault().removeStickyEvent(event);
        bean=event.getBean();
        initViewData();
    }
    private  void initViewData(){
        if (bean != null) {
            text_real_name.setText(bean.getRealname());
            text_identity_number.setText(bean.getIdentificationCard());
            String data=bean.getExpirationDate();
            //text_picture_status.setText(data);//已过期跳转实名认证，未过期显示已实名不可点击 "expirationDate": "20250428", frontageIdcardImageUrl backIdcardImageUrl

            text_picture_status.setText(getDateStyle(data));
        }
    }
    private String getDateStyle(String date) {
        if (TextUtils.isEmpty(date)) {
            return "已过期";
        }
        if (date.equals("长期")) {
            return "已实名";
        } else if (date.length() < 7) {
            return "已过期";
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");// HH:mm:ss
            Date date2 = new Date(System.currentTimeMillis());
            int now=Integer.parseInt(simpleDateFormat.format(date2));
            if (Integer.parseInt(date)>=now ) {
                date= "已实名";
            }else{
                date= "已过期";
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            date= "已过期";
        }
        return date;
    }
    private void initDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(getBaseActivity(), R.layout.dialog_real_name_purchaser_edit, null);


        EditText edit_content=view.findViewById(R.id.edit_content);
        TextView text_left=view.findViewById(R.id.text_left);
        TextView text_right=view.findViewById(R.id.text_right);

        builder.setView(view);
        builder.setCancelable(false);
//        TextView title = (TextView) view.findViewById(R.id.title);//设置标题
//        Button btn_comfirm = (Button) view.findViewById(R.id.real_name_authentication_dialog_button);//确定按钮
        final AlertDialog dialog = builder.create();
//                取消或确定按钮监听事件处理
        text_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        text_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_real_name.setText(edit_content.getText().toString());
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_real_name:{
                //initDialog();
                Intent intent=new Intent(this, RealNamePurchaserAuthenticationActivity.class);
                intent.putExtra("bean",bean);
                startActivity(intent);
                break;}
            case R.id.ll_picture_status:{
                Intent intent=new Intent(this, RealNamePurchaserAuthenticationActivity.class);
                intent.putExtra("bean",bean);
                startActivity(intent);
                break;}
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
