package com.story.view.alert_view_ios;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.story.view.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by story on 2017/3/13 0013 下午 2:49.
 * 弹出框按钮列表适配器
 */
public class AlertViewAdapter extends BaseAdapter {
    private List<String> mDatas = new ArrayList<>();
    private List<String> mDestructive;
    public AlertViewAdapter(List<String> data,List<String> destructive){
        if (data != null) {
            this.mDatas.addAll(data);
        }
        this.mDestructive =destructive;
    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.alert_view_ios_button, null);
            holder = createHolder(convertView);
            convertView.setTag(holder);
        }
        else{
            holder = (Holder) convertView.getTag();
        }
        String data = mDatas.get(position);
        holder.UpdateUI(parent.getContext(), data);
        return convertView;
    }

    private Holder createHolder(View view){
        return new Holder(view);
    }

    private class Holder {
        private TextView tvAlert;

        Holder(View view){
            tvAlert = (TextView) view.findViewById(R.id.tv_button);
        }

        void UpdateUI(Context context, String data){
            tvAlert.setText(data);
            if (mDestructive!= null && mDestructive.contains(data)){
                tvAlert.setTextColor(ContextCompat.getColor(context, R.color.alert_view_ios_txt_destructive));
            }
            else{
                tvAlert.setTextColor(ContextCompat.getColor(context, R.color.alert_view_ios_txt_others));
            }
        }
    }
}
