package cn.com.taodaji.ui.ppw;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.base.utils.ViewUtils;
import com.base.viewModel.adapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.InviteTypeBean;
import cn.com.taodaji.viewModel.adapter.InviteTypeListAdapter;

public  abstract class InviteTypePopupWindow extends BasePopupWindow implements PopupResultInterface{
    private RecyclerView recycleView;
    private Context mContext;
    private InviteTypeListAdapter adapter;
   // private String inviteStr[];
    private List<InviteTypeBean> list=new ArrayList<>();
    private TextView textView;
    public InviteTypePopupWindow(View mainView,Context context,String inviteStr[]) {
        super(mainView);
        textView=(TextView) mainView;
      //  mContext = context;
       // inviteStr=temp;

        if (inviteStr != null) {
            for (int i = 0; i <inviteStr.length ; i++) {
                InviteTypeBean bean=new InviteTypeBean();
                bean.setType(inviteStr[i]);
                list.add(bean);
            }
        }

        adapter.setList(list);
    }

    @Override
    public View createContextView(View mainView) {
        View view = ViewUtils.getLayoutView(mainView.getContext(), R.layout.popup_window_invite_type);
        recycleView = ViewUtils.findViewById(view, R.id.recycleView);
        recycleView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter=new InviteTypeListAdapter();
        recycleView.setAdapter(adapter);

        adapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {

                if (onclickType == 0) {
                    if (bean == null) {
                        return true;
                    }
                    InviteTypeBean typeBean=(InviteTypeBean)bean;
                    textView.setText(typeBean.getType());
                    setResult(position);
                    dismiss();
                }
                return false;
            }
        });


        return view;
    }

    @Override
    public boolean isTransparent() {
        return false;
    }
}
