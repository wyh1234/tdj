package cn.com.taodaji.viewModel.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.ShopEmployeeItem;
import cn.com.taodaji.model.entity.ShopEmployeeList;
import cn.com.taodaji.ui.activity.employeeManagement.ChainShopActivity;
import cn.com.taodaji.ui.activity.employeeManagement.DialogActivity;

/**
 * Created by zhaowenlong on 2019/3/6.
 */
public class ChainShopAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private static final String TAG = ChainShopAdapter.class.getSimpleName();

    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_PERSON = 2;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ChainShopAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_1, R.layout.item_shop_employess_list);
        addItemType(TYPE_PERSON, R.layout.item_shop_employee);
    }


    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_1:
                final ShopEmployeeList lv1 = (ShopEmployeeList) item;
                holder.setText(R.id.tv_item_title, lv1.title)
                        .setText(R.id.tv_item_num_employees,lv1.employeesNum+"人")
                        .setImageResource(R.id.iv_item_icon, lv1.isChain ? R.drawable.chain_shop : R.mipmap.other_shop)
                        .setImageResource(R.id.iv_item_expand,lv1.isExpanded()?R.drawable.expand:R.drawable.unexpand);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        if (lv1.isExpanded()) {
                            collapse(pos, false);
                        } else {
                            expand(pos, false);
                        }
                    }
                });

                break;
            case TYPE_PERSON:
                final ShopEmployeeItem person = (ShopEmployeeItem) item;
                StringBuffer sb = new StringBuffer();
                holder.setText(R.id.item_employee_name, person.name)
                        .setText(R.id.item_employee_phone,person.phone);
                switch (person.position){
                    //角色 0：管理员、1：厨师、2：财务、3：子管理员、 4：店长、5：员工
                    case 0:
                        sb.append("管理员");
                        break;
                    case 1:
                        sb.append("厨师");
                        break;
                    case 2:
                        sb.append("财务");
                        break;
                    case 3:
                        sb.append("管理员");
                        break;
                    case 4:
                        sb.append("店长");
                        break;
                    case 5:
                        sb.append("员工");
                        break;
                    default:break;
                }
                if (person.isCreator){
                    sb.insert(0,"创建人/");
                    SpannableString spannableString = new SpannableString("("+sb+")");
                    //设置颜色
                    holder.setText(R.id.item_employee_position,spannableString);
                }else {
                    holder.setText(R.id.item_employee_position,"("+sb+")");
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, DialogActivity.class);
                        intent.putExtra("phone",person);
                        mContext.startActivity(intent);
                        Log.i("zhao", "onClick: "+person.getEnterpriseCode());
                    }
                });
                break;
            default:
                break;
        }
    }
}
