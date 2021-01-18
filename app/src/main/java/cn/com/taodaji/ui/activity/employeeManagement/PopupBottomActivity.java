package cn.com.taodaji.ui.activity.employeeManagement;

import android.content.Intent;
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
import cn.com.taodaji.model.event.SelectShopOrPositionEvent;
import cn.com.taodaji.viewModel.adapter.PopupBottomAdapter;

public class PopupBottomActivity extends AppCompatActivity {

    private TextView title,tips;
    private RecyclerView recyclerView;
    private PopupBottomAdapter adapter;
    private String[] stringsArrary = {""};
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
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, 800);

        initView();
    }

    public void  initView(){
        title = (TextView)findViewById(R.id.tv_popup_title);
        tips = (TextView)findViewById(R.id.tv_tips);
        recyclerView = (RecyclerView)findViewById(R.id.rv_popup_list);
        if (getIntent().getStringArrayExtra("itemList").length!=0){
            stringsArrary = getIntent().getStringArrayExtra("itemList");
        }
        if (getIntent().getBooleanExtra("isLeader",false)){
            tips.setVisibility(View.VISIBLE);
        }
        title.setText(getIntent().getStringExtra("title"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        adapter = new PopupBottomAdapter(stringsArrary,this);
        recyclerView.setAdapter(adapter);
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (getIntent().getBooleanExtra("flag",false)){
                    EventBus.getDefault().post(new SelectShopOrPositionEvent(stringsArrary[position],true,position));
                }else {
                    switch (position){
                        case 0:
                            EventBus.getDefault().post(new SelectShopOrPositionEvent(stringsArrary[position],false,3));
                            break;
                        case 1:
                            EventBus.getDefault().post(new SelectShopOrPositionEvent(stringsArrary[position],false,4));
                            break;
                        case 2:
                            EventBus.getDefault().post(new SelectShopOrPositionEvent(stringsArrary[position],false,2));
                            break;
                        case 3:
                            EventBus.getDefault().post(new SelectShopOrPositionEvent(stringsArrary[position],false,1));
                            break;
                        case 4:
                            EventBus.getDefault().post(new SelectShopOrPositionEvent(stringsArrary[position],false,5));
                            break;
                            default:break;
                    }
                }
                finish();
            }
        });
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        finish();
        return true;
    }
}
