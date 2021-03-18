package cn.cqs.map.gaode.bean;

import android.support.annotation.NonNull;

/**
 * Created by bingo on 2021/3/3.
 *
 * @Author: bingo
 * @Email: 657952166@qq.com
 * @Description: 类作用描述
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/3/3
 */

public class Point implements Comparable<Point>{
    /**
     * 用于记录每个点的序号
     */
    public int id;
    /**
     * 经度
     */
    public double latitude;
    /**
     * 纬度
     */
    public double longitude;

    public Point(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Point(int id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    @Override
    public int compareTo(@NonNull Point point) {
        if(this.id < point.id){
            return -1;
        }else if(this.id > point.id){
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Point{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
