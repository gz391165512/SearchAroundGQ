package com.example.SearchAround;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.*;
import com.baidu.platform.comapi.basestruct.GeoPoint;



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
    private double getLatitude;
    private double getLongitude;
    private PoiOverlay poiOverlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bMapManager = new BMapManager(this);
        String query = getIntent().getStringExtra("query");
        bMapManager.init(StringKey,new MKGeneralListener() {
            @Override
            public void onGetNetworkState(int error) {
                if (error == MKEvent.ERROR_NETWORK_CONNECT) {
                    Toast.makeText(MapShow.this, "您的网络出错啦！", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onGetPermissionState(int error) {
                if (error == MKEvent.ERROR_PERMISSION_DENIED) {
                    Toast.makeText(MapShow.this, "请在 DemoApplication.java文件输入正确的授权Key！", Toast.LENGTH_LONG).show();
                }
            }
        });
        setContentView(R.layout.mapshow);
        mapView = (MapView) findViewById(R.id.bdMapView);
        mapShowTextView = (TextView) findViewById(R.id.mapShowTextView);
        mapShowTextView.setText(query);
        mapController = mapView.getController();
        mapController.setZoom(19);
        Log.e("-------------!",JsonUtil.address);
        new JsonUtil().getMyLocation(MapShow.this, new JsonUtil.GetMyLocationCallback(){
            @Override
            public void onLocation(BDLocation bdLocation) {
                getLatitude = bdLocation.getLatitude();
                getLongitude = bdLocation.getLongitude();
                final GeoPoint geoPoint = new GeoPoint((int)(getLatitude*1E6),(int)(getLongitude*1E6));
                mapController.setCenter(geoPoint);
                Drawable centerIop = getResources().getDrawable(R.drawable.u22_normal);
                poiOverlay = new PoiOverlay(centerIop,mapView);
                OverlayItem overlayItem = new OverlayItem(geoPoint,"","");
                poiOverlay.addItem(overlayItem);
                mapView.getOverlays().add(poiOverlay) ;
            }
        });
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mapController.animateTo(geoPoint);
//            }
//        }, 3000);


    }
    class PoiOverlay extends ItemizedOverlay{
        @Override
        protected boolean onTap(int i) {
            return super.onTap(i);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public boolean onTap(GeoPoint geoPoint, MapView mapView) {
            return super.onTap(geoPoint, mapView);    //To change body of overridden methods use File | Settings | File Templates.
        }

        public PoiOverlay(Drawable drawable, MapView mapView) {
            super(drawable, mapView);    //To change body of overridden methods use File | Settings | File Templates.
        }
    }
}
