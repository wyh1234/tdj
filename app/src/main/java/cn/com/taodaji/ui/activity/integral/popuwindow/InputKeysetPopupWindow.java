package cn.com.taodaji.ui.activity.integral.popuwindow;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.base.activity.BaseActivity;
import com.base.utils.ADInfo;
import com.base.utils.ClickCheckedUtil;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.LoginMethod;
import cn.com.taodaji.model.event.CartEvent;
import cn.com.taodaji.viewModel.vm.ADInfoViewModel;

public class InputKeysetPopupWindow extends PopupWindow {
    private TextView input_count;
    private SingleRecyclerViewAdapter myRecyclerViewAdapter;


    private String[] custom = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "删除", "0", "确定"};
    private List<ADInfo> list_custom = new ArrayList<>();

    private CartEvent cartEvent;
    private int itemposition;
    private int pos;

    private InputKeysetPopuWindowListener listener;
    public void setInputKeysetPopuWindowListener(InputKeysetPopuWindowListener listener) {
        this.listener = listener;
    }
    public interface InputKeysetPopuWindowListener {
        void button_1(int num,int pos);

    }

    public InputKeysetPopupWindow(Context context,int quantity,int pos) {
        this.pos=pos;

        getData(list_custom, custom);
        final View view = ViewUtils.getLayoutView(context, R.layout.popup_window_input_keyset);
        input_count = ViewUtils.findViewById(view, R.id.input_count);
        RecyclerView input_recycler = ViewUtils.findViewById(view, R.id.input_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        input_recycler.setLayoutManager(gridLayoutManager);
        input_count.setText(quantity+"");
        myRecyclerViewAdapter = new SingleRecyclerViewAdapter() {
            @Override
            public void initBaseVM() {
                putBaseVM(TYPE_CHILD, new ADInfoViewModel());
            }

            @Override
            public View onCreateHolderView(ViewGroup parent, int viewType) {
                return ViewUtils.getFragmentView(parent, R.layout.item_popup_window_input_keyset);
            }

            @Override
            public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
                super.onBindViewHolderCustomer(holder, position);
                TextView textView = holder.findViewById(R.id.image_name);
                switch (position) {
                    case 11:
                        textView.setBackgroundResource(R.drawable.r_round_rect_solid_orange_ff7d01);
                        textView.setTextColor(UIUtils.getColor(R.color.white));
                        break;
                    default:
                        textView.setBackgroundResource(R.drawable.s_goods_count_input_keyset);
                        textView.setTextColor(UIUtils.getColor(R.color.black_63));
                        break;
                }
            }

            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object itemBean) {
                if (onclickType == 0) {
                    ADInfo bean = (ADInfo) itemBean;
                    String ss = input_count.getText().toString().trim();
                    switch (position) {
                        case 9:
                            if (ss.length() == 1) input_count.setText("0");
                            else input_count.setText(ss.substring(0, ss.length() - 1));
                            return true;
                        case 11:
//                            if (ss.equals("0")) {
//                                UIUtils.showToastSafesShort("选购数量不能为0");
//                                return true;
//                            }
                            listener.button_1(Integer.parseInt(ss),pos);
                            dismiss();
                            return true;
                        default:
                            if (ss.equals("0")) input_count.setText(bean.getImageName());
                            else {
                                input_count.setText(ss + bean.getImageName());
                            }

                            return true;
                    }
                }
                return super.onItemClick(view, onclickType, position, itemBean);
            }
        };

        input_recycler.setAdapter(myRecyclerViewAdapter);
        myRecyclerViewAdapter.notifyDataSetChanged(list_custom);


        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ActionBar.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ActionBar.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottomPopupWindow);
        ColorDrawable dw = new ColorDrawable(UIUtils.getColor(android.R.color.transparent));
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                View popu = view.findViewById(R.id.popup_window);
                int top = popu.getTop();
                int bottom = popu.getBottom();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < top || y > bottom) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }
    private void getData(List<ADInfo> list, String[] sst) {
        for (String ss : sst) {
            ADInfo adInfo = new ADInfo();
            adInfo.setImageName(ss);
            list.add(adInfo);
        }
    }

}
