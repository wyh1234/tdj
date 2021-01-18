package cn.com.taodaji.viewModel.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.UpdateCustomerBean;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;
import cn.com.taodaji.viewModel.vm.OrderPlaceGoodsVM;

import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.utils.ViewUtils;

import java.util.HashMap;
import java.util.Map;

public class SubmitOrderPlaceAdapter extends SingleRecyclerViewAdapter {
    private Context context;
    public SubmitOrderPlaceAdapter(){

    }
    public SubmitOrderPlaceAdapter(Context context){
        this.context=context;

    }
    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new OrderPlaceGoodsVM(this));
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_order_place_goods);
    }

    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        super.onBindViewHolderCustomer(holder, position);
       TextView tv_btn= holder.findViewById(R.id.tv_btn);

        tv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDialog(context,(CartGoodsBean)list.get(position));
            }
        });
//        holder.setText(R.id.tv_textView1,"订购");
    }
    private void initDialog(Context context, CartGoodsBean bean){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.dialog_reak_purchaser_edit, null);


        EditText edit_content=view.findViewById(R.id.edit_content);
        TextView text_left=view.findViewById(R.id.text_left);
        TextView text_right=view.findViewById(R.id.text_right);

        TextView text_title=view.findViewById(R.id.text_title);
        text_title.setText(bean.getProductName());
        TextView text_name=view.findViewById(R.id.text_name);
        text_name.setText("("+bean.getNickName()+")");

        if (UIUtils.isNullOrZeroLenght(bean.getRemark())){
            edit_content.setHint("请输入单个商品备注,最多输入15个字哦");
        }else {
            edit_content.setText(bean.getRemark());
        }
        edit_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = charSequence.toString();
                if (str.length()>15){
                    UIUtils.showToastSafesShort("最多输入15个字符哦");
                    edit_content.setText(str.substring(0,15)); //截取前x位
                    edit_content.requestFocus();
                    edit_content.setSelection(edit_content.getText().length()); //光标移动到最后
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
           /*     if ( editable.toString().length()>15){
                    edit_content.setText(editable.toString());
                    edit_content.setFocusable(false);
                    UIUtils.showToastSafesShort("最多输入15个字符哦");
                }else {
                    edit_content.setText(editable.toString());
                    edit_content.setFocusable(true);
                }
*/

            }
        });



        builder.setView(view);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
//                取消或确定按钮监听事件处理
        text_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        text_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String et=edit_content.getText().toString().trim();
                  if (et.length()==0){
                    bean.setRemark("");

                }else {
                    bean.setRemark(et);
                }
                Log.d("SS","SSSSSSSS");
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
