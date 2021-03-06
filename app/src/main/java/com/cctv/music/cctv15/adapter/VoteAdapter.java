package com.cctv.music.cctv15.adapter;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.music.cctv15.R;
import com.cctv.music.cctv15.model.Vote;
import com.cctv.music.cctv15.utils.DateUtils;
import com.cctv.music.cctv15.utils.DisplayOptions;
import com.cctv.music.cctv15.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class VoteAdapter extends BaseAdapter {

    private Context context;

    private List<Vote> list;

    private int width;

    private int height;

    public VoteAdapter(Context context, List<Vote> list) {
        this.context = context;
        this.list = list;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        width = display.getWidth();
        height = Utils.dpToPx(context,202);
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
        Vote vote = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_vote, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(vote.isEnd()){
            holder.deadline.setVisibility(View.VISIBLE);
            holder.date.setVisibility(View.GONE);
        }else{
            holder.deadline.setVisibility(View.GONE);
            holder.date.setVisibility(View.VISIBLE);
        }
        holder.title.setText(""+vote.getVotetitle());
        holder.date.setText(""+DateUtils.format(vote.getDatetime())+"-"+DateUtils.format(vote.getEndtime()));
        holder.count.setText(""+vote.getVoteusercount());
        ImageLoader.getInstance().displayImage(vote.getAttachment().getAttachmentimgurl(width,height), holder.img, DisplayOptions.IMG.getOptions());
        return convertView;
    }

    private static class ViewHolder {
        private ImageView img;
        private TextView count;
        private TextView date;
        private TextView title;
        private View deadline;
        public ViewHolder(View view) {
            deadline = view.findViewById(R.id.deadline);
            img = (ImageView) view.findViewById(R.id.img);
            count = (TextView) view.findViewById(R.id.count);
            date = (TextView) view.findViewById(R.id.date);
            title = (TextView) view.findViewById(R.id.title);
        }
    }

}
