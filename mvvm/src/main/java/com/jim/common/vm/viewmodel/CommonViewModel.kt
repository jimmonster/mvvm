package com.jim.common.vm.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.jim.common.model.bean.BaseBean
import com.jim.common.vm.repository.CommonRepository

/**
 * @author :  Jim
 * @date :  2019/4/30
 * @description :数据处理共同类,可以直接在这一个类中请求网络数据，但是放在CommonRepository中做网络请求效果更好，CommonViewModel专注于数据的处理
 *
 */
class CommonViewModel : BaseViewModel() {
    var rePo = CommonRepository()


    //获取版本信息
    fun getResponse(): MutableLiveData<BaseBean> {
        //这里拿到数据后可以做处理
        val response = rePo.getResponse()
        return response
    }
}