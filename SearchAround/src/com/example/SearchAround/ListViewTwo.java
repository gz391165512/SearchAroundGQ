package com.example.SearchAround;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-16
 * Time: 上午8:42
 * To change this template use File | Settings | File Templates.
 */
public class ListViewTwo extends Activity {
    private ListView listView2;
    private TextView textView;    //二层顶部textview
    private String tittleValue;
    private String [] lvTwoText;//存放在二级分类里ListVIewTwo里Text的数据
    private String [] noButtonListView;//存放不需要button的二级菜单中的文字
    private ImageButton imageButton;
    public ListViewTwo() {
    }

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
        final int positionOne = getIntent().getIntExtra("PositionParameter",-1);//  接参数，如果没有值的话是0
        listView2 = (ListView) findViewById(R.id.ListView2);
        noButtonListView = new String [] {"休闲餐饮场所","茶艺馆","冷饮店","糕饼店","甜品店","文化用品店","旅行社","信息咨询中心","物流速递","人才市场","自来水营业厅","电力营业厅",
                "电力营业厅","美容美发店","维修站点","摄影冲印店","洗浴推拿场所","洗衣店","中介机构","搬家公司","彩票彩券销售点","诊所","急救中心","疾病预防机构",
                "展览馆","会展中心","美术馆","图书馆","科技馆","天文馆","文化宫","档案馆","文艺团体","科研机构","培训机构","驾校","飞机场","火车站","长途汽车站","地铁站","轻轨站","班车站","过境口岸",
                "报刊亭","公用电话","公共厕所","紧急避难场所"};
        switch(positionOne){
            case -1 : tittleValue = "出错了,请返回";break;
            case 0 : tittleValue = "餐饮服务";
                lvTwoText = new String [] {"中餐厅","外国餐厅","快餐厅","休闲餐饮场所","咖啡厅","茶艺馆","冷饮店","糕饼店","甜品店" };  break;
            case 1 :  tittleValue = "购物服务";
                lvTwoText = new String [] {"商场","便民商店/便利店","家电电子卖场","超级市场","花鸟鱼虫市场","家具建材市场","综合市场","文化用品店","体育用品店","特色商业街","服装鞋帽皮具店","专卖店","特殊买卖场所","个人用品/化妆品店" };  break;
            case 2 :  tittleValue = "生活服务";
                lvTwoText = new String [] {"旅行社","信息咨询中心","售票处","邮局","物流速递","电讯营业厅","事务所","人才市场","自来水营业厅","电力营业厅","美容美发店","维修站点","摄影冲印店","洗浴推拿场所","洗衣店","中介机构","搬家公司","彩票彩券销售点","丧葬设施"};  break;
            case 3 :  tittleValue = "体育休闲服务";
                lvTwoText = new String [] {"运动场所","高尔夫相关","娱乐场所","度假疗养场所","休闲场所","影剧院"};  break;
            case 4 :  tittleValue = "医疗保健服务";
                lvTwoText = new String [] {"综合医院","专科医院","诊所","急救中心","疾病预防机构","医药保健相关","动物医疗场所"};  break;
            case 5 :  tittleValue = "住宿服务";
                lvTwoText = new String [] {"宾馆酒店","旅馆招待所"};  break;
            case 6 :  tittleValue = "科教文化服务";
                lvTwoText = new String [] {"博物馆","展览馆","会展中心","美术馆","图书馆","科技馆","天文馆","文化宫","档案馆","文艺团体","传媒机构","学校","科研机构","培训机构","驾校"};  break;
            case 7 :  tittleValue = "交通设施服务";
                lvTwoText = new String [] {"飞机场","火车站","港口码头","长途汽车站","地铁站","轻轨站","公交车站","班车站","停车场","过境口岸" };  break;
            case 8 :  tittleValue = "公共设施";
                lvTwoText = new String [] {"报刊亭","公用电话","公共厕所","紧急避难场所"};  break;
        }
        textView.setText(tittleValue);
        BaseAdapter baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return lvTwoText.length;
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
                Button button = (Button) view.findViewById(R.id.lvOneButton);
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.listbg));
//                view.setBackground(getResources().getDrawable(R.drawable.listbg));  2.3.3无此方法
                textView.setText(lvTwoText[position]);
                textView.setTextColor(0xFF000000);
                textView.setTextSize(16);
                final String text = (String) textView.getText();
                for(int i=0;i<noButtonListView.length;i++){
                    if(text.equals(noButtonListView[i])){
                        button.setVisibility(View.GONE);
                        break;
                    }
                }
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ListViewTwo.this,ListViewThree.class);
                        intent.putExtra("PositionParameter",positionOne);
                        intent.putExtra("PositionParameterTwo",positionTemp);
                        startActivity(intent);
                    }
                });
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ListViewTwo.this,MessageShow.class);
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
