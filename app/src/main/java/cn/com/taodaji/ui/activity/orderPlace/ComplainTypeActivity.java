package cn.com.taodaji.ui.activity.orderPlace;

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

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.model.entity.ProblemItem;
import cn.com.taodaji.model.event.ComplaintEvent;
import cn.com.taodaji.viewModel.adapter.ComplaintTypeAdapter;
import cn.com.taodaji.viewModel.adapter.PopupBottomAdapter;

public class ComplainTypeActivity extends AppCompatActivity {

    private TextView title;
    private RecyclerView recyclerView;
    private ComplaintTypeAdapter adapter;
    private List<ProblemItem> itemList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_type);

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
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, 800);

        initView();
    }

    public void initView() {
        title = (TextView) findViewById(R.id.tv_popup_title);
        recyclerView = (RecyclerView) findViewById(R.id.rv_popup_list);
        itemList =( ArrayList<ProblemItem> )getIntent().getSerializableExtra("itemList");
        title.setText(getIntent().getStringExtra("title"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new ComplaintTypeAdapter( this,itemList);
        recyclerView.setAdapter(adapter);
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                EventBus.getDefault().post(new ComplaintEvent(itemList.get(position).getText(),itemList.get(position).getId()));
                finish();
            }
        });
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }
}
