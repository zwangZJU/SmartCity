package com.wzlab.smartcity.activity.main;

import android.bluetooth.BluetoothClass;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wzlab.smartcity.activity.R;
import com.wzlab.smartcity.adapter.DeviceOverviewAdapter;
import com.wzlab.smartcity.po.Device;

import java.util.ArrayList;


public class DeviceOverviewFragment extends Fragment {

    private RecyclerView mRvDeviceOverview;
    private ArrayList<Device> deviceList;
    private DeviceOverviewAdapter deviceOverviewAdapter;

    public DeviceOverviewFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_device_overview, container, false);
    }

    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        initData();
        mRvDeviceOverview = rootView.findViewById(R.id.rv_device_overview);
        mRvDeviceOverview.setLayoutManager(new LinearLayoutManager(getContext()));
        deviceOverviewAdapter = new DeviceOverviewAdapter(getContext(),deviceList);
        mRvDeviceOverview.setAdapter(deviceOverviewAdapter);
        deviceOverviewAdapter.setOnItemClickListener(new DeviceOverviewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                DeviceDetailFragment deviceDetailFragment = new DeviceDetailFragment();
               // getFragmentManager().beginTransaction().addToBackStack(null).add(deviceDetailFragment,)
            }
        });

    }

    private void initData() {
        Device device = new Device("001","wzlab","zju","0");
        deviceList = new ArrayList<Device>();
        deviceList.add(device);
        deviceList.add(device);
        deviceList.add(device);
        deviceList.add(device);
        deviceList.add(device);
    }
}
