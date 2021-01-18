package tools.loadingDownFile;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.event.APKInstallEvent;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.model.presenter.RequestService;
import cn.com.taodaji.model.entity.AndroidUpdate;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

import com.base.common.Config;
import com.base.utils.IOUtils;
import com.base.utils.SystemUtils;
import com.base.utils.ThreadManager;
import com.base.utils.UIUtils;

public class DownLoadService extends Service {

    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir = Config.APKSaveDir;
    /**
     * 目标文件存储的文件名
     */
    private String destFileName = "taodaji.apk";


    private int preProgress = 0;
    private int NOTIFY_ID = 1000;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
//    private Retrofit.Builder retrofit;

    private String title;
    private String content;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //接收ApkUpadteAutoUitl  发来的粘性事件
    @Subscribe(sticky = true)
    public void onEvent(AndroidUpdate.DataBean event) {
        EventBus.getDefault().removeStickyEvent(event);
        title = event.getTitle();
        content = event.getContent();
        loadFile(event.getDownload_url());
    }


    //下载进度   接收下载进度事件
    @Subscribe
    public void onEvent(FileLoadingBean event) {

        //为null表示，还在下载
        if (event.getFile() == null) {
            updateNotification(event.getProgress() * 1000 / event.getTotal());
        } else {
            final File file = event.getFile();
            //发送程序可以安装事件  ApkSaveActivity接收
            EventBus.getDefault().postSticky(new APKInstallEvent(file, content));
//            SystemUtils.install(this, event.getFile());
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 下载文件
     */
    private void loadFile(String downLoadUrl) {

        initNotification();
        ModelRequest.getInstance().loadFile(downLoadUrl).enqueue(new FileCallback(destFileDir, destFileName) {
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                UIUtils.showToastSafesShort("连接更新服务器失败，取消更新!");
                cancelNotification();
            }
        });


    }

    /**
     * 初始化Notification通知
     */
    public void initNotification() {
        builder = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.tdj_logo)// 设置通知的图标
                .setContentText("0%")// 进度Text
                // 通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis()).setAutoCancel(true).setContentTitle(title)// 标题
                .setProgress(100, 0, false);// 设置进度条

        // 通过EventBus发布进度信息
        EventBus.getDefault().post(new FileLoadingBean(1, 0));

        Intent intent = new Intent(this, ApkSaveActivity.class);
        /**
         *
         * pendingIntent:封装了Intent的预意图，表示
         * 动作还没有发生，但是会一直保留着这个动作
         * context 上下文
         * requestCode:请求码
         * intent ：跳转意图
         * Flag：标记
         */
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 200, intent, PendingIntent.FLAG_ONE_SHOT);

        //PendingIntent.FLAG_NO_CREATE;一般用一个
        builder.setContentIntent(pendingIntent);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);// 获取系统通知管理器
        notificationManager.notify(NOTIFY_ID, builder.build());// 发送通知
    }

    /**
     * 更新通知
     */
    public void updateNotification(long progress) {
        int currProgress = (int) progress;
        if (preProgress < currProgress) {
            builder.setContentText(((float) progress / 10) + "%");
            builder.setProgress(100, currProgress, false);
            notificationManager.notify(NOTIFY_ID, builder.build());
        }
        preProgress = (int) progress;
    }

    /**
     * 取消通知
     */
    public void cancelNotification() {
        notificationManager.cancel(NOTIFY_ID);
    }
}
