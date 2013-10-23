package com.example.SearchAroundUI;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.baidu.location.BDLocation;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-16
 * Time: 上午9:05
 * To change this template use File | Settings | File Templates.
 */
public class ButtonActivity extends Activity {
    private Button search_button;
    private Button about_button;
    private ImageButton location_button;
    private TextView addressTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.first_page);
    }

}
