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
import com.cctv.music.cctv15.model.Content;
import com.cctv.music.cctv15.utils.DateUtils;
import com.cctv.music.cctv15.utils.DisplayOptions;
import com.cctv.music.cctv15.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class NewsAdapter extends BaseAdapter {

    private Context context;

    private List<Content> list;

    private int width;

    private int height;

    public NewsAdapter(Context context, List<Content> list) {
        this.context = context;
        this.list = list;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        width = display.getWidth()-Utils.dpToPx(context,26);
        height = Utils.dpToPx(context,208);
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
        Content content = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_news, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(""+content.getContentstitle());
        holder.date.setText(""+DateUtils.format(content.getContentsdate()));
        holder.comment.setText(""+content.getCommentcount());
        ImageLoader.getInstance().displayImage(content.getAttachment().getAttachmentimgurl(width,height), holder.img, DisplayOptions.IMG.getOptions());
        return convertView;
    }

    private static class ViewHolder {
        private ImageView img;
        private TextView comment;
        private TextView date;
        private TextView title;

        public ViewHolder(View view) {
            img = (ImageView) view.findViewById(R.id.img);
            comment = (TextView) view.findViewById(R.id.comment);
            date = (TextView) view.findViewById(R.id.date);
            title = (TextView) view.findViewById(R.id.title);
        }
    }

}
