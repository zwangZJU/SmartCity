package com.wzlab.smartcity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.wzlab.smartcity.R;
import com.wzlab.smartcity.po.RepairLog;

import java.util.ArrayList;

/**
 * Created by wzlab on 2018/8/10.
 */

public class RepairLogAdapter extends RecyclerView.Adapter{
    private Context context;

    public void setRepairLogList(ArrayList<RepairLog> repairLogList) {
        this.repairLogList = repairLogList;
        notifyDataSetChanged();
    }

    private ArrayList<RepairLog> repairLogList;

    public RepairLogAdapter(Context context, ArrayList<RepairLog> repairLogList){
        this.context = context;
        this.repairLogList = repairLogList;
    }

    public interface OnItemClickListener{
        public void onItemClick(View view, int position);
    }

    public OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnOperateButtonClickListerner{
        public void OnOperateButtonClick(View view, int position);
    }
    public OnOperateButtonClickListerner onOperateButtonClickListerner;
    public void setOnOperateButtonClickListerner(OnOperateButtonClickListerner onOperateButtonClickListerner){
        this.onOperateButtonClickListerner = onOperateButtonClickListerner;
    }
    @Override
    public RepairLogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RepairLogViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_item_repair_order,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        RepairLogViewHolder holder = (RepairLogViewHolder) viewHolder;
        holder.itemView.setTag(position);

        RepairLog repairLog = repairLogList.get(position);

        String deviceId =  getData(repairLog.getDevice_id());
        String repairTime =  getData(repairLog.getRepair_time());
        String repairContent = getData(repairLog.getRepair_content());
        String repairUserPhone = getData(repairLog.getUser_phone());
        String repairUserAddr = getData(repairLog.getUser_address()).split("#")[0];
        final String progressingState = getData(repairLog.getProcessing_state());

        switch (progressingState){
            case "0":
                holder.mIvProgressingState.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_receive_order));
                break;
            case "1":
                holder.mIvProgressingState.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_receive_order));
                break;
            case "2":
                holder.mIvProgressingState.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_to_repair));
                break;
            case "3":
                holder.mIvProgressingState.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_repair_finished));
                break;
        }
        holder.mIvProgressingState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onOperateButtonClickListerner != null){
                    onOperateButtonClickListerner.OnOperateButtonClick(view,position);
                }
            }
        });

        holder.mTvDeviceId.setText("设备ID: " + deviceId);
        holder.mTvRepairTime.setText(repairTime);
        holder.mTvHandler.setText("用户电话：" + repairUserPhone);
        holder.mTvHandlerPhone.setText("用户住址：" + repairUserAddr);
        holder.mTvRepairContent.setText("报修内容：" + repairContent);

    }

    @Override
    public int getItemCount() {
        return repairLogList.size();
    }

    class RepairLogViewHolder extends RecyclerView.ViewHolder{

        TextView mTvDeviceId;
        TextView mTvRepairTime;
        TextView mTvRepairContent;
        TextView mTvHandler;
        TextView mTvHandlerPhone;
        ImageView mIvProgressingState;

        public RepairLogViewHolder(final View itemView) {
            super(itemView);
            mTvDeviceId = itemView.findViewById(R.id.tv_repair_device_id);
            mTvRepairTime = itemView.findViewById(R.id.tv_repair_time);
            mTvRepairContent = itemView.findViewById(R.id.tv_repair_content);
            mTvHandler = itemView.findViewById(R.id.tv_repair_handler);
            mTvHandlerPhone = itemView.findViewById(R.id.tv_repair_handler_phone);
            mIvProgressingState = itemView.findViewById(R.id.iv_repair_state);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener != null){
                        int position = (int) itemView.getTag();
                        onItemClickListener.onItemClick(view, position);
                    }
                }
            });
        }

    }

    public String getData(String s){
        if(s == null || s.equals("")){
            return "暂无数据";
        }else {
            return s;
        }
    }
}
