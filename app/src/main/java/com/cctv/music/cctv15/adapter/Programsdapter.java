package com.cctv.music.cctv15.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cctv.music.cctv15.R;
import com.cctv.music.cctv15.model.Program;
import com.cctv.music.cctv15.utils.DateUtils;
import com.cctv.music.cctv15.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Programsdapter extends BaseAdapter {

    public static class Model{
        private String date;
        private String week;
        private List<Program> list;
        private Integer selected;
        public Model(Date date, List<Program> list, Integer selected) {
            this.week = DateUtils.getWeek(date);
            this.date = new SimpleDateFormat("yyyy年MM月dd").format(date);
            this.list = list;
            this.selected = selected;
        }

        public Model(Date date, List<Program> list) {
            this(date, list,null);
        }
    }

    private Context context;

    private List<Model> list;

    public Programsdapter(Context context, List<Model> list) {
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
        Model model = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_programs, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.date.setText(""+model.date);

        holder.week.setText(""+model.week);

        holder.listview.setAdapter(new ProgramAdapter(context, model.list, model.selected));

        Utils.setListViewHeightBasedOnItems(holder.listview);

        return convertView;
    }

    private static class ViewHolder {
        private TextView date;
        private TextView week;
        private ListView listview;

        public ViewHolder(View view) {
            date = (TextView) view.findViewById(R.id.date);
            week = (TextView) view.findViewById(R.id.week);
            listview = (ListView) view.findViewById(R.id.listview);
        }
    }

}
