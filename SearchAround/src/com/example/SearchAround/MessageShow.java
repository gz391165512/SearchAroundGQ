package com.example.SearchAround;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
    private SimpleAdapter simpleAdapter;
    private Spinner spinner;
    private String[] objects = new String[]{"1000m以内", "2000m以内", "3000m以内","4000m以内","5000m以内"};
    private Boolean flag = true;//开关是否是加载adapter
    private double Latitude;
    private double Longitude;
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
                startActivity(intent);
            }
        });
        footView = (LinearLayout)view;
        footView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = false;
                int num = (listView.getCount() - 1)/20 + 1;
                loadMoreData(query,num);
            }
        });
        progressDialog = new ProgressDialog(this);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeProgram(query,"刷新中",1);
            }
        });
        executeProgram(query,"加载中",1);
        //spinner
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
                Object item = arg0.getAdapter().getItem(arg2);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }

        });

        MySpinnerAdapter<String> spinnerAdapter = new MySpinnerAdapter<String>(
                this, R.layout.spinner_item, R.id.spinner_item_content, objects);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);

        Log.d("Spinner", "create MySpinnerAdapter");



        spinner.setAdapter(spinnerAdapter);
        Log.d("Spinner", "set MySpinnerAdapter");
    }
    public void executeProgram(String query,String progressDialogMessage,int page){
        final String finalQuery = query;
        final JsonUtil jsonUtil = new JsonUtil();
        final int finalPage = page;
        final String finalProgressDialogMessage =   progressDialogMessage;
        new JsonUtil().getMyLocation(MessageShow.this, new JsonUtil.GetMyLocationCallback(){
            @Override
            public void onLocation(BDLocation bdLocation) {
                Latitude = bdLocation.getLatitude();
                Longitude = bdLocation.getLongitude();
                Log.e("BaiduMapDemo","begin");
                AsyncTask<Integer,Integer,Integer> asyncTask = new AsyncTask<Integer, Integer, Integer>() {

                    @Override
                    protected Integer doInBackground(Integer... params) {
                        String jsonString;
                        try {
                            jsonString = jsonUtil.getJson(finalQuery,Longitude,Latitude,finalPage);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return 1;//代表网络有问题
                        }
                        try {
                            list = jsonUtil.parseJson(jsonString);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return 2;//代表json解析有问题
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
                        }else{
                            loadData();
                        }
                        super.onPostExecute(integer);    //To change body of overridden methods use File | Settings | File Templates.
                    }

                    @Override
                    protected void onPreExecute() {
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
        });



    }
    public void loadData(){
        for(int i=0;i<list.size();i++){
            totalList.add(list.get(i));
        }
       if(flag == true){
           simpleAdapter = new SimpleAdapter(this,totalList,R.layout.lvmessagecontent,new String []{"address","name","distance"},
                   new int []{R.id.lvMessageTextView1,R.id.lvMessageTextView2,R.id.lvMessageTextView3});
            listView.setAdapter(simpleAdapter);
       }else{
           simpleAdapter.notifyDataSetChanged();
       }
       flag = true;
    }
    class MySpinnerAdapter<T> extends ArrayAdapter<T> {
        private int dropDownViewResourceId;
        private LayoutInflater inflater;

        public MySpinnerAdapter(Context context, int textViewResourceId,
                                T[] objects) {
            super(context, textViewResourceId, objects);
            init();
        }

        public MySpinnerAdapter(Context context, int resource,
                                int textViewResourceId, T[] objects) {
            super(context, resource, textViewResourceId, objects);
            init();
        }
        public MySpinnerAdapter(Context context, int resource,
                                int textViewResourceId) {
            super(context, resource, textViewResourceId);
            init();
        }

        public void setDropDownViewResource(int resource) {
            super.setDropDownViewResource(resource);
            dropDownViewResourceId = resource;
        }

        public void init() {
            inflater = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return super.getView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {

            Log.d("Spinner", "getDropDownView at position " + position);

            Object item = getItem(position);

            LinearLayout dropDownItemView = (LinearLayout) inflater.inflate(dropDownViewResourceId,
                    null);

            TextView text1 = (TextView) dropDownItemView
                    .findViewById(R.id.spinner_item_content);
            text1.setText(item.toString());
            return dropDownItemView;
        }

    }
    public void loadMoreData(String query,int page){
        executeProgram(query,"加载更多中",page);
        Log.e("aaaaaaaa",page + "-----------");
    }
}
