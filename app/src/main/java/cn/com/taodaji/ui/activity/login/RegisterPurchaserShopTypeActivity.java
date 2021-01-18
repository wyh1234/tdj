package cn.com.taodaji.ui.activity.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.retrofit.RequestCallback;
import com.base.utils.DialogUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.adapter.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.ShopTypeBean;
import cn.com.taodaji.model.entity.ShopTypeListResultBean;
import cn.com.taodaji.model.event.ShopTypeSearchListEvent;
import cn.com.taodaji.model.event.ShopTypeSelectEvent;
import cn.com.taodaji.model.event.ShopTypeSelectListEvent;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.ui.activity.myself.ShopTypeSearchActivity;
import cn.com.taodaji.ui.view.FlowViewGroup;
import cn.com.taodaji.viewModel.adapter.RegisterPurchaserShopTypeAdapter;
import tools.activity.SimpleToolbarActivity;

public class RegisterPurchaserShopTypeActivity extends SimpleToolbarActivity implements View.OnClickListener {
    private View mainView;
    private RecyclerView recycleView_type1, recycleView_type2, recycleView_type3;

    private RegisterPurchaserShopTypeAdapter adapter1, adapter2, adapter3;
//    private RegisterPurchaserShopTypeBottomAdapter bottomAdapter;

//    public LoadMoreUtil loadMoreUtil1, loadMoreUtil2, loadMoreUtil3;

//    private List<ShopTypeBean> typeList1 = new ArrayList<>();0
//    private List<ShopTypeBean> typeList2 = new ArrayList<>();
//    private List<ShopTypeBean> typeList3 = new ArrayList<>();

    private List<ShopTypeBean> selectedList = new ArrayList<>();

    private FlowViewGroup flowViewGroup;

    private List<ShopTypeBean> list;

    private List<ShopTypeBean> oldSelectedList;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("门店类型");
        right_icon_text.setText("保存");
        right_icon_text.setTextSize(18);
        right_onclick_line.setOnClickListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_register_purchaser_shop_type);
        body_other.addView(mainView);

        recycleView_type1 = ViewUtils.findViewById(mainView, R.id.recycleView_type1);
        recycleView_type2 = ViewUtils.findViewById(mainView, R.id.recycleView_type2);
        recycleView_type3 = ViewUtils.findViewById(mainView, R.id.recycleView_type3);

        LinearLayout line_search = ViewUtils.findViewById(mainView, R.id.line_search);
        line_search.setOnClickListener(this);

        recycleView_type1.setLayoutManager(new LinearLayoutManager(this));
        //recycleView_type1.addItemDecoration(new DividerItemDecoration(this));
        recycleView_type1.setBackgroundColor(UIUtils.getColor(R.color.gray_f3));

        recycleView_type2.setLayoutManager(new LinearLayoutManager(this));
        // recycleView_type2.addItemDecoration(new DividerItemDecoration(this));
        recycleView_type2.setBackgroundColor(UIUtils.getColor(R.color.gray_f7));

        recycleView_type3.setLayoutManager(new LinearLayoutManager(this));
        //recycleView_type3.addItemDecoration(new DividerItemDecoration(this));
        recycleView_type3.setBackgroundColor(UIUtils.getColor(R.color.white));

        //ShopTypeBean

        adapter1 = new RegisterPurchaserShopTypeAdapter();
        adapter1.setType(1);
        adapter1.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType == 0) {
                    switch (view.getId()) {
                        case R.id.item_view:
                            ShopTypeBean tempBean = (ShopTypeBean) bean;

                            if (tempBean.isAdd() || tempBean.isCheck()) {
                                return true;
                            }

                            List<ShopTypeBean> tempList = adapter1.getList();
                            if (tempBean.isSingle()) {

                                if (selectedList.size() >= 5) {
                                    // tempList.get(position).setCheck(true);
                                    UIUtils.showToastSafesShort("最多只能选择5种类型。");
                                    return true;
                                }

                                adapter2.clearAll();
                                adapter3.clearAll();

                                if (!tempBean.isCheck() && !tempBean.isAdd()) {
                                    //tempBean.setAdd(true);
                                    for (int i = 0; i < tempList.size(); i++) {
                                        tempList.get(i).setCheck(false);
                                    }
                                    tempList.get(position).setAdd(true);
                                    addSelectedView(tempBean, position, adapter1);
                                    adapter1.notifyDataSetChanged();
                                    //adapter1.setSelected(position);
                                } else {
                                    if (tempBean.isAdd()) {
                                        for (int i = 0; i < tempList.size(); i++) {
                                            tempList.get(i).setCheck(false);
                                        }
                                        adapter1.notifyDataSetChanged();
                                    }
                                }
                            } else {
                                if (!tempBean.isAdd()) {
                                    // adapter1.setSelected(position);

                                    for (int i = 0; i < tempList.size(); i++) {
                                        tempList.get(i).setCheck(false);
                                    }
                                    tempList.get(position).setCheck(true);
                                    adapter1.notifyDataSetChanged();

                                    for (int i = 0; i < tempBean.getChildren().size(); i++) {
                                        tempBean.getChildren().get(i).setCheck(false);
                                    }

                                    if (tempBean.getChildren().get(0).isSingle()) {
                                        adapter2.setList(tempBean.getChildren());
                                        // adapter2.setSelectedCancel(0);
                                        adapter3.clearAll();
                                    } else {
                                        tempBean.getChildren().get(0).setCheck(true);
                                        adapter2.setList(tempBean.getChildren());
                                        adapter3.setList(tempBean.getChildren().get(0).getChildren());
                                        // adapter3.setSelectedCancel(0);
                                    }
                                }
                            }
                            break;
                    }
                }
                return false;
            }
        });
        // adapter1.setSelectFieldName("selected");
        //adapter1.setRadio(true);
        recycleView_type1.setAdapter(adapter1);
