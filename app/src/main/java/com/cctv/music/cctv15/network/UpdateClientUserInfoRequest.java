package com.cctv.music.cctv15.network;


import android.content.Context;

import com.cctv.music.cctv15.utils.Preferences;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

public class UpdateClientUserInfoRequest extends BaseClient{

    public static class Params{
        private String userid;
        private String pkey;
        private String userimgguid;
        private String userimgformat;
        private String address;
        private String nickname;
        private String username;

        public Params(String userid, String pkey) {
            this.userid = userid;
            this.pkey = pkey;
        }


        public void setUsername(String username) {
            this.username = username;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setUserimgguid(String userimgguid) {
            this.userimgguid = userimgguid;
        }

        public void setUserimgformat(String userimgformat) {
            this.userimgformat = userimgformat;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }

    public static class Result{
        private String pkey;
        private String userid;

    }

    private Params params;

    public UpdateClientUserInfoRequest(Context context, Params params) {
        super(context);
        this.params = params;
    }

    @Override
    protected RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.add("userid",this.params.userid);
        params.add("pkey",this.params.pkey);
        if(this.params.userimgguid != null){
            params.add("userimgguid",this.params.userimgguid);
        }
        if(this.params.userimgformat != null){
            params.add("userimgformat",this.params.userimgformat);
        }
        if(this.params.address != null){
            params.add("address",this.params.address);
        }
        if(this.params.nickname != null){
            params.add("nickname",this.params.nickname);
        }
        if(this.params.username != null){
            params.add("username",this.params.username);
        }
        return params;
    }

    @Override
    protected String getURL() {
        return HOST+"/cctv15/updateClientUserInfo";
    }

    @Override
    protected Method getMethod() {
        return Method.POST;
    }

    @Override
    public Object onSuccess(String str) {
        Result result = new Gson().fromJson(str,Result.class);
        Preferences.getInstance().setUid(result.userid);
        Preferences.getInstance().setPkey(result.pkey);
        return result;
    }

    @Override
    public void onError(int error, String msg) {

    }
}
