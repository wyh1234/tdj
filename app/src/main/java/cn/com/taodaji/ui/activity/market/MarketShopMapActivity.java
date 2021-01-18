package cn.com.taodaji.ui.activity.market;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;

import java.util.HashMap;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.MarketLocal;

import com.base.retrofit.RequestCallback;

import tools.activity.SimpleToolbarActivity;

import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;


public class MarketShopMapActivity extends SimpleToolbarActivity implements LocationSource, AMapLocationListener, AMap.OnMarkerClickListener {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setToolBarColor(R.color.orange_yellow_ff7d01);
        simple_title.setText("逛市场");
        right_textView.setText("列表");
        right_textView.setTextColor(UIUtils.getColor(R.color.white));
        toolbar.setNavigationIcon(null);
        toolbar.setContentInsetsRelative(UIUtils.dip2px(50), UIUtils.dip2px(5));
        right_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private MapView mapView;
    private AMap aMap;
    private AMapLocationClient locationClient = null;
    private MarkerOptions markerOption;
    private LatLng latlng = null;
    private LocationSource.OnLocationChangedListener mListener;
    private boolean isOK = false;
    Map<Marker, MarketLocal.DataBean> marketLocalMap = new HashMap<>();

    //    private boolean isMove = true;//定位结束后移动到当前定位的位置
//    private MarketLocal marketLocal;//开放市场的位置信息
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView = getLayoutView(R.layout.t_mapview);

        body_other.addView(mapView);
        mapView.onCreate(savedInstanceState);//
        //移动到位置
        switch (PublicCache.site){
            case 2:
                latLng = XIANGYANG_LAT;
                break;
            case 3:
                latLng = WUHAN_LAT;
                break;
                default:
                    latLng = new LatLng(PublicCache.latitude, PublicCache.longtitude);
                    break;
        }
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }

        aMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(13));//改变地图的缩放级别
        aMap.setOnMarkerClickListener(this);


        //初始化定位
        initLocation();
        startLocation();


        //获取市场位置信息
        getmarketData();
    }

    @Override
    protected void initMainView() {

    }


    @Override
    public void initData() {

    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
//        myLocationStyle.anchor( int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
    }

    /**
     * 在地图上添加marker
     */
    private Marker addMarkersToMap(double x, double y, String title, String detail) {
        markerOption = new MarkerOptions()
                //  .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .icon(BitmapDescriptorFactory.fromBitmap(getIconToMarker(title))).position(new LatLng(y, x))
                // .title(title)
                //.snippet(detail)
                .draggable(true);
        Marker marker = aMap.addMarker(markerOption);
        marker.showInfoWindow();
        return marker;
    }

    private Bitmap getIconToMarker(String marketName) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_map_text).copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bmp);
        canvas.drawBitmap(bmp, 0, 0, null);

        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(UIUtils.dip2px(14));
        StaticLayout sl = new StaticLayout(marketName, textPaint, bmp.getWidth() - 8, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        canvas.translate(6, UIUtils.dip2px(3));
        sl.draw(canvas);
        return bmp;
    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(true);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        Intent intent = new Intent(getBaseActivity(), MarketStrollShopListActivity.class);
        intent.putExtra("marketId", marketLocalMap.get(marker).getEntityId());
        startActivity(intent);
        return true;
    }

    /**
     * 显示市场位置
     */
    private void showMarket(MarketLocal body) {
        marketLocalMap.clear();
        for (MarketLocal.DataBean ml : body.getData()) {
            marketLocalMap.put(addMarkersToMap(ml.getXPos(), ml.getYPos(), ml.getName(), ml.getAddress()), ml);
        }
    }

    private void getmarketData() {
        getRequestPresenter().getMarket_ListAll(new RequestCallback<MarketLocal>() {
            @Override
            public void onSuc(MarketLocal body) {
                showMarket(body);
            }

            @Override
            public void onFailed(int marketLocal, String msg) {

            }
        });
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(final AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
//                if (isMove) {
//                    isMove = false;
                aMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())));
                aMap.moveCamera(CameraUpdateFactory.zoomTo(13));//改变地图的缩放级别
//                    aMap.setOnMarkerClickListener(this);
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点

            } else if (amapLocation.getErrorCode() == 12) {
                ViewUtils.showDialog(this, "提示信息", "应用缺少定位权限，定位功能暂时无法使用。如若需要，请进入设置页面授权。").setNegativeButton("取消", (dialog, which) -> dialog.dismiss()).setPositiveButton("进入", (dialog, which) -> {
                    dialog.dismiss();
                    SystemUtils.startManageJurisdiction(this);
                }).show();
            } else if (amapLocation.getErrorCode() == 7) {
                UIUtils.showToastSafesShort("测试包，无法使用高德地图定位");
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
//                UIUtils.showToastSafesShort("定位失败，" + JavaMethod.getFieldValue(amapLocation, "q") + "，请打开手机位置信息");
                Log.e("AmapErr", errText);
            }


        }
    }

    /**
     * 初始化定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(this);
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        locationClient.setLocationListener(this);
    }

    @Override
    public void activate(LocationSource.OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
        stopLocation();
        destroyLocation();
    }

    /**
     * 开始定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void startLocation() {
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 停止定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void stopLocation() {
        // 停止定位
        locationClient.stopLocation();
    }

    /**
     * 销毁定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


}
