package com.wzlab.smartcity.activity.main;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.VerticalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.google.gson.Gson;
import com.wzlab.smartcity.R;
import com.wzlab.smartcity.activity.account.Config;
import com.wzlab.smartcity.adapter.RepairLogAdapter;
import com.wzlab.smartcity.net.HttpMethod;
import com.wzlab.smartcity.net.NetConnection;
import com.wzlab.smartcity.po.RepairLog;
import com.wzlab.smartcity.widget.LoadingLayout;
import com.wzlab.smartcity.widget.NoScrollViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class RepairFragment extends Fragment {
    private RecyclerView recyclerView;
    private LoadingLayout loadingLayout;
    private ArrayList<RepairLog> repairLogList;
    private String phone;
    private AlertDialog alertDialog = null;

    private int clickedItemPosition;
    private HorizontalStepView stepViewHorizontal;
    private VerticalStepView stepViewVertical;
    private LoadingLayout loadingLayoutAlert;
    private NoScrollViewPager viewPager;
    private List<StepBean> stepHorList;
    private List<String> stepVerList;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == Config.KEY_LOADING_EMPTY){
              //  loadingLayout.setEmptyFloatButtonVisible(false);
                loadingLayout.showEmpty();

            }else if(msg.what == Config.KEY_LOADING_ERROR){
                loadingLayout.showError();
                loadingLayout.setBackgroundColor(getResources().getColor(R.color.background));
                loadingLayout.setRetryListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getRepairLogList();
                    }
                });

            }else if(msg.what == Config.KEY_LOADING_SUCCESS){

                repairLogAdapter.setRepairLogList(repairLogList);
                loadingLayout.showContent();
            }
        }
    };
    private RepairLogAdapter repairLogAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repair_log, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        phone = Config.getCachedPhone(getContext());
        loadingLayout = view.findViewById(R.id.loading_layout_repair_log);

        recyclerView = view.findViewById(R.id.rv_repair_log);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        repairLogList = new ArrayList<>();
        repairLogAdapter = new RepairLogAdapter(getContext(),repairLogList);
        repairLogAdapter.setOnItemClickListener(new RepairLogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                clickedItemPosition = position;

            }
        });
        repairLogAdapter.setOnOperateButtonClickListerner(new RepairLogAdapter.OnOperateButtonClickListerner() {
            @Override
            public void OnOperateButtonClick(View view, int position) {
                String state = repairLogList.get(position).getProcessing_state();
                if(state.equals("2")){
                    updateRepairState(position, state);
                    Intent intent = new Intent(getContext(),HandleActivity.class);
                    intent.putExtra("id",repairLogList.get(position).getRepair_id());
                    intent.putExtra("type","1");
                    startActivity(intent);
                }else if(state.equals("3")){
                    Intent intent = new Intent(getContext(),HandleActivity.class);
                    intent.putExtra("id",repairLogList.get(position).getRepair_id());
                    intent.putExtra("type","1");
                    startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(repairLogAdapter);
        getRepairLogList();


    }

    private void updateRepairState(int position,String state) {
        new NetConnection(Config.SERVER_URL + Config.ACTION_UPDATE_REPAIR_PROCESSING_STATE, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                getRepairLogList();
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                Toast.makeText(getContext(),"未能连接服务器",Toast.LENGTH_LONG).show();

            }
        },"repair_id",repairLogList.get(position).getRepair_id(),"processing_state",String.valueOf((Integer.parseInt(state)+1)));

    }

    public void getRepairLogList(){
        new NetConnection(Config.SERVER_URL + Config.ACTION_GET_REPAIR_Orders, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    switch (jsonObject.getString(Config.KEY_STATUS)){
                        case Config.RESULT_STATUS_SUCCESS:

                            //TODO
                            repairLogList = new ArrayList<>();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            Gson gson = new Gson();
                            for(int i=0;i<jsonArray.length();i++){
                                repairLogList.add(gson.fromJson(jsonArray.get(i).toString(),RepairLog.class));
                            }

                            Message message = new Message();
                            message.what = Config.KEY_LOADING_SUCCESS;
                            handler.sendMessage(message);

                            break;
                        default:
                            Message message1 = new Message();
                            message1.what = Config.KEY_LOADING_EMPTY;
                            handler.sendMessage(message1);
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    loadingLayout.setErrorText("数据解析异常");
                    Message message = new Message();
                    message.what = Config.KEY_LOADING_ERROR;
                    handler.sendMessage(message);

                }
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                loadingLayout.setErrorText("未能连接到服务器");
                Message message = new Message();
                message.what = Config.KEY_LOADING_ERROR;
                handler.sendMessage(message);
            }
        },Config.KEY_PHONE, phone);
    }





}