//        loadMoreUtil1 = new LoadMoreUtil(this, recycleView_type1);
//        loadMoreUtil1.setHasLoadMore(false);


        adapter2 = new RegisterPurchaserShopTypeAdapter();
        adapter2.setType(2);
        adapter2.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType == 0) {
                    switch (view.getId()) {
                        case R.id.item_view:
                            ShopTypeBean tempBean = (ShopTypeBean) bean;
                            if (tempBean.isAdd() || tempBean.isCheck()) {
                                return true;
                            }
                            List<ShopTypeBean> tempList = adapter2.getList();
                            if (tempBean.isSingle()) {
                                if (selectedList.size() >= 5) {
                                    //tempList.get(position).setCheck(true);
                                    UIUtils.showToastSafesShort("最多只能选择5种类型。");
                                    return true;
                                }
                                adapter3.clearAll();
                                if (!tempBean.isCheck() && !tempBean.isAdd()) {
                                    for (int i = 0; i < tempList.size(); i++) {
                                        tempList.get(i).setCheck(false);
                                    }
                                    tempList.get(position).setAdd(true);
                                    addSelectedView(tempBean, position, adapter2);
                                    adapter2.notifyDataSetChanged();
                                } else {
                                    if (tempBean.isAdd()) {
                                        for (int i = 0; i < tempList.size(); i++) {
                                            tempList.get(i).setCheck(false);
                                        }
                                        adapter2.notifyDataSetChanged();
                                    }
                                }
                            } else {
                                if (!tempBean.isAdd()) {
//                                    List<ShopTypeBean> tempList=adapter2.getList();
                                    for (int i = 0; i < tempList.size(); i++) {
                                        tempList.get(i).setCheck(false);
                                    }
                                    tempList.get(position).setCheck(true);
                                    adapter2.notifyDataSetChanged();
                                    adapter3.setList(tempBean.getChildren());
                                }
                                // adapter3.setSelected(0);
                            }
                            break;
                    }
                }
                return false;
            }
        });
        // adapter2.setSelectFieldName("selected");
        //adapter2.setRadio(true);
        recycleView_type2.setAdapter(adapter2);
