package cn.com.taodaji.ui.activity.integral.tools;

import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import cn.com.taodaji.R;
import cn.com.taodaji.ui.activity.integral.fragment.IntegralShopFragment;
public class ShowLoadingDialog {
    public static LoadingDialog loadingDialog;

    public static void showLoadingDialog(Context context){
            loadingDialog = new LoadingDialog(context);
            loadingDialog.setLoadingText("加载中...").show();


    }

    public static void close(){
        if (loadingDialog!=null){
            loadingDialog.close();
        }
    }

}
