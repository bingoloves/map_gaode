# map_gaode
基于高德的定位封装
Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
   repositories {
       maven { url 'https://jitpack.io' }
   }
}
```
Step 2. Add the dependency
```gradle
dependencies {
    implementation 'com.github.bingoloves:map_gaode:1.0.1'
}
```

**使用示例**

可以不使用，也可以注明支持的架构
```grade
android {
    defaultConfig {
       ndk {
           //设置支持的SO库架构（开发者可以根据需要，选择一个或多个平台的so）
           abiFilters "armeabi", "armeabi-v7a", "arm64-v8a", "x86","x86_64"
       }
    }
}
```

AndroidManifest.xml添加自己申请的KEY(只是需要GPS坐标可以不填写)
```xml
 <meta-data
     android:name="com.amap.api.v2.apikey"
     android:value="YOU APPKEY" />
```
```java
LocationManager locationManager = new LocationManager(context);
locationManager.setLocationListener(new OnLocationListener() {
      @Override
      public void onChange(AMapLocation aMapLocation) {
          if (aMapLocation.getErrorCode() == 0) {
             LatLng currentLatLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
             LogUtils.d("定位成功:"+ LocationUtils.getLocationLog(aMapLocation));
          }else{
               LogUtils.d("定位失败");
          }
      }
});
locationManager.startLocation();

```

