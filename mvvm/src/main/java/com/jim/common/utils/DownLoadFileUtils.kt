package com.zefusoft.mobileoffice.utils

import android.content.Context
import android.os.Environment
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.FileCallback
import java.io.File

/**
 * @author :  Jim
 * @date :  2019/1/19
 * @description :
 */
object DownLoadFileUtils {
    private var mBasePath: String? = null //本地文件存储的完整路径  /storage/emulated/0/book/恰似寒光遇骄阳.txt

    //拼接一个本地的完整的url 供下载文件时传入一个本地的路径
    private val mSDPath = Environment.getExternalStorageDirectory().path
    //分类别路径
    private var mClassifyPath: String? = null

    /**
     *
     * @param context 上下文
     * @param fileUrl 下载完整url
     * @param destFileDir  SD路径
     * @param destFileName  文件名
     * @param mFileRelativeUrl 下载相对地址
     * （我们从服务器端获取到的数据都是相对的地址）例如： "filepath": "/movie/20180511/1526028508.txt"
     */
    fun downloadFile(
        context: Context,
        fileUrl: String,
        destFileDir: String,
        destFileName: String,
        mFileRelativeUrl: String
    ) {
        val mDestFileName =
            destFileName + mFileRelativeUrl.substring(mFileRelativeUrl.lastIndexOf("."), mFileRelativeUrl.length)
        OkGo.get<File>(fileUrl).tag(context)
            .execute(object : FileCallback(destFileDir, mDestFileName) { //文件下载时指定下载的路径以及下载的文件的名称

                override fun onSuccess(response: com.lzy.okgo.model.Response<File>) {

                    mBasePath = response.body().absolutePath
                }



            })
    }

    /**
     * 自定义本次存储路径
     */
    fun customLocalStoragePath(differentName: String): String {
        val basePath = File(mSDPath) // /storage/emulated/0
        mClassifyPath = "$mSDPath/$differentName/"  //如果传来的是 book 拼接就是 /storage/emulated/0/book/
        //如果传来的是game  那拼接就是 /storage/emulated/0/game/
        if (!basePath.exists()) {
            basePath.mkdirs()
        }

        return mClassifyPath as String
    }


    //截取一个文件加载显示时传入的一个本地完整路径
    fun subFileFullName(fileName: String, fileUrl: String): String {
        return fileName + fileUrl.substring(fileUrl.lastIndexOf("."), fileUrl.length)
    }
}

