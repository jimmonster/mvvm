package com.jim.common

import com.jim.common.ui.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

class MainActivity : BaseActivity() {
    override fun initLayoutID(): Int {
        return R.layout.activity_main
    }

    override fun initData() {

    }

    override fun initView() {

        tv_onclick.onClick {
            href("http://www.baidu.com")
            toast("点击了")
        }

    }


}
