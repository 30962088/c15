package com.cctv.music.cctv15;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.cctv.music.cctv15.adapter.Programsdapter;
import com.cctv.music.cctv15.model.Program;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.ProgramRequest;
import com.cctv.music.cctv15.ui.LoadingPopup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class ProgramsActivity extends BaseActivity{

    public static void open(Context context) {

        Intent intent = new Intent(context, ProgramsActivity.class);

        context.startActivity(intent);

    }

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);
        listView = (ListView)findViewById(R.id.listview);
        listView.post(new Runnable() {
            @Override
            public void run() {
                request();
            }
        });

    }

    private void request() {
        LoadingPopup.show(context);
        for(int i = 0;i<=6;i++){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, i);
            request(cal.getTime(),i);
        }
    }

    private int count = 0;

    private Programsdapter.Model[] objects = new Programsdapter.Model[7];

    private void request(final Date time,final int index) {

        ProgramRequest programRequest = new ProgramRequest(this,new ProgramRequest.Params(time));
        programRequest.request(new BaseClient.RequestHandler() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSuccess(Object object) {
                ProgramRequest.Result result = (ProgramRequest.Result)object;
                if(result == null){
                    objects[index] = new Programsdapter.Model(time,new ArrayList<Program>(),null);
                }else{
                    Integer current = null;
                    if(index == 0){
                        current = result.getCurrent();
                    }
                    objects[index] = new Programsdapter.Model(time,result.getCctv15().getProgram(),current);
                }
                count ++;
                if(count == 7){
                    initList();
                    LoadingPopup.hide(context);
                }
            }



            @Override
            public void onError(int error, String msg) {

            }
        });
    }


    private void initList() {

        listView.setAdapter(new Programsdapter(this, Arrays.asList(objects)));

    }

}
