package com.example.SearchAround;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
 * Date: 13-10-22
 * Time: 上午11:19
 * To change this template use File | Settings | File Templates.
 */
public class Search extends Activity {
    private ImageButton searchBackIbt;
    private ImageButton searchIbt;
    private EditText searchEditText;
    private ProgressDialog progressDialog;
    private double Latitude;
    private double Longitude;
    private List<HashMap<String,Object>> totalList = new ArrayList<HashMap<String, Object>>();
    private List<HashMap<String,Object>> list = new ArrayList<HashMap<String, Object>>();
    private ListView listView;
    private MySimpleAdapter simpleAdapter;
    private Boolean flag = true;//开关是否是加载adapter
    private LinearLayout footView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search_page);
        searchBackIbt = (ImageButton) findViewById(R.id.searchBackIbt);
        searchIbt = (ImageButton) findViewById(R.id.searchIbt);
        searchEditText = (EditText) findViewById(R.id.searchEditText);
        progressDialog = new ProgressDialog(this);
        listView = (ListView) findViewById(R.id.lvSearch);
//        listView.setDivider(getResources().getDrawable(R.));
        View view = getLayoutInflater().inflate(R.layout.footview, null);
        listView.addFooterView(view);
        footView = (LinearLayout)view;
        listView.setCacheColorHint(0);
        footView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = false;
                int totalPage = Integer.parseInt(list.get(0).get("total").toString())/20;   //总页数
                int num = (listView.getCount() - 1)/20 + 1;
                if(totalPage < num){
                    Toast toast = Toast.makeText(Search.this,"已无数据,无法加载更多",Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    loadMoreData(String.valueOf(searchEditText.getText()),num);
                }
            }
        });
        searchIbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalList.clear();
                executeProgram(String.valueOf(searchEditText.getText()),"搜索中 ",1);        //按下搜索键执行的操作
            }
        });
        searchBackIbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void executeProgram(final String query,final String progressDialogMessage,final int page){
        final JsonUtil jsonUtil = new JsonUtil();
        new JsonUtil().getMyLocation(Search.this, new JsonUtil.GetMyLocationCallback(){
            @Override
            public void onLocation(BDLocation bdLocation) {
                Latitude = bdLocation.getLatitude();
                Longitude = bdLocation.getLongitude();
                AsyncTask<Integer,Integer,Integer> asyncTask = new AsyncTask<Integer, Integer, Integer>() {
                    @Override
                    protected Integer doInBackground(Integer... params) {
                        String jsonString;
                        try {
                            jsonString = jsonUtil.getJson(query,Longitude,Latitude,page,30000);
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
                            Toast toast = Toast.makeText(Search.this,"网络不通,请重试",Toast.LENGTH_SHORT) ;
                            toast.show();
                        }else if(integer == 2){
                            Toast toast = Toast.makeText(Search.this,"无法解析数据",Toast.LENGTH_SHORT) ;
                            toast.show();
                        }else if(integer == 3){
                            Toast toast = Toast.makeText(Search.this,"范围内无数据,请更改查询方式",Toast.LENGTH_SHORT) ;
                            toast.show();
                        }else{
                            loadData();
                        }
                        super.onPostExecute(integer);    //To change body of overridden methods use File | Settings | File Templates.
                    }

                    @Override
                    protected void onPreExecute() {
                        progressDialog.setMessage(progressDialogMessage);
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
        });
    }
    public void loadData(){
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
        executeProgram(query,"加载更多中",page);
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
//            convertView.setBackground(getResources().getDrawable(R.drawable.listbg));
            convertView.setBackgroundDrawable(getResources().getDrawable(R.drawable.listbg));
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Search.this,DetailMessage.class);
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
