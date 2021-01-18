package cn.com.taodaji.ui.activity.homepage;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.base.common.Config;
import com.base.takephoto.model.TResult;
import com.base.utils.IOUtils;
import com.base.utils.SystemUtils;
import com.base.utils.ViewUtils;
import com.base.widget.FloatingDragView;

import java.io.File;

import cn.com.taodaji.R;
import tools.activity.MenuToolbarActivity;
import tools.activity.TakePhotosActivity;
import tools.extend.TakePhotoUtils;
import tools.share.ShareUtils;

/**
 * Created by yangkuo on 2018-08-16.
 */
public class AppletWebActivity extends TakePhotosActivity {

    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.activity_applet_web);
    }


    private WebView webview;
    private String url;
    private ProgressBar progressBar1;


    private int count = 0;
    private FloatingDragView floatingDragView;


    private boolean isFirst = false;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;//4.4之后用
    private ValueCallback<Uri> uploadFile;//4.4之前用

    @Override
    protected void titleSetting(Toolbar toolbar) {

    }

    @Override
    protected Toolbar initToolbar() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        setStatusBarColor(R.color.orange_ff5f17);
        return null;
    }

    @SuppressLint("JavascriptInterface")
    @Override
    protected void initMainView() {
        url = getIntent().getStringExtra("url");
        webview = (WebView) findViewById(R.id.webView1);
        webview.requestFocusFromTouch();
        progressBar1 = findViewById(R.id.progressBar1);
        floatingDragView = findViewById(R.id.bt_floating);
        floatingDragView.setOnClickListener(v -> MenuToolbarActivity.goToPage(2));


        WebSettings webSettings = webview.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);


        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDatabaseEnabled(true);//允许加载视频
        webSettings.setDomStorageEnabled(true); //处理js方法调用出错情况
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//设置脚本是否允许自动打开弹窗，默认false，不允许
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局


        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setBuiltInZoomControls(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setUseWideViewPort(true); // 关键点
        webSettings.setPluginState(WebSettings.PluginState.ON);
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
//        webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }


        // sebs.setTextZoom(140);//字体大小默认是100
        //加载需要显示的网页
//        webview.loadUrl("http://admin.taodaji.com.cn/platform/toApplyJoinCity.html?applySource=1");
        webview.loadUrl(url);

       //Android调用JS方法有参数
//        webview.loadUrl("javascript:javacalljs()");
//        webview.loadUrl("javascript:sum(6,6)");

//        对于JS调用Android代码的方法有3种：

//        通过WebView的addJavascriptInterface（）进行对象映射
//        通过 WebViewClient 的shouldOverrideUrlLoading ()方法回调拦截 url
//        通过 WebChromeClient 的onJsAlert()、onJsConfirm()、onJsPrompt（）方法回调拦截JS对话框alert()、confirm()、prompt（） 消息

        //添加与js的交互接口,起的名称与js代码中的接口名称要一致
        webview.addJavascriptInterface(this, "webViewController");
        //设置Web视图
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress > progressBar1.getProgress()) {
//                    progressBar1.setVisibility(View.VISIBLE);
                    progressBar1.setProgress(newProgress);
                }
                if (newProgress == 100) {
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar1.setVisibility(View.GONE);
                        }
                    }, 500);

                }
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = ViewUtils.showDialog(getBaseActivity(), "提示", message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }


            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                String fileName = System.currentTimeMillis() + ".jpg";
                Uri imageUri = Uri.fromFile(IOUtils.createFile(Config.imageSaveDir, fileName));
                mUploadCallbackAboveL = filePathCallback;
                if (fileChooserParams.getMode() == FileChooserParams.MODE_OPEN) {
                    TakePhotoUtils.getInstance().setCrop(true).setImageUri(imageUri).openPhotoAlbum(getTakePhoto(), 1, true);
                } else if (fileChooserParams.getMode() == FileChooserParams.MODE_OPEN_MULTIPLE) {
                    TakePhotoUtils.getInstance().setCrop(true).setImageUri(imageUri).openPhotoAlbum(getTakePhoto(), 9, true);
                }
                return true;
            }

            //4.4以前使用
            public void openFileChooser(ValueCallback<Uri> uploadFiles, String acceptType, String capture) {
                uploadFile = uploadFiles;
                String fileName = System.currentTimeMillis() + ".jpg";
                Uri imageUri = Uri.fromFile(IOUtils.createFile(Config.imageSaveDir, fileName));
                TakePhotoUtils.getInstance().setCrop(true).setImageUri(imageUri).openPhotoAlbum(getTakePhoto(), 1, true);
            }

        });
        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    SystemUtils.callPhone(AppletWebActivity.this, url);
                    return true;
                } else if (url.startsWith("sms:")) {
                    SystemUtils.sendMessage(AppletWebActivity.this, url.replace("sms:", ""), "");
                    return true;
                }
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                String homePage = "http://msg.taodaji.com.cn/m/index.php?mod=index";
                String history = "http://msg.taodaji.com.cn/m/index.php?mod=history";
                String myself = "http://msg.taodaji.com.cn/m/index.php?mod=member";
                String post = "http://msg.taodaji.com.cn/m/index.php?mod=post";

                if (url.startsWith(homePage) || url.startsWith(history) || url.startsWith(myself) || url.startsWith(post)) {
                    isFirst = true;
                } else isFirst = false;


            }

        });
    }

    @JavascriptInterface
    public void shareWeb(String url, String title, String description) {
        new ShareUtils(this).shareWeb(url, title, description);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        int count = result.getImages().size();
        if (count > 0) {
            Uri[] results = new Uri[count];
            for (int i = 0; i < count; i++) {
                String path = result.getImages().get(i).getCompressPath();
                results[i] = Uri.fromFile(new File(path));
            }
            if (mUploadCallbackAboveL != null) {
                mUploadCallbackAboveL.onReceiveValue(results);
                mUploadCallbackAboveL = null;
            } else if (uploadFile != null && results.length > 0) {
                uploadFile.onReceiveValue(results[0]);
                uploadFile = null;
            }
        }

    }

    @Override
    public void takeCancel() {
        super.takeCancel();
        if (uploadFile != null) {
            uploadFile.onReceiveValue(null);
            uploadFile = null;
        }
        if (mUploadCallbackAboveL != null) {
            mUploadCallbackAboveL.onReceiveValue(null);
            mUploadCallbackAboveL = null;
        }
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        if (uploadFile != null) {
            uploadFile.onReceiveValue(null);
            uploadFile = null;
        }
        if (mUploadCallbackAboveL != null) {
            mUploadCallbackAboveL.onReceiveValue(null);
            mUploadCallbackAboveL = null;
        }
    }

    @Override
    public void onBackPressed() {
        if (isFirst) super.onBackPressed();
        else if (webview != null) webview.goBack();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview.clearFormData();
        webview.destroy();
    }

    public static void startActivity(Context context, String url) {
        Intent intent = new Intent(context, AppletWebActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

}
