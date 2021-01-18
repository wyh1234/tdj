package cn.com.taodaji.ui.activity.tdjc;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.apkfuns.logutils.LogUtils;
import com.base.retrofit.RequestCallback;
import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.MapSearchRange;
import cn.com.taodaji.model.entity.ShopAddressItem;
import cn.com.taodaji.model.event.FenceGid;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.ui.activity.login.CityActivity;
import cn.com.taodaji.viewModel.adapter.ShopAddressAdapter;
import tools.activity.DataBaseActivity;
import tools.statusbar.Eyes;

public class SeachPickUpActivity extends DataBaseActivity implements PoiSearch.OnPoiSearchListener,AMap.OnMarkerClickListener, AMap.OnMapLoadedListener, AMap.OnMapClickListener, LocationSource, AMapLocationListener, GeocodeSearch.OnGeocodeSearchListener, AMap.OnCameraChangeListener {
    @BindView(R.id.mv_shop_address)
     MapView mapView;
    @BindView(R.id.rv_shop_address_list)
     RecyclerView addressRecyclerView;
    @BindView(R.id.tv_current_city)
     TextView cityName;
    @BindView(R.id.tv_cancel_search)
    TextView cancelSearch;
    @BindView(R.id.et_shop_address)
     EditText addressSearch;
    @BindView(R.id.iv_clear_keyword)
     ImageView clearKeyword;
    @BindView(R.id.rl_xl)
    RelativeLayout rl_xl;
    @BindView(R.id.tv_ok)
    TextView tv_ok;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.ll_bottom)
    LinearLayout ll_bottom;

    private AMap mAMap;
    private Marker mGPSMarker;             //定位位置显示
    private AMapLocation location;
    private LocationSource.OnLocationChangedListener mListener;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    //你编码对象
    private GeocodeSearch geocoderSearch;
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    public  String fenceId="";
    private static LatLonPoint lp = null;
    private double currentLatitude,currentLongitude;
    private String finalAddress,cityCode,name,title;
    private double distance;
    private MarkerOptions markOptions;
    private LatLng latLng;
    private String addressName;
    private PoiSearch poiSearch = null;
    private List<ShopAddressItem> itemList = new ArrayList<>();
    private int searchRange = 1000,id;
    private ShopAddressAdapter addressAdapter;
    private boolean flag,sel;
    private String keywords;
    @OnClick({R.id.tv_ok,R.id.btn_back,R.id.rl_xl})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_ok:
                if (itemList.size()>0){
                    if (! sel){
                        finalAddress = itemList.get(0).getSnippet();
                        currentLatitude = itemList.get(0).getPoint().getLatitude();
                        currentLongitude = itemList.get(0).getPoint().getLongitude();
                        distance=  UIUtils.getDistance(itemList.get(0).getPoint().getLongitude(),itemList.get(0).getPoint().getLatitude(), PublicCache.longtitude,PublicCache.latitude);
                        title=itemList.get(0).getTitle();
                    }
                    commitAddress();
                }

                break;
            case R.id.rl_xl:
                if (flag){
                    flag=false;
                    ll_bottom.setVisibility(View.VISIBLE);
                }else {
                    flag=true;
                    ll_bottom.setVisibility(View.GONE);
                }

                break;
        }
    }

    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.seach_pickup_activity);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMap(savedInstanceState);
    }
    private void initMap(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        geocoderSearch = new GeocodeSearch(this);
        mAMap = mapView.getMap();
        // 设置定位监听
        mAMap.setOnMapLoadedListener(this);
        mAMap.setOnMarkerClickListener(this);
        mAMap.setOnMapClickListener(this);

        mAMap.setLocationSource(this);
        //设置地图拖动监听
        mAMap.setOnCameraChangeListener(this);

        geocoderSearch.setOnGeocodeSearchListener(this);

        //地图UI设置
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.current_loction));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        //myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        //myLocationStyle.strokeWidth(0f);// 设置圆形的边框粗细
        //myLocationStyle.anchor(0.5f, 0.7f);
        mAMap.setMyLocationStyle(myLocationStyle.showMyLocation(true));

        mAMap.moveCamera(CameraUpdateFactory.zoomTo(17)); //缩放比例

        //设置amap的属性
        UiSettings settings = mAMap.getUiSettings();
        settings.setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        settings.setZoomControlsEnabled(false);
        mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
    }

    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        Eyes.translucentStatusBar(getBaseActivity(),true);

        id = getIntent().getIntExtra("id",-1);
        name = getIntent().getStringExtra("name");
        cityCode = getIntent().getStringExtra("name");

        cityName.setText(name);

        addressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        addressRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        addressAdapter = new ShopAddressAdapter(itemList, this);
        addressAdapter.setIndex(1);
        addressRecyclerView.setAdapter(addressAdapter);
        //当RecyclerView从下向上滑动的时候，onBindViewHolder 方法不调用，这个api去调整 RecyclerView 的复用逻辑和方式来解决 onBindViewHolder 没有调用的这个问题。
        addressRecyclerView.setItemViewCacheSize(10);
        addressAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                sel=true;
                for(ShopAddressItem s:itemList){
                    s.setCurrent(false);
                }
                itemList.get(position).setCurrent(true);
                finalAddress = itemList.get(position).getSnippet();
                currentLatitude = itemList.get(position).getPoint().getLatitude();
                currentLongitude = itemList.get(position).getPoint().getLongitude();
                distance= UIUtils.getDistance(itemList.get(position).getPoint().getLongitude(),itemList.get(position).getPoint().getLatitude(), PublicCache.longtitude,PublicCache.latitude);
                title=itemList.get(position).getTitle();
                addressAdapter.notifyDataSetChanged();
            }
        });

        getSearchRange();
        keywords=getIntent().getStringExtra("keyWord");
        addressSearch.setText(keywords);

        addressSearch.clearFocus();

        addressSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().equals("")){
                    clearKeyword.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().equals("")){
                    clearKeyword.setVisibility(View.INVISIBLE);
                }else {
                    keywords=editable.toString().trim();
                    doSearchQuery(keywords,cityCode,"",null);
                }
            }
        });
        //取消搜索
        cancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressSearch.clearFocus();
                addressSearch.setText("");
                clearKeyword.setVisibility(View.INVISIBLE);
            }
        });

        //清空搜索内容
        clearKeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressSearch.setText("");
                clearKeyword.setVisibility(View.INVISIBLE);
            }
        });


        //切换城市事件
        cityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PublicCache.loginPurchase==null){
                    Intent intent1 = new Intent(SeachPickUpActivity.this, CityActivity.class);
                    intent1.putExtra("data", PublicCache.findByIsActive);
                    startActivityForResult(intent1, 3);
                }
            }
        });

    }

    private void getSearchRange(){
        loading("请稍候");
        RequestPresenter.getInstance().getSearchRange(new RequestCallback<MapSearchRange>() {
            @Override
            protected void onSuc(MapSearchRange body) {
                loadingDimss();
                if (!TextUtils.isEmpty(body.getData().getMap_search_range())){
                    searchRange = Integer.parseInt(body.getData().getMap_search_range());
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                loadingDimss();
                UIUtils.showToastSafesShort(msg);
            }
        });
    }



    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        location = aMapLocation;
        if (mListener != null && location != null) {
            if (location != null && location.getErrorCode() == 0) {
                mListener.onLocationChanged(location);// 显示系统箭头
                LatLng la = new LatLng(location.getLatitude(), location.getLongitude());
                setMarket(keywords,la, location.getCity(), location.getAddress());

                finalAddress = location.getAddress();
                currentLongitude = location.getLongitude();
                currentLatitude = location.getLatitude();
                mLocationClient.stopLocation();


            }
        } else {
            UIUtils.showToastSafesShort("定位失败");
        }
    }
    private void setMarket(String str,LatLng latLng, String title, String content) {

        if (mGPSMarker != null) {
            mGPSMarker.remove();
        }
        int width = (mapView.getWidth()) / 2;
        int height = ((mapView.getHeight()) / 2)-25;
        markOptions = new MarkerOptions();
        markOptions.draggable(true);//设置Marker可拖动
        markOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.shop_location_selected))).anchor(0.5f, 0.7f);
        //设置一个角标
        mGPSMarker = mAMap.addMarker(markOptions);
        //设置marker在屏幕的像素坐标
        mGPSMarker.setPosition(latLng);
        mGPSMarker.setTitle(title);
        mGPSMarker.setSnippet(content);
        //设置像素坐标
        mGPSMarker.setPositionByPixels(width, height);
