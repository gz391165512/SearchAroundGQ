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
            if(positionTwo == 0){
                lvTwoText = new String [] {"购物中心","普通商城","免税品店"};
                tittleValue = "商城";
            }else if(positionTwo == 1){
                lvTwoText = new String[] { "7-ELEVEN便利店","OK便利店"};
                tittleValue = "便民商店/便利店";
            }else if(positionTwo == 2){
                lvTwoText = new String[] { "综合家电商场","国美","大中","苏宁","手机销售","数码电子","丰泽","镭射"};
                tittleValue = "家电电子卖场";
            }else if(positionTwo == 3){
                lvTwoText = new String[] { "家福乐","沃尔玛","华润","北京华联","上海华联","麦德龙","万客隆","华堂","易初莲花","好又多","屈臣氏","乐购","惠康超市","百佳超市","万宁超市"};
                tittleValue = "超级市场";
            }else if(positionTwo == 4){
                lvTwoText = new String[] { "花卉市场","宠物市场"};
                tittleValue = "花鸟鱼虫市场";
            }else if(positionTwo == 5){
                lvTwoText = new String[] { "家具建材综合市场","家具城","建材五金市场","厨卫市场","布艺市场","灯具瓷器市场"};
                tittleValue = "家居建材市场";
            }else if(positionTwo == 6){
                lvTwoText = new String[] { "小商品市场","旧货市场","农副产品市场","果品市场","蔬菜市场","水产海鲜市场"};
                tittleValue = "综合市场";
            }else if(positionTwo == 8){
                lvTwoText = new String[] { "李宁专卖店","耐克专卖店","阿迪达斯专卖店","锐步专卖店","彪马专卖店","高尔夫专卖店","户外用品"};
                tittleValue = "体育用品店";
            }else if(positionTwo == 9){
                lvTwoText = new String[] { "步行街"};
                tittleValue = "特色商业街";
            }else if(positionTwo == 10){
                lvTwoText = new String[] { "品牌服装店","品牌鞋店","品牌皮具店"};
                tittleValue = "服装鞋帽皮具店";
            }else if(positionTwo == 11){
                lvTwoText = new String[] { "古玩字画店","珠宝首饰工艺品","钟表店","眼镜店","书店","音像店","儿童用品店","自行车专卖店","礼品饰品店","烟酒专卖店","宠物用品店","摄影器材店","宝马生活方式"};
                tittleValue = "专卖店";
            }else if(positionTwo == 12){
                lvTwoText = new String[] { "拍卖行","典当行"};
                tittleValue = "特殊买卖场所";
            }else if(positionTwo == 13){
                lvTwoText = new String[] { "莎莎"};
                tittleValue = "个人用品/化妆品店";
            }
        }else if(positionOne == 2){
            if(positionTwo == 2){
                lvTwoText = new String [] {"飞机票代售点","火车票代售点","长途汽车票代售点","船票代售点","公交卡/月票代售点","公园景点售票处"};
                tittleValue = "售票处";
            }else if(positionTwo == 3){
                lvTwoText = new String[] { "邮政速递"};
                tittleValue = "邮局";
            }else if(positionTwo == 5){
                lvTwoText = new String[] { "中国电信营业厅","中国网通营业厅","中国移动营业厅","中国联通营业厅","中国铁通营业厅","中国卫通营业厅","和记电讯","数码通电讯","电讯盈科","中国移动万众/Peoples"};
                tittleValue = "电讯营业厅";
            }else if(positionTwo == 6){
                lvTwoText = new String[] { "律师事务所","会计师事务所","评估事务所","审计事务所","认证事务所","专利事务所"};
                tittleValue = "事务所";
            }else if(positionTwo == 18){
                lvTwoText = new String[] { "陵园","公墓","殡仪馆"};
                tittleValue = "丧葬设施";
            }
        }else if(positionOne == 3){
            if(positionTwo == 0){
                lvTwoText = new String [] {"综合体育馆","保龄球馆","网球场","篮球场馆","足球场","滑雪场","溜冰场","户外健身场所","海滨浴场","游泳馆","健身中心","乒乓球馆","台球厅","壁球场","橄榄球场","羽毛球场","跆拳道场馆"};
                tittleValue = "运动场馆";
            }else if(positionTwo == 1){
                lvTwoText = new String[] { "高尔夫球场","高尔夫练习场"};
                tittleValue = "高尔夫相关";
            }else if(positionTwo == 2){
                lvTwoText = new String[] { "夜总会","K T V","迪厅","酒吧","游戏厅","棋牌室","博彩中心","网吧"};
                tittleValue = "娱乐场所";
            }else if(positionTwo == 3){
                lvTwoText = new String[] { "度假村","疗养院"};
                tittleValue = "度假休养场所";
            }else if(positionTwo == 4){
                lvTwoText = new String[] { "游乐场","垂钓场","采摘园","露营地","水上活动中心"};
                tittleValue = "休闲场所";
            }else if(positionTwo == 5){
                lvTwoText = new String[] { "电影院","音乐厅","剧场"};
                tittleValue = "影剧院";
            }
        }else if(positionOne == 4){
            if(positionTwo == 0){
                lvTwoText = new String [] {"三级甲等医院","卫生院"};
                tittleValue = "综合医院";
            }else if(positionTwo == 1){
                lvTwoText = new String[] {"整形美容","口腔医院","眼科医院","耳鼻喉医院","胸科医院","骨科医院","肿瘤医院","脑科医院","妇科医院","精神科医院","传染科医院"};
                tittleValue = "专科医院";
            }else if(positionTwo == 5){
                lvTwoText = new String[] { "药房","医疗保健用品"};
                tittleValue = "医药保健相关";
            }else if(positionTwo == 6){
                lvTwoText = new String[] {"宠物诊所","兽医站"};
                tittleValue = "动物医疗场所";
            }
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
