package com.maf.base.activity;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.maf.activity.BaseTitleActivity;
import com.maf.utils.Lg;

import java.util.Date;

import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：GPS测试
 * 创建人：zgmao
 * 创建时间：2017/6/29
 * 修改人：zgmao
 * 修改时间：2017/6/29
 * 修改备注：
 * Created by zgmao on 2017/6/29.
 */

public class GPSActivity extends BaseTitleActivity {

    private LocationManager locationManager;

    @Override
    protected void initTitleView() {
        titleBarView.setTitle("gps测试");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_gps;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {
        Button btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }

    @Override
    protected void initValue() {

    }

    private void start() {
        try {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            Lg.d("上一个位置");
//            logLocation(location);
            locationListener.onLocationChanged(location);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
//        Location location = new Location(LocationManager.PASSIVE_PROVIDER);
//        location.setLatitude(33.023);
//        location.setLongitude(120);
//        location.setTime(new Date().getTime());
//        location.setAccuracy(1.0f);
//        location.setElapsedRealtimeNanos(new Date().getTime());
//        locationManager.setTestProviderLocation(LocationManager.PASSIVE_PROVIDER, location);
    }

    //创建一个事件监听器
    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            Lg.d("onLocationChanged");
            logLocation(location);
        }

        public void onProviderDisabled(String provider) {
            Lg.d("onProviderDisabled:" + provider);
        }

        public void onProviderEnabled(String provider) {
            Lg.d("onProviderEnabled:" + provider);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            Lg.d("onStatusChanged status：" + status);
        }
    };


    @Override
    public void onClick(View v) {

    }

    private void logLocation(Location location) {
        if (null == location) {
            Lg.d("location is null ");
            return;
        }
        Lg.d("lat:" + location.getLatitude() + ";long" + location.getLongitude());
    }
}
