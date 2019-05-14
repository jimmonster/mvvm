package com.jim.common

import com.jim.common.ui.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_test1.*
import org.greenrobot.eventbus.EventBus

/**
 * @author :  Jim
 * @date :  2019/5/14
 * @description :
 *
 */
class TestActivity : BaseActivity() {
    var key: String? = ""
    lateinit var eventMessage: String

    override fun initLayoutID(): Int {
        return R.layout.activity_test1
    }

    override fun initData() {
        key = intent?.getStringExtra("key")
        if (key != null) {
            tv_test_info.text = key
        }
        val stickyEvent = EventBus.getDefault().getStickyEvent(MainActivity.MainEvent::class.java)
        if (stickyEvent != null) {
            eventMessage = stickyEvent.messages
            tv_test_info.text = eventMessage
        }


    }

    override fun initView() {


    }


}


