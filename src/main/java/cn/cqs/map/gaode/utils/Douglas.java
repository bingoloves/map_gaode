package cn.cqs.map.gaode.utils;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import cn.cqs.map.gaode.bean.Point;
/**
 * 道格拉斯-普克算法抽稀
 * 减少数据量而不影响目标数据整体走向
 */

public class Douglas {
    private int start;
    private int end;
    private double dMax;
    private ArrayList<Point> mLineInit = new ArrayList<>();
    private ArrayList<Point> mLineFilter = new ArrayList<>();

    public Douglas(ArrayList<LatLng> mLineInit, double dmax){
        if(mLineInit == null){
            throw  new IllegalArgumentException("传入的经纬度坐标list为空");
        }
        this.dMax = dmax;
        this.start = 0;
        this.end = mLineInit.size() - 1;
        for (int i = 0; i < mLineInit.size(); i++) {
            this.mLineInit.add(new Point(i,mLineInit.get(i).latitude,mLineInit.get(i).longitude));
        }
    }

    public ArrayList<LatLng> compress(){
        int size = mLineInit.size();
        ArrayList<Point> latLngPoints = compressLine(mLineInit.toArray(new Point[size]),mLineFilter,start,end,dMax);
        latLngPoints.add(mLineInit.get(0));
        latLngPoints.add(mLineInit.get(size - 1));
        //对抽稀之后的点进行排序
        Collections.sort(latLngPoints, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                return p1.compareTo(p2);
            }
        });
        ArrayList<LatLng> latLngs = new ArrayList<>();
        for (Point latLngPoint:latLngPoints) {
            latLngs.add(new LatLng(latLngPoint.latitude,latLngPoint.longitude));
        }
        return latLngs;
    }

    /**
     *
     * @param start  起始点
     * @param end    终止点
     * @param center 中间点
     * @return
     */
    private double disToSegment(Point start, Point end, Point center) {
        double a = Math.abs(AMapUtils.calculateLineDistance(new LatLng(start.latitude,start.longitude), new LatLng(end.latitude,end.longitude)));
        double b = Math.abs(AMapUtils.calculateLineDistance(new LatLng(start.latitude,start.longitude), new LatLng(center.latitude,center.longitude)));
        double c = Math.abs(AMapUtils.calculateLineDistance(new LatLng(center.latitude,center.longitude), new LatLng(end.latitude,end.longitude)));
        double p = (a + b + c) / 2.0;
        double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));
        double h = s * 2.0 / a;
        return h;
    }

    private ArrayList<Point> compressLine(Point[] originalLatLngs, ArrayList<Point> endLatLngs, int start, int end, double dMax) {

        int centerIndex = 0;
        double max = 0;
        Point startLng = originalLatLngs[start];
        Point endLng = originalLatLngs[end];

        for (int j = start + 1; j < end; j++) {
            double dis = disToSegment(startLng, endLng, originalLatLngs[j]);
            if (dis > max) {
                centerIndex = j;
                max = dis;
            }
        }
        if (max > dMax) {
            endLatLngs.add(originalLatLngs[centerIndex]);
            compressLine(originalLatLngs, endLatLngs, start, centerIndex, dMax);
            compressLine(originalLatLngs, endLatLngs, centerIndex, end, dMax);
        }
        return endLatLngs;
    }
}
