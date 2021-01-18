package cn.com.taodaji.viewModel.adapter;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseVM;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.OnItemClickListener;
import com.base.viewModel.adapter.splite_line.DividerGridItemDecoration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;

import com.base.listener.UploadPicturesDoneListener;
import com.base.utils.DensityUtil;
import com.base.utils.JavaMethod;
import com.base.utils.ViewUtils;
import com.base.takephoto.app.TakePhoto;
import com.base.takephoto.model.TResult;

public class SimpleEvaluatePurchaseAdapter extends SingleRecyclerViewAdapter {


    //图片上传完成接口
    private UploadPicturesDoneListener uploadPicturesDoneListener;
    private SparseArrayCompat<String> images = new SparseArrayCompat<>();//上传后的图片信息
    private SparseArrayCompat<Integer> evaluate_id = new SparseArrayCompat<>();//存储评价比如好评
    private SparseArrayCompat<String> evaluate_content = new SparseArrayCompat<>();//存储评价内容


    private SparseArrayCompat<Boolean> isUpDone = new SparseArrayCompat<>();//存储图片是否上传完毕
    private TakePhoto takePhoto;
    private int maxSelect = 5;
    private boolean isCallBack = false;

    private int UPImagePosition = -1;//点击添加图片的位置


    //设置 上传完成后是否需要自动回调接口
    public void setCallBack(boolean isCallBack) {
        this.isCallBack = isCallBack;
    }


    public void setMaxSelect(int maxSelect) {
        this.maxSelect = maxSelect;
    }

    public void setTakePhoto(TakePhoto takePhoto) {
        this.takePhoto = takePhoto;
    }


    public void setUploadPicturesDoneListener(UploadPicturesDoneListener uploadPicturesDoneListener) {
        this.uploadPicturesDoneListener = uploadPicturesDoneListener;
    }

    public Map<String, Object> getViewValues() {
        //List<Map<String, Object>> parameterMapList = new ArrayList<>();
//        for (int i = getFirstPosition(); i < getLastPosition() + 1; i++) {
//            if (getListBean(i) == null) continue;

        int i = getFirstPosition();
        Map<String, Object> map = new HashMap<>();
        CartGoodsBean bean = (CartGoodsBean) getListBean(i);
        //orderId订单id（必填）、orderItemId订单明细id（必填）、storeId店铺id（必填）、productId产品id（必填）

        map.put("entityId", bean.getEntityId());
        map.put("productId", bean.getProductId());


        //evaluationImg评价图片 如果不存在则传空
        String image = images.get(i);
        map.put("evalImg", image == null ? "" : image);


        BaseViewHolder holder = getViewHolder(i);
        if (holder != null) {
            initItemData(holder);
        }

        //creditLevel评分等级1好评2中评3差评
        int id = evaluate_id.get(i, -1);
        switch (id) {
            case R.id.radioButton1:
                map.put("creditLevel", 1);
                break;
            case R.id.radioButton2:
                map.put("creditLevel", 2);
                break;
            case R.id.radioButton3:
                map.put("creditLevel", 3);
                break;
            case -1:
                map.put("creditLevel", 1);
                break;
        }

        //evaluationContent评价内容
        String evaluateString = evaluate_content.get(i);
        if (evaluateString == null) evaluateString = "";
        map.put("evaluationContent", evaluateString);

        // parameterMapList.add(map);
        //  }

        return map;
    }

    @Override
    public void notifyDataSetChanged(List lst) {
       if (lst!=null){
           for (int i = 0; i < lst.size(); i++) {
               CartGoodsBean  bean=(CartGoodsBean)lst.get(i);
               images.put(i,bean.getCreditImgs());
               evaluate_content.put(i,bean.getCreditContent());
               switch (bean.getCreditLevel()) {
                   case 1:
                       evaluate_id.put(i,R.id.radioButton1);
                       break;
                   case 2:
                       evaluate_id.put(i,R.id.radioButton2);
                       break;
                   case 3:
                       evaluate_id.put(i,R.id.radioButton3);
                       break;
               }
           }

       }
        super.notifyDataSetChanged(lst);
    }


