package com.example.SearchAround;

import android.content.Context;
import com.baidu.mapapi.BMapManager;

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
    public static BMapManager init(Context context,int number){
        if(number == 1){
            if (mBMapManager == null) {
                mBMapManager = new BMapManager(context);
                mBMapManager.init(strKey,null);
            }
            return  mBMapManager;
        }
        if (mBMapManager2 == null) {
            mBMapManager2 = new BMapManager(context);
            mBMapManager2.init(strKey,null);
        }
        return  mBMapManager2;
        }

}
