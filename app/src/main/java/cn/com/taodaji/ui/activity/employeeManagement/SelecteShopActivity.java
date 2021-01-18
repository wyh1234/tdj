package cn.com.taodaji.ui.activity.employeeManagement;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.base.retrofit.RequestCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.EmployeesListBean;
import cn.com.taodaji.model.entity.HotelList;
import cn.com.taodaji.model.entity.ShopAddressItem;
import cn.com.taodaji.model.entity.ShopEmployeeItem;
import cn.com.taodaji.model.entity.ShopEmployeeList;
import cn.com.taodaji.model.event.EmpoleeStoreList;
import cn.com.taodaji.model.event.SelectShopOrPositionEvent;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.viewModel.adapter.PopupBottomAdapter;
import tools.activity.DataBaseActivity;

public class SelecteShopActivity extends AppCompatActivity {
    private TextView title;
    private RecyclerView recyclerView;
    private PopupBottomAdapter adapter;
    private List<String> stringList = new ArrayList<>();
    private List<ShopEmployeeList> employeeLists = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_bottom);

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

        initView();
    }

    public void  initView(){
        title = (TextView)findViewById(R.id.tv_popup_title);
        recyclerView = (RecyclerView)findViewById(R.id.rv_popup_list);
        title.setText("选择门店");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        adapter = new PopupBottomAdapter(this,stringList);
        recyclerView.setAdapter(adapter);
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                SelectShopOrPositionEvent event = new SelectShopOrPositionEvent();
                event.setCurrentSelected(employeeLists.get(position).getTitle());
                event.setFlag(true);
                event.setPosition(employeeLists.get(position).getId());
                event.setMarkCode(employeeLists.get(position).getMarkCode());
                EventBus.getDefault().post(event);
                finish();
            }
        });
        iniData();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        finish();
        return true;
    }


    public void iniData() {
        employeeLists.clear();
        stringList.clear();
        RequestPresenter.getInstance().getEmpStoreList(PublicCache.loginPurchase.getLoginUserId(), new RequestCallback<EmpoleeStoreList>() {
            @Override
            protected void onSuc(EmpoleeStoreList body) {
                for (EmpoleeStoreList.DataBean.ListBean bean : body.getData().getList()) {
                    ShopEmployeeList item = new ShopEmployeeList();
                    item.setTitle(bean.getEnterpriseCode());
                    item.setId(bean.getCustomerEntityId());
                    item.setMarkCode(bean.getMarkCode());
                    stringList.add(bean.getEnterpriseCode());
                    employeeLists.add(item);
                   // Log.i("zhao", "onSuc: "+stringList.size());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int errCode, String msg) {
                super.onFailed(errCode, msg);
            }
        });
    }
}
