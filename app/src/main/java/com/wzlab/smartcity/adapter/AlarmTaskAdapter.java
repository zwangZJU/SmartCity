package com.wzlab.smartcity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wzlab.smartcity.R;
import com.wzlab.smartcity.po.AlarmLog;
import com.wzlab.smartcity.po.Device;

import java.util.ArrayList;

/**
 * Created by wzlab on 2018/7/10.
 */

public class AlarmTaskAdapter extends RecyclerView.Adapter{

    Context context;



    ArrayList<AlarmLog> alarmTaskList;
    private String lat;
    private String lon;

    public AlarmTaskAdapter(Context context, ArrayList<AlarmLog> alarmTaskList){
        this.context = context;
        this.alarmTaskList = alarmTaskList;
    }

    public void setAlarmTaskList(ArrayList<AlarmLog> alarmTaskList) {
        this.alarmTaskList = alarmTaskList;
        notifyDataSetChanged();
    }

    // Item 的点击事件
    public interface OnItemClickListener{
        public void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    // 打电话的点击事件
    public interface OnCallClickListener{
        public void onCallClick(View view, int position);
    }
    private OnCallClickListener onCallClickListener;
    public void setOnCallClickListener(OnCallClickListener onCallClickListener) {
        this.onCallClickListener = onCallClickListener;
    }


    // 打电话的点击事件
    public interface OnMapClickListener{
        public void onMapClick(View view, int position, double lat, double lon);
    }
    private OnMapClickListener onMapClickListener;
    public void setOnMapClickListener(OnMapClickListener onMapClickListener) {
        this.onMapClickListener = onMapClickListener;
    }


    @Override
    public AlarmTaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AlarmTaskViewHolder deviceOverviewViewHolder = new AlarmTaskViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_item_alarn_task,parent,false));
        return deviceOverviewViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        AlarmTaskViewHolder holder = (AlarmTaskViewHolder) viewHolder;
        holder.itemView.setTag(position);
        AlarmLog alarmLog = alarmTaskList.get(position);
        String[] addrInfo = alarmLog.getUser_address().split("#");
        if(addrInfo.length>1){
            lat = addrInfo[1].split(",")[0].split(":")[1].trim();
            lon = addrInfo[1].split(",")[1].split(":")[1].trim();
        }


        holder.mTvAlarmTime.setText(alarmLog.getAlarm_time());
        holder.mTvPhone.setText("用户手机："+alarmLog.getUser_phone());
        holder.mTvReason.setText("报警原因："+alarmLog.getReason());
        holder.mTvAddress.setText(addrInfo[0]);
        holder.mLlCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onCallClickListener != null){
                    onCallClickListener.onCallClick(view, position);
                }
            }
        });

        holder.mLlMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onMapClickListener != null){
                    onMapClickListener.onMapClick(view, position,Double.parseDouble(lat),Double.parseDouble(lon));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return alarmTaskList.size();
    }

    class AlarmTaskViewHolder extends RecyclerView.ViewHolder{

        TextView mTvAlarmTime;
        TextView mTvPhone;
        TextView mTvReason;
        TextView mTvAddress;
        LinearLayout mLlCall;
        LinearLayout mLlMap;
        public AlarmTaskViewHolder(final View itemView) {
            super(itemView);
            mTvAlarmTime = itemView.findViewById(R.id.tv_alarm_time);
            mTvPhone = itemView.findViewById(R.id.tv_phone);
            mTvReason = itemView.findViewById(R.id.tv_reason);
            mTvAddress = itemView.findViewById(R.id.tv_address);
            mLlCall = itemView.findViewById(R.id.ll_alarm_call);
            mLlMap = itemView.findViewById(R.id.ll_alarm_map);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int position = (int) itemView.getTag();
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(view,position);
                    }
                }
            });
        }


    }
}
