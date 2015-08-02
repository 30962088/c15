package com.cctv.music.cctv15.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.music.cctv15.R;
import com.cctv.music.cctv15.model.RankItem;
import com.cctv.music.cctv15.utils.DisplayOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class RankAdapter extends BaseAdapter {

    private Context context;

    private List<RankItem> list;

    public RankAdapter(Context context, List<RankItem> list) {
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
        RankItem item = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_rank, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.score.setText(""+item.getTotal_score());
        holder.name.setText(""+item.getUsername());
        holder.rank.setText(""+(position+1));
        ImageLoader.getInstance().displayImage(item.getLoginuserimgurl(), holder.avatar, DisplayOptions.IMG.getOptions());
        return convertView;
    }

    private static class ViewHolder {
        private ImageView avatar;
        private TextView name;
        private TextView score;
        private TextView rank;

        public ViewHolder(View view) {
            avatar = (ImageView) view.findViewById(R.id.avatar);
            name = (TextView) view.findViewById(R.id.name);
            score = (TextView) view.findViewById(R.id.score);
            rank = (TextView) view.findViewById(R.id.rank);

        }
    }

}
