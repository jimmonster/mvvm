package com.jim.common.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import org.jetbrains.anko.AnkoLogger

/**
 * @author :  Jim
 * @date :  2018/12/7
 * @description :
 *
 */
abstract class  BaseFragment: Fragment(),AnkoLogger {
    /**
     * 切换刷新
     */
    protected var isCreated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 标记
        isCreated=true
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(initLayoutID(),container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initData()
        initEvent()

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (!isCreated) {
            return
        }

        if (isVisibleToUser) {
            initData()

        }

    }



    abstract fun initLayoutID(): Int
    abstract fun initData()
    abstract fun initView(view: View)

    /**
     * 初始化事件
     */
    open fun initEvent() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbindDrawables(view)
    }


    private fun unbindDrawables(view: View?) {
        if (view?.background != null) {
            view.background.callback = null
        }
        if (view is ViewGroup && view !is AdapterView<*>) {
            for (i in 0 until view.childCount) {
                unbindDrawables(view.getChildAt(i))
            }
            view.removeAllViews()
        }
    }


}
