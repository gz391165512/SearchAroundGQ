package com.example.SearchAroundUI;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-16
 * Time: 上午10:27
 * To change this template use File | Settings | File Templates.
 */
public class SpinnerActivity extends Activity {
    private Spinner spinner;
    private String[] objects = new String[]{"1000m以内", "2000m以内", "3000m以内","4000m以内","5000m以内"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.scope_imformation);


        spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
               // Object item = arg0.getAdapter().getItem(arg2);
                String item = objects[arg2];

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });


        ArrayAdapter<CharSequence> spinnerAdapter = new ArrayAdapter<CharSequence>(
            this,R.layout.spinner_item,R.id.spinner_item_content, objects);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item_content);

        Log.d("Spinner", "create ArrayAdapter");

        spinner.setAdapter(spinnerAdapter);
        Log.d("Spinner", "set ArrayAdapter");

    }
}



