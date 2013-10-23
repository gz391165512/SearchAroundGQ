package com.example.SearchAround;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.*;
import com.baidu.platform.comapi.basestruct.GeoPoint;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bMapManager =  Init.init(this,2);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.detailed_information);
        detailAddress = (TextView) findViewById(R.id.detailAddress);
        detailName = (TextView) findViewById(R.id.detailName);
        detailPhone = (TextView) findViewById(R.id.detailPhone);
        detailBackIbt = (ImageButton) findViewById(R.id.detailBackIbt);
        mapView = (MapView) findViewById(R.id.bdMapDetail);
        detailAddress.setText(getIntent().getStringExtra("address"));
        detailName.setText(getIntent().getStringExtra("name"));
        endLongitude =  Double.parseDouble(getIntent().getStringExtra("endLongitude"));
        endLatitude =  Double.parseDouble(getIntent().getStringExtra("endLatitude"));
        startLongitude = getIntent().getDoubleExtra("startLongitude",0);
        startLatitude = getIntent().getDoubleExtra("startLatitude",0);
        String phone =  getIntent().getStringExtra("tel") ;
        if(phone.equals("")){
            detailPhone.setText("暂无号码");
        }else{
            detailPhone.setText(phone);
        }
        detailBackIbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mapController = mapView.getController();
        final GeoPoint startPoint = new GeoPoint((int)(startLatitude*1E6),(int)(startLongitude*1E6));
        GeoPoint endGeoPoint = new GeoPoint((int)(endLatitude*1E6),(int)(endLongitude*1E6));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mapController.animateTo(startPoint);
            }
        }, 2000);
        mapController.setZoom(16);
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
        mapView.getOverlays().add(startOverlay);
        mapView.getOverlays().add(endOverlay);
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
