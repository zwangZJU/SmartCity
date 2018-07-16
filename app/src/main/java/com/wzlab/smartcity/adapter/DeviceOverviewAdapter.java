package com.wzlab.smartcity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wzlab.smartcity.activity.R;
import com.wzlab.smartcity.po.Device;

import java.util.ArrayList;

/**
 * Created by wzlab on 2018/7/10.
 */

public class DeviceOverviewAdapter extends RecyclerView.Adapter{

    Context context;
    ArrayList<Device> deviceList;
    public DeviceOverviewAdapter(Context context, ArrayList<Device> deviceList){
        this.context = context;
        this.deviceList = deviceList;
    }

    public interface OnItemClickListener{
        public void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public DeviceOverviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DeviceOverviewViewHolder deviceOverviewViewHolder = new DeviceOverviewViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_item_device_info,parent,false));
        return deviceOverviewViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        DeviceOverviewViewHolder holder = (DeviceOverviewViewHolder) viewHolder;
        holder.itemView.setTag(position);
        Device device = deviceList.get(position);
        holder.mTvDeviceId.setText(device.getUser_name() + "  " + device.getUserId_());
        holder.mTvDeviceLocation.setText(device.getUser_address());
        holder.mTvDeviceStatus.setText(device.getStatus());

    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    class DeviceOverviewViewHolder extends RecyclerView.ViewHolder{

        TextView mTvDeviceId;
        TextView mTvDeviceLocation;
        TextView mTvDeviceStatus;
        public DeviceOverviewViewHolder(final View itemView) {
            super(itemView);
            mTvDeviceId = itemView.findViewById(R.id.tv_device_id);
            mTvDeviceLocation = itemView.findViewById(R.id.tv_device_location);
            mTvDeviceStatus = itemView.findViewById(R.id.tv_device_status);

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
