package com.cctv.music.cctv15.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.music.cctv15.R;
import com.cctv.music.cctv15.model.Song;
import com.cctv.music.cctv15.utils.DisplayOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class SongAlbumView extends FrameLayout{

    private class ViewHolder{
        private ImageView img;
        private TextView rank;
        private TextView singer;
        private TextView songname;

        public ViewHolder() {
            img = (ImageView) findViewById(R.id.img);
            rank = (TextView) findViewById(R.id.rank);
            singer = (TextView) findViewById(R.id.singer);
            songname = (TextView) findViewById(R.id.songname);
        }
    }

    public SongAlbumView(Context context) {
        super(context);
        init();
    }



    public SongAlbumView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SongAlbumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private ViewHolder holder;

    private static int[] rankBgs = new int[]{R.drawable.bg_red,R.drawable.bg_orange,R.drawable.bg_orange2};

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.item_disk, this);
        holder = new ViewHolder();
    }

    public void setModel(Song song,Integer rank){

        if(rank != null){
            holder.rank.setVisibility(View.VISIBLE);
            holder.rank.setBackgroundResource(rankBgs[rank]);
            holder.rank.setText(""+rank);
        }else{
            holder.rank.setVisibility(View.GONE);
        }

        ImageLoader.getInstance().displayImage(song.getSurfaceurl(), holder.img, DisplayOptions.IMG.getOptions());

        holder.singer.setText(""+song.getSingername());

        holder.songname.setText(""+song.getSongname());

    }


}
