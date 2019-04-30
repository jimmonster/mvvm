package com.jim.common.utils;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;


import java.util.Date;

public class WaterMarkTextUtil {
    //设置背景
    @SuppressWarnings("deprecation")
    public void setWaterMarkTextBg(View view, Context gContext, String userId, String userName, int alpha) {
        view.setBackgroundDrawable(drawTextToBitmap(gContext, mergeString(userId, userName), alpha, false));
    }

    @SuppressWarnings("deprecation")
    public void setWaterMarkTextTransparentBg(View view, Context gContext, String userId, String userName, int alpha) {
        view.setBackgroundDrawable(drawTextToBitmap(gContext, mergeString(userId, userName), alpha, true));
    }

    /**
     * 生成水印文字图片
     */
    @SuppressWarnings("deprecation")
    public BitmapDrawable drawTextToBitmap(Context gContext, String gText, int alpha, boolean transparent) {
        int alphaNum = 50;
        if (alpha != -1) {
            alphaNum = alpha;
        }
        if (ACache.get(gContext).getAsBitmap(gText) != null) {
            BitmapDrawable drawable = new BitmapDrawable(ACache.get(gContext).getAsBitmap(gText));
            return drawable;
        }
        try {
            DisplayMetrics dm = getDisplayMetrics(gContext);
            int width = dm.widthPixels;
            int height = dm.heightPixels;
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            if (transparent) {
                canvas.drawColor(Color.TRANSPARENT);
            } else {
                canvas.drawColor(Color.WHITE);
            }
            Paint paint = new Paint();
            paint.setColor(Color.LTGRAY);
            paint.setAlpha(alphaNum);
            paint.setAntiAlias(true);
            paint.setTextAlign(Paint.Align.LEFT);
            paint.setTextSize(DensityUtils.sp2px(gContext, 16));
            canvas.rotate(-15);
            int textWidth = getTextWidth(paint, gText);
            int textHeight = getTextHeight(paint, gText);
            int verticalSpacing = 380;
            int xCount = width / textWidth + 2;
            int yCount = height / (textHeight + verticalSpacing);
            for (int r = 0; r < yCount; r++) {
                for (int c = 0; c < xCount; c++) {
                    float x = -(r + 1) * 150 + textWidth * c + 200 * c;// 字符串末尾的空格不会被绘制
                    float y = (verticalSpacing + textHeight) * (r + 1);
                    canvas.drawText(gText, x, y, paint);
                }
            }
//            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.save();
            canvas.restore();
            ACache.get(gContext).put(gText, bitmap);
            BitmapDrawable drawable = new BitmapDrawable(bitmap);
            return drawable;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String mergeString(String userId, String userName) {
        String user = userId;
        if (userId.length() == 32) {
            user = userId.substring(24);
        }
        String date = FormatUtils.formatDate(new Date(), "yy-MM-dd HH:mm");
        return user + "　　" + date;
    }

    /**
     * 获取DisplayMetrics对象
     *
     * @param context
     * @return
     */
    private static DisplayMetrics getDisplayMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

//    private static int getTextWidth(Paint paint, String str) {
//        int iRet = 0;
//        if (str != null && str.length() > 0) {
//            int len = str.length();
//            float[] widths = new float[len];
//            paint.getTextWidths(str, widths);
//            for (int j = 0; j < len; j++) {
//                iRet += (int) Math.ceil(widths[j]);
//            }
//        }
//        return iRet;
//    }

    private static int getTextWidth(Paint paint, String text) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.width();
    }

    private static int getTextHeight(Paint paint, String text) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.height();
    }
}