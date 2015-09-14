package com.cctv.music.cctv15;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.cctv.music.cctv15.adapter.MyTicketAdapter;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.GetMyActivistTicketListRequest;
import com.cctv.music.cctv15.ui.LoadingPopup;
import com.cctv.music.cctv15.utils.Preferences;
import com.cctv.music.cctv15.utils.Utils;

public class MyTicketActivity extends BaseActivity{

    public static void open(Context context) {

        Intent intent = new Intent(context, MyTicketActivity.class);

        context.startActivity(intent);

    }

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,TicketActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        listView = (ListView) findViewById(R.id.listview);
        listView.post(new Runnable() {
            @Override
            public void run() {
                request();
            }
        });

    }

    private void request() {
        LoadingPopup.show(context);
        final GetMyActivistTicketListRequest request = new GetMyActivistTicketListRequest(context,
                new GetMyActivistTicketListRequest.Params(Preferences.getInstance().getUid()));

        request.request(new BaseClient.RequestHandler() {
            @Override
            public void onComplete() {
                LoadingPopup.hide(context);
            }

            @Override
            public void onSuccess(Object object) {
                GetMyActivistTicketListRequest.Result result = (GetMyActivistTicketListRequest.Result)object;

                listView.setAdapter(new MyTicketAdapter(context,result.getMyticketlist()));

            }

            @Override
            public void onError(int error, String msg) {
                Utils.tip(context,"加载失败");
            }
        });

    }
}
