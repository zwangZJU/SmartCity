package com.wzlab.smartcity.activity.main;

import android.Manifest;
import android.content.Context;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.wzlab.smartcity.Listener.MyOrientationListener;
import com.wzlab.smartcity.R;
import com.wzlab.smartcity.activity.start.StartActivity;
import com.wzlab.smartcity.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;


//{@link AlarmFragment.OnFragmentInteractionListener} interface

public class AlarmFragment extends Fragment {


    private LocationManager locationManager;
    private View rootView;
    private List<LatLng> latLngList;


    public AlarmFragment() {

    }


    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private boolean isFirstLoc=true;
    private MyLocationConfiguration.LocationMode locationMode;

    public LocationClient mLocationClient = null;
    private MyLocationListener myLocationListener = new MyLocationListener();
    private MyOrientationListener myOrientationListener;
    private float mCurrentDirection;
    private double mCurrentLatitude;
    private double mCurrentLongitude;
    private float mRadius;



    private static final String TAG = "AlarmFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SDKInitializer.initialize(getActivity().getApplicationContext());
        rootView = inflater.inflate(R.layout.fragment_alarm, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);






    }

    // 绘制标志物
    private void drawMarker(List<LatLng> pointList) {
        //创建OverlayOptions的集合
        List<OverlayOptions> options = new ArrayList<OverlayOptions>();

        //创建OverlayOptions属性
        Bitmap bitmap = UIUtil.zoomImg(BitmapFactory.decodeResource(getResources(), R.drawable.ic_alarm_enable), 150, 150);
        BitmapDescriptor marker = BitmapDescriptorFactory.fromBitmap(bitmap);
        for(int i=0;i<pointList.size();i++){
            options.add(new MarkerOptions().position(pointList.get(i)).icon(marker));
        }


        //在地图上批量添加
        mBaiduMap.addOverlays(options);
    }



    @Override
    public void onStart() {
        super.onStart();


    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public void onDetach() {
        super.onDetach();
       // mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
      //  mMapView.onDestroy();
        mBaiduMap.setMyLocationEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();


        mMapView = rootView.findViewById(R.id.map_view);

        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);

        // 用百度地图的函数获取定位信息
        mLocationClient = new LocationClient(getContext().getApplicationContext());
        mLocationClient.registerLocationListener(myLocationListener);

        //注册监听函数
        //设置定位方式
        initLocation();
        // 初始化传感器
        initOritationListener();

        mBaiduMap.setMyLocationEnabled(true); //开启定位图层
        MyLocationConfiguration.LocationMode mode =  MyLocationConfiguration.LocationMode.NORMAL;// 罗盘模式
        boolean enableDirection = true;  // 设置允许显示方向
        BitmapDescriptor myLocation = BitmapDescriptorFactory.fromResource(R.drawable.ic_my_location);
        //设置缩放
        MapStatusUpdate scale = MapStatusUpdateFactory.zoomTo(17);
        mBaiduMap.setMapStatus(scale);
        //设置定位方式
        MyLocationConfiguration config = new MyLocationConfiguration(mode, enableDirection, myLocation);
        mBaiduMap.setMyLocationConfiguration(config);
        mLocationClient.start();



      //  latLngList.add(point1);
      //  latLngList.add(point2);

        initData();
        drawMarker(latLngList);


        myOrientationListener.start();
        mMapView.onResume();
    }

    private void initData() {
        // 如果来自于首页alarmTask的点击事件
        //设置坐标点

        latLngList = new ArrayList<>();
        String from = ((MainActivity)getActivity()).getFrom();
        if(from!=null && from.equals("alarmTask")){
            double lat = ((MainActivity)getActivity()).getLat();
            double lon = ((MainActivity)getActivity()).getLon();
            LatLng point = new LatLng(lat, lon);
            latLngList.add(point);
            drawMarker(latLngList);
        }else{

        }

    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        mMapView = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
        myOrientationListener.stop();
    }




    /**
     * 初始化方向传感器
     */
    private void initOritationListener()
    {
        myOrientationListener = new MyOrientationListener(
                getContext().getApplicationContext());
        myOrientationListener
                .setOnOrientationListener(new MyOrientationListener.OnOrientationListener()
                {
                    @Override
                    public void onOrientationChanged(float x)
                    {
                        mCurrentDirection =  x;
                        MyLocationData.Builder builder = new MyLocationData.Builder();
                        builder.accuracy(mRadius);
                        // builder.direction(location.getDirection());
                        builder.direction(mCurrentDirection);
                        builder.latitude(mCurrentLatitude);
                        builder.longitude(mCurrentLongitude);
                       // Log.d(TAG, "onReceiveLocation: "+mXDirection);

                        MyLocationData locationData = builder.build();
                        mBaiduMap.setMyLocationData(locationData);
                     //   Log.d(TAG, "onOrientationChanged: " + x);
                    }
                });
    }


    //设置定位SDK的定位方式
    private void initLocation(){

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps
        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        //option.setIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        //option.setWifiValidTime(5*60*1000);
        //可选，7.2版本新增能力，如果您设置了这个接口，首次启动定位时，会先判断当前WiFi是否超出有效期，超出有效期的话，会先重新扫描WiFi，然后再定位
        mLocationClient.setLocOption(option);
    }



    public class MyLocationListener extends BDAbstractLocationListener {


        public void onReceiveLocation(BDLocation location) {

            //获取定位结果
            location.getTime();    //获取定位时间
            location.getLocationID();    //获取定位唯一ID，v7.2版本新增，用于排查定位问题
            location.getLocType();    //获取定位类型
          //  Log.d(TAG, "onReceiveLocation: "+ location.getLocType());
            mCurrentLatitude = location.getLatitude();    //获取纬度信息
            mCurrentLongitude = location.getLongitude();    //获取经度信息
            mRadius = location.getRadius();    //获取定位精准度
            location.getAddrStr();    //获取地址信息
            location.getCountry();    //获取国家信息
            location.getCountryCode();    //获取国家码
            location.getCity();    //获取城市信息
            location.getCityCode();    //获取城市码
            location.getDistrict();    //获取区县信息
            location.getStreet();    //获取街道信息
            location.getStreetNumber();    //获取街道码
            location.getLocationDescribe();    //获取当前位置描述信息
            location.getPoiList();    //获取当前位置周边POI信息

            location.getBuildingID();    //室内精准定位下，获取楼宇ID
            location.getBuildingName();    //室内精准定位下，获取楼宇名称
            location.getFloor();    //室内精准定位下，获取当前位置所处的楼层信息

            //设置地图中心
            if(isFirstLoc){
                MapStatusUpdate setCenter = MapStatusUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude()));

                mBaiduMap.animateMapStatus(setCenter);
                Log.d(TAG, "onReceiveLocation: " + isFirstLoc);
                isFirstLoc = false;
            }

            // TODO
            MyLocationData.Builder builder = new MyLocationData.Builder();
            builder.accuracy(mRadius);
            // builder.direction(location.getDirection());
            builder.direction(mCurrentDirection);
            builder.latitude(mCurrentLatitude);
            builder.longitude(mCurrentLongitude);
            // Log.d(TAG, "onReceiveLocation: "+mXDirection);

            MyLocationData locationData = builder.build();
            mBaiduMap.setMyLocationData(locationData);

            if (location.getLocType() == BDLocation.TypeGpsLocation){

                //当前为GPS定位结果，可获取以下信息
                location.getSpeed();    //获取当前速度，单位：公里每小时
                location.getSatelliteNumber();    //获取当前卫星数
                location.getAltitude();    //获取海拔高度信息，单位米
                location.getDirection();    //获取方向信息，单位度


            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){

                //当前为网络定位结果，可获取以下信息
                location.getOperators();    //获取运营商信息

            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {

                //当前为网络定位结果

            } else if (location.getLocType() == BDLocation.TypeServerError) {

                //当前网络定位失败
                //可将定位唯一ID、IMEI、定位失败时间反馈至loc-bugs@baidu.com

            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {

                //当前网络不通

            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {

                //当前缺少定位依据，可能是用户没有授权，建议弹出提示框让用户开启权限
                //可进一步参考onLocDiagnosticMessage中的错误返回码

            }
        }
    }


}
