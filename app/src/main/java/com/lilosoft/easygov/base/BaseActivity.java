package com.lilosoft.easygov.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.lilosoft.easygov.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * @author qw
 * @name BaseActivity
 * @description 基类
 * @date: 2015年12月8日
 */
public abstract class BaseActivity extends FragmentActivity {
    public MyApplication appContext;
    protected SweetAlertDialog sd;
    protected SweetAlertDialog pDialog;
    protected FragmentManager mFragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initProgress();
        AppManager.getAppManager().addActivity(this);
        mFragmentManager = getSupportFragmentManager();
        appContext = (MyApplication) getApplication();
    }


    /**
     * 显示进度条
     */
    public void showProgress() {
        if (pDialog == null)
            return;
        pDialog.show();
    }

    /**
     * 隐藏进度条
     */
    public void dismissProgress() {
        if (pDialog == null)
            return;
        pDialog.dismiss();
    }


    public void showAlert(String alert) {
        SweetAlertDialog sd = new SweetAlertDialog(this);
        sd.setCancelable(true);
        sd.setContentText(alert);
        sd.setTitleText(getResources().getString(R.string.alert_title));
        sd.setConfirmText(getResources().getString(R.string.ok));
        sd.setCanceledOnTouchOutside(false);
        sd.show();
    }

    public void showAlert(String alert, SweetAlertDialog.OnSweetClickListener confirmclick) {
        SweetAlertDialog sd = new SweetAlertDialog(this);
        sd.setCancelable(true);
        sd.setContentText(alert);
        sd.setTitleText(getResources().getString(R.string.alert_title));
        sd.setConfirmClickListener(confirmclick);
        sd.setConfirmText(getResources().getString(R.string.ok));
        sd.setCanceledOnTouchOutside(false);
        sd.show();
    }

    public void showAlert(SweetAlertDialog.OnSweetClickListener confirmclick1, SweetAlertDialog.OnSweetClickListener confirmclick2) {
        SweetAlertDialog sd = new SweetAlertDialog(this);
        sd.setCancelable(true);
        sd.setContentText("是否从相册获取");
        sd.setTitleText(getResources().getString(R.string.alert_title));
        sd.setConfirmClickListener(confirmclick1);
        sd.setConfirmText("是");
        sd.setCancelClickListener(confirmclick2);
        sd.setCancelText("否");
        //sd.setConfirmText(getResources().getString(R.string.ok));
        sd.setCanceledOnTouchOutside(false);
        sd.show();
    }

    public void showAlertTrueorFalse(SweetAlertDialog.OnSweetClickListener confirmclick1, SweetAlertDialog.OnSweetClickListener confirmclick2) {
        SweetAlertDialog sd = new SweetAlertDialog(this);
        sd.setCancelable(true);
        sd.setContentText("是否继续上传");
        sd.setTitleText(getResources().getString(R.string.alert_title));
        sd.setConfirmClickListener(confirmclick1);
        sd.setConfirmText("是");
        sd.setCancelClickListener(confirmclick2);
        sd.setCancelText("否");
        //sd.setConfirmText(getResources().getString(R.string.ok));
        sd.setCanceledOnTouchOutside(false);
        sd.show();
    }

    public void showChoiceAlert(String alert, SweetAlertDialog.OnSweetClickListener confirmclick) {
        final SweetAlertDialog sd = new SweetAlertDialog(this);
        sd.setCancelable(true);
        sd.setContentText(alert);
        sd.setTitleText(getResources().getString(R.string.alert_title));
        sd.setConfirmText(getResources().getString(R.string.ok));
        sd.setCancelText(getResources().getString(R.string.cancle));
        sd.setConfirmClickListener(confirmclick);
        sd.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {

            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sd.cancel();
            }

        });
        sd.setCanceledOnTouchOutside(false);
        sd.show();
    }


    public void closeChoiceAlert() {
        if (sd != null) {
            sd.dismiss();
        }
    }

    public void openActivity(Class<?> toActivity) {
        // 创建一个Intent对象
        Intent intent = new Intent(this, toActivity);
        // 定义Bundle,用于携带数据
        Bundle arguments = new Bundle();
        // 添加数据对象

        intent.putExtras(arguments);

        startActivity(intent);

    }

    /**
     * progress进度条
     */
    private void initProgress() {
        // 进度条
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText(getResources().getString(R.string.pleasewait));
        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blue_btn_bg_color));
        pDialog.setCancelable(false);
    }

    @Override
    public void finish() {
        super.finish();
        // 动画效果
        overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    /**
     * 通过序列化(Serilization)，对给定的对象进行深层复制
     *
     * @param obj 被复制的对象
     * @return 新的对象实例
     */
    protected Object cloneObject(Object obj) {
        Object duplicate = null;

        ByteArrayOutputStream bo = null;
        ObjectOutputStream oo = null;
        ByteArrayInputStream bi = null;
        ObjectInputStream oi = null;
        try {
            // 将对象写到流里
            bo = new ByteArrayOutputStream();
            oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);

            // 从流中读出对象
            bi = new ByteArrayInputStream(bo.toByteArray());
            oi = new ObjectInputStream(bi);
            // 通过读取对象，得到一个新的对象
            duplicate = oi.readObject();
        } catch (IOException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        } finally {
            // 关闭所有输入输出流
            try {
                if (oo != null) {
                    oo.close();
                }
                if (bo != null) {
                    bo.close();
                }
                if (bi != null) {
                    bi.close();
                }
                if (oi != null) {
                    oi.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return duplicate;
    }
}