    //evaluationInfos	json数组的字符串

    //评价信息：orderId订单id（必填）、orderItemId订单明细id（必填）、
    // storeId店铺id（必填）、productId产品id（必填）、
    // creditLevel评分等级1好评2中评3差评、evaluationContent评价内容、evaluationImg评价图片
    public String geteValuationInfos() {
        // List<String> parame = new ArrayList<>();

        StringBuilder parame = new StringBuilder();
        parame.append("[");

        int count = getItemCount();


        for (int i = getFirstPosition(); i <= getLastPosition(); i++) {
            if (getListBean(i) == null) continue;

            Map<String, Object> map = new HashMap<>();
            CartGoodsBean bean = (CartGoodsBean) getListBean(i);
            //orderId订单id（必填）、orderItemId订单明细id（必填）、storeId店铺id（必填）、productId产品id（必填）
            map.put("orderId", bean.getOrderId());
            map.put("orderItemId", bean.getItemId());
            map.put("storeId", bean.getStoreId());
            map.put("productId", bean.getProductId());


            //evaluationImg评价图片 如果不存在则传空
            String image = images.get(i);
            map.put("evaluationImg", image == null ? "" : image);


            BaseViewHolder holder = getViewHolder(i);
            if (holder != null) {
                initItemData(holder);
            }

            //creditLevel评分等级1好评2中评3差评
            int id = evaluate_id.get(i, -1);
            switch (id) {
                case R.id.radioButton1:
                    map.put("creditLevel", 1);
                    break;
                case R.id.radioButton2:
                    map.put("creditLevel", 2);
                    break;
                case R.id.radioButton3:
                    map.put("creditLevel", 3);
                    break;
                case -1:
                    map.put("creditLevel", 1);
                    break;
            }

            //evaluationContent评价内容
            String evaluateString = evaluate_content.get(i);
            if (evaluateString == null) evaluateString = "";
            map.put("evaluationContent", evaluateString);


            String json = JavaMethod.transMap2Json(map);
            parame.append(json);
            parame.append(",");
        }

        parame.deleteCharAt(parame.length() - 1);
        parame.append("]");

        return parame.toString();
    }

    //将相册的图片分发到需要添加的地方
    public void takeSuccess(TResult result) {
        if (UPImagePosition == -1) return;
        BaseViewHolder holder = getViewHolder(UPImagePosition);
        if (holder == null) return;
        isUpDone.put(UPImagePosition, false);//添加图片正在上传
        RecyclerView recyclerView = holder.findViewById(R.id.recyclerView);
        SimpleAddPicturesAdapter addPicturesAdapter = (SimpleAddPicturesAdapter) recyclerView.getAdapter();
        if (addPicturesAdapter == null) return;
        addPicturesAdapter.takeSuccess(result);
    }

