package tools.loadingDownFile;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.AndroidUpdate;
import cn.com.taodaji.model.event.APKInstallEvent;
import tools.activity.DataBaseActivity;

import com.base.listener.OnPermissionListener;
import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public class ApkSaveActivity extends DataBaseActivity {


    private File saveFile;

    private TextView tv_mess, bt_ok;
    private AndroidUpdate.DataBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isScreenVertical = false;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        bean = (AndroidUpdate.DataBean) getIntent().getSerializableExtra("data");

        tv_mess = findViewById(R.id.tv_mess);
        bt_ok = findViewById(R.id.bt_ok);
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bt_ok.getText().toString().equals("立即升级")) {


                    if (SystemUtils.isServiceExisted(ApkSaveActivity.this, DownLoadService.class.getName()))
                        return;
                    addPermissionListen(1000, new OnPermissionListener() {
                        @Override
                        public void permissionSuccess(int requestCode) {
                            if (bean != null) {
                                // 启动后台服务下载apk
                                EventBus.getDefault().postSticky(bean);//向DownLoadService  发送粘性下载事件
                                startService(new Intent(ApkSaveActivity.this, DownLoadService.class));
                            }

                        }

                        @Override
                        public void permissionFail(int requestCode) {
                            //提示没有权限，安装不了咯
                            UIUtils.showToastSafesShort("无法获取安装权限，请从应用宝更新");
                            bt_ok.setText("退出");
                        }
                    });
                    permissionRequest(1000, Manifest.permission.WRITE_EXTERNAL_STORAGE);

                } else if (bt_ok.getText().toString().equals("安装")) {
                    if (saveFile != null) SystemUtils.install(ApkSaveActivity.this, saveFile);
                } else if (bt_ok.getText().toString().equals("退出")) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }
        });
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected View getContentLayout() {
        return ViewUtils.getLayoutViewMatch(this, R.layout.upgrade_dialog);
    }

    //下载完毕后才打开本页面，
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(APKInstallEvent event) {
        tv_mess.setText(event.getVersion_info());
        if (event.getFile() != null) {
            saveFile = event.getFile();
            bt_ok.setText("安装");
        }
    }

    //下载时就打开本页面   接收下载进度事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FileLoadingBean event) {
        //为null表示，还在下载
        if (event.getFile() == null) {
            long pro = event.getProgress() * 1000 / event.getTotal();
            bt_ok.setText(String.valueOf((float) pro / 10) + " %");
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
