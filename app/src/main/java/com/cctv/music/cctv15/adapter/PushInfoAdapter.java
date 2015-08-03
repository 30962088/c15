package com.cctv.music.cctv15.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cctv.music.cctv15.R;
import com.cctv.music.cctv15.db.InfoTable;
import com.cctv.music.cctv15.model.PushInfo;

import java.util.List;

public class PushInfoAdapter extends BaseAdapter {

    private Context context;

    private List<PushInfo> list;

    public PushInfoAdapter(Context context, List<PushInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        PushInfo info = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_info, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (list.size() == 1) {
            holder.sep.setVisibility(View.GONE);
            holder.container.setBackgroundResource(R.drawable.i_t);
        } else {
            if (position == 0) {
                holder.sep.setVisibility(View.VISIBLE);
                holder.container.setBackgroundResource(R.drawable.i_t_1);
            } else if (position > 0 && position < list.size() - 1) {
                holder.sep.setVisibility(View.VISIBLE);
                holder.container.setBackgroundResource(R.drawable.i_t_2);
            } else {
                holder.sep.setVisibility(View.GONE);
                holder.container.setBackgroundResource(R.drawable.i_t_3);
            }
        }

        holder.name.setSelected(!InfoTable.isRead(context,info.getPid()));

        holder.date.setText(info.getDate());

        holder.name.setText(info.getTitle());

        return convertView;
    }

    private static class ViewHolder {

        private TextView name;
        private TextView date;
        private View sep;
        private View container;

        public ViewHolder(View view) {
            container = view.findViewById(R.id.container);
            name = (TextView) view.findViewById(R.id.name);
            date = (TextView) view.findViewById(R.id.date);
            sep = view.findViewById(R.id.sep);
        }
    }

}
