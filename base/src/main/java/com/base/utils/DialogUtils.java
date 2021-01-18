package com.base.utils;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.R;
import com.base.viewModel.BaseUtils;
import com.zyinux.specialstring.relase.SpecialStringBuilder;

import static com.base.utils.UIUtils.getResources;


/**
 * Created by yangkuo on 2018-08-14.
 */
public class DialogUtils {


    private AlertDialog alertDialog;
    private Context context;

    private View contentView;

    public interface DialogUtilsListener{
        void viewsListener();
    }

    private DialogUtils(Context context) {
        this.context = context;
    }

    public static DialogUtils getInstance(Context context) {
        return new DialogUtils(context);
    }

    public DialogUtils getTipsWithButtonDialog(String title,String mess_content[],int mess_index[]){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.dialog_tips_with_button, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(contentView);
        TextView tv_mess = contentView.findViewById(R.id.tv_mess);
        Button button = contentView.findViewById(R.id.btn_I_see);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        if (mess_content.length > 0&&mess_index.length > 0 && tv_mess != null){

            MySpecialStyle style=new MySpecialStyle();
            SpecialStringBuilder sb=new SpecialStringBuilder();

            for (int i = 0; i < mess_content.length; i++) {
                boolean isSelect=false;
                for (int j = 0; j < mess_index.length; j++) {
                    if (i+1 == mess_index[j]) {
                        isSelect=true;
                    }
                }
                if (isSelect) {
                    style.setColor(ColorUtil.getColor(R.color.orange_yellow_ff7d01));
                    sb.append(mess_content[i],style);
                }else{
                    style.setColor(ColorUtil.getColor(R.color.black_4b));
                    // style.setAbsoluteSizeStyle(DensityUtil.sp2px(16f));
                    sb.append(mess_content[i],style);
                }
            }

            tv_mess.setText(sb.getCharSequence());
        }
        TextView tv_title = contentView.findViewById(R.id.tv_title);
        if (tv_title != null) {
            if (TextUtils.isEmpty(title)) {
                tv_title.setVisibility(View.GONE);
            }else{
                tv_title.setText(title);
            }
        }

        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        return this;
    }

    public DialogUtils getSimpleDialog(String... mess_title) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.dialog_customer, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(contentView);
        TextView tv_mess = contentView.findViewById(R.id.tv_mess);
        if (mess_title.length > 0 && tv_mess != null) tv_mess.setText(mess_title[0]);

        TextView tv_title = contentView.findViewById(R.id.tv_title);
        if (mess_title.length > 1 && tv_title != null) tv_title.setText(mess_title[1]);
        if (mess_title.length == 1 && tv_title != null) tv_title.setVisibility(View.GONE);

        setNegativeButton("取消", null);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        return this;
    }
    public DialogUtils getSimpleColorDialog(String title,String mess_content[],int mess_index[]) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.dialog_customer, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(contentView);
        TextView tv_mess = contentView.findViewById(R.id.tv_mess);
        if (mess_content.length > 0&&mess_index.length > 0 && tv_mess != null){

            MySpecialStyle style=new MySpecialStyle();
            SpecialStringBuilder sb=new SpecialStringBuilder();

            for (int i = 0; i < mess_content.length; i++) {
                boolean isSelect=false;
                for (int j = 0; j < mess_index.length; j++) {
                    if (i+1 == mess_index[j]) {
                        isSelect=true;
                    }
                }
                if (isSelect) {
                    style.setColor(ColorUtil.getColor(R.color.orange_yellow_ff7d01));
                    sb.append(mess_content[i],style);
                }else{
                    style.setColor(ColorUtil.getColor(R.color.black_4b));
                    // style.setAbsoluteSizeStyle(DensityUtil.sp2px(16f));
                    sb.append(mess_content[i],style);
                }
            }

            tv_mess.setText(sb.getCharSequence());
        }

        TextView tv_title = contentView.findViewById(R.id.tv_title);
        if (tv_title != null) {
            if (TextUtils.isEmpty(title)) {
                tv_title.setVisibility(View.GONE);
            }else{
                tv_title.setText(title);
            }
        }

        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        return this;
    }
    public DialogUtils getSingleDialog(@LayoutRes int resource, String... mess_title) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(resource, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setCancelable(false);
        builder.setView(contentView);
        TextView tv_mess = contentView.findViewById(R.id.tv_mess);
        if (mess_title.length > 0 && tv_mess != null) tv_mess.setText(mess_title[0]);
        TextView tv_title = contentView.findViewById(R.id.tv_title);
        if (mess_title.length > 1 && tv_title != null) tv_title.setText(mess_title[1]);
        if (mess_title.length == 1 && tv_title != null) tv_title.setVisibility(View.GONE);


        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

        return this;
    }



    public DialogUtils setViewValues(@IdRes int viewId, Object value) {
        View view = contentView.findViewById(viewId);
        if (view != null) {
            BaseUtils.setValues(view, value);
        }
        return this;
    }


    public DialogUtils setViewOnClick(@IdRes int viewId, DialogUtilsListener listener) {
        View view = contentView.findViewById(viewId);
        if (view != null && listener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();
                    listener.viewsListener();
                }
            });
        }
        return this;
    }


    public DialogUtils setPositiveButton(String positiveButtonText, DialogInterface.OnClickListener listener, int... colorId_viewId) {
        TextView textView = contentView.findViewById(colorId_viewId.length > 1 ? colorId_viewId[1] : R.id.bt_ok);
        if (textView != null) {
            textView.setText(positiveButtonText);
            if (colorId_viewId.length > 0 && colorId_viewId[0] != 0)
                textView.setTextColor(ContextCompat.getColor(context, colorId_viewId[0]));


            textView.setOnClickListener(v -> {
                dismiss();
                if (listener != null) listener.onClick(alertDialog, 1);
            });
        }

        return this;
    }

    public DialogUtils setNegativeButton(String negativeButtonText, DialogInterface.OnClickListener listener, int... colorId_viewId) {
        TextView textView = contentView.findViewById(colorId_viewId.length > 1 ? colorId_viewId[1] : R.id.bt_cancel);

        if (textView != null) {
            textView.setText(negativeButtonText);
            if (colorId_viewId.length > 0 && colorId_viewId[0] != 0)
                textView.setTextColor(ContextCompat.getColor(context, colorId_viewId[0]));

            textView.setOnClickListener(v -> {
                dismiss();
                if (listener != null) listener.onClick(alertDialog, 0);
            });
        }

        return this;
    }

    public DialogUtils show() {
        if (alertDialog != null && !alertDialog.isShowing()) alertDialog.show();
        return this;
    }

    public void dismiss() {
        if (alertDialog != null && alertDialog.isShowing()) alertDialog.dismiss();
    }
    public static SpecialStringBuilder getTipsContent(String name, String nickName, int productType, int productCriteria, int isP){

        MySpecialStyle style=new MySpecialStyle();
        SpecialStringBuilder sb=new SpecialStringBuilder();

            style.setColor(ColorUtil.getColor(R.color.orange_yellow_ff7d01));


        style.setColor(ColorUtil.getColor(R.color.black_4b));
        style.setAbsoluteSizeStyle(DensityUtil.sp2px(16f));
        sb.append(name,style);


        return sb;
    }
}
