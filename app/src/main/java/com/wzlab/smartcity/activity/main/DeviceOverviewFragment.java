package com.wzlab.smartcity.activity.main;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothClass;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tuesda.walker.circlerefresh.CircleRefreshLayout;
import com.wzlab.smartcity.activity.R;
import com.wzlab.smartcity.activity.account.Config;
import com.wzlab.smartcity.adapter.DeviceOverviewAdapter;
import com.wzlab.smartcity.net.main.GetDeviceInfo;
import com.wzlab.smartcity.po.Device;
import com.wzlab.smartcity.utils.CheckNetworkStatus;
import com.wzlab.smartcity.widget.LoadingLayout;

import java.util.ArrayList;


public class DeviceOverviewFragment extends Fragment {

    private static final String TAG = "DeviceOverviewFragment";
    private static final int KEY_LOADING_ERROR = -1;
    private static final int KEY_LOADING_EMPTY = 0;
    private static final int KEY_LOADING_SUCCESS = 1;
    private static final int KEY_LOADING_LOADING = 2;
    private static final int FINISH_REFRESH = 3;
    private RecyclerView mRvDeviceOverview;
    private ArrayList<Device> deviceList;
    private DeviceOverviewAdapter deviceOverviewAdapter;
    private LoadingLayout loadingLayout;
    private CircleRefreshLayout circleRefreshLayout;

    public DeviceOverviewFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }





    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == KEY_LOADING_EMPTY){
                loadingLayout.showEmpty();
            }else if(msg.what == KEY_LOADING_ERROR){
                loadingLayout.showError();
            }else if(msg.what == KEY_LOADING_SUCCESS){
                deviceOverviewAdapter = new DeviceOverviewAdapter(getContext(),deviceList);
                deviceOverviewAdapter.setOnItemClickListener(new DeviceOverviewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(getContext(), DeviceDetailActivity.class);
                        Device deviec = deviceList.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putString("user_id",deviec.getUserId_());
                        bundle.putString("user_name",deviec.getUser_name());
                        bundle.putString("user_address",deviec.getUser_address());
                        bundle.putString("device_status",deviec.getStatus());
                        bundle.putString("head",deviec.getHead());
                        bundle.putString("head_phone",deviec.getHead_phone());
                        bundle.putString("police_station",deviec.getPolice_station());

                        intent.putExtras(bundle);
                        startActivity(intent);

                        // getFragmentManager().beginTransaction().addToBackStack(null).add(deviceDetailFragment,)
                    }
                });
                mRvDeviceOverview.setAdapter(deviceOverviewAdapter);
                loadingLayout.showContent();
            }else if(msg.what == KEY_LOADING_LOADING){
                loadingLayout.showLoading();
            }else if(msg.what == FINISH_REFRESH){
                initData(true);
                circleRefreshLayout.finishRefreshing();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_device_overview, container, false);
    }

    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        mRvDeviceOverview = rootView.findViewById(R.id.rv_device_overview);
        mRvDeviceOverview.setLayoutManager(new LinearLayoutManager(getContext()));



        loadingLayout = rootView.findViewById(R.id.loading_layout_device_overview);
        initData(false);
        //没有网络 页面 的重新加载
        loadingLayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData(false);
            }
        });

        circleRefreshLayout = rootView.findViewById(R.id.refresh_layout);
        circleRefreshLayout.setOnRefreshListener(new CircleRefreshLayout.OnCircleRefreshListener() {
            @Override
            public void completeRefresh() {

            }

            @Override
            public void refreshing() {
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1200);
                            Message msg = new Message();
                            msg.what = FINISH_REFRESH;
                            handler.sendMessage(msg);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });




    }

    private void initData(boolean isPulling) {

        deviceList = new ArrayList<Device>();

        //显示加载页面，并加载数据
        if(!isPulling){
            loadingLayout.showLoading();
        }

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        String phone = sp.getString(Config.KEY_PHONE,"");

        GetDeviceInfo.getDeviceList(phone, new GetDeviceInfo.SuccessCallback() {
            @Override
            public void onSuccess(ArrayList list, String msg) {
                Message handlerMsg = new Message();
                if(list.size()>0){
                    deviceList = list;
                    handlerMsg.what = KEY_LOADING_SUCCESS;
                }else{//如果为空
                    handlerMsg.what = KEY_LOADING_EMPTY;
                }
                handler.sendMessage(handlerMsg);
            }
        }, new GetDeviceInfo.FailCallback() {
            @Override
            public void onFail(String msg) {
                Message handlerMsg = new Message();
                handlerMsg.what = KEY_LOADING_ERROR;
                handler.sendMessage(handlerMsg);
                loadingLayout.setErrorText(msg);
                Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
            }
        });
      //  deviceOverviewAdapter = new DeviceOverviewAdapter(getContext(),deviceList);
    }
}
