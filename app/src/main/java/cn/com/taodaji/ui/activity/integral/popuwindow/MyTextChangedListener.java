package cn.com.taodaji.ui.activity.integral.popuwindow;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

public class MyTextChangedListener implements TextWatcher {
    private Context context;
    public MyTextChangedListener(Context context){
        this.context=context;
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length()==11){
            listener.viewshow();
        }else {
            listener.viewhide();
        }
    }
    private ViewListener listener;
    public interface ViewListener {
        void viewshow();
        void viewhide();
    }

    public void setOnItemClickListener(ViewListener listener) {
        this.listener = listener;
    }

}
