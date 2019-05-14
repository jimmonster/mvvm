package com.jim.common

import com.blankj.utilcode.util.SPUtils
import com.jim.common.ui.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity() {
    override fun initLayoutID(): Int {
        return R.layout.activity_main
    }

    override fun initData() {

    }

    override fun initView() {
        tv_main_web.onClick {
            href("http://www.baidu.com")
        }

        tv_main_ui.onClick {
            EventBus.getDefault().removeAllStickyEvents()
            startActivity<TestActivity>("key" to "hello test page")
        }

        tv_main_sp.onClick {
            SPUtils.getInstance().put("SPTEST", "hello SP")
            tv_main_sp.text = SPUtils.getInstance().getString("SPTEST")
        }

        tv_main_clear.onClick {
            SPUtils.getInstance().clear()
        }
        tv_main_event.onClick {
            EventBus.getDefault().postSticky(MainEvent("hello eventBus!"))
            startActivity<TestActivity>()

        }


    }

    class MainEvent(var messages: String)


}
