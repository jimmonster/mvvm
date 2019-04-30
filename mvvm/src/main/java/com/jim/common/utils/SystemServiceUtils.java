package com.jim.common.utils;

import android.annotation.SuppressLint;
import android.app.*;
import android.bluetooth.BluetoothManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.hardware.SensorManager;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.nfc.NfcManager;
import android.os.Build;
import android.os.PowerManager;
import android.os.Vibrator;
import android.os.storage.StorageManager;
import android.print.PrintManager;
import android.service.wallpaper.WallpaperService;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * @author :  Jim
 * @date :  2018/12/21
 * @description :
 */
public class SystemServiceUtils {
    public SystemServiceUtils() {
    }

    @SuppressLint("WrongConstant")
    public static ActivityManager getActivityManager(Context var0) {
        return (ActivityManager)var0.getSystemService("activity");
    }

    @SuppressLint("WrongConstant")
    public static AlarmManager getAlarmManager(Context var0) {
        return (AlarmManager)var0.getSystemService("alarm");
    }

    @SuppressLint("WrongConstant")
    public static AudioManager getAudioManager(Context var0) {
        return (AudioManager)var0.getSystemService("audio");
    }

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static BluetoothManager getBluetoothManager(Context var0) {
        return (BluetoothManager)var0.getSystemService("bluetooth");
    }

    @SuppressLint("WrongConstant")
    public static ClipboardManager getClipboardManager(Context var0) {
        return (ClipboardManager)var0.getSystemService("clipboard");
    }

    @SuppressLint("WrongConstant")
    public static ConnectivityManager getConnectivityManager(Context var0) {
        return (ConnectivityManager)var0.getSystemService("connectivity");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("WrongConstant")
    public static ConsumerIrManager getConsumerIrManager(Context var0) {
        return (ConsumerIrManager)var0.getSystemService("consumer_ir");
    }

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static DisplayManager getDisplayManager(Context var0) {
        return (DisplayManager)var0.getSystemService("display");
    }

    @SuppressLint("WrongConstant")
    public static DownloadManager getDownloadManager(Context var0) {
        return (DownloadManager)var0.getSystemService("download");
    }

    @SuppressLint("WrongConstant")
    public static InputMethodManager getInputMethodManager(Context var0) {
        return (InputMethodManager)var0.getSystemService("input_method");
    }

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static InputManager getInputManager(Context var0) {
        return (InputManager)var0.getSystemService("input");
    }

    @SuppressLint("WrongConstant")
    public static LocationManager getLocationManager(Context var0) {
        return (LocationManager)var0.getSystemService("location");
    }

    @SuppressLint("WrongConstant")
    public static LayoutInflater getLayoutInflater(Context var0) {
        return (LayoutInflater)var0.getSystemService("layout_inflater");
    }

    @SuppressLint("WrongConstant")
    public static NfcManager getNfcManager(Context var0) {
        return (NfcManager)var0.getSystemService("nfc");
    }

    @SuppressLint("WrongConstant")
    public static NotificationManager getNotificationManager(Context var0) {
        return (NotificationManager)var0.getSystemService("notification");
    }

    @SuppressLint("WrongConstant")
    public static PowerManager getPowerManager(Context var0) {
        return (PowerManager)var0.getSystemService("power");
    }

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static PrintManager getPrintManager(Context var0) {
        return (PrintManager)var0.getSystemService("print");
    }

    @SuppressLint("WrongConstant")
    public static SearchManager getSearchManager(Context var0) {
        return (SearchManager)var0.getSystemService("search");
    }

    @SuppressLint("WrongConstant")
    public static SensorManager getSensorManager(Context var0) {
        return (SensorManager)var0.getSystemService("sensor");
    }

    @SuppressLint("WrongConstant")
    public static StorageManager getStorageManager(Context var0) {
        return (StorageManager)var0.getSystemService("storage");
    }

    @SuppressLint("WrongConstant")
    public static TelephonyManager getTelephonyManager(Context var0) {
        return (TelephonyManager)var0.getSystemService("phone");
    }

    @SuppressLint("WrongConstant")
    public static Vibrator getVibrator(Context var0) {
        return (Vibrator)var0.getSystemService("vibrator");
    }

    @SuppressLint({"ServiceCast", "WrongConstant"})
    public static WallpaperService getWallpaperService(Context var0) {
        return (WallpaperService)var0.getSystemService("wallpaper");
    }

    @SuppressLint("WrongConstant")
    public static WifiP2pManager getWifiP2pManager(Context var0) {
        return (WifiP2pManager)var0.getSystemService("wifip2p");
    }

    @SuppressLint("WrongConstant")
    public static WifiManager getWifiManager(Context var0) {
        return (WifiManager)var0.getSystemService("wifi");
    }

    @SuppressLint("WrongConstant")
    public static WindowManager getWindowManager(Context var0) {
        return (WindowManager)var0.getSystemService("window");
    }
}

