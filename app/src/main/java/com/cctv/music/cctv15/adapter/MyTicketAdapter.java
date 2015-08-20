package com.cctv.music.cctv15.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cctv.music.cctv15.R;
import com.cctv.music.cctv15.model.ActivistTicket;

import java.util.List;

public class MyTicketAdapter extends BaseAdapter {

    private Context context;

    private List<ActivistTicket> list;

    public MyTicketAdapter(Context context, List<ActivistTicket> list) {
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
        ActivistTicket item = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_myticket, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(item.getIsover() == 0){
            holder.c1.setEnabled(false);
            holder.c2.setVisibility(View.GONE);
            holder.c3.setVisibility(View.VISIBLE);
            holder.date.setEnabled(false);
        }else{
            holder.c1.setEnabled(true);
            holder.c2.setVisibility(View.VISIBLE);
            holder.c3.setVisibility(View.GONE);
            holder.date.setEnabled(true);
        }

        holder.title.setText(item.getTitle());

        holder.date.setText(item.getDate());

        holder.name.setText(""+item.getChanged_name());

        holder.phone.setText(""+item.getChanged_code());

        return convertView;
    }

    private static class ViewHolder {
        private View c1;
        private View c2;
        private View c3;
        private TextView title;
        private TextView date;
        private TextView phone;
        private TextView name;

        public ViewHolder(View view) {
            c1 = view.findViewById(R.id.c1);
            c2 = view.findViewById(R.id.c2);
            c3 = view.findViewById(R.id.c3);
            title = (TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);
            phone = (TextView) view.findViewById(R.id.phone);
            name = (TextView) view.findViewById(R.id.name);
        }
    }

}
