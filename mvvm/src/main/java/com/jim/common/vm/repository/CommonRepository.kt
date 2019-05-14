package com.jim.common.vm.repository

import ApiManager.post
import android.arch.lifecycle.MutableLiveData
import com.jim.common.model.bean.BaseBean
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response

/**
 * @author :  Jim
 * @date :  2019/4/30
 * @description :数据处理仓库
 *
 */
class CommonRepository : BaseRepository(){
    var getResponse = MutableLiveData<BaseBean>()
    fun getResponse(): MutableLiveData<BaseBean> {
        return getResponse
    }

    fun requestResponse(
        url: String,
        params: LinkedHashMap<String, String>
    ) {
        post(url, params, object : StringCallback() {
            override fun onSuccess(response: Response<String>?) {
                if (response != null) {
//                    val baseBean = GsonUtil.GsonToBean(response.body(), BaseBean::class.javaObjectType)
//                    getResponse.postValue(baseBean)
                }

            }
        })
    }

}
