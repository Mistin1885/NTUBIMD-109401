package com.asus.zenbodialogsample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class GridAdapter extends ArrayAdapter<String> {
    private int resourceId;

    public GridAdapter(Context context, int resource, List<String> data) {
        super(context, resource, data);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String item = getItem(position);
        View view;
        ViewHolder holder = new ViewHolder();  // viewHolder 是提升 ListView 运行效率
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            holder.title = view.findViewById(R.id.list_item_title);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.title.setText(item);
        return view;

    }

    class ViewHolder {
        TextView title;
    }

}