//        if (!content.equals(null)){
//            mGPSMarker.showInfoWindow();
//        }
           mapView.invalidate();
//           if (latLng==null){
               doSearchQuery(str,cityCode,"",null);
//           }else {
//               doSearchQuery(str,cityCode,"",new LatLonPoint(latLng.latitude,latLng.longitude));
//           }




    }
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
    }
    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery(String keyWord ,String cityCode,String typeCode,LatLonPoint lp) {
        int currentPage = 0;
        PoiSearch.Query query = new PoiSearch.Query(keyWord, typeCode, cityCode);
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        query.setCityLimit(true);//设置城市限制

        if (lp != null) {
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.setBound(new PoiSearch.SearchBound(lp, searchRange,true));
            poiSearch.searchPOIAsyn();// 异步搜索
        }else {
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.searchPOIAsyn();// 异步搜索
        }
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        latLng = cameraPosition.target;
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        Log.e("latitude", latitude + "");
        Log.e("longitude", longitude + "");
        getAddress(latLng);

    }
    /**
     * 根据经纬度得到地址
     */
    public void getAddress(final LatLng latLonPoint) {
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系

        RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(latLonPoint.latitude, latLonPoint.longitude), 500, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLoaded() {

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

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == 1000) {
            if (result != null && result.getRegeocodeAddress() != null && result.getRegeocodeAddress().getFormatAddress() != null) {
                addressName = result.getRegeocodeAddress().getFormatAddress(); // 逆转地里编码不是每次都可以得到对应地图上的opi;
                    setMarket(keywords,latLng, cityCode, addressName);

            }
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        //解析result获取POI信息
        if (rCode==1000){
            itemList.clear();
            for (PoiItem item :result.getPois()){
                ShopAddressItem item1 = new ShopAddressItem();
                item1.setId(item.getPoiId());
                item1.setPoint(item.getLatLonPoint());
                item1.setTitle(item.getTitle());
                item1.setSnippet(item.getSnippet());
                item1.setDistance(item.getDistance());
                item1.setCurrent(false);
                itemList.add(item1);
            }
            itemList.get(0).setCurrent(true);
            addressAdapter.notifyDataSetChanged();

        }else {
//            UIUtils.showToastSafe("错误码"+rCode);
//            UIUtils.showToastSafesShort("搜索失败");
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

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

    //选择城市后回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK){
            id= data.getExtras().getInt("id");
            name=data.getExtras().getString("name");
            cityName.setText(name);
            cityCode = data.getStringExtra("cityCode");

            mAMap = mapView.getMap();//得到aMap对象
            currentLatitude=data.getDoubleExtra("lat",0);
            currentLongitude=data.getDoubleExtra("lon",0);
            LatLng latLng = new LatLng(data.getDoubleExtra("lat",0),data.getDoubleExtra("lon",0));//构造一个位置
            mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
            doSearchQuery("",cityCode,"",new LatLonPoint(currentLatitude,currentLongitude));
        }
    }
    public void commitAddress(){
        //数据是使用Intent返回
        Intent intent = new Intent();
        //把返回数据存入Intent
        LogUtils.i(currentLongitude);
        LogUtils.i(currentLatitude);
        LogUtils.i(addressSearch.getText());
        intent.putExtra("result", finalAddress);
        intent.putExtra("longitude",currentLongitude);
        intent.putExtra("latitude",currentLatitude);
        intent.putExtra("id",id);
        intent.putExtra("name",addressSearch.getText().toString());
        intent.putExtra("title",title);
        intent.putExtra("distance",distance+"");

        //设置返回数据
        this.setResult(RESULT_OK, intent);
        //关闭Activity
        this.finish();
    }
}
