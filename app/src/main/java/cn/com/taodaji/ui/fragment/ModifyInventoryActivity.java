package cn.com.taodaji.ui.fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.retrofit.RequestCallback;
import com.base.utils.ClickCheckedUtil;
import com.base.utils.UIUtils;
import com.base.widget.BusinessEditText;
import com.bumptech.glide.util.ByteBufferUtil;

import org.greenrobot.eventbus.EventBus;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.AddCategory;
import cn.com.taodaji.model.entity.ModifyInventory;
import cn.com.taodaji.model.entity.TakeUp;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.model.presenter.RequestPresenter2;

public class ModifyInventoryActivity extends AppCompatActivity {

    private Button cancel,confirm;
    private BusinessEditText stock;
    private TextView unit;
    private ModifyInventory modifyInventory = new ModifyInventory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_inventory);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //窗口对齐屏幕宽度
        Window win = this.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;//设置对话框置顶显示
        lp.alpha = 1.0f;
        win.setAttributes(lp);
        getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        initView();
    }

    public void initView(){
        cancel = (Button) findViewById(R.id.bt_cancel);
        confirm = (Button) findViewById(R.id.bt_add);
        stock = (BusinessEditText) findViewById(R.id.stock);
        unit = (TextView) findViewById(R.id.tv_inventory);
        int productId = getIntent().getIntExtra("id", 0);
        RequestPresenter2.getInstance().getSpecificationIdByEntityId(productId, new RequestCallback<ModifyInventory>() {
            @Override
            protected void onSuc(ModifyInventory body) {
                if (body.getErr()==0) {
                    modifyInventory = body;
                    stock.setText(body.getData().getStock() + "");
                    unit.setText(body.getData().getLevel_1_unit());
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafe(msg);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ClickCheckedUtil.onClickChecked(1000))
                if (modifyInventory.getData()==null){return;}
                RequestPresenter.getInstance().modifyInventory(modifyInventory.getData().getEntity_id(), productId, modifyInventory.getData().getPrice(), modifyInventory.getData().getLevel_2_value(), modifyInventory.getData().getLevel_3_value(), Integer.valueOf(stock.getText().toString().trim()), modifyInventory.getData().getAvg_price(),modifyInventory.getData().getLevel_type(),modifyInventory.getData().getLevel_1_unit(),modifyInventory.getData().getLevel_2_unit(),modifyInventory.getData().getLevel_3_unit(),modifyInventory.getData().getAvg_unit(),new RequestCallback<AddCategory>() {
                    @Override
                    protected void onSuc(AddCategory body) {
                        UIUtils.showToastSafe("修改成功");
                        EventBus.getDefault().post(new TakeUp());
                        finish();
                    }

                    @Override
                    public void onFailed(int errCode, String msg) {
                        UIUtils.showToastSafe(msg);
                    }
                });
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
