package com.cctv.music.cctv15.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cctv.music.cctv15.R;
import com.cctv.music.cctv15.adapter.RankAdapter;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.RankListRequest;
import com.cctv.music.cctv15.utils.DisplayOptions;
import com.cctv.music.cctv15.utils.Preferences;
import com.cctv.music.cctv15.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class RankFragment extends BaseFragment implements BaseClient.RequestHandler {

    public static RankFragment newInstance() {
        return new RankFragment();
    }

    private class ViewHolder {

        private ImageView avatar;

        private TextView name;

        private TextView score;

        private TextView rank;

        private ListView listview;

        public ViewHolder(View view) {
            avatar = (ImageView) view.findViewById(R.id.avatar);
            name = (TextView) view.findViewById(R.id.name);
            score = (TextView) view.findViewById(R.id.score);
            rank = (TextView) view.findViewById(R.id.rank);
            listview = (ListView) view.findViewById(R.id.listview);
        }
    }

    private ViewHolder holder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rank, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        holder = new ViewHolder(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RankListRequest request = new RankListRequest(getActivity(), new RankListRequest.Params(Preferences.getInstance().getUid()));
        request.request(this);
    }


    @Override
    public void onComplete() {

    }

    @Override
    public void onSuccess(Object object) {
        RankListRequest.Result result = (RankListRequest.Result) object;
        ImageLoader.getInstance().displayImage(result.getMyloginuserimgurl(), holder.avatar, DisplayOptions.IMG.getOptions());
        holder.name.setText("" + result.getMyusername());
        holder.rank.setText("" + result.getMyranking());
        holder.score.setText("" + result.getMyscore());
        holder.listview.setAdapter(new RankAdapter(getActivity(), result.getRanklist()));
    }

    @Override
    public void onError(int error, String msg) {
        Utils.tip(getActivity(), "我的排名加载失败");
    }

}
