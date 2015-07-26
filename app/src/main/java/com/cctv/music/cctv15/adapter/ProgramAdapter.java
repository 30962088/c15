package com.cctv.music.cctv15.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cctv.music.cctv15.R;
import com.cctv.music.cctv15.model.CCTV15;
import com.cctv.music.cctv15.model.Program;


public class ProgramAdapter extends BaseAdapter {

    private Context context;

    private CCTV15 cctv15;

    public ProgramAdapter(Context context, CCTV15 cctv15) {
        this.context = context;
        this.cctv15 = cctv15;
    }

    @Override
    public int getCount() {
        return cctv15.getProgram().size();
    }

    @Override
    public Object getItem(int position) {
        return cctv15.getProgram().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Program program = cctv15.getProgram().get(position);
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
        if(program.getSt() == cctv15.getLiveSt()){
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
