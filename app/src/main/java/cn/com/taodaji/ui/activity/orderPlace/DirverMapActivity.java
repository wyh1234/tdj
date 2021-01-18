package cn.com.taodaji.ui.activity.orderPlace;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.base.glide.GlideImageView;
import com.base.retrofit.RequestCallback;
import com.base.utils.BitmapUtil;
import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;
import com.base.utils.UserNameUtill;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.DegreeOfSatisfaction;
import cn.com.taodaji.model.entity.DriverLocation;
import cn.com.taodaji.model.entity.OrderDetail;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.model.presenter.RequestPresenter2;
import tools.extend.PhoneUtils;

import static com.amap.api.services.route.RouteSearch.DRIVING_SINGLE_DEFAULT;
import static com.amap.api.services.route.RouteSearch.TRUCK_AVOID_CONGESTION;

public class DirverMapActivity extends AppCompatActivity implements View.OnClickListener, RouteSearch.OnRouteSearchListener {
    private MapView mMapView;
    private AMap mAMap;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    private GlideImageView driverIcon;
    private TextView deliveryComplaint,deliverStatus,driverRate,driverName,distance,deliveryEvaluation,deliveryDate;
    private LinearLayout logisticsEvaluation,callDriver;
    private OrderDetail orderDetail= new OrderDetail();
    private int complaintType=0,evaluationType=0;
    private RouteSearch routeSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_dirver_map);
        orderDetail = (OrderDetail) getIntent().getSerializableExtra("orderDetail");
        initMap(savedInstanceState);

        deliveryComplaint = (TextView) findViewById(R.id.tv_delivery_complaint);
        deliveryComplaint.setOnClickListener(this);

        deliveryEvaluation = (TextView) findViewById(R.id.tv_delivery_evaluation);

        deliveryDate = (TextView) findViewById(R.id.tv_delivery_date);

        deliverStatus = (TextView) findViewById(R.id.tv_delivery_status);
        deliverStatus.setOnClickListener(this);

        logisticsEvaluation = (LinearLayout) findViewById(R.id.ll_logistics_evaluation);
        logisticsEvaluation.setOnClickListener(this);

        callDriver = (LinearLayout) findViewById(R.id.ll_call_driver);
        callDriver.setOnClickListener(this);

        driverIcon = (GlideImageView) findViewById(R.id.giv_driver_icon);
        driverIcon.setImageBitmap(BitmapUtil.toRoundBitmap(R.mipmap.driver_icon));
        if (PublicCache.loginPurchase != null) {
            if (PublicCache.loginPurchase.getImgCheckStatus() == 1) {
                driverIcon.loadImage(PublicCache.loginPurchase.getImageUrl());
            }
        }

        driverRate = (TextView) findViewById(R.id.tv_driver_rate);
        driverName = (TextView) findViewById(R.id.tv_driver_name);
        driverName.setText(orderDetail.getItems().get(0).getDriverName());
        distance = (TextView)findViewById(R.id.tv_distance);
        distance.setVisibility(View.VISIBLE);
        deliveryDate.setText("当前距离收获地址还有");

        routeSearch = new RouteSearch(this);
        routeSearch.setRouteSearchListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_delivery_complaint:
                Intent intent = new Intent(DirverMapActivity.this,LogisticsComplaintsActivity.class);
                intent.putExtra("orderDetail",orderDetail);
                intent.putExtra("type",complaintType);
                startActivity(intent);
                break;
            case R.id.tv_delivery_status:
                finish();
                break;
            case R.id.ll_logistics_evaluation:
                Intent intent1 = new Intent(DirverMapActivity.this,LogisticsEvaluationActivity.class);
                intent1.putExtra("orderDetail",orderDetail);
                intent1.putExtra("type",evaluationType);
                startActivity(intent1);
                break;
            case R.id.ll_call_driver:
                String phone = orderDetail.getItems().get(0).getDriverTel();
                if (!UserNameUtill.isPhoneNO(phone)) {
                    UIUtils.showToastSafesShort("手机号码格式不正确");
                    return;
                }
                SystemUtils.callPhone(DirverMapActivity.this,phone);
                default:break;
        }
    }

    private void initMap(Bundle savedInstanceState) {
        mMapView = (MapView) findViewById(R.id.mv_dirver_map);
        mMapView.onCreate(savedInstanceState);
        if (mAMap == null){
            mAMap = mMapView.getMap();
        }
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(17)); //缩放比例

        //设置amap的属性
        UiSettings settings = mAMap.getUiSettings();
        settings.setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        settings.setZoomControlsEnabled(false);
        mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        initDriverMap();
    }

    public void initDriverMap(){
        String orderNo = orderDetail.getOrderNo();
        String driverTel = orderDetail.getItems().get(0).getDriverTel();
        String extOrderId = orderDetail.getExtOrderId();
        int addressId = orderDetail.getCustomerAddrId();
        int driverId = orderDetail.getItems().get(0).getDriverId();
        RequestPresenter.getInstance().getDriverLocation(orderNo, driverTel, addressId, new RequestCallback<DriverLocation>() {
            @Override
            protected void onSuc(DriverLocation body) {
                if (body.getErr()==0){
                    switch (body.getData().getDriverStatus()){
                        case 5:
                            deliverStatus.setText("已送达");
                            distance.setVisibility(View.GONE);
                            deliveryDate.setText("已于"+body.getData().getDeliveryTime()+"送达");
                            break;
                        case 6:
                            deliverStatus.setText("配送中");
                            break;
                            default:break;
                    }
                    if (body.getData().getCustomerLat()>0&&body.getData().getCustomerLng()>0) {
                        MarkerOptions store = new MarkerOptions().anchor(0.5f,0.5f)
                                .position(new LatLng(body.getData().getCustomerLat(), body.getData().getCustomerLng()))
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.store));
                        mAMap.addMarker(store);
                    }else {
                        UIUtils.showToastSafe("无店铺坐标");
                    }
                    if (body.getData().getDriverLat()>0&&body.getData().getDriverLng()>0){
                        MarkerOptions driver = new MarkerOptions().anchor(0.5f,0.5f)
                                .position(new LatLng(body.getData().getDriverLat(),body.getData().getDriverLng()))
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.delivery_car));
                        mAMap.addMarker(driver);
                    }else {
                        UIUtils.showToastSafe("无司机坐标");
                    }
                    if (body.getData().getDriverStatus()==6&&body.getData().getCustomerLat()>0&&body.getData().getCustomerLng()>0&&body.getData().getDriverLat()>0&&body.getData().getDriverLng()>0){
                        LatLng storePoint = new LatLng(body.getData().getCustomerLat(), body.getData().getCustomerLng());
                        LatLng driverPoint = new LatLng(body.getData().getDriverLat(),body.getData().getDriverLng());
                        float distanceNum = AMapUtils.calculateLineDistance(storePoint,driverPoint);
                        if ((int)distanceNum>1000){
                            distance.setText((int)distanceNum/1000+"公里");
                        }else {
                            distance.setText((int)distanceNum + "米");
                        }
                        mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(driverPoint,setMapZoom((int)distanceNum)));
                        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(new LatLonPoint(driverPoint.latitude, driverPoint.longitude),
                                new LatLonPoint(storePoint.latitude, storePoint.longitude));
                        RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, DRIVING_SINGLE_DEFAULT, null, null, "");
                        routeSearch.calculateDriveRouteAsyn(query);
                    }
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafe(msg);
            }
        });

        RequestPresenter2.getInstance().getDegreeOfSatisfaction(driverId, extOrderId,new RequestCallback<DegreeOfSatisfaction>() {
            @Override
            protected void onSuc(DegreeOfSatisfaction body) {
                driverRate.setText("满意度："+body.getData().getDegreeOfSatisfaction());
                if (body.getData().getIsC()==1){
                    deliveryComplaint.setText("查看投诉");
                    complaintType = 1;
                }
                if (body.getData().getIsE()==1){
                    deliveryEvaluation.setText("查看评价");
                    evaluationType = 1 ;
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafe(msg);
            }
        });
    }


    //公交路线
    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {}

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
        if (i==1000) {
            List<DrivePath> pathList = driveRouteResult.getPaths();
            List<LatLng> driverPath = new ArrayList<>();
            for (DrivePath dp : pathList) {
                List<DriveStep> stepList = dp.getSteps();
                for (DriveStep ds : stepList) {
                    List<LatLonPoint> points = ds.getPolyline();
                    for (LatLonPoint llp : points) {
                        driverPath.add(new LatLng(llp.getLatitude(), llp.getLongitude()));
                    }
                }
            }
            mAMap.addPolyline(new PolylineOptions()
                    .addAll(driverPath)
                    .width(10)
                    //绘制成大地线
                    .geodesic(false)
                    //设置画线的颜色
                    .color(Color.argb(200, 39, 152, 235)));
        }else {
            UIUtils.showToastSafe("errorCode:"+i);
        }
    }

    //步行路线
    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) { }
    //骑行路线
    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) { }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    protected void onResume() {
        super.onResume();
        mMapView.onResume();
        initDriverMap();
    }

    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
        // 销毁定位
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mMapView.onDestroy();
    }

    public int setMapZoom(int distanceNum){
        if (distanceNum>50000) {
            return 9;
        }else if (distanceNum>30000){
            return 10;
        }else if (distanceNum>20000){
            return 10;
        }else if (distanceNum>10000){
            return 11;
        }else if (distanceNum>5000){
            return 12;
        }else if (distanceNum>2000){
            return 13;
        }else if (distanceNum>1000){
            return 14;
        }else if (distanceNum>500){
            return 15;
        }else if (distanceNum>200){
            return 16;
        }else if (distanceNum>100){
            return 17;
        }else if (distanceNum>50){
            return 18;
        }else {
            return 19;
        }
    }
}
