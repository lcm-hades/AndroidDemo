package com.hades.Sample;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Hades on 2015/12/23.
 */
public class AliOrderAdt extends BaseAdapter {
    private String[] adapterData =new String[] { "Activity","Service","Content Provider","Intent","BroadcastReceiver","ADT","Sqlite3","HttpClient",
            "DDMS","Android Studio","Fragment","Loader", "BroadcastReceiver","ADT","Sqlite3","HttpClient","DDMS","AndroidStudio","Fragment","Loader" };

    private Context mContext;
    public AliOrderAdt(Context context){
        mContext = context;
    }

    @Override
    public int getCount() {
        return adapterData.length + 1;
    }

    @Override
    public Object getItem(int position) {
        return adapterData[position - 1];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (position == 0){
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(mContext, R.layout.item_image, null);
                viewHolder.icon = (ImageView)convertView.findViewById(R.id.icon);

                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder)convertView.getTag();
            }
        }else {
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(mContext, R.layout.item_text, null);
                viewHolder.text = (TextView)convertView.findViewById(R.id.text);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder)convertView.getTag();
            }

            viewHolder.text.setText(adapterData[position - 1]);
        }

        return convertView;
    }
    private class ViewHolder {
        public ImageView icon;

        public TextView text;

    }
}
