package cn.cqs.map.gaode.listener;

import com.amap.api.location.AMapLocation;

/**
 * 定位接口回调
 */
public interface OnLocationListener {
    void onChange(AMapLocation aMapLocation);
}
