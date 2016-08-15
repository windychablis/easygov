package com.lilosoft.easygov.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lilosoft.easygov.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BaseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaseFragment extends Fragment {
    // 上下文
    protected static BaseActivity mcontext;
    // 警告弹窗
    protected SweetAlertDialog pDialog;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    protected View mView;

    public OnFragmentInteractionListener mListener;

    public BaseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BaseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BaseFragment newInstance(String param1, String param2) {
        BaseFragment fragment = new BaseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mcontext = (BaseActivity) getActivity();
        // 初始化进度条
        initProgress();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /**
     * 警告弹出框
     *
     * @param alert 警告内容
     */
    public void showAlert(String alert) {
        SweetAlertDialog sd = new SweetAlertDialog(mcontext);
        sd.setCancelable(true);
        sd.setContentText(alert);
        sd.setTitleText(getResources().getString(R.string.alert_title));
        sd.setConfirmText(getResources().getString(R.string.ok));
        sd.setCanceledOnTouchOutside(false);
        sd.show();
    }

    public void showChoiceAlert(String alert, SweetAlertDialog.OnSweetClickListener confirmclick) {
        SweetAlertDialog sd = new SweetAlertDialog(getActivity());
        sd.setCancelable(true);
        sd.setContentText(alert);
        sd.setTitleText(getResources().getString(R.string.alert_title));
        sd.setConfirmText(getResources().getString(R.string.ok));
        sd.setCancelText(getResources().getString(R.string.cancle));
        sd.setConfirmClickListener(confirmclick);
        sd.setCanceledOnTouchOutside(false);
        sd.show();
    }


    /**
     * 进度条展示
     */
    public void showProgress() {
        if (pDialog == null)
            return;
        pDialog.show();
    }

    /**
     * 进度条隐藏
     */
    public void dismissProgress() {
        if (pDialog == null)
            return;
        pDialog.dismiss();
    }

    /**
     * 跳转activity方法
     *
     * @param toActivity 要跳转到的activity
     */
    public void openActivity(Class<?> toActivity) {
        // 创建一个Intent对象
        Intent intent = new Intent(mcontext, toActivity);
        // 定义Bundle,用于携带数据
        Bundle arguments = new Bundle();
        // 添加数据对象

        intent.putExtras(arguments);

        startActivity(intent);

    }

    /**
     * progress进度条初始化
     */
    private void initProgress() {
        // 进度条
        pDialog = new SweetAlertDialog(mcontext, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText(getResources().getString(R.string.pleasewait));
        pDialog.getProgressHelper().setBarColor(
                getResources().getColor(R.color.blue_btn_bg_color));
        pDialog.setCancelable(false);
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
