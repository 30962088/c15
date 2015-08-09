package com.cctv.music.cctv15.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import com.cctv.music.cctv15.R;
import com.cctv.music.cctv15.utils.DisplayOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.viewPager.LoopViewPager;
import com.viewpagerindicator.CirclePageIndicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SliderFragment extends Fragment {

	public static SliderFragment newInstance(
			OnSliderItemClickListener onSliderItemClickListener,
			ArrayList<Model> models) {
		SliderFragment fragment = new SliderFragment();
		Bundle args = new Bundle();
		fragment.onSliderItemClickListener = onSliderItemClickListener;
		args.putSerializable("model", models);
		fragment.setArguments(args);
		return fragment;
	}

	private LoopViewPager viewPager;

	private CirclePageIndicator indicator;

	private ArrayList<Model> models;

	private TextView titleView;

	private OnSliderItemClickListener onSliderItemClickListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		models = (ArrayList<Model>) getArguments().getSerializable("model");
		/*onSliderItemClickListener = (OnSliderItemClickListener) getArguments()
				.getSerializable("onSliderItemClickListener");*/
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.slider_layout, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		viewPager = (LoopViewPager) view.findViewById(R.id.viewPager);
		viewPager.setOffscreenPageLimit(models.size());
		indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
		titleView = (TextView) view.findViewById(R.id.title);
		ImageView imgView = (ImageView) view.findViewById(R.id.img);
		if(models.size() == 1){
			indicator.setVisibility(View.GONE);
			viewPager.setVisibility(View.GONE);
			titleView.setText(models.get(0).title);
			imgView.setVisibility(View.VISIBLE);
			ImageLoader.getInstance().displayImage(models.get(0).img, imgView, DisplayOptions.IMG.getOptions());
			imgView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					onSliderItemClickListener.OnSliderItemClick(0, models.get(0));
					
				}
			});
		}else{
			imgView.setVisibility(View.GONE);
			
			MyAdapter adapter = new MyAdapter();
			
			viewPager.setAdapter(adapter);
			

			indicator.setViewPager(viewPager);
			indicator.setOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int position) {

					Model model = models.get(position);
					titleView.setText(model.title);

				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub

				}
			});

			if (models != null && models.size() > 0) {
				titleView.setText(models.get(0).title);
			}
		}
		
		

	}

	public interface OnSliderItemClickListener extends Serializable {
		public void OnSliderItemClick(int index,Model model);
	}

	public static class Model implements Serializable {
		private String extra;
		private String img;
		private String title;

		public Model(String extra, String img, String title) {
			this.extra = extra;
			this.img = img;
			this.title = title;
		}

		public String getExtra() {
			return extra;
		}

		public String getImg() {
			return img;
		}

		public String getTitle() {
			return title;
		}


		public static ArrayList<Model> getMocks(){
			ArrayList<Model> list = new ArrayList<>();
			for(int i = 0;i<=5;i++){
				list.add(new Model(null,"http://pic1a.nipic.com/2009-02-20/2009220152758211_2.jpg","标题"+i));
			}
			return list;
		}

	}

	public class MyAdapter extends PagerAdapter {

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			final Model model = models.get(position);
			View view = LayoutInflater.from(getActivity()).inflate(
					R.layout.slider_item, null);
			ImageView imageView = (ImageView) view.findViewById(R.id.img);
			ImageLoader.getInstance().displayImage(model.img, imageView,
					DisplayOptions.IMG.getOptions());
			container.addView(view);
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					onSliderItemClickListener.OnSliderItemClick(position,model);

				}
			});
			return view;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return models == null ? 0 : models.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return models.get(position).title;
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			// TODO Auto-generated method stub
			return view == (View) obj;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

}
