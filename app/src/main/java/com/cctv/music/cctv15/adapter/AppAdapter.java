package com.cctv.music.cctv15.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.music.cctv15.R;
import com.cctv.music.cctv15.model.AppItem;
import com.cctv.music.cctv15.utils.DisplayOptions;
import com.cctv.music.cctv15.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class AppAdapter extends BaseAdapter {

    public static interface OnDownloadListener{
        public void ondownload(AppItem item);
    }

    private Context context;

    private List<AppItem> list;

    private OnDownloadListener onDownloadListener;

    public AppAdapter(Context context, List<AppItem> list, OnDownloadListener onDownloadListener) {
        this.context = context;
        this.list = list;
        this.onDownloadListener = onDownloadListener;
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
        final AppItem app = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_app, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onDownloadListener != null){
                    onDownloadListener.ondownload(app);
                }
            }
        });

        if(Utils.isPackageInstalled(app.getPackagename(),context)){
            holder.btn.setEnabled(false);
            holder.btn.setText("已下载");
        }else{
            holder.btn.setEnabled(true);
            holder.btn.setText("下载");
        }

        holder.desc.setText(app.getDesc());

        holder.name.setText(app.getName());

        ImageLoader.getInstance().displayImage(app.getImg(), holder.img, DisplayOptions.IMG.getOptions());

        return convertView;
    }

    private static class ViewHolder {
        private ImageView img;
        private TextView name;
        private TextView desc;
        private TextView btn;

        public ViewHolder(View view) {
            img = (ImageView) view.findViewById(R.id.img);
            name = (TextView) view.findViewById(R.id.name);
            desc = (TextView) view.findViewById(R.id.desc);
            btn = (TextView) view.findViewById(R.id.btn);
        }
    }

}
