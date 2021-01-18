package tools.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.ImageButton;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.base.activity.BaseActivity;
import com.base.listener.OnPermissionListener;
import com.base.utils.SystemUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import tools.extend.AMapLocationUtils;


/**
 * Created by yangkuo on 2018-09-26.
 */
public abstract class LocationManagerUtils {

    private BaseActivity activity;

    private int location_service_requestCode = 1025;
    private int location_jurisdiction_requestCode = 1026;

    private AlertDialog alertDialog_jurisdiction;
    private AlertDialog alertDialog_location_service;


    public LocationManagerUtils(BaseActivity activity) {
        this.activity = activity;
    }

//    public abstract void showLocation(double latitude, double longitude, String city);
        public abstract void showLocation(AMapLocation aMapLocation);

    public void beginLocatioon() {
        //获得位置服务
//        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
//        if (locationManager == null) return;

        if (!isLocationEnabled(activity)) {
            if (alertDialog_location_service == null)
                alertDialog_location_service = ViewUtils.showDialog(activity, "提示信息", "GPS定位服务未开启，定位功能暂时无法使用。是否进入设置页面授权？").setNegativeButton("取消", (dialog, which) -> dialog.dismiss()).setPositiveButton("进入", (dialog, which) -> {
                    dialog.dismiss();
                    SystemUtils.startAppLocationSetting(activity, location_service_requestCode);
                }).create();

            if (!alertDialog_location_service.isShowing()) alertDialog_location_service.show();

            return;
        }


//        List<String> prodiverlist = locationManager.getProviders(true);
//        Collections.reverse(prodiverlist);

        //检查权限是否开启
        activity.addPermissionListen(10023, new OnPermissionListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void permissionSuccess(int requestCode) {
                new AMapLocationUtils(activity, new AMapLocationListener() {
                    @Override
                    public void onLocationChanged(AMapLocation aMapLocation) {
                        if (aMapLocation != null) {
                            int err = aMapLocation.getErrorCode();
                            switch (err) {
                                case 0:
//                                    showLocation(aMapLocation.getLatitude(), aMapLocation.getLongitude(), aMapLocation.getCity());
                                    showLocation(aMapLocation);
                                    break;
                                case 12://缺少定位权限。
                                    if (alertDialog_jurisdiction == null)
                                        alertDialog_jurisdiction = ViewUtils.showDialog(activity, "提示信息", "应用缺少定位权限，定位功能暂时无法使用。如若需要，请进入设置页面授权。").setNegativeButton("取消", (dialog, which) -> dialog.dismiss()).setPositiveButton("进入", (dialog, which) -> {
                                            dialog.dismiss();
                                            SystemUtils.startManageJurisdiction(activity, location_jurisdiction_requestCode);
                                        }).create();


                                    if (!alertDialog_jurisdiction.isShowing())
                                        alertDialog_jurisdiction.show();

                                    break;
                                case 7://KEY鉴权失败
                                    UIUtils.showToastSafesShort("测试包，无法使用高德地图定位");
                                    break;
                                default:  //定位失败
                                    break;
                            }
                        }
                    }
                });

            }

            @Override
            public void permissionFail(int requestCode) {
                if (alertDialog_jurisdiction == null)
                    alertDialog_jurisdiction = ViewUtils.showDialog(activity, "提示信息", "应用缺少定位权限，定位功能暂时无法使用。如若需要，请进入设置页面授权。").setNegativeButton("取消", (dialog, which) -> dialog.dismiss()).setPositiveButton("进入", (dialog, which) -> {
                        dialog.dismiss();
                        lasttime = System.currentTimeMillis();
                        SystemUtils.startManageJurisdiction(activity, location_jurisdiction_requestCode);
                    }).create();

                if (!alertDialog_jurisdiction.isShowing()) alertDialog_jurisdiction.show();
            }
        });
        activity.permissionRequest(10023, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);

    }

    private long lasttime = 0;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == location_service_requestCode || requestCode == location_jurisdiction_requestCode) {
            if (System.currentTimeMillis() - lasttime > 1000) beginLocatioon();
        }
    }

    /**
     * 判断定位服务是否开启
     *
     * @param
     * @return true 表示开启
     */
    private static boolean isLocationEnabled(Context context) {

//        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//        if (locationManager != null) {
//            List ll = locationManager.getProviders(true);
//            return locationManager.getProviders(true).size() > 0;
//        }
//        return false;

        int locationMode = 0;
//        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            String locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }


    //    {
//
//        String longtitude = String.valueOf(location.getLongitude());
//        String latitude = String.valueOf(location.getLatitude());
////        Log.e("经纬度信息：",longtitude+"  "+latitude);
//    }
//放入经纬度就可以了
    public String getAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                return addresses.get(0).getLocality();
//                Address address = addresses.get(0);
//                String data = address.toString();
//                int startCity = data.indexOf("1:\"") + "1:\"".length();
//                int endCity = data.indexOf("\"", startCity);
//                String city = data.substring(startCity, endCity);
//                int startPlace = data.indexOf("feature=") + "feature=".length();
//                int endplace = data.indexOf(",", startPlace);
//                String place = data.substring(startPlace, endplace);
//                return city + place;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检测GPS是否打开
     *
     * @return
     */
    private boolean checkGPSIsOpen() {
        boolean isOpen = false;
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            isOpen = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        return isOpen;
    }


}