//        loadMoreUtil2 = new LoadMoreUtil(this, recycleView_type2);
//        loadMoreUtil2.setHasLoadMore(false);

        adapter3 = new RegisterPurchaserShopTypeAdapter();
        adapter3.setType(3);
        adapter3.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType == 0) {
                    switch (view.getId()) {
                        case R.id.item_view:
                            ShopTypeBean tempBean = (ShopTypeBean) bean;

                            if (tempBean.isAdd() || tempBean.isCheck()) {
                                return true;
                            }

                            if (selectedList.size() >= 5) {
                                //tempList.get(position).setCheck(true);
                                UIUtils.showToastSafesShort("最多只能选择5种类型");
                                return true;
                            }

                            if (!tempBean.isCheck() && !tempBean.isAdd()) {
                                tempBean.setAdd(true);
                                List<ShopTypeBean> tempList = adapter3.getList();
                                for (int i = 0; i < tempList.size(); i++) {
                                    tempList.get(i).setCheck(false);
                                }
                                adapter3.notifyDataSetChanged();
                                addSelectedView(tempBean, position, adapter3);
                            }
                            break;
                    }
                }
                return false;
            }
        });
        //adapter3.setSelectFieldName("selected");
        //adapter3.setRadio(false);
        recycleView_type3.setAdapter(adapter3);
