package com.cctv.music.cctv15.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cctv.music.cctv15.R;
import com.cctv.music.cctv15.model.Song;
import com.cctv.music.cctv15.ui.SongAlbumView;
import java.util.List;

public class SongAlbumAdapter extends BaseAdapter {


    public static interface OnSongAlbumClick{
        void onsongalbumclick(int index);
    }

    public static class Model{
        private Song col1;
        private Song col2;

        public Model() {
        }

        public void setCol1(Song col1) {
            this.col1 = col1;
        }

        public void setCol2(Song col2) {
            this.col2 = col2;
        }
    }

    private Context context;

    private List<Model> list;

    private OnSongAlbumClick onSongAlbumClick;

    public SongAlbumAdapter(Context context, List<Model> list, OnSongAlbumClick onSongAlbumClick) {
        this.context = context;
        this.list = list;
        this.onSongAlbumClick = onSongAlbumClick;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Model model = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_disk_row, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.col1.setVisibility(View.INVISIBLE);
        holder.col1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSongAlbumClick.onsongalbumclick(position * 2);
            }
        });
        holder.col2.setVisibility(View.INVISIBLE);
        holder.col2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSongAlbumClick.onsongalbumclick(position*2+1);
            }
        });
        if(model.col1 != null){
            holder.col1.setVisibility(View.VISIBLE);
            Integer rank = null;
            switch (position){
                case 0:
                    rank = 0;
                    break;
                case 1:
                    rank = 2;
                    break;

            }
            holder.col1.setModel(model.col1,rank);
        }

        if(model.col2 != null){
            holder.col2.setVisibility(View.VISIBLE);
            holder.col2.setModel(model.col2,position==0?1:null);
        }

        return convertView;
    }

    private static class ViewHolder {
        private SongAlbumView col1;
        private SongAlbumView col2;

        public ViewHolder(View view) {
            col1 = (SongAlbumView) view.findViewById(R.id.col1);
            col2 = (SongAlbumView) view.findViewById(R.id.col2);
        }
    }

}
