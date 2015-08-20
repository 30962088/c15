package com.cctv.music.cctv15.ui;

import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.utils.Utils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.cctv.music.cctv15.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

public class BaseListView extends PullToRefreshListView {
	public static interface OnLoadListener {
		public BaseClient onLoad(int offset, int limit);

		public boolean onLoadSuccess(Object object, int offset, int limit);

		public void onError(String error);

		public Type getRequestType();
	}

	public enum Type {
		PAGE, OFFSET
	}

	public static class RequestResult {
		private Type type;
		private BaseClient baseClient;

		public RequestResult(Type type, BaseClient baseClient) {
			super();
			this.type = type;
			this.baseClient = baseClient;
		}

	}

	private int offset = 0;

	private OnLoadListener onLoadListener;

	private View mFooterLoading;

	protected boolean hasMode = false;

	private int limit = 20;

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setOnLoadListener(OnLoadListener onLoadListener) {
		this.onLoadListener = onLoadListener;
	}

	public BaseListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BaseListView(
			Context context,
			PullToRefreshBase.Mode mode,
			PullToRefreshBase.AnimationStyle style) {
		super(context, mode, style);
		init();
	}

	public BaseListView(Context context,
			PullToRefreshBase.Mode mode) {
		super(context, mode);
		init();
	}

	public BaseListView(Context context) {
		super(context);
		init();
	}

	private void init() {
		View footer = inflate(getContext(), R.layout.footer_loading, null);
		mFooterLoading = footer.findViewById(R.id.layout_checkmore);
		mFooterLoading.setVisibility(View.GONE);
		getRefreshableView().addFooterView(footer);
		setOnRefreshListener(new OnRefreshListener<ListView>() {

			public void onRefresh(PullToRefreshBase<ListView> refreshView) {

				String label = DateUtils.formatDateTime(getContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				_load(true);

			}
		});

		setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			public void onLastItemVisible() {
				if (hasMode) {
					_load(false);
				}

			}
		});
	}

	public void load(boolean refresh){
		setRefreshing(refresh);
	}
	
	private void _load(boolean refresh) {
		final Type type = onLoadListener.getRequestType();
		hasMode = false;
		if (refresh) {
			
			if (type == Type.PAGE) {
				offset = 1;
			} else {
				offset = 0;
			}

		}
		if (onLoadListener != null) {
			BaseClient baseClient = onLoadListener.onLoad(offset, limit);
			baseClient.request(new BaseClient.RequestHandler() {

				@Override
				public void onSuccess(Object object) {
					boolean result = onLoadListener.onLoadSuccess(object,
							offset, limit);
					hasMode = result;
					if (!result) {
						mFooterLoading.setVisibility(View.GONE);
					} else {
						mFooterLoading.setVisibility(View.VISIBLE);
					}
					
					if (type == Type.OFFSET) {
						offset += limit;
					} else {
						offset++;
					}
					String label = DateUtils.formatDateTime(getContext(),
							System.currentTimeMillis(),
							DateUtils.FORMAT_SHOW_TIME
									| DateUtils.FORMAT_SHOW_DATE
									| DateUtils.FORMAT_ABBREV_ALL);

				}

				@Override
				public void onComplete() {
					BaseListView.this.onRefreshComplete();
					
				}

				@Override
				public void onError(int error, String msg) {
					Utils.tip(getContext(), "列表加载失败");
					
				}
			});

		}
	}

}