//        loadMoreUtil3 = new LoadMoreUtil(this, recycleView_type3);
//        loadMoreUtil3.setHasLoadMore(false);

        initBottomData();


    }

    private void addSelectedView(ShopTypeBean bean, int position, RegisterPurchaserShopTypeAdapter adapter) {
        if (body_bottom.getVisibility() == View.GONE) {
            body_bottom.setVisibility(View.VISIBLE);
        }


        View itemView = ViewUtils.getLayoutView(this, R.layout.item_bottom_register_purchaser_shop_type);
        TextView text_name = ViewUtils.findViewById(itemView, R.id.text_name);
        text_name.setText(bean.getName());
        ImageView img_delete = ViewUtils.findViewById(itemView, R.id.img_delete);
        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedList.remove(bean);
                flowViewGroup.removeView(itemView);
                if (flowViewGroup.getChildCount() == 0) {
                    body_bottom.setVisibility(View.GONE);
                }
                bean.setAdd(false);
                bean.setCheck(false);
                adapter.notifyItemChanged(position);
                //adapter.setSelectedCancel(position);
            }
        });
        flowViewGroup.addView(itemView);
        selectedList.add(bean);
    }

    private void initBottomData() {

        View bottomView = ViewUtils.getLayoutView(this, R.layout.part_bottom_register_shop_type);
        flowViewGroup = ViewUtils.findViewById(bottomView, R.id.flowViewGroup);

        body_bottom.addView(bottomView);
        body_bottom.setVisibility(View.GONE);
        body_bottom.setBackgroundResource(R.color.white);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.right_onclick_line: {
                save();
                break;
            }
            case R.id.line_search: {
                EventBus.getDefault().postSticky(new ShopTypeSearchListEvent(list));
                Intent intent = new Intent(RegisterPurchaserShopTypeActivity.this, ShopTypeSearchActivity.class);
               // intent.putExtra("searchList", (Serializable) list);
                startActivity(intent);
                break;
            }

        }
    }

    private void save(){

        String tips="";
        String cancel = "";
        String ok = "";

        if (oldSelectedList==null) {
            tips = UIUtils.getString(R.string.tips_shop_type_content_new);
            cancel = UIUtils.getString(R.string.tips_shop_type_cancel_new);
            ok = UIUtils.getString(R.string.tips_shop_type_ok_new);
        }else {
            tips = UIUtils.getString(R.string.tips_shop_type_content_edit);
            cancel = UIUtils.getString(R.string.tips_shop_type_cancel_edit);
            ok = UIUtils.getString(R.string.tips_shop_type_ok_edit);
        }

        DialogUtils.getInstance(getBaseActivity()).getSimpleDialog(tips).setNegativeButton(cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }, R.color.gray_69).setPositiveButton(ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EventBus.getDefault().postSticky(new ShopTypeSelectListEvent(selectedList, getIntent().getIntExtra("flag", -1)));
                finish();
            }
        }, R.color.orange_yellow_ff7d01).show();

    }

    @Override
    protected void initData() {
//        super.initData();重新加载
        initListDatas();
    }


    private void initListDatas() {

        addRequest(ModelRequest.getInstance().findHotelTypeList(PublicCache.site), new RequestCallback<ShopTypeListResultBean>() {
            @Override
            protected void onSuc(ShopTypeListResultBean body) {
                if (body.getData() != null) {
                    list = body.getData().getList();
                    if (list != null) {

                        for (int i = 0; i < list.size(); i++) {
                            ShopTypeBean bean1 = list.get(i);
                            List<ShopTypeBean> list2 = bean1.getChildren();
                            if (list2 == null) {
                                bean1.setSingle(true);
                            } else {
                                for (int j = 0; j < list2.size(); j++) {
                                    ShopTypeBean bean2 = list2.get(j);
                                    List<ShopTypeBean> list3 = bean2.getChildren();
                                    if (list3 == null) {
                                        bean2.setSingle(true);
                                    }
                                }

                            }

                        }

                        if (getIntent().hasExtra("oldSelectedList")) {
                          oldSelectedList = (List<ShopTypeBean>) getIntent().getSerializableExtra("oldSelectedList");
                            if (oldSelectedList != null&&oldSelectedList.size()>0) {
                                initSelectData();
                            } else {
                                initShopTypeList(true);
                            }

                        } else {
                            initShopTypeList(true);
                        }

                    }
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                super.onFailed(errCode, msg);
            }
        });


        // adapter3.setSelected(0);
    }

    private void initShopTypeList(boolean isStart) {
        String  str1="酒店";
        String  str2="酒店";

        int first=0;
      //  int second=0;

        if (isStart) {

        for (int i = 0; i <list.size() ; i++) {
            if (!list.get(i).isSingle()) {
                if (list.get(i).getName().equals(str1)){
                    list.get(i).setCheck(true);


                    List<ShopTypeBean> list2 = list.get(i).getChildren();

                    for (int j = 0; j < list2.size(); j++) {
                        if (list2.get(j).isSingle()&&list2.get(j).getName().equals(str2)) {
                               first=i;

                                list2.get(j).setAdd(true);
                                // adapter2.setSelected(0);
                                addSelectedView(list2.get(j), j, adapter2);
                        }

                    }
                }
            } else {
                if (list.get(i).getName().equals(str1)) {
                    list.get(i).setAdd(true);
                    addSelectedView(list.get(i), i, adapter1);
                }
              //  adapter1.setList(list);
            }
        }
        }
        adapter1.setList(list);
        adapter2.setList(list.get(first).getChildren());


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }


    @Override
    protected void onStop() {
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
        super.onStop();
    }

    private void initSelectData() {
//        List<ShopTypeBean> tempList1 =new ArrayList<>();
//        List<ShopTypeBean> tempList2 =new ArrayList<>();
//        List<ShopTypeBean> tempList3 =new ArrayList<>();

        boolean  oneAdd = false;
        boolean  twoAdd = false;
        boolean  threeAdd = false;
        int one = 0;
        int two = 0;
      //  int three = 0;


        for (int m = 0; m < oldSelectedList.size(); m++) {

            ShopTypeBean item = oldSelectedList.get(m);

            for (int i = 0; i < list.size(); i++) {
                ShopTypeBean bean1 = list.get(i);
                if (bean1.getParentId() == item.getParentId()) {
                    if (bean1.getHotelTypeId() == item.getHotelTypeId()) {
                        bean1.setAdd(true);
                        addSelectedView(bean1, i, adapter1);
                       // tempList1.add(bean1);
                        if (!oneAdd) {
                            oneAdd=true;
                            one=i;
                        }

                    }
                } else {
                    List<ShopTypeBean> list2 = bean1.getChildren();

                    if (list2 != null) {
                        for (int j = 0; j < list2.size(); j++) {
                            ShopTypeBean bean2 = list2.get(j);
                            if (bean2.getParentId() == item.getParentId()) {
                                if (bean2.getHotelTypeId() == item.getHotelTypeId()) {
                                   // bean1.setCheck(true);
                                    bean2.setAdd(true);
                                    addSelectedView(bean2, j, adapter2);
                                   // tempList2.add(bean2);

                                    if (!twoAdd) {
                                        twoAdd=true;
                                        one=i;
                                        two=j;
                                    }

                                }
                            } else {
                                List<ShopTypeBean> list3 = bean2.getChildren();
                                if (list3 != null) {
                                    for (int k = 0; k < list3.size(); k++) {
                                        ShopTypeBean bean3 = list3.get(k);
                                        if (bean3.getParentId() == item.getParentId()) {
                                            if (bean3.getHotelTypeId() == item.getHotelTypeId()) {
//                                                bean1.setCheck(true);
//                                                bean2.setCheck(true);
                                                bean3.setAdd(true);
                                                addSelectedView(bean3, k, adapter3);
                                               // tempList3.add(bean3);

                                                if (!threeAdd) {
                                                    threeAdd=true;
                                                    one=i;
                                                    two=j;
                                                 //   three=k;
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }


                }

            }

        }


        if (oneAdd) {
            adapter1.setList(list);
        } else {
            if (twoAdd) {
                list.get(one).setCheck(true);
                adapter1.setList(list);
                adapter2.setList(list.get(one).getChildren());
            } else {
                if (threeAdd) {
                    list.get(one).setCheck(true);
                    list.get(one).getChildren().get(two).setCheck(true);

                    adapter1.setList(list);
                    adapter2.setList(list.get(one).getChildren());
                    adapter3.setList(list.get(one).getChildren().get(two).getChildren());
                }
            }
        }
        //initShopTypeList(false);
    }

    @Subscribe(sticky = true)
    public void onMessageEvent(ShopTypeSelectEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
        ShopTypeBean item = event.getBean();

        if (selectedList.contains(item)) {
            return;
        }

        boolean check=false;
        if (adapter1.getList() .size()>=0) {
            for (int i = 0; i <adapter1.getList().size() ; i++) {
                ShopTypeBean bean1 = (ShopTypeBean)adapter1.getList().get(i);
                if (bean1.getHotelTypeId() == item.getHotelTypeId()) {
                    bean1.setAdd(true);
                    adapter1.notifyItemChanged(i);
                    addSelectedView(bean1, i, adapter1);
                    check=true;
                }

            }
        }
        if (adapter2.getList().size()>=0) {
            for (int i = 0; i <adapter2.getList().size() ; i++) {
                ShopTypeBean bean1 = (ShopTypeBean)adapter2.getList().get(i);
                if (bean1.getHotelTypeId() == item.getHotelTypeId()) {
                    bean1.setAdd(true);
                    adapter2.notifyItemChanged(i);
                    addSelectedView(bean1, i, adapter2);
                    check=true;
                }

            }
        }
        if (adapter3.getList().size()>=0) {
            for (int i = 0; i <adapter3.getList().size() ; i++) {
                ShopTypeBean bean1 = (ShopTypeBean)adapter3.getList().get(i);
                if (bean1.getHotelTypeId() == item.getHotelTypeId()) {
                    bean1.setAdd(true);
                    adapter3.notifyItemChanged(i);
                    addSelectedView(bean1, i, adapter3);
                    check=true;
                }

            }
        }


        if (check ) {
            return;
        }
        for (int i = 0; i <list.size() ; i++) {
            ShopTypeBean bean1 = list.get(i);
            if (bean1.getHotelTypeId() == item.getHotelTypeId()) {
                bean1.setAdd(true);
//                adapter1.notifyItemChanged(i);
                addSelectedView(bean1, i, adapter1);
            }else {
                List<ShopTypeBean> list2 = bean1.getChildren();
                if (list2 != null) {
                    for (int j = 0; j < list2.size(); j++) {
                        ShopTypeBean bean2 = list2.get(j);
                            if (bean2.getHotelTypeId() == item.getHotelTypeId()) {
                                bean2.setAdd(true);
                                addSelectedView(bean2, j, adapter2);
                        } else {
                            List<ShopTypeBean> list3 = bean2.getChildren();
                                if (list3 != null) {
                                    for (int k = 0; k < list3.size(); k++) {
                                        ShopTypeBean bean3 = list3.get(k);
                                            if (bean3.getHotelTypeId() == item.getHotelTypeId()) {
                                                bean3.setAdd(true);
                                                addSelectedView(bean3, k, adapter3);
                                        }
                                    }
                                }
                        }
                    }
                }
            }

        }



    }

    public static void startActivity(Context context, List<ShopTypeBean> oldSelectedList) {
        Intent intent = new Intent(context, RegisterPurchaserShopTypeActivity.class);
//        intent.putExtra("year", year);
//        intent.putExtra("month", month);
        intent.putExtra("oldSelectedList", (Serializable) oldSelectedList);
        intent.putExtra("flag", 0);
        context.startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        save();
    }
}
