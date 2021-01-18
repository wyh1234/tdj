package cn.com.taodaji.ui.activity.login;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.taodaji.R;
import cn.com.taodaji.ui.activity.integral.popuwindow.RemarkPopuWindow;
import tools.activity.DataBaseActivity;
import tools.statusbar.Eyes;

public class SelectRegisterActivity extends DataBaseActivity {
    @BindView(R.id.tv_right)
     TextView tv_right;
    @BindView(R.id.rl_gr)
     RelativeLayout rl_gr;
    @BindView(R.id.rl_qy)
     RelativeLayout rl_qy;
    @BindView(R.id.rl_vip)
    RelativeLayout rl_vip;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    private String login_flag;//采购商或者供应商的标志
    private RemarkPopuWindow remarkPopuWindow;
    @OnClick({R.id.tv_right,R.id.rl_gr,R.id.rl_qy,R.id.btn_back,R.id.rl_vip})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.rl_gr:
                Intent register = new Intent(this, RegisterPurchaserSecondActivity.class);
                register.putExtra(FLAG, login_flag);
                register.putExtra("type",1);
                startActivity(register);
                break;
            case R.id.rl_qy:
                Intent register1 = new Intent(this, RegisterPurchaserFirstActivity.class);
                register1.putExtra(FLAG, login_flag);
                register1.putExtra("type",2);
                startActivity(register1);
                break;
            case R.id.rl_vip:
                Intent register_vip = new Intent(this, RegisterPurchaserSecondActivity.class);
                register_vip.putExtra(FLAG, login_flag);
                register_vip.putExtra("type",1);
                register_vip.putExtra("vip","vip");
                startActivity(register_vip);
                break;
            case R.id. tv_right:
                if (remarkPopuWindow!=null){
                    if (remarkPopuWindow.isShowing()){
                        return;
                    }

                }
                remarkPopuWindow = new RemarkPopuWindow(this);
                remarkPopuWindow.setDismissWhenTouchOutside(false);
                remarkPopuWindow.setInterceptTouchEvent(false);
                remarkPopuWindow.setPopupWindowFullScreen(true);//铺满
                remarkPopuWindow.showPopupWindow();
                break;
        }
    }
    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.sel_register_layout);
    }
    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        Eyes.setStatusBarColor(this, UIUtils.getColor(this, R.color.white));
        Eyes.setLightStatusBar(this,true);//设置状态栏字体颜色
    }

}
