package cn.com.taodaji.ui.ppw;

import android.app.FragmentManager;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.AddCategory;

import com.base.retrofit.RequestCallback;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.viewModel.vm.CustomerNameViewModel;
import tools.fragment.AddedPicturesFragment;

import java.util.Map;

import com.base.viewModel.BaseViewHolder;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public abstract class GoodsNameCustomerPopupwindow extends BasePopupWindow implements View.OnClickListener, PopupResultInterface {

    private Map<String, Object> map;

    // private boolean isExist;
    private View mainview;
    private TextInputLayout textInputLayout;

    private BaseViewHolder viewHolder;

    public GoodsNameCustomerPopupwindow(View mainView, Map<String, Object> map) {
        super(mainView);
        this.map = map;
    }

    @Override
    public View createContextView(View mainView) {
        mainview = ViewUtils.getLayoutView(mainView.getContext(), R.layout.popup_window_goods_nameadd);

        textInputLayout = ViewUtils.findViewById(mainview, R.id.goods_name_input_layout);
        final EditText editText = textInputLayout.getEditText();
        editText.setTextColor(UIUtils.getColor(R.color.gray_66));
/*        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) textInputLayout.setErrorEnabled(false);
                else
                    RequestPresenter.getInstance().checkIfCategoryExist(charSequence.toString(), new RequestCallback<CheckIfCategoryExist>() {
                        @Override
                        public void onSuc(CheckIfCategoryExist body) {
                            isExist = body.getData();
                            if (!body.getData()) {
                                textInputLayout.setErrorEnabled(true);
                                textInputLayout.setError(" 列表中已存在");
                            } else {
                                textInputLayout.setErrorEnabled(false);
                            }
                        }

                        @Override
                        public void onFailed(int checkIfCategoryExist, String msg) {

                        }
                    });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/
        Button popupWindow_close = (Button) mainview.findViewById(R.id.popupWindow_close);
        mainview.findViewById(R.id.goods_price_change_OK).setOnClickListener(this);
        popupWindow_close.setOnClickListener(this);
        viewHolder = new BaseViewHolder(mainview, new CustomerNameViewModel(), null);
        return mainview;
    }

    @Override
    public boolean isTransparent() {
        return false;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.popupWindow_close:
                dismiss();
                break;
            case R.id.goods_price_change_OK:
                if (textInputLayout.getEditText().getText().length() == 0) {
                    UIUtils.showToastSafesShort("请输入商品名称");
                    return;
                }
                map.putAll(viewHolder.getViewValues());
                RequestPresenter.getInstance().addCategory(map, new RequestCallback<AddCategory>() {
                    @Override
                    public void onSuc(AddCategory body) {
                        UIUtils.showToastSafesShort("添加成功");
                        setResult(body);
                        dismiss();
                    }

                    @Override
                    public void onFailed(int addCategory, String msg) {
                        UIUtils.showToastSafesShort(msg);
                    }
                });
                break;
        }
    }
}
