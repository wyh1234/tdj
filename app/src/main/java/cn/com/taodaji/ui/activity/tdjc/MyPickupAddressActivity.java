package cn.com.taodaji.ui.activity.tdjc;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.apkfuns.logutils.LogUtils;
import com.base.utils.UIUtils;

import cn.com.taodaji.R;
import tools.activity.SimpleToolbarActivity;

public class MyPickupAddressActivity  extends SimpleToolbarActivity implements
        AMap.OnMarkerClickListener, AMap.OnMapLoadedListener, AMap.OnMapClickListener, LocationSource, AMapLocationListener{
    View mainView;
    MapView mapView;
    private AMap mAMap;
    //你编码对象
    private AMapLocation location;
    private LocationSource.OnLocationChangedListener mListener;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private double currentLatitude,currentLongitude;

    private MarkerOptions markOptions;
    private MyLocationStyle myLocationStyle;
    private Marker mGPSMarker;             //定位位置显示

    private MarkerOptions markerOption;
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText(getIntent().getStringExtra("communityShortName"));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMap(savedInstanceState);
    }
    private void initMap(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        mAMap = mapView.getMap();
        // 设置定位监听
        mAMap.setOnMapLoadedListener(this);
        mAMap.setOnMarkerClickListener(this);
        mAMap.setOnMapClickListener(this);

        mAMap.setLocationSource(this);
        //设置地图拖动监听


        //地图UI设置
        MyLocationStyle myLocationStyle = new MyLocationStyle();
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.current_loction));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        //myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        //myLocationStyle.strokeWidth(0f);// 设置圆形的边框粗细
        //myLocationStyle.anchor(0.5f, 0.7f);
        mAMap.setMyLocationStyle(myLocationStyle.showMyLocation(true));

//        mAMap.moveCamera(CameraUpdateFactory.zoomTo(17)); //缩放比例
        LatLng la = new LatLng(Double.parseDouble(getIntent().getStringExtra("lat")), Double.parseDouble(getIntent().getStringExtra("lon")));
        mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(la,17)); //缩放比例，并显示指定位置

        //设置amap的属性
        UiSettings settings = mAMap.getUiSettings();
        settings.setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        settings.setZoomControlsEnabled(false);
        mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
    }




    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.my_pick_up_address_activity);
        body_other.addView(mainView);
        mapView=mainView.findViewById(R.id.mv_shop_address);

    }

    private void setMarket() {
        LatLng la = new LatLng(Double.parseDouble(getIntent().getStringExtra("lat")), Double.parseDouble(getIntent().getStringExtra("lon")));
        LogUtils.i(la);
        int width = (mapView.getWidth()) / 2;
        int height = ((mapView.getHeight()) / 2)-25;
        markOptions = new MarkerOptions();

        markOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.shop_location_selected))).anchor(0.5f, 0.7f);
        //设置一个角标
        mGPSMarker = mAMap.addMarker(markOptions);
        //设置marker在屏幕的像素坐标
        mGPSMarker.setPosition(la);
        mGPSMarker.setTitle(getIntent().getStringExtra("communityShortName"));
        mGPSMarker.setSnippet(getIntent().getStringExtra("address"));
        //设置像素坐标
        markOptions.draggable(false);//设置Marker可拖动(flase不可拖到)
//        mGPSMarker.setPositionByPixels(width, height);//draggable(true)并且设置setPositionByPixels才可拖动
//        if (!content.equals(null)){
//            mGPSMarker.showInfoWindow();
//        }
        mapView.invalidate();


    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        location = aMapLocation;
//        if (mListener != null && location != null) {
//            if (location != null && location.getErrorCode() == 0) {
//              mListener.onLocationChanged(location);// 显示系统箭头
//               currentLongitude = location.getLongitude();
//               currentLatitude = location.getLatitude();
//                LogUtils.e(currentLatitude+"----"+currentLongitude);
//            }
//        } else {
//            UIUtils.showToastSafesShort("定位失败");
//        }
    }


    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLoaded() {

        setMarket();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.isInfoWindowShown()) {
            marker.hideInfoWindow();

        } else {
            marker.showInfoWindow();
        }
        return false;
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000 * 10);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
    }

    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
        // 销毁定位
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mapView.onDestroy();
    }


}
