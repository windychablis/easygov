package com.lilosoft.easygov;

import android.net.Uri;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lilosoft.easygov.base.BaseActivity;
import com.lilosoft.easygov.base.BaseFragment;
import com.lilosoft.easygov.fragment.ConsultationFragment;
import com.lilosoft.easygov.fragment.IndexFragment;
import com.lilosoft.easygov.fragment.IndicatorFragment;
import com.lilosoft.easygov.fragment.PersonalCenterFragment;

public class MainActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener {
    private TextView tv_title;
    private RadioGroup rg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
        IndexFragment fragment = IndexFragment.newInstance(null,null);
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment).commit();
    }

    public void initView() {
        tv_title = (TextView) findViewById(R.id.title);
        tv_title.setText("首页");
        rg = (RadioGroup) findViewById(R.id.tabIndicators);
    }

    public void setListener() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tabIndexButton:
                        tv_title.setText("首页");
                        mFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, IndexFragment.newInstance(null,null))
                                .commitAllowingStateLoss();
                        break;
                    case R.id.tabIndicatorButton:
                        tv_title.setText("办事大厅");
                        mFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, IndicatorFragment.newInstance(null,null))
                                .commitAllowingStateLoss();
                        break;
                    case R.id.tabConsultationButton:
                        tv_title.setText("咨询");
                        mFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, ConsultationFragment.newInstance(null,null))
                                .commitAllowingStateLoss();
                        break;
                    case R.id.UserButton:
                        tv_title.setText("个人中心");
                        mFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, PersonalCenterFragment.newInstance(null,null))
                                .commitAllowingStateLoss();
                        break;
                }
            }
        });
    }

//    private void getDepTask() {
//        new AsyncTask<JSONArray, Integer, JSONArray>() {
//            @Override
//            protected JSONArray doInBackground(JSONArray... params) {
//                JSONArray arry=null;
//                try {
//                    arry = getDep();
//                } catch (IOException | XmlPullParserException | JSONException e) {
//                    e.printStackTrace();
//                }
//                return arry;
//            }
//
//            @Override
//            protected void onPostExecute(JSONArray s) {
//                super.onPostExecute(s);
//            }
//        }.execute();
//    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
