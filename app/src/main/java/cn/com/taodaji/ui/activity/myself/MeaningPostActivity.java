package cn.com.taodaji.ui.activity.myself;

import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.FeedbackSave;
import cn.com.taodaji.common.PublicCache;

import java.util.HashMap;
import java.util.Map;

import com.base.retrofit.RequestCallback;
import cn.com.taodaji.ui.ppw.SimpleButtonPopupWindow;
import tools.activity.MenuToolbarActivity;
import tools.activity.SimpleToolbarActivity;
import com.base.utils.UIDataProcessing;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public class MeaningPostActivity extends SimpleToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("意见反馈");

    }

    private View mainView;
    private Button meaning_post_ok;
    private EditText content;
    private EditText contact;


    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_myself_meaning_post);
        body_scroll.addView(mainView);
        meaning_post_ok = ViewUtils.findViewById(mainView, R.id.meaning_post_ok);
        content = ViewUtils.findViewById(mainView, R.id.content);
        contact = ViewUtils.findViewById(mainView, R.id.contact);
        setViewBackColor(meaning_post_ok);
    }

    @Override
    protected void initData() {
    }


    public void onClick(View view) {
        if (content.getText().toString().trim().length() == 0) {
            UIUtils.showToastSafesShort("内容不可为空");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        UIDataProcessing.getChildControlsValue(mainView, map);
        map.put("title", "意见反馈");
        if (PublicCache.login_mode.equals(PURCHASER)) {
            map.put("source", "0");
            map.put("sourceEntityId", PublicCache.loginPurchase.getLoginUserId());
            map.put("contact",PublicCache.loginPurchase.getPhoneNumber());
        } else {
            map.put("source", "1");
            map.put("sourceEntityId", PublicCache.loginSupplier.getEntityId());
            map.put("contact",PublicCache.loginSupplier.getPhoneNumber());
        }
        loading();
        getRequestPresenter().feedback_save(map, new RequestCallback<FeedbackSave>() {
            @Override
            public void onSuc(FeedbackSave body) {
                loadingDimss();
                UIUtils.showToastSafesShort("提交成功");
                //弹出选择窗口
                final SimpleButtonPopupWindow simpleButtonPopupWindow = new SimpleButtonPopupWindow(0, mainView);
                simpleButtonPopupWindow.setMessage("我们已经收到您的意见反馈，感谢您的帮助和支持。如有空，去逛逛市场吧~")
                        .isClose(false)
                        .isTransparent(true)
                        .setButton_left_text("逛市场")
                        .setButton_right_text("去挑菜")
                        .setButton_left_backgroundResource(R.drawable.r_round_rect_solid_orange_ff7d01)
                        .setButton_left_TextColor(R.color.white)
                        .setButtonOnclickInterface(new SimpleButtonPopupWindow.ButtonOnclickInterface() {
                            @Override
                            public void buttonLeftOnclick() {
                                MenuToolbarActivity.goToPage(1);
                                simpleButtonPopupWindow.dismiss();
                                finish();
                            }

                            @Override
                            public void buttonRightOnclick(int position, View showCountView) {
                                MenuToolbarActivity.goToPage(2);
                                simpleButtonPopupWindow.dismiss();
                                finish();
                            }
                        });
                simpleButtonPopupWindow.showAtLocation(mainView, Gravity.CENTER, 0, 0);
            }

            @Override
            public void onFailed(int feedbackSave, String msg) {
                loadingDimss();
            }
        });
    }
}
