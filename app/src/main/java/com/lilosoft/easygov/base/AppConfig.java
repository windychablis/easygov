package com.lilosoft.easygov.base;

/**
 * Created by chablis on 2016/8/10.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.lilosoft.easygov.utils.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * 应用程序配置类：用于保存用户相关信息及设置
 * @author ray
 * @date 2015-03-13
 */
public class AppConfig {
    private static AppConfig appConfig;
    private Context mContext;
    public final static String CONF_APP_UNIQUEID = "APP_UNIQUEID";
    private final static String APP_CONFIG = "config";
    public static String HEAD = "";
    public static String ICON_CACHE_DIR;
    public static String CACHE_UPLOAD_IMAGES_DIR;
    public static String DOWNLOAD_DIR = HEAD;
    public static String VOICE_DIR;
    /**
     * 缓存相关保存文件的名称
     */
    public static final String APP_CACHE_DIR_NAME = "/easygov";
    /**
     * 错误日志保存路径
     */
    public static final String DIR_LOG = APP_CACHE_DIR_NAME + "/Log";

    public static AppConfig getAppConfig(Context context) {
        if (appConfig == null) {
            appConfig = new AppConfig();
            appConfig.mContext = context;
            init(context);
        }
        return appConfig;
    }

    //本方法只在程序第一次启动时执行
    private static void init(Context context) {
        SharedPreferences sp = context.getSharedPreferences(APP_CONFIG, Context.MODE_PRIVATE);
        int code = sp.getInt("is_first_load", 0);
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            HEAD = Environment.getExternalStorageDirectory().getAbsolutePath() ;
        }
        else {
            HEAD = context.getCacheDir().getAbsolutePath();
        }
        ICON_CACHE_DIR = HEAD+ "/easygov/icon_images";
        CACHE_UPLOAD_IMAGES_DIR = HEAD + "/easygov/cache_upload_images";
        DOWNLOAD_DIR = HEAD + "/easygov/downloads";
        VOICE_DIR = HEAD + "/easygov/voices";
        if (code == 0)
        {
            doClearCache(sp);
        }
        File cache_upload_images = new File(CACHE_UPLOAD_IMAGES_DIR);
        if (!cache_upload_images.exists()) {
            cache_upload_images.mkdirs();
        }
        File download_dir = new File(DOWNLOAD_DIR);
        if (!download_dir.exists()) {
            download_dir.mkdirs();
        }
        File icon_cache_dir = new File(ICON_CACHE_DIR);
        if (!icon_cache_dir.exists()) {
            icon_cache_dir.mkdirs();
        }
        File voice_dir = new File(VOICE_DIR);
        if (!voice_dir.exists()) {
            voice_dir.mkdirs();
        }
    }
    private static void doClearCache(SharedPreferences sp)
    {
        File file = new File(APP_CACHE_DIR_NAME);
        Util.DeleteFile(file);
        sp.edit().putInt("is_first_load", 1).commit();
    }
    public static void doClearCache()
    {
        File file = new File(getSdCard()+"/easygov");
        Util.DeleteFile(file);
    }
    public static String getSdCard(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if   (sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir==null?null:sdDir.toString();

    }
    public Properties get() {
        FileInputStream fis = null;
        Properties props = new Properties();
        try {
            File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
            fis = new FileInputStream(dirConf.getPath() + File.separator
                    + APP_CONFIG);
            props.load(fis);
        } catch (Exception e) {
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return props;
    }

    public String get(String key) {
        Properties props = get();
        return (props != null) ? props.getProperty(key) : null;
    }

    public void set(String key, String value) {
        Properties props = get();
        props.setProperty(key, value);
        setProps(props);
    }

    private void setProps(Properties p) {
        FileOutputStream fos = null;
        try {
            // 把config建在files目录下
            // fos = activity.openFileOutput(APP_CONFIG, Context.MODE_PRIVATE);

            // 把config建在(自定义)app_config的目录下
            File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
            File conf = new File(dirConf, APP_CONFIG);
            fos = new FileOutputStream(conf);

            p.store(fos, null);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
            }
        }
    }

}
