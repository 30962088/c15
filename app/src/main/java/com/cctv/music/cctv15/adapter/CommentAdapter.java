package com.cctv.music.cctv15.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.cctv.music.cctv15.model.Comment;
import com.cctv.music.cctv15.ui.CommentView;

import java.util.List;

public class CommentAdapter extends BaseAdapter {

    private Context context;

    private List<Comment> list;

    public CommentAdapter(Context context, List<Comment> list) {
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
        Comment comment = list.get(position);
        if (convertView == null) {
            convertView = new CommentView(context);
        }

        ((CommentView)convertView).setModel(comment);

        return convertView;
    }


}
