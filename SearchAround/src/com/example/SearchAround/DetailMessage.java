package com.example.SearchAround;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.search.*;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-22
 * Time: 下午2:44
 * To change this template use File | Settings | File Templates.
 */
public class DetailMessage extends Activity {
    private MapView mapView;
    private BMapManager bMapManager;
    private TextView detailAddress;
    private TextView detailName;
    private TextView detailPhone;
    private ImageButton detailBackIbt;
    private double endLongitude;
    private double endLatitude;
    private double startLongitude;
    private double startLatitude;
    private MapController mapController;
    private ItemizedOverlay centerOverlay;
    private ItemizedOverlay startOverlay;
    private ItemizedOverlay endOverlay;
    private MKSearch mkSearch;
    int searchType;
    RouteOverlay routeOverlay = null;
    MKRoute mkRoute = null;//保存驾车/步行路线数据的变量，供浏览节点时使用
    int nodeIndex = -2;//节点索引,供浏览节点时使用
    TransitOverlay transitOverlay = null;//保存公交路线图层数据的变量，供浏览节点时使用
    private RadioButton walkRadioBtn;
    private RadioButton busRadioBtn;
    private RadioButton driveRadioBtn;
    private int maxLatitude;
    private int minLatitude;
    private int maxLongitude;
    private int minLongitude;
    private TextView detailTime;
    private Button detailSavePicture;
    private MKMapViewListener mkMapViewListener = null;
    private ImageButton detailGoToShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bMapManager =  Init.init(this,2);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.detailed_information);
        detailAddress = (TextView) findViewById(R.id.detailAddress);
        detailName = (TextView) findViewById(R.id.detailName);
        detailPhone = (TextView) findViewById(R.id.detailPhone);
        detailTime = (TextView) findViewById(R.id.detailTime);
        detailBackIbt = (ImageButton) findViewById(R.id.detailBackIbt);
        mapView = (MapView) findViewById(R.id.bdMapDetail);
        walkRadioBtn = (RadioButton) findViewById(R.id.detailFoot);
        busRadioBtn = (RadioButton) findViewById(R.id.detailBus);
        driveRadioBtn = (RadioButton) findViewById(R.id.detailCar);
        detailSavePicture = (Button) findViewById(R.id.detailSavePicture);
        detailGoToShow = (ImageButton) findViewById(R.id.detailGoToShow);
        detailAddress.setText(getIntent().getStringExtra("address"));
        detailName.setText(getIntent().getStringExtra("name"));
        endLongitude =  Double.parseDouble(getIntent().getStringExtra("endLongitude"));
        endLatitude =  Double.parseDouble(getIntent().getStringExtra("endLatitude"));
        startLongitude = getIntent().getDoubleExtra("startLongitude",0);
        startLatitude = getIntent().getDoubleExtra("startLatitude",0);
        String phone =  getIntent().getStringExtra("tel") ;
        maxLatitude = (int)(startLatitude*1E6);
        minLatitude = (int)(endLatitude*1E6);
        maxLongitude =(int)(startLongitude*1E6);
        minLongitude = (int)(endLongitude*1E6);
        if(maxLatitude < minLatitude){
            maxLatitude = (int)(endLatitude*1E6);
            minLatitude = (int)(startLatitude*1E6);
        }
        if(maxLongitude < minLongitude){
            maxLongitude = (int)(endLongitude*1E6);
            minLongitude = (int)(startLongitude*1E6);
        }
        mkMapViewListener = new MKMapViewListener() {
            @Override
            public void onMapMoveFinish() {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onClickMapPoi(MapPoi mapPoi) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onGetCurrentMap(Bitmap bitmap) {
                int number = 0;
                File fileList = new File("/mnt/sdcard/gqmap/");
                File [] files = fileList.listFiles();
                for(int i=0;i<files.length;i++){
                    if(number<Integer.parseInt(files[i].getName().substring(0,1))){
                        number = Integer.parseInt(files[i].getName().substring(0,1));
                    }
                }
                number = number + 1;
                File createDocument = new File("/mnt/sdcard/gqmap");
                File file = new File("/mnt/sdcard/gqmap/" + number +".png");
                createDocument.mkdirs();
                FileOutputStream out;
                try{
                    out = new FileOutputStream(file);
                    if(bitmap.compress(Bitmap.CompressFormat.PNG, 70, out))
                    {
                        out.flush();
                        out.close();
                    }
                    Toast.makeText(DetailMessage.this,
                            "屏幕截图成功，图片存在: "+file.toString(),
                            Toast.LENGTH_SHORT)
                            .show();
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMapAnimationFinish() {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onMapLoadFinish() {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        };
        mapView.regMapViewListener(bMapManager,mkMapViewListener);
        mkSearch = new MKSearch();
        mkSearch.init(bMapManager,new MKSearchListener() {
            @Override
            public void onGetPoiResult(MKPoiResult mkPoiResult, int i, int i2) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onGetTransitRouteResult(MKTransitRouteResult mkTransitRouteResult, int error) {
                if (error == MKEvent.ERROR_ROUTE_ADDR){
                    return;
                }
                if (error != 0 || mkTransitRouteResult == null) {
                    Toast.makeText(DetailMessage.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
                    return;
                }

                searchType = 1;//是公交
                transitOverlay = new TransitOverlay (DetailMessage.this, mapView);
                transitOverlay.setData(mkTransitRouteResult.getPlan(0));
                mapView.getOverlays().clear();
                mapView.getOverlays().add(centerOverlay);
                mapView.getOverlays().add(transitOverlay);
                mapView.refresh();
                mapController.zoomToSpan(maxLatitude - minLatitude, maxLongitude - minLongitude);
                mapController.animateTo(new GeoPoint((maxLatitude + minLatitude)/2,(maxLongitude + minLongitude)/2));
                calculateTime(mkTransitRouteResult.getPlan(0).getDistance());
                nodeIndex = 0;
            }

            @Override
            public void onGetDrivingRouteResult(MKDrivingRouteResult mkDrivingRouteResult, int error) {
                if (error == MKEvent.ERROR_ROUTE_ADDR){
                    return;
                }
                if (error != 0 || mkDrivingRouteResult == null) {
                    Toast.makeText(DetailMessage.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
                    return;
                }
                searchType = 0;//是驾车
                routeOverlay = new RouteOverlay(DetailMessage.this,mapView);
                routeOverlay.setData(mkDrivingRouteResult.getPlan(0).getRoute(0));
                mapView.getOverlays().clear();
                mapView.getOverlays().add(centerOverlay);
                mapView.getOverlays().add(routeOverlay);
                mapView.refresh();
                mapController.zoomToSpan(maxLatitude - minLatitude, maxLongitude - minLongitude);
                mapController.animateTo(new GeoPoint((maxLatitude + minLatitude) / 2, (maxLongitude + minLongitude) / 2));
                mkRoute = mkDrivingRouteResult.getPlan(0).getRoute(0);
                calculateTime(mkRoute.getDistance());
                nodeIndex = -1;
            }

            @Override
            public void onGetWalkingRouteResult(MKWalkingRouteResult mkWalkingRouteResult, int error) {
                if (error == MKEvent.ERROR_ROUTE_ADDR){
                    return;
                }
                if (error != 0 || mkWalkingRouteResult == null) {
                    Toast.makeText(DetailMessage.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
                    return;
                }

                searchType = 2;        //是 步行
                routeOverlay = new RouteOverlay(DetailMessage.this, mapView);
                routeOverlay.setData(mkWalkingRouteResult.getPlan(0).getRoute(0));
                mapView.getOverlays().clear();
                mapView.getOverlays().add(centerOverlay);
                mapView.getOverlays().add(routeOverlay);
                mapView.refresh();
                mapController.zoomToSpan(maxLatitude - minLatitude, maxLongitude - minLongitude);
                mapController.animateTo(new GeoPoint((maxLatitude + minLatitude) / 2, (maxLongitude + minLongitude) / 2));
                mkRoute = mkWalkingRouteResult.getPlan(0).getRoute(0);
                calculateTime(mkRoute.getDistance());
                nodeIndex = -1;
            }

            @Override
            public void onGetAddrResult(MKAddrInfo mkAddrInfo, int i) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onGetBusDetailResult(MKBusLineResult mkBusLineResult, int i) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onGetSuggestionResult(MKSuggestionResult mkSuggestionResult, int i) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onGetPoiDetailSearchResult(int i, int i2) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onGetShareUrlResult(MKShareUrlResult mkShareUrlResult, int i, int i2) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        if(phone.equals("")){
            detailPhone.setText("暂无号码");
        }else{
            detailPhone.setText(phone);
        }
        detailGoToShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailMessage.this,ShowSavePicture.class);
                startActivity(intent);
            }
        });
        walkRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchProcess(v);
            }
        });
        busRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchProcess(v);
            }
        });
        driveRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchProcess(v);
            }
        });
        detailBackIbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        detailSavePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapView.getCurrentMap();
                Toast.makeText(DetailMessage.this,
                        "正在截取屏幕图片...",
                        Toast.LENGTH_SHORT ).show();
            }
        });
        mapController = mapView.getController();
        mapController.setZoom(16);
        mapController.enableClick(true);
        final GeoPoint startPoint = new GeoPoint((int)(startLatitude*1E6),(int)(startLongitude*1E6));
        GeoPoint endGeoPoint = new GeoPoint((int)(endLatitude*1E6),(int)(endLongitude*1E6));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mapController.animateTo(startPoint);
            }
        }, 2000);
        Drawable startTop = getResources().getDrawable(R.drawable.ic_loc_from);
        Drawable centerIop = getResources().getDrawable(R.drawable.u22_normal);
        Drawable endTop = getResources().getDrawable(R.drawable.ic_loc_to);
        centerOverlay = new ItemizedOverlay(centerIop,mapView);
        startOverlay = new ItemizedOverlay(startTop,mapView);
        endOverlay = new ItemizedOverlay(endTop,mapView);
        OverlayItem overlayItem = new OverlayItem(startPoint,"","");
        OverlayItem overlayItemStart = new OverlayItem(startPoint,"","");
        OverlayItem overlayItemEnd = new OverlayItem(endGeoPoint,"","");
        centerOverlay.addItem(overlayItem);
        startOverlay.addItem(overlayItemStart);
        endOverlay.addItem(overlayItemEnd);
        mapView.getOverlays().add(centerOverlay);
