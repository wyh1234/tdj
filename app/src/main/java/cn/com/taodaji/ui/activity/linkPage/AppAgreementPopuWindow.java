package cn.com.taodaji.ui.activity.linkPage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.com.taodaji.R;
import cn.com.taodaji.ui.activity.homepage.SpecialActivitiesActivity;
import cn.com.taodaji.ui.activity.integral.WebViewActivity;
import razerdp.basepopup.BasePopupWindow;

public class AppAgreementPopuWindow  extends BasePopupWindow {
    View popupView;
    TextView tv_content,tv_ok,tv_cancel;
    private AppAgreementPopuWindowListener listener;
    public void setAppAgreementPopuWindowListener(AppAgreementPopuWindowListener listener) {
        this.listener = listener;
    }
    public interface AppAgreementPopuWindowListener {
        void ok();
        void cancel();
    }
    public AppAgreementPopuWindow(Context context) {
        super(context);
        tv_content=popupView.findViewById(R.id.tv_content);
        tv_cancel=popupView.findViewById(R.id.tv_cancel);
        tv_ok=popupView.findViewById(R.id.tv_ok);

        final SpannableStringBuilder style = new SpannableStringBuilder();

        //设置文字
        style.append("请你务必审慎阅读,充分理解服务协议和隐私政策各条款,在包括但不限于用户注意事项、" +
                "用户行为规范以及为了向你提供服务而收集、使用、存储你个人信息的情况等。你可阅读《服务协议》和《淘大集隐私保护指引》了解详情。如你同意，请点击下方按钮开始接收我们的服务" );
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#00aeef")), 79, 85, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#00aeef")), 86, 97, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new  ClickableSpan() {
            @Override
            public void onClick(View view) {
                //点击的响应事件
                //点击的响应事件
                Intent intent_tv_right=new Intent(context, WebViewActivity.class);
                intent_tv_right.putExtra("url","file:///android_asset/app_agreement.html");
                intent_tv_right.putExtra("title","服务协议");
                context.startActivity(intent_tv_right);

            }
        }, 79, 85, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        style.setSpan(new  ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent_tv_right=new Intent(context, WebViewActivity.class);
                intent_tv_right.putExtra("url","http://m.51taodj.com/tdjh5/ysxy/ysxy.html");
                intent_tv_right.putExtra("title","淘大集隐私保护指引");
                context.startActivity(intent_tv_right);
            }
        }, 86, 97, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //配置给TextView
        tv_content.setMovementMethod(LinkMovementMethod.getInstance());
        tv_content.setText(style);

//        String html = "\n";
//        html += "<a href='https://m.51taodj.com/tdjh5/new/newRegister/serverClause'>《服务协议》</a>"+"和"+"<a href='http://m.51taodj.com/tdjh5/ysxy/ysxy.html'>《淘大集隐私保护指引》</a>"+"了解详情。如你同意，请点击下方按钮开始接收我们的服务";
//        tv_content.setMovementMethod(LinkMovementMethod.getInstance());
//        tv_content.setText(Html.fromHtml(html));
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.ok();
                }
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.cancel();
                }
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
        popupView = createPopupById(R.layout.app_agreement_popu);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }
}
