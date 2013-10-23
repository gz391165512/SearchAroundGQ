package com.example.SearchAround;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-22
 * Time: 上午10:49
 * To change this template use File | Settings | File Templates.
 */
public class About extends Activity {
    private ImageButton backImageBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.version_information);
        backImageBtn = (ImageButton) findViewById(R.id.aboutBackIbt);
        backImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
