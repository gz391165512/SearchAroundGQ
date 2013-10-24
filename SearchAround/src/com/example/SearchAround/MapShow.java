package com.example.SearchAround;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.*;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-17
 * Time: 下午4:48
 * To change this template use File | Settings | File Templates.
 */
public class MapShow extends Activity {
    private BMapManager bMapManager;
    private static final String StringKey = "D62a405bc0e4bcbebbaac9a9e6870242";
    private MapView mapView;
    private MapController mapController;
    private TextView mapShowTextView;
    private PoiOverlay poiOverlay;
    private PoiOverlay poiOverlayMany;
    private View mapWindow;
    private List<HashMap<String,Object>> list = null;
    private Double startLongitude;
    private Double startLatitude;
    private String scope;
    private TextView mapShowSpinner;
    private ImageButton mapShowNext;
    private ImageButton mapShowPre;
    private ImageButton mapShowLocation;
    private ImageButton mapShowChange;
    private ImageButton mapShowRefresh;
    private ImageButton mapShowBack;
    private LayoutInflater layoutInflater;
    private GeoPoint geoPoint;//自己所处的点
    private int page = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bMapManager =  Init.init(this,1);
        final String query = getIntent().getStringExtra("query");
        scope = getIntent().getStringExtra("scope");
        setContentView(R.layout.mapshow);
        mapView = (MapView) findViewById(R.id.bdMapView);
        layoutInflater = LayoutInflater.from(this);
        mapShowTextView = (TextView) findViewById(R.id.mapShowTextView);
        mapShowSpinner = (TextView) findViewById(R.id.mapShowSpinner);
        mapShowNext = (ImageButton) findViewById(R.id.mapShowNextIbt);
        mapShowLocation = (ImageButton) findViewById(R.id.mapShowLocationIbt);
        mapShowPre = (ImageButton) findViewById(R.id.mapShowPreIbt);
        mapShowChange = (ImageButton) findViewById(R.id.mapShowChangeBtn);
        mapShowRefresh = (ImageButton) findViewById(R.id.mapShowRefreshBtn);
        mapShowBack = (ImageButton) findViewById(R.id.mapShowBackBtn);
        mapShowChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mapShowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mapShowRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(MapShow.this,"刷新为开始状态",Toast.LENGTH_SHORT);
                toast.show();
                page = 1;
                mapView.getOverlays().remove(poiOverlayMany);
                mapView.getOverlays().remove(poiOverlay);
                executeJson(query);
            }
        });
        mapShowNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(MapShow.this,"下一组数据加载中",Toast.LENGTH_SHORT);
                toast.show();
                mapView.getOverlays().remove(poiOverlayMany);
                mapView.getOverlays().remove(poiOverlay);
                page = page + 1;
                executeJson(query);
            }
        });
        mapShowPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(MapShow.this,"前一组数据加载中",Toast.LENGTH_SHORT);
                toast.show();
                mapView.getOverlays().remove(poiOverlayMany);
                mapView.getOverlays().remove(poiOverlay);
                page = page - 1;
                executeJson(query);
            }
        });
        mapShowSpinner.setText(scope + "m内");
        mapShowTextView.setText(query);
        mapController = mapView.getController();
        if(JsonUtil.address == null){
            new JsonUtil().getMyLocation1(this);
        }
        executeJson(query);
        mapShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mapController.animateTo(geoPoint);
                    }
                },500);
            }
        });

    }
    private void executeJson(final String query){

        AsyncTask <Integer,Integer,Integer> asyncTask = new AsyncTask<Integer,Integer,Integer>() {
            @Override
            protected Integer doInBackground(Integer... params) {
                String getJson = "";
                try {
                    getJson = new JsonUtil().getJson(query,JsonUtil.longitude,JsonUtil.latitude,page,Integer.parseInt(scope));
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    return 1;
                }
                try {
                    list = new JsonUtil().parseJsonTwo(getJson);
                } catch (JSONException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    return 2;

                }
                if(list == null){
                    return 3;
                }
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();    //To change body of overridden methods use File | Settings | File Templates.
            }

            @Override
            protected void onPostExecute(Integer integer) {
                if(integer == 1){
                    Toast toast = Toast.makeText(MapShow.this,"网络不通,请重试",Toast.LENGTH_SHORT) ;
                    toast.show();
                }else if(integer == 2){
                    Toast toast = Toast.makeText(MapShow.this,"无法解析数据",Toast.LENGTH_SHORT) ;
                    toast.show();
                }else if(integer == 3){
                    Toast toast = Toast.makeText(MapShow.this,"范围内无数据,请更改查询方式",Toast.LENGTH_SHORT) ;
                    toast.show();
                }else{
                    loadMapData();
                }
                super.onPostExecute(integer);    //To change body of overridden methods use File | Settings | File Templates.
            }
        } ;
        asyncTask.execute(0);
    }
    class PoiOverlay extends ItemizedOverlay{
        @Override
        protected boolean onTap(final int i) {
            OverlayItem overlayItem = poiOverlayMany.getItem(i);
            TextView textViewName = (TextView) mapWindow.findViewById(R.id.mapWindowTextView1);
            TextView textViewAddress = (TextView) mapWindow.findViewById(R.id.mapWindowTextView2);
            textViewName.setText(overlayItem.getTitle());
            textViewAddress.setText(overlayItem.getSnippet());
            MapView.LayoutParams layoutParams = new MapView.LayoutParams(
                    MapView.LayoutParams.WRAP_CONTENT,
                    MapView.LayoutParams.WRAP_CONTENT,
                    overlayItem.getPoint(),
                    0,-40,MapView.LayoutParams.BOTTOM_CENTER
            );
            mapWindow.setVisibility(View.VISIBLE);
            mapWindow.setLayoutParams(layoutParams);
            mapController.animateTo(overlayItem.getPoint());
            mapWindow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MapShow.this,DetailMessage.class);
                    intent.putExtra("address",list.get(i).get("address").toString());
                    intent.putExtra("name",list.get(i).get("name").toString());
                    intent.putExtra("tel",list.get(i).get("tel").toString());
                    intent.putExtra("startLongitude",startLongitude);
                    intent.putExtra("startLatitude",startLatitude);
                    intent.putExtra("endLongitude",list.get(i).get("longitude").toString());
                    intent.putExtra("endLatitude",list.get(i).get("latitude").toString());
                    startActivity(intent);
                }
            });
            return super.onTap(i);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public boolean onTap(GeoPoint geoPoint, MapView mapView) {
            mapWindow.setVisibility(View.GONE);
            return super.onTap(geoPoint, mapView);    //To change body of overridden methods use File | Settings | File Templates.
        }

        public PoiOverlay(Drawable drawable, MapView mapView) {
            super(drawable, mapView);    //To change body of overridden methods use File | Settings | File Templates.
        }
    }
    public void loadMapData(){
        startLongitude = JsonUtil.longitude;
        startLatitude =  JsonUtil.latitude;
        geoPoint = new GeoPoint((int)(startLatitude*1E6),(int)(startLongitude*1E6));
        mapWindow = layoutInflater.inflate(R.layout.mapwindow,null);
        mapWindow.setVisibility(View.GONE);
        mapView.addView(mapWindow);
        Drawable centerIop = getResources().getDrawable(R.drawable.u22_normal);        //中间点也就是自己所在的位置的点
        Drawable PoiImage = getResources().getDrawable(R.drawable.ic_loc_normal);     // 所有目标的POI点
        poiOverlay = new PoiOverlay(centerIop,mapView);
        poiOverlayMany = new PoiOverlay(PoiImage,mapView);
        OverlayItem overlayItem = new OverlayItem(geoPoint,"","");
        int maxLatitude = (int)(startLatitude*1E6);
        int maxLongitude =(int)(startLongitude*1E6);
        int minLatitude = (int)(startLatitude*1E6);
        int minLongitude = (int)(startLongitude*1E6);
        for(int i=1;i<list.size();i++){//从1开始
            double latitude = Double.parseDouble(list.get(i).get("latitude").toString());
            double longitude = Double.parseDouble(list.get(i).get("longitude").toString());
            if(maxLatitude < latitude*1E6){
                maxLatitude = (int)(latitude*1E6);
            }
            if(maxLongitude < longitude*1E6){
                maxLongitude = (int)(longitude*1E6);
            }
            if(minLatitude > latitude*1E6){
                minLatitude = (int) (latitude*1E6);
            }
            if(minLongitude > longitude*1E6){
                minLongitude = (int)(longitude*1E6);
            }
            GeoPoint geoPointPoi = new GeoPoint((int)(latitude*1E6),(int)(longitude*1E6));
            OverlayItem overlayItemPoi = new OverlayItem(geoPointPoi,list.get(i).get("name").toString(),list.get(i).get("address").toString());
            poiOverlayMany.addItem(overlayItemPoi);
        }
        final int zoomX = maxLatitude-minLatitude;
        final int zoomY =  maxLongitude-minLongitude;
        poiOverlay.addItem(overlayItem);
        mapView.getOverlays().add(poiOverlay) ;
        mapView.getOverlays().add(poiOverlayMany);
        mapController.zoomToSpan(zoomX, zoomY);//高科技自动计算图层
        mapController.animateTo(new GeoPoint((maxLatitude + minLatitude)/2,(maxLongitude + minLongitude)/2));
    }

    @Override
    protected void onDestroy() {
        mapView.destroy();
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
