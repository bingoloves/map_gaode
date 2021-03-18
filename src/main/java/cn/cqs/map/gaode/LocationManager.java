package cn.cqs.map.gaode;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import cn.cqs.map.gaode.listener.OnLocationListener;

/**
 * 高德地图定位管理类
 */
public class LocationManager implements AMapLocationListener {
    /**
     * 地图client
     */
    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption;
    /**
     * 回调监听监听
     */
    private OnLocationListener locationListener;

    public LocationManager(Context context){
        initLocation(context);
    }
    /**
     * 初始化定位
     * @param context
     */
    public void initLocation(Context context) {
        //初始化client
        locationClient = new AMapLocationClient(context.getApplicationContext());
        locationOption = getDefaultOption(5000);
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        //设置定位监听
        locationClient.setLocationListener(this);
    }

    /**
     * 设置定位时间间隔
     * @param interval 定位频率
     */
    public LocationManager setInterval(int interval) {
        locationOption = getDefaultOption(interval);
        locationClient.setLocationOption(locationOption);
        return this;
    }

    /**
     * 设置定位监听
     * @param locationListener
     */
    public void setLocationListener(OnLocationListener locationListener) {
        this.locationListener = locationListener;
    }


    /**
     * 默认的定位参数
     */
    public AMapLocationClientOption getDefaultOption(int interval) {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        //高德地图说明，来自高德android开发常见问题：
        //GPS模块无法计算出定位结果的情况多发生在卫星信号被阻隔时，在室内环境卫星信号会被墙体、玻璃窗阻隔反射，在“城市峡谷”（高楼林立的狭长街道）地段卫星信号也会损失比较多。
        ////强依赖GPS模块的定位模式——如定位SDK的仅设备定位模式，需要在室外环境进行，多用于行车、骑行、室外运动等场景。
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        /*mOption.setGpsFirst(true);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setInterval(interval);//可选，设置定位间隔。默认为2秒
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差*/
        mOption.setGpsFirst(true);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(interval);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }



    /**
     * 开始定位
     */
    public void startLocation() {
        if(locationClient != null){
            locationClient.startLocation();
        }
    }

    /**
     * 关闭定位
     */
    public void stopLocation(){
        if(locationClient != null){
            locationClient.stopLocation();
        }
    }

    /**
     * 回收注销广播
     */
    public void destroy(){
        if (locationClient != null){
            locationClient.onDestroy();
        }
        locationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (locationListener != null) {
                locationListener.onChange(aMapLocation);
            }
        } else {
            Log.e("TAG","aMapLocation == null");
        }
    }
}
