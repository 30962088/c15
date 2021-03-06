package com.cctv.music.cctv15;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.cctv.music.cctv15.adapter.VoteAdapter;
import com.cctv.music.cctv15.db.OfflineDataField;
import com.cctv.music.cctv15.model.Content;
import com.cctv.music.cctv15.model.Vote;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.VoteRequest;
import com.cctv.music.cctv15.ui.BaseListView;
import com.cctv.music.cctv15.utils.AppConfig;
import com.cctv.music.cctv15.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class VoteActivity extends BaseActivity implements BaseListView.OnLoadListener,AdapterView.OnItemClickListener{


    public static void open(Context context) {

        Intent intent = new Intent(context, VoteActivity.class);

        context.startActivity(intent);

    }
    private List<Vote> list = new ArrayList<>();

    private VoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        BaseListView listView = (BaseListView)findViewById(R.id.listview);
        int padding = Utils.dpToPx(this,13);
        listView.getRefreshableView().setPadding(padding, 0, padding, 0);
        listView.getRefreshableView().setDividerHeight(padding);
        listView.getRefreshableView().setHeaderDividersEnabled(false);
        listView.getRefreshableView().setFooterDividersEnabled(false);
        listView.getRefreshableView().setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        OfflineDataField offlineDataField =  OfflineDataField.getOffline(context, AppConfig.getInstance().getHost() + "/cctv15/vote");
        if(offlineDataField != null){
            List<Vote> list1 = new Gson().fromJson(offlineDataField.getData(),new TypeToken<List<Vote>>(){}.getType());
            list.addAll(list1);
        }
        adapter = new VoteAdapter(this,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnLoadListener(this);
        listView.load(true);
    }

    @Override
    public BaseClient onLoad(int offset, int limit) {

        VoteRequest request = new VoteRequest(this,new VoteRequest.Params(offset,limit));

        return request;
    }

    @Override
    public boolean onLoadSuccess(Object object, int offset, int limit) {

        VoteRequest.Result result = (VoteRequest.Result)object;

        if(offset == 1){
            list.clear();
        }

        list.addAll(result.getVotelist());

        adapter.notifyDataSetChanged();

        return result.getVotelist().size()>=limit?true:false;
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public BaseListView.Type getRequestType() {
        return BaseListView.Type.PAGE;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Vote vote = list.get(position - 1);
        VoteDetailActivity.open(this,vote);
    }


}
