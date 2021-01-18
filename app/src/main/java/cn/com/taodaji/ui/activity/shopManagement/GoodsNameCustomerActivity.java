package cn.com.taodaji.ui.activity.shopManagement;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.listener.UploadPicturesDoneListener;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;

import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.AddCategory;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.ui.ppw.PopupResultInterface;
import tools.extend.SerializableMap;
import tools.fragment.AddedPicturesFragment;

public class GoodsNameCustomerActivity extends AppCompatActivity implements UploadPicturesDoneListener, View.OnClickListener {

    private EditText title,content;
    private Button confirm,close;
    private AddedPicturesFragment addedPicturesFragment;
    private Map<String, Object> map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_window_goods_nameadd);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //窗口对齐屏幕宽度
        Window win = this.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;//设置对话框置顶显示
        lp.alpha = 0.8f;
        win.setAttributes(lp);
        getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        initView();
    }

    public void  initView(){
        close = (Button) findViewById(R.id.popupWindow_close);
        close.setOnClickListener(this);
        confirm = (Button) findViewById(R.id.goods_price_change_OK);
        confirm.setOnClickListener(this);
        title = (EditText) findViewById(R.id.goods_name);
        content = (EditText) findViewById(R.id.goods_description);
        addedPicturesFragment = (AddedPicturesFragment) getSupportFragmentManager().findFragmentById(R.id.addedPicturesFragment);
        //addedPicturesFragment.setBackgroundColor(R.color.white);
        addedPicturesFragment.setMaxSelect(3);
        Bundle bundle = getIntent().getExtras();
        SerializableMap serializableMap = (SerializableMap) bundle.get("map");
        map = serializableMap.getMap();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.popupWindow_close:
                finish();
                break;
            case R.id.goods_price_change_OK:
                if (title.getText().length() == 0) {
                    UIUtils.showToastSafesShort("请输入商品名称");
                    return;
                }
                map.put("name",title.getText().toString().trim());
                map.put("description",content.getText().toString().trim());
                map.put("img_url",addedPicturesFragment.getImageStr());
                RequestPresenter.getInstance().addCategory(map, new RequestCallback<AddCategory>() {
                    @Override
                    public void onSuc(AddCategory body) {
                        UIUtils.showToastSafesShort("添加成功");
                        setResult();
                        finish();
                    }

                    @Override
                    public void onFailed(int addCategory, String msg) {
                        UIUtils.showToastSafesShort(msg);
                    }
                });
                break;
                default:break;
        }
    }

    @Override
    public void uploadPicturesIsDone(Object obj) {
        if (isDestroyed()) return;
    }

    public void setResult(){
        this.setResult(101);
    }
}