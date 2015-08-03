package com.cctv.music.cctv15.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.music.cctv15.R;
import com.cctv.music.cctv15.model.Content;
import com.cctv.music.cctv15.model.TicketItem;
import com.cctv.music.cctv15.utils.DateUtils;
import com.cctv.music.cctv15.utils.DisplayOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class TicketAdapter extends BaseAdapter {

    private Context context;

    private List<TicketItem> list;

    public TicketAdapter(Context context, List<TicketItem> list) {
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
        TicketItem item = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_ticket, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(""+item.getTitle());
        holder.date.setText(""+item.getDate());
        holder.score.setText(""+item.getConvert_score());
        ImageLoader.getInstance().displayImage(item.getImgurl(), holder.img, DisplayOptions.IMG.getOptions());
        return convertView;
    }

    private static class ViewHolder {
        private ImageView img;
        private TextView score;
        private TextView date;
        private TextView title;

        public ViewHolder(View view) {
            img = (ImageView) view.findViewById(R.id.img);
            score = (TextView) view.findViewById(R.id.score);
            date = (TextView) view.findViewById(R.id.date);
            title = (TextView) view.findViewById(R.id.title);
        }
    }

}