//        mapView.getOverlays().add(startOverlay);
//        mapView.getOverlays().add(endOverlay);
    }
    protected void searchProcess(View v){
        mkRoute = null;
        routeOverlay = null;
        transitOverlay = null;
        MKPlanNode stNode = new MKPlanNode();
        stNode.pt = new GeoPoint((int)(startLatitude*1E6),(int)(startLongitude*1E6));
        MKPlanNode enNode = new MKPlanNode();
        enNode.pt = new GeoPoint((int)(endLatitude*1E6),(int)(endLongitude*1E6));
        if(driveRadioBtn.equals(v)){
            mkSearch.drivingSearch("西安", stNode, "西安", enNode);
        }else if(busRadioBtn.equals(v)){
            mkSearch.transitSearch("西安", stNode, enNode);
        }else if(walkRadioBtn.equals(v)){
            mkSearch.walkingSearch("西安", stNode, "西安", enNode);
        }
    }
    public void calculateTime(int totalDistance){
        String time = "";
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        if(searchType == 0){
            seconds = totalDistance*10/55 + 1;
        }else if(searchType == 1){
            seconds = totalDistance*10/33 + 1;
        }else if(searchType == 2){
            seconds = totalDistance/2 + 1;
        }
        if(seconds >= 60){
            minutes = seconds/60;
            seconds = seconds%60;
            time = minutes +"分" + seconds + "秒";
            if(minutes>=60){
                hours = minutes/60;
                minutes = minutes%60;
                time = hours + "小时" + minutes +"分" + seconds + "秒";
            }
        }else{
            time = seconds + "秒";
        }
        detailTime.setText("全程约" + totalDistance +"m，耗时约"+ time);
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
