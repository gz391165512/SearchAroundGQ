package com.example.SearchAroundUI;

import android.content.Context;
import android.util.Log;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-16
 * Time: 下午4:45
 * To change this template use File | Settings | File Templates.
 */
public class JsonUtil {
    public static String address;
    public static double latitude;
    public static double longitude;
    public String getJson(String query,double Longitude,double Latitude,int page)throws IOException {
        Log.e("------------",Longitude+"");
        Log.e("------------",Latitude+"");
        String url = "https://api.weibo.com/2/location/pois/search/by_geo.json?q=" + query +"&coordinate=" +Longitude + "%2C" + Latitude +"&access_token=2.00JL99ME8Di7lC2a43a110fa0SjrQP&count=20&page=" + page ;
        HttpGet httpGet = new HttpGet(url);
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = httpClient.execute(httpGet);
        String jsonString = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
        return jsonString;
    }
    public List<HashMap<String,Object>> parseJson(String jsonString) throws JSONException{
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.optJSONArray("poilist");
        List <HashMap<String,Object>> list = new ArrayList<HashMap<String, Object>>();
        for(int i=0;i<jsonArray.length();i++){
            HashMap<String,Object> hashMap = new HashMap<String, Object>();
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            hashMap.put("address",jsonObject1.optString("address"));
            hashMap.put("name",jsonObject1.optString("name"));
            hashMap.put("distance",jsonObject1.optString("distance") + "m");
            list.add(hashMap);
        }
        //给list按distance排序
        for(int i=0;i<list.size()-1;i++) {
            for(int j=list.size();j<j-i;j++){
                HashMap<String,Object> tempMap = new HashMap<String, Object>();
                if((Integer.parseInt(String.valueOf(list.get(i).get("distance")))) >= Integer.parseInt(String.valueOf(list.get(i).get("distance")))){
                    tempMap = list.get(i);
                    list.set(i,list.get(i+1));
                    list.set(i+1,tempMap);
                }
            }
        }
        return list;
    }
    public void getMyLocation(Context context, final GetMyLocationCallback getMyLocationCallback) {
        final String [] returnArr = new String[3];
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setAddrType("all");
        option.setOpenGps(true);
        LocationClient locationClient = new LocationClient(context);
        locationClient.setLocOption(option);

        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                Log.d("BaiduMapDemo", "onReceiveLocation address " + bdLocation.getAddrStr());
                Log.d("BaiduMapDemo", "onReceiveLocation Latitude " + bdLocation.getLatitude());
                Log.d("BaiduMapDemo", "onReceiveLocation Longitude " + bdLocation.getLongitude());
                address = bdLocation.getAddrStr();
                latitude = bdLocation.getLatitude();
                longitude = bdLocation.getLongitude();
                getMyLocationCallback.onLocation(bdLocation);
            }

            @Override
            public void onReceivePoi(BDLocation bdLocation) {
                Log.d("BaiduMapDemo", "onReceivePoi ");
            }
        });
        locationClient.start();
        locationClient.requestLocation();
    }
    public void getMyLocation1(Context context) {
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setAddrType("all");
        option.setOpenGps(true);
        LocationClient locationClient = new LocationClient(context);
        locationClient.setLocOption(option);

        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                Log.d("BaiduMapDemo", "onReceiveLocation address " + bdLocation.getAddrStr());
                Log.d("BaiduMapDemo", "onReceiveLocation Latitude " + bdLocation.getLatitude());
                Log.d("BaiduMapDemo", "onReceiveLocation Longitude " + bdLocation.getLongitude());
                address = bdLocation.getAddrStr();
                latitude = bdLocation.getLatitude();
                longitude = bdLocation.getLongitude();
            }

            @Override
            public void onReceivePoi(BDLocation bdLocation) {
                Log.d("BaiduMapDemo", "onReceivePoi ");
            }
        });
        locationClient.start();
        locationClient.requestLocation();
    }

    public static interface GetMyLocationCallback {
        public void onLocation(BDLocation bdLocation);
    }
}
