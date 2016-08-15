package com.lilosoft.easygov.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lilosoft.easygov.R;
import com.lilosoft.easygov.base.BaseActivity;

/**
 * Created by chablis on 2016/8/15.
 */
public class GridAdapter extends BaseAdapter {
    private LayoutInflater mInflater = null;
    private String data[];

    public GridAdapter(BaseActivity context,String data[]) {
        mInflater=LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int i) {
        return data[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder=null;
        if(view==null){
            holder = new Holder();
            view = mInflater.inflate(R.layout.item_grid, viewGroup,
                    false);
            holder.tv1= (TextView) view.findViewById(R.id.tv1);
            view.setTag(holder);
        }else{
            holder= (Holder) view.getTag();
        }
        holder.tv1.setText(data[i]);
        return view;
    }


    static class Holder {
        private TextView tv1;
    }
}
