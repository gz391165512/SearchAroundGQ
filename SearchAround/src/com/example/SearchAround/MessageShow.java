package com.example.SearchAround;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;
import com.baidu.location.BDLocation;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-16
 * Time: 下午9:03
 * To change this template use File | Settings | File Templates.
 */
public class MessageShow extends Activity {
    private ProgressDialog progressDialog;
    private ListView listView;
    private List<HashMap<String,Object>> totalList = new ArrayList<HashMap<String, Object>>();
    private List<HashMap<String,Object>> list = new ArrayList<HashMap<String, Object>>();
    private ImageButton backBtn;
    private ImageButton refreshBtn;
    private ImageButton changeBtn;
    private TextView messageTextView;
    private LinearLayout footView;
    private MySimpleAdapter simpleAdapter;
    private Spinner spinner;
    private String[] objects = new String[]{"1000m以内", "2000m以内", "3000m以内","4000m以内","5000m以内"};
    private Boolean flag = true;//开关是否是加载adapter
    private double Latitude;
    private double Longitude;
    private String scope;
    private ProgressDialog progressDialogMessageLocation = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.message);
        backBtn = (ImageButton) findViewById(R.id.messageBackBtn);
        refreshBtn = (ImageButton) findViewById(R.id.messageRefreshBtn);
        changeBtn = (ImageButton) findViewById(R.id.messageChangeBtn);
        messageTextView = (TextView) findViewById(R.id.messageTextView);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView = (ListView) findViewById(R.id.lvMessage);
        View view = getLayoutInflater().inflate(R.layout.footview, null);
        listView.addFooterView(view);
        final String query = getIntent().getStringExtra("keyValue");
        messageTextView.setText(query);
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessageShow.this,MapShow.class);
                intent.putExtra("query",query);
                intent.putExtra("scope",scope);
                startActivity(intent);
            }
        });
        footView = (LinearLayout)view;
        footView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = false;
                int totalPage = Integer.parseInt(list.get(0).get("total").toString())/20;   //总页数
                int num = (listView.getCount() - 1)/20 + 1;
                if(totalPage < num){
                    Toast toast = Toast.makeText(MessageShow.this,"已无数据,无法加载更多",Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    loadMoreData(query,num);
                }
            }
        });
        progressDialog = new ProgressDialog(this);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalList.clear();
                spinner.setSelection(2);
                executeProgram(query,"刷新中",1,3000);
            }
        });
        //spinner
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
//                Object item = arg0.getAdapter().getItem(arg2);
                String item = objects[arg2].substring(0,4);
                executeProgram(query,"按范围加载中",1,Integer.parseInt(item));
                scope = item;
                listView.refreshDrawableState();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });


        ArrayAdapter<CharSequence> spinnerAdapter = new ArrayAdapter<CharSequence>(
                this,R.layout.spinner_item,R.id.spinner_item_content, objects);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_content);

        Log.d("Spinner", "create ArrayAdapter");

        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(2);
        Log.d("Spinner", "set ArrayAdapter");

    }
    public void executeProgram(final String query,final String progressDialogMessage,final int page,final int scope){
        if(JsonUtil.latitude == 0){
            progressDialogMessageLocation = new ProgressDialog(MessageShow.this);
            progressDialogMessageLocation.setMessage("定位中,请稍等");
            progressDialogMessageLocation.show();
            new JsonUtil().getMyLocation(MessageShow.this, new JsonUtil.GetMyLocationCallback(){
                @Override
                public void onLocation(BDLocation bdLocation) {
                    Latitude = bdLocation.getLatitude();
                    Longitude = bdLocation.getLongitude();
                    asyncTask(query,progressDialogMessage,page,scope);
                }
            });
        }else{
            Latitude = JsonUtil.latitude;
            Longitude = JsonUtil.longitude;
            asyncTask(query,progressDialogMessage,page,scope);
        }
    }
    public void loadData(){
        if(flag == true){
            totalList.clear();
        }
        for(int i=1;i<list.size();i++){
            totalList.add(list.get(i));
        }
       if(flag == true){
           simpleAdapter = new MySimpleAdapter(this,totalList,R.layout.lvmessagecontent,new String []{"address","name","distance"},
                   new int []{R.id.lvMessageTextView1,R.id.lvMessageTextView2,R.id.lvMessageTextView3});
            listView.setAdapter(simpleAdapter);
       }else{
           simpleAdapter.notifyDataSetChanged();
       }
       flag = true;
    }

    public void loadMoreData(String query,int page){
        executeProgram(query,"加载更多中",page,3000);
    }
    public void asyncTask (String query,String progressDialogMessage,int page,final int scope){
        final String finalQuery = query;
        final JsonUtil jsonUtil = new JsonUtil();
        final int finalPage = page;
        final String finalProgressDialogMessage =   progressDialogMessage;
        AsyncTask<Integer,Integer,Integer> asyncTask = new AsyncTask<Integer, Integer, Integer>() {

            @Override
            protected Integer doInBackground(Integer... params) {
                String jsonString;
                try {
                    jsonString = jsonUtil.getJson(finalQuery,Longitude,Latitude,finalPage,scope);
                } catch (IOException e) {
                    e.printStackTrace();
                    return 1;//代表网络有问题
                }
                try {
                    list = jsonUtil.parseJsonTwo(jsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return 2;//代表json解析有问题
                }
                if(list == null){
                    return 3;
                }
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            protected void onPostExecute(Integer integer) {
                progressDialog.dismiss();
                if(integer == 1){
                    Toast toast = Toast.makeText(MessageShow.this,"网络不通,请重试",Toast.LENGTH_SHORT) ;
                    toast.show();
                }else if(integer == 2){
                    Toast toast = Toast.makeText(MessageShow.this,"无法解析数据",Toast.LENGTH_SHORT) ;
                    toast.show();
                }else if(integer == 3){
                    Toast toast = Toast.makeText(MessageShow.this,"范围内无数据,请更改查询方式或者网络不良",Toast.LENGTH_SHORT) ;
                    toast.show();
                }else{
                    loadData();
                }
                super.onPostExecute(integer);    //To change body of overridden methods use File | Settings | File Templates.
            }

            @Override
            protected void onPreExecute() {
                if(progressDialogMessageLocation != null){
                    progressDialogMessageLocation.dismiss();
                }
                progressDialog.setMessage(finalProgressDialogMessage);
                progressDialog.show();
                super.onPreExecute();    //To change body of overridden methods use File | Settings | File Templates.
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);    //To change body of overridden methods use File | Settings | File Templates.
            }

        };
        asyncTask.execute(0);
    }
    class MySimpleAdapter extends  SimpleAdapter{
        public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.lvmessagecontent,null);
            }
            final TextView textView = (TextView) convertView.findViewById(R.id.lvMessageTextView1);
            convertView.setBackground(getResources().getDrawable(R.drawable.listbg));
            convertView.setBackground(getResources().getDrawable(R.drawable.listbg));

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MessageShow.this,DetailMessage.class);
                    intent.putExtra("address",totalList.get(position).get("address").toString());
                    intent.putExtra("name",totalList.get(position).get("name").toString());
                    intent.putExtra("tel",totalList.get(position).get("tel").toString());
                    intent.putExtra("startLongitude",Longitude);
                    intent.putExtra("startLatitude",Latitude);
                    intent.putExtra("endLongitude",totalList.get(position).get("longitude").toString());
                    intent.putExtra("endLatitude",totalList.get(position).get("latitude").toString());
                    startActivity(intent);
                }
            });
            return super.getView(position, convertView, parent);    //To change body of overridden methods use File | Settings | File Templates.
        }
    }
}
