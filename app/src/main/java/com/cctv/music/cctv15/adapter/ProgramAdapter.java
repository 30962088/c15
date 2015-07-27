package com.cctv.music.cctv15.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cctv.music.cctv15.R;
import com.cctv.music.cctv15.model.Program;

import java.util.List;


public class ProgramAdapter extends BaseAdapter {

    private Context context;

    private List<Program> list;

    private Integer selected;

    public ProgramAdapter(Context context, List<Program> list, Integer selected) {
        this.context = context;
        this.list = list;
        this.selected = selected;
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
        Program program = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_program, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.content.setTextColor(Color.WHITE);
        holder.play.setVisibility(View.GONE);
        if(position%2==0){
            holder.bg1.setBackgroundResource(R.drawable.bg_c1);
            holder.bg2.setBackgroundResource(R.drawable.bg_c3);
        }else{
            holder.bg1.setBackgroundResource(R.drawable.bg_c2);
            holder.bg2.setBackgroundResource(R.drawable.bg_c4);
        }

        if(selected != null && position == selected){
            holder.bg2.setBackgroundColor(Color.WHITE);
            holder.content.setTextColor(Color.BLACK);
            holder.play.setVisibility(View.VISIBLE);
        }
        holder.content.setText(""+program.getT());
        holder.time.setText(""+program.getShowTime());
        return convertView;
    }

    private static class ViewHolder {

        private View bg1;
        private View bg2;
        private View play;
        private TextView time;
        private TextView content;

        public ViewHolder(View view) {
            bg1 = view.findViewById(R.id.bg1);
            bg2 = view.findViewById(R.id.bg2);
            play = view.findViewById(R.id.play);
            time = (TextView) view.findViewById(R.id.time);
            content = (TextView) view.findViewById(R.id.content);
        }
    }

}
