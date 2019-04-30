package com.zefusoft.mobileoffice.utils

import android.graphics.Color
import com.blankj.utilcode.util.Utils
import com.zyao89.view.zloading.ZLoadingDialog
import com.zyao89.view.zloading.Z_TYPE


/**
 * @author :  Jim
 * @date :  2019/1/15
 * @description :
 *
 */
object ProgressUtils {
    /**
     * 加载成功
     */
    fun success(){
        val dialog = ZLoadingDialog(Utils.getApp())
        dialog.setLoadingBuilder(Z_TYPE.CIRCLE)//设置类型
            .setLoadingColor(Color.RED)//颜色
            .setHintText("Loading...")
            .setHintTextSize(16f) // 设置字体大小 dp
            .setHintTextColor(Color.GRAY)  // 设置字体颜色
            .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
            .setDialogBackgroundColor(Color.parseColor("#CC111111")) // 设置背景色，默认白色
            .show()
    }

    /**
     * 加载失败
     */
    fun error(){


    }

    /**
     * 加载失败重新加载
     */
    fun reload(){

    }

}