package com.example.SearchAround;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private ListView listView1; //主页的listView
    private String [] lvOneText;//存放主页ListVIew里Text的数据
    private ImageButton locationButton;
    private TextView addressTextView;
    private Map<String,Object> map = new HashMap<String, Object>();//接受参数
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        locationButton = (ImageButton) findViewById(R.id.locationButton);
        addressTextView = (TextView) findViewById(R.id.addressTextView);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressTextView.setText("定位中");
                 new JsonUtil().getMyLocation(MyActivity.this, new JsonUtil.GetMyLocationCallback(){
                     @Override
                     public void onLocation(BDLocation bdLocation) {
                         addressTextView.setText(bdLocation.getAddrStr());
//                         Log.e("BaiduMapDemo",""+bdLocation.getAddrStr());
                     }
                 });

            }
        });
        listView1 = (ListView) findViewById(R.id.ListView1);
        lvOneText = new String [] {"餐饮服务","购物服务","生活服务","体育休闲服务","医疗保健服务","住宿服务","科教文化服务","交通设施服务","公共设施"};
        BaseAdapter baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return lvOneText.length;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public Object getItem(int position) {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public long getItemId(int position) {
                return position;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final int positionTemp = position;
                View view = getLayoutInflater().inflate(R.layout.lvonecontent,parent,false);     //!!!!!!!!
                TextView textView = (TextView) view.findViewById(R.id.lvOneTextView);
                textView.setText(lvOneText[position]);
                textView.setTextColor(0xFF000000);
                textView.setTextSize(16);
                final String text = (String) textView.getText();
                Button button = (Button) view.findViewById(R.id.lvOneButton);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MyActivity.this,ListViewTwo.class);
                        intent.putExtra("PositionParameter",positionTemp);
                        startActivity(intent);
                    }
                });
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MyActivity.this,MessageShow.class);
                        intent.putExtra("keyValue",text);
                        startActivity(intent);
                    }
                });
                return view;
            }
        };
        listView1.setAdapter(baseAdapter);
    }

}
