package com.example.SearchAround;

import android.content.Context;
import android.widget.Toast;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-21
 * Time: 下午2:52
 * To change this template use File | Settings | File Templates.
 */
public class Init{
    private static BMapManager mBMapManager = null;
    private static BMapManager mBMapManager2 = null;
    private static final String strKey = "D62a405bc0e4bcbebbaac9a9e6870242";
    public static BMapManager init(final Context context,int number){
        if(number == 1){
            if (mBMapManager == null) {
                mBMapManager = new BMapManager(context);
                mBMapManager.init(strKey,new MKGeneralListener() {
                    @Override
                    public void onGetNetworkState(int error) {
                        if (error == MKEvent.ERROR_NETWORK_CONNECT) {
                            Toast.makeText(context, "您的网络出错啦！", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onGetPermissionState(int error) {
                        if (error == MKEvent.ERROR_PERMISSION_DENIED) {
                            Toast.makeText(context, "请在 DemoApplication.java文件输入正确的授权Key！", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
            return  mBMapManager;
        }
        if (mBMapManager2 == null) {
            mBMapManager2 = new BMapManager(context);
            mBMapManager2.init(strKey,new MKGeneralListener() {
                @Override
                public void onGetNetworkState(int error) {
                    if (error == MKEvent.ERROR_NETWORK_CONNECT) {
                        Toast.makeText(context, "您的网络出错啦！", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onGetPermissionState(int error) {
                    if (error == MKEvent.ERROR_PERMISSION_DENIED) {
                        Toast.makeText(context, "请在 DemoApplication.java文件输入正确的授权Key！", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        return  mBMapManager2;
        }

}
