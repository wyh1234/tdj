package cn.com.taodaji.ui.activity.myself;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import cn.com.taodaji.R;

import cn.com.taodaji.model.entity.AndroidUpdate;
import cn.com.taodaji.model.event.APKInstallEvent;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.ui.activity.login.AgreementActivity;
import tools.activity.SimpleToolbarActivity;
import tools.loadingDownFile.ApkSaveActivity;

import com.base.common.Config;
import com.base.event.VersionEvent;
import com.base.retrofit.RequestCallback;
import com.base.utils.IOUtils;
import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;


public class AboutMyselfActivity extends SimpleToolbarActivity {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("关于我们");
    }

    private View mainView;

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_myself_about_myself);
        body_other.addView(mainView);
        TextView text = ViewUtils.findViewById(mainView, R.id.text);
        text.setText("淘大集" + SystemUtils.getVersionName() + "版本");
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.about_tdj:
                Intent intent = new Intent(this, AboutTdjActivity.class);
                startActivity(intent);
                break;
            case R.id.checkUpdate:
                //检查是否更新
//                Beta.checkUpgrade(false, false);
                //checkUpdate();
                EventBus.getDefault().post(new VersionEvent(true));
                break;
            case R.id.agreement:
                startActivity(new Intent(this, AgreementActivity.class));
                break;
        }
    }

   /* *//**
     * - 检测软件更新
     *//*
    public void checkUpdate() {
        *//**
         * 在这里请求后台接口，获取更新的内容和最新的版本号
         *//*
        RequestPresenter.getInstance().androidUpdate(new RequestCallback<AndroidUpdate>() {
            @Override
            public void onSuc(AndroidUpdate body) {
                AndroidUpdate.DataBean bean = body.getData();
                // 版本的更新信息
                String version_info = body.getData().getContent();
                String mVersion_name = SystemUtils.getVersionName();// 当前的版本号
                String nVersion_code = body.getData().getVersioin();
                // 显示提示对话
                String particular = version_info.replace("\\n", "\n");
                bean.setContent(particular);

                if (mVersion_name.compareTo(nVersion_code) < 0) {
                    Intent intent = new Intent(getBaseActivity(), ApkSaveActivity.class);
                    intent.putExtra("data", bean);
                    startActivity(intent);

                    //检查本地是否有该版本的安装包
                    String absPath = Config.APKSaveDir + "/taodaji.apk";

                    final File file = new File(absPath);
                    if (IOUtils.fileIsExists(file) && nVersion_code.compareTo(SystemUtils.getApkVersionName(absPath, getBaseActivity())) == 0) {
                        //发送程序可以安装事件  ApkSaveActivity接收
                        EventBus.getDefault().postSticky(new APKInstallEvent(file, particular + "\n\n 已下载完毕,请安装"));
                    } else {
                        //发送程序可以安装事件  ApkSaveActivity接收
                        EventBus.getDefault().postSticky(new APKInstallEvent(null, particular));
                    }

                } else {
                    UIUtils.showToastSafesShort("已是最新版本");
                }


            }

            @Override
            public void onFailed(int androidUpdate, String msg) {

            }
        });
    }
*/
    @Override
    protected void initData() {

    }
}
