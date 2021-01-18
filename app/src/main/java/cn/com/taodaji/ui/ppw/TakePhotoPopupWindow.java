package cn.com.taodaji.ui.ppw;

import android.view.View;

import com.base.utils.ViewUtils;

import cn.com.taodaji.R;

public abstract class TakePhotoPopupWindow extends BasePopupWindow implements View.OnClickListener {
    public TakePhotoPopupWindow(View mainView) {
        super(mainView);
    }

    @Override
    public boolean isTransparent() {
        return false;
    }

    @Override
    public View createContextView(View mainView) {
        View view = ViewUtils.getLayoutView(mainView.getContext(), R.layout.popup_window_take_photo);
        ViewUtils.findViewById(view, R.id.text_camera).setOnClickListener(this);
        ViewUtils.findViewById(view, R.id.text_album).setOnClickListener(this);
        ViewUtils.findViewById(view, R.id.text_cancel).setOnClickListener(this);
        return view;
    }

    public abstract void goCamera();

    public abstract void goAlbum();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_camera:
                goCamera();
                dismiss();
                break;
            case R.id.text_album:
                goAlbum();
                dismiss();
                break;
            case R.id.text_cancel:
                dismiss();
                break;
        }
    }
}
