package cn.com.taodaji.ui.activity.advertisement.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.glide.ImageLoaderUtils;
import com.base.utils.UIUtils;

import java.math.BigDecimal;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.CreateTfAdvManage;
import cn.com.taodaji.model.entity.SelGoods;


public class CreateTfAdvManageAdapter extends RecyclerView.Adapter<CreateTfAdvManageAdapter.CreateTfAdvManageViewHolder>{
    private Context context;
    private OnItemClickListener listener;
    public List<CreateTfAdvManage> data;
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public CreateTfAdvManageAdapter(Context context, List<CreateTfAdvManage> data) {
        this.context=context;
        this.data=data;
    }
    @NonNull
    @Override
    public CreateTfAdvManageAdapter.CreateTfAdvManageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.create_tfadv_manage_list, parent, false);
        CreateTfAdvManageViewHolder createTfAdvManageViewHolder = new CreateTfAdvManageViewHolder(root);
        return createTfAdvManageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CreateTfAdvManageAdapter.CreateTfAdvManageViewHolder holder, int position) {



        if (data.get(position).getGoods()!=null){
            if (data.get(position).getGoods().size()>0){
                holder.tv_type.setText("已选商品");
                holder.tv_type_right.setHint("");
                setOtherMoney(data.get(position).getGoods(), position,holder.mLlContain);

            }else {
                holder.tv_type.setText("投放商品");
                holder.tv_type_right.setHint("请选择需要投放的商品");

            }
        }
        if (position==0){
            holder.tv_right.setText("新建计划");
            holder.iv_right.setImageResource(R.mipmap.add_tf);
        }else {
            holder.tv_right.setText("删除计划");
            holder.iv_right.setImageResource(R.mipmap.del_adv);
        }
        if (data.get(position).isB()){
            holder.tv_adv_tj.setVisibility(View.VISIBLE);
            holder.tv_sel_adv.setVisibility(View.GONE);
            holder.ed_sel_adv.setVisibility(View.VISIBLE);
            holder.tv_dw.setVisibility(View.VISIBLE);

        }else {
            holder.tv_adv_tj.setVisibility(View.GONE);
            holder.tv_sel_adv.setVisibility(View.VISIBLE);
            holder.ed_sel_adv.setVisibility(View.GONE);
            holder.tv_dw.setVisibility(View.GONE);
        }

        holder.tv_num.setText("广告计划"+(position+1));
        if (listener!=null){
            holder.rl_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick( view, position);
                }
            });
            holder.rl_title2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick( view, position);
                }
            });

            holder.rl_title1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!data.get(position).isB()){
                        listener.onItemClick( view, position);
                    }

                }
            });
            holder.rl_title3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick( view, position);
                }
            });
            holder.tv_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        listener.onItemClick( view, position);

                }
            });
            holder.tv_adv_tj.setText("*投放天数最多不超过"+data.get(position).getLimit_days()+"天");

        }
        if (!UIUtils.isNullOrZeroLenght(data.get(position).getDay())){
            holder.ed_sel_adv.setText(data.get(position).getDay());
        }else {
            holder.ed_sel_adv.setHint("请输入投放天数");
        }


        setEditTextFocusChangeListener( holder.ed_sel_adv, holder.getAdapterPosition());
        holder.setIsRecyclable(false);
        holder.ed_sel_adv.setTag(R.id.ed_sel_adv, data.get(position));


        if (!UIUtils.isNullOrZeroLenght(data.get(position).getAdvPackageName())){
            if (!UIUtils.isNullOrZeroLenght(data.get(position).getJsDay())){
                String strMsg = data.get(position).getAdvPackageName()+"<font color=\"#FD0404\">"+data.get(position).getJsDay()+"</font>"+data.get(position).getAdvPackagePice()+"元";
                holder.tv_sel_adv.setText(Html.fromHtml(strMsg));

            }else {
                holder.tv_sel_adv.setText(data.get(position).getAdvPackageName()+data.get(position).getAdvPackagePice()+"元");
            }
        }
         holder.tv_time.setText(data.get(position).getTime());
        if (!UIUtils.isNullOrZeroLenght(data.get(position).getRemark())){
            holder.tv_remark.setText(data.get(position).getRemark());
        }







    }

    private void setEditTextFocusChangeListener(EditText editText, int adapterPosition) {


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String edit = editText.getText().toString();
                // 如果填入数据与缓存数据相同返回
                if (UIUtils.isNullOrZeroLenght(edit)){
                    data.get(adapterPosition).setDay("0");
                }else {
                    data.get(adapterPosition).setDay(edit);
                }
            }
        });
    }

    private void setOtherMoney(List<SelGoods.DataBean.ItemsBean> goods, int position, LinearLayout mLlContain) {
        mLlContain.removeAllViews();
            LayoutInflater from = LayoutInflater.from(context);
                for (int i = 0; i < goods.size(); i++) {
                    View view = from.inflate(R.layout.include_goods_item, null, false);
                    ImageView iv = (ImageView) view.findViewById(R.id.iv);
                    TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
                    TextView tv_nickname = (TextView) view.findViewById(R.id.tv_nickname);
                    TextView tv_content = (TextView) view.findViewById(R.id.tv_content);

                    ImageLoaderUtils.loadImage(iv,goods.get(i).getImage());
                    tv_name.setText(goods.get(i).getName());
                    tv_nickname.setText("("+goods.get(i).getNickName()+")");
                    if ((goods.get(i).getMaxPrice()).compareTo(new BigDecimal(-1)) == 0 && goods.get(i).getSpecs().size() == 1) {
                        SelGoods.DataBean.ItemsBean.SpecsBean gsf = goods.get(i).getSpecs().get(0);
                        if (gsf == null) return;
                        if (gsf.getLevelType() == 1) {
                            tv_content.setText(goods.get(i).getMinPrice() + "元/" + goods.get(i).getUnit());
                        } else {
                            if (gsf.getLevelType() == 3) {
                                tv_content.setText(goods.get(i).getMinPrice() + "元/" + goods.get(i).getUnit() + "(" + gsf.getLevel2Value() + "" + gsf.getLevel2Unit() + "*" + gsf.getLevel3Value() + gsf.getLevel3Unit() + "" + ")");
                            } else {
                                tv_content.setText(goods.get(i).getMinPrice() + "元/" + goods.get(i).getUnit() + "(" + gsf.getLevel2Value() + "" + gsf.getLevel2Unit() + ")");
                            }
                        }
                    } else {
                        tv_content.setText(goods.get(i).getMinPrice() + "元/" + goods.get(i).getUnit());
                    }


              /*      int finalI = i;
                    ll_include.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (null != clickLister) {
                                clickLister.click(position, finalI);
                            }
                        }
                    });*/

                    mLlContain.addView(view);
            }

    }
    public ClickItemLister clickLister;

    public interface ClickItemLister {
        void click(int position, int i);
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    class  CreateTfAdvManageViewHolder extends RecyclerView.ViewHolder{
        TextView tv_type,tv_type_right,tv_right,tv_num,tv_adv_tj,tv_sel_adv,tv_dw,tv_time,tv_remark;
        LinearLayout mLlContain;
        ImageView iv_right;
        RelativeLayout rl_time,rl_title2,rl_title1,rl_title3;
        EditText ed_sel_adv;


        public CreateTfAdvManageViewHolder(View itemView) {
            super(itemView);
            tv_type=itemView.findViewById(R.id.tv_type);
            tv_type_right=itemView.findViewById(R.id.tv_type_right);
            mLlContain=itemView.findViewById(R.id.ll_contain);
            tv_right=itemView.findViewById(R.id.tv_right);
            iv_right=itemView.findViewById(R.id.iv_right);
            tv_num=itemView.findViewById(R.id.tv_num);
            rl_time=itemView.findViewById(R.id.rl_time);
            rl_title2=itemView.findViewById(R.id.rl_title2);
            rl_title1=itemView.findViewById(R.id.rl_title1);
            rl_title3=itemView.findViewById(R.id.rl_title3);
            tv_adv_tj=itemView.findViewById(R.id.tv_adv_tj);
            tv_sel_adv=itemView.findViewById(R.id.tv_sel_adv);
            ed_sel_adv=itemView.findViewById(R.id.ed_sel_adv);
            tv_dw=itemView.findViewById(R.id.tv_dw);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_remark=itemView.findViewById(R.id.tv_remark);
        }
    }
}
