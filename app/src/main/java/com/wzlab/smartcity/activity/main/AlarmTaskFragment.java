package com.wzlab.smartcity.activity.main;

import android.annotation.SuppressLint;
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

import com.google.gson.Gson;
import com.tuesda.walker.circlerefresh.CircleRefreshLayout;
import com.wzlab.smartcity.R;
import com.wzlab.smartcity.activity.account.Config;
import com.wzlab.smartcity.adapter.AlarmTaskAdapter;

import com.wzlab.smartcity.net.HttpMethod;
import com.wzlab.smartcity.net.NetConnection;
import com.wzlab.smartcity.po.AlarmLog;
import com.wzlab.smartcity.widget.LoadingLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AlarmTaskFragment extends Fragment {

    private static final String TAG = "AlarmTaskFragment";
    private static final int KEY_LOADING_ERROR = -1;
    private static final int KEY_LOADING_EMPTY = 0;
    private static final int KEY_LOADING_SUCCESS = 1;
    private static final int KEY_LOADING_LOADING = 2;
    private static final int FINISH_REFRESH = 3;
    private RecyclerView mRvAlarmLogOverview;
    private ArrayList<AlarmLog> alarmTaskList;
    private AlarmTaskAdapter alarmTaskAdapter;
    private LoadingLayout loadingLayout;
    private CircleRefreshLayout circleRefreshLayout;
    private String phone;

    public AlarmTaskFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        phone = Config.getCachedPhone(getContext());
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

                alarmTaskAdapter.setAlarmTaskList(alarmTaskList);
                loadingLayout.showContent();
            }else if(msg.what == KEY_LOADING_LOADING){
                loadingLayout.showLoading();
            }else if(msg.what == FINISH_REFRESH){
                alarmTaskList.clear();
                initData(true);
                circleRefreshLayout.finishRefreshing();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alarm_task, container, false);
    }

    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        alarmTaskList = new ArrayList<>();
        mRvAlarmLogOverview = rootView.findViewById(R.id.rv_device_overview);
        mRvAlarmLogOverview.setLayoutManager(new LinearLayoutManager(getContext()));

        loadingLayout = rootView.findViewById(R.id.loading_layout_device_overview);
        alarmTaskAdapter = new AlarmTaskAdapter(getContext(),alarmTaskList);
        alarmTaskAdapter.setOnItemClickListener(new AlarmTaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(getContext(),HandleActivity.class);
                intent.putExtra("id",alarmTaskList.get(position).getAlarm_id());
                intent.putExtra("type","0");
                startActivity(intent);


            }
        });
        alarmTaskAdapter.setOnMapClickListener(new AlarmTaskAdapter.OnMapClickListener() {
            @Override
            public void onMapClick(View view, int position, double lat, double lon) {
                Intent intent = new Intent(getContext(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP );
                intent.putExtra("from","alarmTask");
                intent.putExtra("lat",lat);
                intent.putExtra("lon",lon);
                startActivity(intent);
            }
        });
        mRvAlarmLogOverview.setAdapter(alarmTaskAdapter);


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


        if(!isPulling){
            loadingLayout.showLoading();
        }

        new NetConnection(Config.SERVER_URL + Config.ACTION_GET_POLICE_MISSION, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(result);
                    //TODO

                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    Gson gson = new Gson();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        alarmTaskList.add(gson.fromJson(jsonArray.get(i).toString(), AlarmLog.class));

                    }

                    if(alarmTaskList.size()>0){
                        Message message = new Message();
                        message.what = KEY_LOADING_SUCCESS;
                        handler.sendMessage(message);
                    }else{
                        Message message = new Message();
                        message.what = KEY_LOADING_EMPTY;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Message message = new Message();
                    message.what = KEY_LOADING_ERROR;
                    handler.sendMessage(message);
                }


            }

        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                Message message = new Message();
                message.what = KEY_LOADING_ERROR;
                handler.sendMessage(message);
            }
        },Config.KEY_PHONE, phone);






    }
}
