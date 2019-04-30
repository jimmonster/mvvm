package com.jim.common.ui.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import com.jaeger.library.StatusBarUtil;

/**
 * @author :  Jim
 * @date :  2019/4/30
 * @description :
 */
public class BaseWebActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

    }

    private void init() {
        //状态栏显示效果
//        setTranslucentStatus(this, true);
        //去除标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //沉浸式状态栏
        StatusBarUtil.setTranslucent(this);

        //去除状态栏,全屏显示
        setFullscreen(false);
    }

    /**
     * @param isFull 设置是否全屏显示
     */
    public void setFullscreen(boolean isFull) {
        if (isFull) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }

    }

    /**
     * 设置沉浸式状态栏
     * SDK >= 19 (4.4): 可以设置状态栏透明，但是部分机型会有半透明的SYSTEM_BAR_BACKGROUNDS不好看
     * SDK >= 21 (5.0): 可以设置状态栏颜色，并且可以清除SYSTEM_BAR_BACKGROUNDS，但是不能设置状态栏字体颜色（默认的白色字体在浅色背景下看不清楚）
     * SDK >= 23 (6.0): 可以设置状态栏为浅色(SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)，字体就会反转为黑色
     *
     * @param activity
     * @param enabled
     */
    public void setTranslucentStatus(Activity activity, boolean enabled) {
        if (enabled) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//注意要清除 FLAG_TRANSLUCENT_STATUS flag
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().setStatusBarColor(Color.GREEN);
            }
        }
    }
}
