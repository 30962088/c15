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
import com.cctv.music.cctv15.model.MyRank;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.RankListRequest;
import com.cctv.music.cctv15.utils.DisplayOptions;
import com.cctv.music.cctv15.utils.Preferences;
import com.cctv.music.cctv15.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class RankFragment extends BaseFragment{

    public static RankFragment newInstance(MyRank myRank) {
        RankFragment fragment = new RankFragment();
        Bundle args = new Bundle();
        args.putSerializable("rank",myRank);
        fragment.setArguments(args);
        return fragment;
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

    private MyRank rank;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rank = (MyRank) getArguments().getSerializable("rank");
    }

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
        ImageLoader.getInstance().displayImage(rank.getMyloginuserimgurl(), holder.avatar, DisplayOptions.IMG.getOptions());
        holder.name.setText("" + rank.getMyusername());
        holder.rank.setText("" + rank.getMyranking());
        holder.score.setText("" + rank.getMyscore());
        holder.listview.setAdapter(new RankAdapter(getActivity(), rank.getRanklist()));
    }

}
