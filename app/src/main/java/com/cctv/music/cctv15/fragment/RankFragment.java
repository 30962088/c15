package com.cctv.music.cctv15.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cctv.music.cctv15.LoginActivity;
import com.cctv.music.cctv15.R;
import com.cctv.music.cctv15.adapter.RankAdapter;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.RankListRequest;
import com.cctv.music.cctv15.utils.DisplayOptions;
import com.cctv.music.cctv15.utils.Preferences;
import com.nostra13.universalimageloader.core.ImageLoader;

public class RankFragment extends BaseFragment implements BaseClient.RequestHandler{

    public static RankFragment newInstance() {
        RankFragment fragment = new RankFragment();
        return fragment;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSuccess(Object object) {
        RankListRequest.Result rank = (RankListRequest.Result)object;
        if(!Preferences.getInstance().isLogin()){
            holder.avatar.setImageResource(R.drawable.user_default);
            holder.name.setText("尚未登录");
        }else{
            ImageLoader.getInstance().displayImage(rank.getMyloginuserimgurl(), holder.avatar, DisplayOptions.IMG.getOptions());
            holder.name.setText("" + rank.getMyusername());
        }
        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Preferences.getInstance().isLogin()){
                    LoginActivity.open(context);
                }

            }
        });
        holder.rank.setText("" + rank.getMyranking());
        holder.score.setText("" + rank.getMyscore());
        holder.listview.setAdapter(new RankAdapter(getActivity(), rank.getRanklist()));
    }

    @Override
    public void onError(int error, String msg) {

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


    }

    @Override
    public void onResume() {
        super.onResume();
        RankListRequest request = new RankListRequest(getActivity(),new RankListRequest.Params(Preferences.getInstance().getUid()));
        request.request(this);
    }
}
