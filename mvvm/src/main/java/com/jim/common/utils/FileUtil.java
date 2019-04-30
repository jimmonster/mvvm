package com.jim.common.utils;

import android.os.Environment;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class FileUtil {

    public static String formetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    public static void writeLog(String content) {
        System.out.println("+++++++++++++++++++");
        System.out.println(content);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            String file = Environment.getExternalStorageDirectory().getPath() + "/testLog.txt";
            FileWriter writer = new FileWriter(file, true);
            writer.write(sdf.format(System.currentTimeMillis())+"\n");
            writer.write(content+"\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
