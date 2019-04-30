package com.jim.common.ui.activity

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import com.jim.common.constant.Constant
import com.jim.common.ui.web.BrowserActivity
import com.jim.common.vm.viewmodel.CommonViewModel
import com.zyao89.view.zloading.ZLoadingDialog
import com.zyao89.view.zloading.Z_TYPE
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity

/**
 * @author :  Jim
 * @date :  2018/12/7
 * @description :
 *
 */
abstract class BaseActivity : AppCompatActivity(), AnkoLogger {
    var viewModel: CommonViewModel? = null
    var dialog: ZLoadingDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setContentView(initLayoutID())
        initViewModel()
        initData()
        initView()
        initDialog()
        initEvent()


    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(
            CommonViewModel::class.java
        )
    }

    /**
     * 加载网页
     */
    fun href(url: String) {
        startActivity<BrowserActivity>(Constant.PARAM_URL to url)
    }

    //初始化进度条
    private fun initDialog() {
        dialog = ZLoadingDialog(this)
        dialog?.setLoadingBuilder(Z_TYPE.CIRCLE)//设置类型
            ?.setLoadingColor(Color.WHITE)//颜色
            ?.setHintText("Loading...")
            ?.setHintTextSize(16f) // 设置字体大小 dp
            ?.setHintTextColor(Color.GRAY)  // 设置字体颜色
            ?.setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
            ?.setDialogBackgroundColor(Color.parseColor("#CC111111")) // 设置背景色，默认白色

    }

    /**
     * 设置沉浸式状态栏
     * SDK >= 19 (4.4): 可以设置状态栏透明，但是部分机型会有半透明的SYSTEM_BAR_BACKGROUNDS不好看
     * SDK >= 21 (5.0): 可以设置状态栏颜色，并且可以清除SYSTEM_BAR_BACKGROUNDS，但是不能设置状态栏字体颜色（默认的白色字体在浅色背景下看不清楚）
     * SDK >= 23 (6.0): 可以设置状态栏为浅色(SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)，字体就会反转为黑色
     * @param activity
     * @param enabled
     */
    fun setTranslucentStatus(activity: Activity, enabled: Boolean) {
        if (enabled) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            //注意要清除 FLAG_TRANSLUCENT_STATUS flag
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.window.statusBarColor = Color.GREEN
            }
        }
    }

    /**
     * 设置状态栏的显示样式
     */
    fun init() {
        //状态栏显示效果
        setTranslucentStatus(this, true)
        //去除标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        //沉浸式状态栏
        //StatusBarUtil.setTranslucent(this,150)


//        //去除状态栏
        setFullScreen(false)
    }

    fun setFullScreen(isFull: Boolean) {
        if (isFull) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

    }


    /**
     * 初始化布局文件
     */
    abstract fun initLayoutID(): Int

    /**
     * 加载数据
     */
    abstract fun initData()

    /**
     * 初始化布局
     */
    abstract fun initView()

    /**
     * 初始化事件
     */
    open fun initEvent() {


    }


}
