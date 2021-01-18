package tools.animation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.glide.GlideImageView;
import com.base.glide.nineImageView.ImagesActivity;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.ClassifyPopuWindowInfo;
import cn.com.taodaji.model.entity.ShopDetail;
import cn.com.taodaji.viewModel.adapter.ClassifyPopuWindowAdapter;
import razerdp.basepopup.BasePopupWindow;

public class ClassifyPopuWindow extends BasePopupWindow implements ClassifyPopuWindowAdapter.OnItemClickListener {
    private View popupView;
    private ClassifyPopuWindowListener listener;
    private GlideImageView shop_logo;
    private TextView shop_name;
    private TextView tv_enshrine_count;
    private TextView shop_evaluate_value;
    private TextView realName_check;
    private TextView foodQuality,licenceDoc;
    private ImageView iv_add_attention;
    private List<ClassifyPopuWindowInfo> list_second;
    private RecyclerView classify_rv;
    private ClassifyPopuWindowAdapter classifyPopuWindowAdapter;


    public void setClassifyPopuWindowListener(ClassifyPopuWindowListener listener) {
        this.listener = listener;
    }
    public ClassifyPopuWindow(Context context,  List<ClassifyPopuWindowInfo> list_second) {
        super(context);
        this.list_second=list_second;
        classify_rv=popupView.findViewById(R.id.classify_rv);
        RelativeLayout rl=popupView.findViewById(R.id.rl);

        rl.setVisibility(View.GONE);
        classify_rv.setLayoutManager(new ScrollLinearLayoutManager(context, 2));
        if (list_second!=null){
            classifyPopuWindowAdapter = new ClassifyPopuWindowAdapter(list_second, context);

            classify_rv.setAdapter(classifyPopuWindowAdapter);
            classifyPopuWindowAdapter.setOnItemClickListener(this);
        }


    }
    public ClassifyPopuWindow(Context context, ShopDetail body, List<ClassifyPopuWindowInfo> list_second) {
        super(context);
        this.list_second=list_second;
        classify_rv=popupView.findViewById(R.id.classify_rv);
        tv_enshrine_count=popupView.findViewById(R.id.tv_enshrine_count);
        tv_enshrine_count.setText(body.getFavoriteCount()+"");
        shop_evaluate_value=popupView.findViewById(R.id.shop_evaluate_value);
        shop_evaluate_value.setText(body.getStoreScore()+"");
        shop_logo=popupView.findViewById(R.id.shop_logo);
        shop_logo.loadImage(body.getLogoImageUrl());
        shop_name=popupView.findViewById(R.id.shop_name);
        shop_name.setText(body.getName());
        realName_check=popupView.findViewById(R.id.realName_check);
        foodQuality=popupView.findViewById(R.id.foodQuality);
        licenceDoc=popupView.findViewById(R.id.licenceDoc);

        iv_add_attention=popupView.findViewById(R.id.iv_add_attention);

//        classify_rv.addItemDecoration(new RecyclerViewDivider(context, LinearLayoutManager.HORIZONTAL, 20, context.getResources().getColor(R.color.white)));

        classify_rv.setLayoutManager(new ScrollLinearLayoutManager(context, 2));
        if (list_second!=null){
            classifyPopuWindowAdapter = new ClassifyPopuWindowAdapter(list_second, context);
            classifyPopuWindowAdapter.setOnItemClickListener(this);
            classify_rv.setAdapter(classifyPopuWindowAdapter);
        }

        if (body.getIdcardNumber() == null || "".equals(body.getIdcardNumber()) || body.getIdcardImageUrl() == null || "".equals(body.getIdcardImageUrl() )){
            realName_check.setSelected(false);
        }else {
            realName_check.setSelected(true);
        }

        if (body.getFoodQualiynoImageUrl() == null || "".equals(body.getFoodQualiynoImageUrl())){
            foodQuality.setSelected( false);
        }else {
            foodQuality.setSelected( true);
        }
        if (body.getLicenceDocurl() == null || "".equals(body.getLicenceDocurl())){
            licenceDoc.setSelected( false);
        }else {
            licenceDoc.setSelected( true);
        }
        if (iv_add_attention != null) iv_add_attention.setSelected(body.isFavor());

        iv_add_attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.add_attention(iv_add_attention,tv_enshrine_count);

            }
        });

        foodQuality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (body.getFoodQualiynoImageUrl() == null || "".equals(body.getFoodQualiynoImageUrl().toString())){
                    return;
                }
                ImagesActivity.startActivity(view, body.getFoodQualiynoImageUrl());
            }
        });
        licenceDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (body.getLicenceDocurl() == null || "".equals(body.getLicenceDocurl().toString())){
                    return;
                }
                ImagesActivity.startActivity(view, body.getLicenceDocurl());
            }
        });


    }

    @Override
    protected Animation initShowAnimation() {
        return null;
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        popupView = createPopupById(R.layout.classsify_popu_item);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }

    @Override
    public void onItemOneClickListener(View view, int position) {
        listener.onItemOneClickListener( view, position);

    }


    public interface ClassifyPopuWindowListener {
        void add_attention(ImageView v, TextView textView);
        void onItemOneClickListener(View view, int position);
    }
}
