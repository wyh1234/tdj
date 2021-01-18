package tools.animation;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import cn.com.taodaji.R;
import razerdp.basepopup.BasePopupWindow;

public class MorePopuPWindow extends BasePopupWindow implements View.OnClickListener {
    private View popupView;
    private MorePopuPWindowListener listener;
    private LinearLayout rl,r2,r3;


    public void setMorePopuPWindow(MorePopuPWindowListener listener) {
        this.listener = listener;
    }

    public MorePopuPWindow(Context context) {
        super(context);
        rl=popupView.findViewById(R.id.rl);
        r2=popupView.findViewById(R.id.r2);
        r3=popupView.findViewById(R.id.r3);
        rl.setOnClickListener(this);
        r2.setOnClickListener(this);
        r3.setOnClickListener(this);
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
        popupView = createPopupById(R.layout.more_popu_item);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.r3:
                listener.button_3();
                break;
            case R.id.r2:
                listener.button_2();
                break;
            case R.id.rl:
                listener.button_1();
                break;
        }

    }

    public interface MorePopuPWindowListener {
        void button_1();
        void button_2();
        void button_3();

    }
}
