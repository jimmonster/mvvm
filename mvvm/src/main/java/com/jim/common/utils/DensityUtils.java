package com.jim.common.utils;

import android.content.Context;

/**
 * @author :  Jim
 * @date :  2019/1/2
 * @description :
 */
public class DensityUtils {
    public DensityUtils() {
    }

    public static int dip2px(Context var0, float var1) {
        float var2 = var0.getResources().getDisplayMetrics().density;
        return (int)(var1 * var2 + 0.5F * (float)(var1 >= 0.0F ? 1 : -1));
    }

    public static int sp2px(Context var0, float var1) {
        float var2 = var0.getResources().getDisplayMetrics().scaledDensity;
        return (int)(var1 * var2 + 0.5F);
    }

    public static int px2dip(Context var0, float var1) {
        float var2 = var0.getResources().getDisplayMetrics().density;
        return (int)(var1 / var2 + 0.5F * (float)(var1 >= 0.0F ? 1 : -1));
    }

    public static int px2sp(Context var0, float var1) {
        float var2 = var0.getResources().getDisplayMetrics().scaledDensity;
        return (int)(var1 / var2 + 0.5F);
    }
}

