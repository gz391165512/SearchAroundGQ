package com.example.SearchAround;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.search.*;
import com.baidu.platform.comapi.basestruct.GeoPoint;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-19
 * Time: 下午12:34
 * To change this template use File | Settings | File Templates.
 */
public class WalkMap extends Activity {
    private MapView mapView;
    private BMapManager bMapManager;
    private MapController mapController;
    private TextView walkMapText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bMapManager =  Init.init(this,2);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.walkmap);
        mapView = (MapView) findViewById(R.id.bdMapViewWalk);

//        walkMapText = (TextView) findViewById(R.id.walkMapText);
//        walkMapText.setText(getIntent().getStringExtra("keyWord"));
//        GeoPoint startPoint = new GeoPoint(getIntent().getIntExtra("startLatitude",0),getIntent().getIntExtra("startLongitude",0));
//        GeoPoint endPoint = new GeoPoint(getIntent().getIntExtra("endLatitude",0),getIntent().getIntExtra("endLongitude",0));
        mapController = mapView.getController();
        mapController.setZoom(18);
//        mapView.refresh();
//        mapController.setCenter(startPoint);
    }
    @Override
    protected void onDestroy() {
        mapView.destroy();
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
