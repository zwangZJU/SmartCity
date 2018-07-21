package com.wzlab.smartcity.activity.main;

import android.Manifest;
import android.content.Context;

import android.content.pm.PackageManager;
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
import com.wzlab.smartcity.R;
import com.wzlab.smartcity.activity.start.StartActivity;

import java.util.ArrayList;
import java.util.List;


//{@link AlarmFragment.OnFragmentInteractionListener} interface

public class AlarmFragment extends Fragment {


    private LocationManager locationManager;

    public AlarmFragment() {

    }


    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private boolean isFirstLoc=true;
    private MyLocationConfiguration.LocationMode locationMode;
    private static final String TAG = "AlarmFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SDKInitializer.initialize(getActivity().getApplicationContext());
        return inflater.inflate(R.layout.fragment_alarm, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = view.findViewById(R.id.map_view);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);


        //获取定位数据
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.d(TAG, "onViewCreated: "+location);
        locationUpdate(location);

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, // 内容提供者
                1000, // 更新事件1秒一次
                1, // 位置间隔为1m
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        locationUpdate(location);
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                });



        // 绘制标志物
        //创建OverlayOptions的集合

        List<OverlayOptions> options = new ArrayList<OverlayOptions>();
//设置坐标点

        LatLng point1 = new LatLng(30.262371, 120.116443);
        LatLng point2 = new LatLng(30.262391, 120.116483);

//创建OverlayOptions属性

        BitmapDescriptor marker = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker);

        OverlayOptions option1 =  new MarkerOptions()
                .position(point1)
                .icon(marker);
        OverlayOptions option2 =  new MarkerOptions()
                .position(point2)
                .icon(marker);
//将OverlayOptions添加到list
        options.add(option1);
        options.add(option2);
        //在地图上批量添加
        mBaiduMap.addOverlays(options);

    }



    private void locationUpdate(Location location) {
        if(location!=null){
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            if(isFirstLoc){
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(mapStatusUpdate);
                isFirstLoc = false;
            }
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getAccuracy())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_my_location);
            locationMode = MyLocationConfiguration.LocationMode.FOLLOWING;  // 设置定位模式
            MyLocationConfiguration myLocationConfiguration = new MyLocationConfiguration(locationMode,true,bitmapDescriptor);
            mBaiduMap.setMyLocationConfiguration(myLocationConfiguration);

        }
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
        mMapView.onDestroy();
        mBaiduMap.setMyLocationEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        mMapView = null;
    }
}
