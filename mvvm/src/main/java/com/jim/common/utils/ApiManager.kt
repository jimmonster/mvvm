
import com.jim.common.utils.OkGoUtils
import com.lzy.okgo.callback.FileCallback
import com.lzy.okgo.callback.StringCallback
import java.util.*

/**
 * @author :  Jim
 * @date :  2018/10/30
 * @description :
 *  网络请求的单例
 */

object ApiManager {
    //GET方法，不需要TAG
     fun get(url: String, paramMap: Map<String, String>, callback: StringCallback) {
        OkGoUtils.get(url,paramMap,callback)

    }

    //GET方法，需要传递TAG
     fun get(url: String, paramMap: Map<String, String>, tag: Any, callback: StringCallback) {
        OkGoUtils.get(url,paramMap,tag,callback)

    }

    //GET方法
     fun get(url: String, callback: StringCallback) {
        OkGoUtils.get(url,callback)

    }

    //post，需要传递TAG
    fun post(url: String, paramMap: Map<String, String>, tag: Any, callback: StringCallback) {
        OkGoUtils.post(url,paramMap,tag,callback)

    }

    //post，不需要传递TAG
    fun post(url: String, paramMap: HashMap<String, String>, callback: StringCallback) {

        OkGoUtils.post(url,paramMap,callback)
    }

    //post,不需要传参
    fun post(url: String, callback: StringCallback) {
        OkGoUtils.post(url,callback)

    }


    //post,需要传递表头(token)
    fun post(url: String, paramMap: Map<String, String>, token: String, callback: StringCallback) {
        OkGoUtils.post(url,paramMap,token,callback)

    }

    //post,需要传递表头(token)以及对象
    fun post(url: String, json: String, token: String, callback: StringCallback) {
        OkGoUtils.post(url,json,token,callback)

    }

    //post，不需要传递TAG
    fun download(url: String, paramMap: HashMap<String, String>, callback: FileCallback) {
        OkGoUtils.download(url,paramMap,callback)
    }

}
