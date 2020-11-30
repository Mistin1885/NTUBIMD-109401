package com.asus.zenbodialogsample;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SleepItemAdapter extends ArrayAdapter<Item> {
    private int resourceId;

    public SleepItemAdapter(Context context, int resource, List<Item> data) {
        super(context, resource, data);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Item item = getItem(position);
        View view;
        ViewHolder holder = new ViewHolder();  // viewHolder 是提升 ListView 运行效率
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            holder.title = view.findViewById(R.id.sleep_list_item_title);
            holder.date = view.findViewById(R.id.sleep_list_item_time);
            // holder.body = view.findViewById(R.id.list_item_body);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.title.setText("睡了：" + item.getTitle() + "秒");
        holder.date.setText("時間：" + item.getDate());
        // holder.body.setText("内容：" + item.getBody());

        return view;

    }

    class ViewHolder {
        TextView title;
        TextView date;
        TextView body;
    }

}