    //检查是否上传完毕
    public boolean checkedImagesUpDone() {
        for (int i = 0; i < isUpDone.size(); i++) {
            if (!isUpDone.valueAt(i)) return false;
        }
        return true;
    }

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new BaseVM() {
            @Override
            protected void dataBinding() {
                putRelation(R.id.shop_logo, "productImage");
            }

        });
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        return ViewUtils.getFragmentView(parent, R.layout.item_evaluate_purchase);
    }

    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, final int position) {
        super.onBindViewHolderCustomer(holder, position);
        RecyclerView recyclerView = holder.findViewById(R.id.recyclerView);
        final SimpleAddPicturesAdapter addPicturesAdapter;
        CartGoodsBean cartGoodsBean = (CartGoodsBean) getListBean(position);
        if (recyclerView.getAdapter() == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(), OrientationHelper.HORIZONTAL, false));
            recyclerView.addItemDecoration(new DividerGridItemDecoration(DensityUtil.dp2px(5), R.color.white));
            addPicturesAdapter = new SimpleAddPicturesAdapter();
            addPicturesAdapter.setTakePhoto(takePhoto);
            addPicturesAdapter.setMaxSelect(maxSelect);
            addPicturesAdapter.setCrop(false);
            addPicturesAdapter.setCallBack(true);

            addPicturesAdapter.setItemClickListener(new OnItemClickListener() {
                @Override
                public boolean onItemClick(View view, int onclickType, int pos, Object bean) {
                    if (onclickType == 0) {
                        UPImagePosition = position;
                    }
                    return false;
                }
            });
            addPicturesAdapter.setUploadPicturesDoneListener(new UploadPicturesDoneListener() {
                @Override
                public void uploadPicturesIsDone(Object obj) {
                    String image = addPicturesAdapter.getImageStr();
                    images.put(position, image);
                    isUpDone.put(position, true);//position添加图片上传完成

                    //检查是否全部上传完毕，如果全部上传完毕且需要回调则回调
                    if (checkedImagesUpDone() && isCallBack) {
                        if (uploadPicturesDoneListener != null) {
                            if (cartGoodsBean.getEntityId() == 0) {
                                uploadPicturesDoneListener.uploadPicturesIsDone(geteValuationInfos());
                            } else {
                                uploadPicturesDoneListener.uploadPicturesIsDone(getViewValues());
                            }

                        }
                    }
                }
            });
            recyclerView.setAdapter(addPicturesAdapter);
/*            addPicturesAdapter.setImageStrs(cartGoodsBean.getCreditImgs());
            EditText editText = holder.findViewById(R.id.evaluate_text);
            editText.setText(cartGoodsBean.getCreditContent());
            RadioButton radioButton1 = holder.findViewById(R.id.radioButton1);
            RadioButton radioButton2 = holder.findViewById(R.id.radioButton2);
            RadioButton radioButton3 = holder.findViewById(R.id.radioButton3);
            switch (cartGoodsBean.getCreditLevel()) {
                case 1:
                    radioButton1.setChecked(true);
                    radioButton2.setChecked(false);
                    radioButton3.setChecked(false);
                    break;
                case 2:
                    radioButton1.setChecked(false);
                    radioButton2.setChecked(true);
                    radioButton3.setChecked(false);
                    break;
                case 3:
                    radioButton1.setChecked(false);
                    radioButton2.setChecked(false);
                    radioButton3.setChecked(true);
                    break;
            }*/

        }

    }

    //初始化存储信息
    public void initItemData(BaseViewHolder holder) {
        int position = holder.getAdapterPosition();
        RadioGroup radioGroup = holder.findViewById(R.id.evaluate_group);
        if (radioGroup != null) evaluate_id.put(position, radioGroup.getCheckedRadioButtonId());
        EditText editText = holder.findViewById(R.id.evaluate_text);
        if (editText != null) evaluate_content.put(position, editText.getText().toString());

    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        int position = holder.getAdapterPosition();
//        RadioGroup radioGroup = holder.findViewById(R.id.evaluate_group);
        RadioButton radioButton = holder.findViewById(evaluate_id.get(position, R.id.radioButton1));
        if (radioButton != null) {
            radioButton.setChecked(true);
        }
        EditText editText = holder.findViewById(R.id.evaluate_text);
        if (editText != null) editText.setText(evaluate_content.get(position, ""));

        RecyclerView recyclerView = holder.findViewById(R.id.recyclerView);
        if (recyclerView != null) {
            SimpleAddPicturesAdapter addPicturesAdapter=(SimpleAddPicturesAdapter)recyclerView.getAdapter();
            if (addPicturesAdapter!=null)addPicturesAdapter.setImageStrs(images.get(position));
        }

    }

    @Override
    public void onViewDetachedFromWindow(BaseViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

        int position = holder.getAdapterPosition();
        RadioGroup radioGroup = holder.findViewById(R.id.evaluate_group);
        if (radioGroup != null) evaluate_id.put(position, radioGroup.getCheckedRadioButtonId());
        EditText editText = holder.findViewById(R.id.evaluate_text);
        if (editText != null) evaluate_content.put(position, editText.getText().toString());


    }


}
