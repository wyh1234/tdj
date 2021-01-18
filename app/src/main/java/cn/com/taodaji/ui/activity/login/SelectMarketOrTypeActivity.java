package cn.com.taodaji.ui.activity.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.retrofit.RequestCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.ShopEmployeeList;
import cn.com.taodaji.model.event.EmpoleeStoreList;
import cn.com.taodaji.model.event.MarketOrTypeEvent;
import cn.com.taodaji.model.event.SelectShopOrPositionEvent;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.viewModel.adapter.PopupBottomAdapter;

public class SelectMarketOrTypeActivity extends AppCompatActivity {
    private TextView title,tips;
    private RecyclerView recyclerView;
    private PopupBottomAdapter adapter;
    private List<String> stringList = new ArrayList<>();
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
        stringList = getIntent().getStringArrayListExtra("itemList");
        initView();
    }

    public void  initView(){
        tips = (TextView)findViewById(R.id.tv_tips);
        tips.setVisibility(View.GONE);
        title = (TextView)findViewById(R.id.tv_popup_title);
        recyclerView = (RecyclerView)findViewById(R.id.rv_popup_list);
        title.setText(getIntent().getStringExtra("title"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        adapter = new PopupBottomAdapter(this,stringList);
        recyclerView.setAdapter(adapter);
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                MarketOrTypeEvent event = new MarketOrTypeEvent();
                event.setFlag(getIntent().getBooleanExtra("flag",false));
                event.setPosition(position);
                EventBus.getDefault().post(event);
                finish();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        finish();
        return true;
    }
}
