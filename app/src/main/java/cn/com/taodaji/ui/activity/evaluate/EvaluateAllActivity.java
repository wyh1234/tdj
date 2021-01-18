package cn.com.taodaji.ui.activity.evaluate;

import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.EvaluationStatics;

import com.base.retrofit.RequestCallback;

import cn.com.taodaji.ui.fragment.EvaluateAllFragment;
import tools.activity.SimpleToolbarActivity;
import tools.extend.FluidLayout;

import com.base.viewModel.BaseVM;
import com.base.viewModel.adapter.OnItemClickListener;
import com.base.utils.ADInfo;
import com.base.utils.ViewUtils;

public class EvaluateAllActivity extends SimpleToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setToolBarColor(R.color.orange_yellow_ff7d01);
        simple_title.setText("评价");

    }

    private FluidLayout fluidLayout;
    private int productId;
    private EvaluateAllFragment evaluateAllFragment;

    @Override
    protected void initMainView() {
        View mainView = getLayoutView(R.layout.activity_evaluate_all);
        body_other.addView(mainView);

        fluidLayout = ViewUtils.findViewById(mainView, R.id.fluidLayout);

    }


    @Override
    protected void initData() {
        onStartLoading();
        productId = getIntent().getIntExtra("productId", -1);
        if (productId > -1) {
            getEvaluateValue(productId);
        }
    }

    //请求评分信息
    private void getEvaluateValue(final int productId) {
        getRequestPresenter().evaluation_statics(productId, new RequestCallback<EvaluationStatics>(this) {
            @Override
            public void onSuc(EvaluationStatics body) {
                List<ADInfo> list = new ArrayList<>();
                ADInfo adInfo = new ADInfo();
                adInfo.setImageName("全部");
                adInfo.setGoodsCount(body.getData().getAllEvaluateCount());
                list.add(adInfo);

                ADInfo adInfo1 = new ADInfo();
                adInfo1.setImageName("好评");
                adInfo1.setGoodsCount(body.getData().getGoodEvaluateCount());
                list.add(adInfo1);

                ADInfo adInfo2 = new ADInfo();
                adInfo2.setImageName("中评");
                adInfo2.setGoodsCount(body.getData().getMediumEvaluateCount());
                list.add(adInfo2);

                ADInfo adInfo3 = new ADInfo();
                adInfo3.setImageName("差评");
                adInfo3.setGoodsCount(body.getData().getBadEvaluateCount());
                list.add(adInfo3);

                ADInfo adInfo4 = new ADInfo();
                adInfo4.setImageName("晒图");
                adInfo4.setGoodsCount(body.getData().getHasImgEvaluateCount());
                list.add(adInfo4);
                fluidLayout.setRadio(true);
                fluidLayout.setChecked(true);
                fluidLayout.setData(list, R.layout.item_evaluate_all_heard, new BaseVM() {
                    @Override
                    protected void dataBinding() {
                        putRelation(R.id.text, "imageName");
                        putRelation(R.id.count, "goodsCount");
                    }

                    @Override
                    protected void addOnclick() {
                        putViewOnClick(R.id.item_view);
                    }
                });
                fluidLayout.setOnCustomerItemClickListener(new OnItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                        if (onclickType == 0) {
                            /**
                             * creditLevel	int	0		评价等级1好评2中评3差评 -1或空 全部
                             * hasImg	int	0		是否有图1晒图 -1或空 全部
                             * productId	int	0		商品id，-1或空全部
                             */
                            if (evaluateAllFragment == null) return true;
                            // int position = (int) v.getTag(R.id.tag_first);
                            evaluateAllFragment.setHasImg(-1);
                            evaluateAllFragment.setProductId(productId);
                            switch (position) {
                                case 0:
                                    evaluateAllFragment.setCreditLevel(-1);
                                    break;
                                case 1:
                                    evaluateAllFragment.setCreditLevel(1);
                                    break;
                                case 2:
                                    evaluateAllFragment.setCreditLevel(2);
                                    break;
                                case 3:
                                    evaluateAllFragment.setCreditLevel(3);
                                    break;
                                case 4:
                                    evaluateAllFragment.setCreditLevel(-1);
                                    evaluateAllFragment.setHasImg(1);
                                    break;
                            }
                            if (evaluateAllFragment.swipeToLoadLayout != null)
                                evaluateAllFragment.swipeToLoadLayout.setRefreshing(true);
                            return true;
                        }
                        return false;
                    }
                });
                if (evaluateAllFragment == null) {
                    evaluateAllFragment = new EvaluateAllFragment();
                    evaluateAllFragment.setCreditLevel(-1);
                    evaluateAllFragment.setProductId(productId);
                    if (!isDestroyed() && !isFinishing())
                        getSupportFragmentManager().beginTransaction().add(R.id.evaluate, evaluateAllFragment).commitAllowingStateLoss();
                }


            }

            @Override
            public void onFailed(int evaluationStatics, String msg) {

            }
        });
    }


}
