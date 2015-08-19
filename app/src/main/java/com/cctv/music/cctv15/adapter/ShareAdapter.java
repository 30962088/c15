package com.cctv.music.cctv15.adapter;

import java.io.Serializable;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.TextView;

import com.cctv.music.cctv15.R;
import com.umeng.socialize.bean.SHARE_MEDIA;

public class ShareAdapter extends BaseAdapter implements Serializable {

	
	public static class Model implements Serializable {
		private int icon;
		private String label;
		private SHARE_MEDIA share_media;

		public Model(int icon, String label, SHARE_MEDIA share_media) {
			this.icon = icon;
			this.label = label;
			this.share_media = share_media;
		}

		public SHARE_MEDIA getShare_media() {
			return share_media;
		}
	}
	
	private Context context;

	private List<Model> list;
	
	

	public ShareAdapter(Context context, List<Model> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		Model model = list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_share, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.icon.setImageResource(model.icon);
		
		holder.label.setText(model.label);
		
		return convertView;
	}

	public static class ViewHolder {

		private ImageView icon;
		
		private TextView label;
		
		public ViewHolder(View view) {
			icon = (ImageView) view.findViewById(R.id.icon);
			label = (TextView) view.findViewById(R.id.label);
		}

	}


}
