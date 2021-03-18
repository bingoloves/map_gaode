package cn.cqs.map.gaode.utils;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;

import cn.cqs.map.gaode.bean.Point;
import cn.cqs.map.gaode.listener.OnLocationListener;
import cn.cqs.map.gaode.listener.OnMapZoomListener;

/**
 * Created by bingo on 2021/3/3.
 *
 * @Author: bingo
 * @Email: 657952166@qq.com
 * @Description: 类作用描述
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/3/3
 */

public class MapUtils {
    /**
     * 把LatLng对象转化为Point对象
     */
    public static Point convertToPoint(LatLng latLng) {
        return new Point(latLng.latitude, latLng.longitude);
    }

    /**
     * 把Point对象转化为LatLng对象
     */
    public static LatLng convertToLatLng(Point point) {
        return new LatLng(point.getLatitude(), point.getLongitude());
    }

    /**********************************************************移动地图**************************************/
    /**
     * 单一缩放地图
     * @param aMap
     * @param zoom
     */
    public static void zoom(AMap aMap,float zoom) {
        aMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
    }
    /**
     * 通过高德地图本身坐标类LatLng移动
     * @param aMap
     * @param latLng
     */
    public static void animMove(AMap aMap, LatLng latLng) {
        animMove(aMap,latLng,18);
    }
    public static void animMove(AMap aMap, LatLng latLng,float zoom) {
        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }
    /**
     * 通过位置点移动
     * @param aMap
     * @param point
     */
    public static void animMove(AMap aMap, Point point) {
        animMove(aMap,point,18);
    }
    public static void animMove(AMap aMap, Point point,float zoom) {
        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(convertToLatLng(point), zoom));
    }

    /**
     * 通过定位AMapLocation移动
     * @param aMap
     * @param location
     */
    public static void animMove(AMap aMap, AMapLocation location) {
        animMove(aMap,location,18);
    }
    public static void animMove(AMap aMap, AMapLocation location,float zoom) {
        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), zoom));
    }

    /**
     * 设置地图变化的监听
     * @param aMap
     * @param cameraChangeListener
     */
    public static void setOnCameraChangeListener(AMap aMap,AMap.OnCameraChangeListener cameraChangeListener) {
        aMap.setOnCameraChangeListener(cameraChangeListener);
    }

    /**
     * 设置缩放监听
     * @param aMap
     * @param listener
     */
    public static void setMapZoomListener(AMap aMap, final OnMapZoomListener listener) {
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                if (null != listener){
                    listener.zoom(cameraPosition.zoom);
                }
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {

            }
        });
    }
}
