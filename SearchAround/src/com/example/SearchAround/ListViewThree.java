package com.example.SearchAround;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-16
 * Time: 上午9:56
 * To change this template use File | Settings | File Templates.
 */
public class ListViewThree extends Activity {
    private ListView listView2;
    private TextView textView;    //二层顶部textview
    private String tittleValue;
    private ImageButton imageButton;
    private String [] lvTwoText;//存放在二级分类里ListVIewTwo里Text的数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.lvtwo);
        textView = (TextView) findViewById(R.id.lvTwoTextView);
        imageButton = (ImageButton) findViewById(R.id.lvTwoBackBt);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        int positionOne = getIntent().getIntExtra("PositionParameter",-1);//  接参数，如果没有值的话是0
        int positionTwo = getIntent().getIntExtra("PositionParameterTwo",-1);//  接参数，如果没有值的话是0
        listView2 = (ListView) findViewById(R.id.ListView2);
        if(positionOne == 0){
            if(positionTwo == 0){
                lvTwoText = new String [] {"综合酒楼","四川菜（川菜）","广东菜（粤菜）","山东菜（鲁菜）","江苏菜 ","浙江菜","上海菜","湖南菜（湘菜）","安徽菜（徽菜）","福建菜","北京菜","湖北菜（鄂菜）","东北菜","云贵菜","西北菜","老字号","火锅店","特色／地方风味餐厅","海鲜酒楼","中式素菜馆","清真菜馆","台湾菜","潮州菜"};
                tittleValue = "中餐厅";
            }else if(positionTwo == 1){
                lvTwoText = new String[] { "西餐厅","日本料理","韩国料理","法式菜品餐厅","日本料理","意式菜品餐厅","泰国/越南菜品餐厅","地中海风格菜品","美式风味","印式风味","英国式菜品餐厅","牛扒店", "俄国菜","葡国菜","德国菜","巴西菜","墨西哥菜","其他亚洲菜"};
                tittleValue = "外国餐厅";
            }else if(positionTwo == 2){
                lvTwoText = new String[] { "肯德基","麦当劳","必胜客","永和豆浆","茶餐厅","大家乐","大快活","美心","吉野家","仙跡岩"};
                tittleValue = "快餐厅";
            }else if(positionTwo == 4){
                lvTwoText = new String[] { "咖啡厅","星巴克咖啡","上岛咖啡","Pacific Coffee Company","茶餐厅","巴黎咖啡店"};
                tittleValue = "咖啡厅";
            }
        }else if(positionOne == 1){

        }else if(positionOne == 2){

        }else if(positionOne == 3){

        }else if(positionOne == 4){

        }else if(positionOne == 5){
            if(positionTwo == 0){
                lvTwoText = new String [] {"六星级宾馆","五星级宾馆","四星级宾馆","三星级宾馆","经济型连锁酒店"};
                tittleValue = "宾馆酒店";
            }else if(positionTwo == 1){
                lvTwoText = new String [] {"青年旅社"};
                tittleValue = "旅馆招待所";
            }
        }else if(positionOne == 6){
            if(positionTwo == 0){
                lvTwoText = new String [] {"奥迪博物馆","奔驰博物馆"};
                tittleValue = "博物馆";
            }else if(positionTwo == 10){
                lvTwoText = new String [] {"电视台","电台","报社","杂志社","出版社"};
                tittleValue = "传媒机构";
            }else if(positionTwo == 11){
                lvTwoText = new String [] {"高等院校","中学","小学","幼儿园","成人教育","职业技术学校"};
                tittleValue = "学校";
            }
        }else if(positionOne == 7){
            if(positionTwo == 2){
                lvTwoText = new String [] {"客运港","车渡口","人渡口"};
                tittleValue = "港口码头";
            }else if(positionTwo == 6){
                lvTwoText = new String [] {"旅游专线车站","普通公交站"};
                tittleValue = "公交车站";
            }else if(positionTwo == 8){
                lvTwoText = new String [] {"室内停车场","室外停车场","停车换乘点"};
                tittleValue = "停车场";
            }
        }else if(positionOne == 8){
        }
        textView.setText(tittleValue);
        BaseAdapter baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return lvTwoText.length;  //To change body of implemented methods use File | Settings | File Templates.
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
                View view = getLayoutInflater().inflate(R.layout.lvonecontent,parent,false);     //!!!!!!!!
                TextView textView = (TextView) view.findViewById(R.id.lvOneTextView);
                view.setBackground(getResources().getDrawable(R.drawable.listbg));
                Button button = (Button) view.findViewById(R.id.lvOneButton);
                textView.setText(lvTwoText[position]);
                textView.setTextColor(0xFF000000);
                textView.setTextSize(16);
                button.setVisibility(View.GONE);
                final String text = (String) textView.getText();
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ListViewThree.this,MessageShow.class);
                        intent.putExtra("keyValue",text);
                        startActivity(intent);
                    }
                });
                return view;
            }
        };
        listView2.setAdapter(baseAdapter);
    }
}
