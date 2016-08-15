package com.lilosoft.easygov.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.TypedValue;
import android.widget.ImageView;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 客户端常用方法类
 *
 * @author ray
 * @date 2015-03-15
 */
public class Util {
    public static final String TAG = "Util";

    public static int dpToPx(Resources res, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                res.getDisplayMetrics());
    }

    public static boolean detect(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == connManager) {
            return false;
        }

        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (null == networkInfo || !networkInfo.isAvailable()) {
            return false;
        }
        return true;
    }

    public static String md5(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] byteArray = md.digest(text.getBytes());
            StringBuffer md5StrBuff = new StringBuffer();
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                    md5StrBuff.append("0").append(
                            Integer.toHexString(0xFF & byteArray[i]));
                else
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
            return md5StrBuff.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";

    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * @param context
     * @return
     */
    public static String getVersion(Context context) {
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi.versionName;
    }


    public static void DeleteFile(File f) {
        if (f == null) {
            return;
        }
        boolean directory = f.isDirectory();
        if (directory) {
            File[] files = f.listFiles();
            for (File child : files) {
                DeleteFile(child);
            }
        }
        f.delete();
    }

    /**
     * 判断当前最上层activity
     *
     * @return
     */
    public static boolean isTopActivity(Context context, String activityName) {
        boolean isTop = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        if (cn.getClassName().contains(activityName)) {
            isTop = true;
        }
        return isTop;
    }

    /**
     * 根据时间类型比较时间大小
     *
     * @param source
     * @param traget
     * @param type   "YYYY-MM-DD" "yyyyMMdd HH:mm:ss"  类型可自定义
     * @return 0 ：source和traget时间相同    两个时间相等
     * 1 ：source比traget时间大        source时间晚于traget时间
     * -1：source比traget时间小        source时间早于traget时间
     * @throws Exception
     */
    public static int DateCompare(String source, String traget, String type) throws Exception {
        int ret = 2;
        SimpleDateFormat format = new SimpleDateFormat(type);
        Date sourcedate = format.parse(source);
        Date tragetdate = format.parse(traget);
        ret = sourcedate.compareTo(tragetdate);
        return ret;
    }

    /**
     * 获取系统时间方法
     */
    public static final String TIME_TYPE_YMD = "yyyyMMdd";    // 年月日
    public static final String TIME_TYPE_YMDHMS = "yyyyMMdd HH:mm:ss";    // 年月日时分秒
    public static final String TIME_TYPE_Y_M_D = "yyyy-MM-dd";    // 年-月-日
    public static final String TIME_TYPE_H_M = "HH:mm";    // 时分
    public static final String TIME_TYPE_H_M_S = "HH:mm:ss";    // 时分秒

    public static String getSystemTime(String timeType) {
        SimpleDateFormat sdf = new SimpleDateFormat(timeType);
        Calendar cal = Calendar.getInstance();
        return sdf.format(cal.getTime());
    }

    /**
     * 修改时间格式
     *
     * @param time
     * @param timeType
     * @return
     */
    public static String changeTimeFormat(String time, String localTimeType, String targetTimeType) {
        SimpleDateFormat localSdf = new SimpleDateFormat(localTimeType);
        Date d = null;
        try {
            d = localSdf.parse(time);    // 将给定的字符串中的日期提取出来
        } catch (Exception e) {    // 如果提供的字符串格式有错误，则进行异常处理
            e.printStackTrace();    // 打印异常信息
        }
        SimpleDateFormat targetSdf = new SimpleDateFormat(targetTimeType);
        return targetSdf.format(d);
    }

    /**
     * 点击变色效果
     *
     * @param imageview
     * @param brightness
     */
    public static void changeLight(ImageView imageview, int brightness) {
        ColorMatrix matrix = new ColorMatrix();
        matrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1, 0, 0,
                brightness, 0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});
        imageview.setColorFilter(new ColorMatrixColorFilter(matrix));
    }

}
