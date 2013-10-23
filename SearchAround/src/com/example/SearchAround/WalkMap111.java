package com.example.SearchAround;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.platform.comapi.basestruct.GeoPoint;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-19
 * Time: 下午12:34
 * To change this template use File | Settings | File Templates.
 */
public class WalkMap111 extends Activity {
//    private TextView walkMapText;
    private MapView mapView;
    private MKSearch mkSearch;
    private MapController mapController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.walkmap);
        mapView = (MapView) findViewById(R.id.bdMapViewWalk);
//        walkMapText = (TextView) findViewById(R.id.walkMapText);
//        walkMapText.setText(getIntent().getStringExtra("keyWord"));
        GeoPoint startPoint = new GeoPoint(getIntent().getIntExtra("startLatitude",0),getIntent().getIntExtra("startLongitude",0));
//        GeoPoint endPoint = new GeoPoint(getIntent().getIntExtra("endLatitude",0),getIntent().getIntExtra("endLongitude",0));
//        mkSearch = new MKSearch();
//        mkSearch.init(new MapApplication().mBMapManager, new MKSearchListener(){
//
//            public void onGetDrivingRouteResult(MKDrivingRouteResult res,
//                                                int error) {
//                //起点或终点有歧义，需要选择具体的城市列表或地址列表
//                if (error == MKEvent.ERROR_ROUTE_ADDR){
//                    //遍历所有地址
////					ArrayList<MKPoiInfo> stPois = res.getAddrResult().mStartPoiList;
////					ArrayList<MKPoiInfo> enPois = res.getAddrResult().mEndPoiList;
////					ArrayList<MKCityListInfo> stCities = res.getAddrResult().mStartCityList;
////					ArrayList<MKCityListInfo> enCities = res.getAddrResult().mEndCityList;
//                    return;
//                }
//                // 错误号可参考MKEvent中的定义
//                if (error != 0 || res == null) {
//                    Toast.makeText(WalkMap.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//
//            }
//
//            @Override
//            public void onGetSuggestionResult(MKSuggestionResult mkSuggestionResult, int i) {
//                //To change body of implemented methods use File | Settings | File Templates.
//            }
//
//            @Override
//            public void onGetAddrResult(MKAddrInfo mkAddrInfo, int i) {
//                //To change body of implemented methods use File | Settings | File Templates.
//            }
//
//            @Override
//            public void onGetWalkingRouteResult(MKWalkingRouteResult mkWalkingRouteResult, int i) {
//                //To change body of implemented methods use File | Settings | File Templates.
//            }
//
//            @Override
//            public void onGetShareUrlResult(MKShareUrlResult mkShareUrlResult, int i, int i2) {
//                //To change body of implemented methods use File | Settings | File Templates.
//            }
//
//            @Override
//            public void onGetBusDetailResult(MKBusLineResult mkBusLineResult, int i) {
//                //To change body of implemented methods use File | Settings | File Templates.
//            }
//
//            @Override
//            public void onGetPoiResult(MKPoiResult mkPoiResult, int i, int i2) {
//                //To change body of implemented methods use File | Settings | File Templates.
//            }
//
//            @Override
//            public void onGetPoiDetailSearchResult(int i, int i2) {
//                //To change body of implemented methods use File | Settings | File Templates.
//            }
//
//            @Override
//            public void onGetTransitRouteResult(MKTransitRouteResult mkTransitRouteResult, int i) {
//                //To change body of implemented methods use File | Settings | File Templates.
//            }
//        });
//        bdWalk(startPoint,endPoint);
        mapController = mapView.getController();
        mapController.setZoom(18);
        mapController.setCenter(startPoint);
    }
//    private void bdWalk(GeoPoint startPoint,GeoPoint endPoint){
//        MKPlanNode stNode = new MKPlanNode();
//        stNode.pt = startPoint;
//        MKPlanNode enNode = new MKPlanNode();
//        enNode.pt = endPoint;
//        mkSearch.drivingSearch("西安",stNode,"西安",enNode);
//
//    }
    @Override
    protected void onDestroy() {
        mapView.destroy();
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
