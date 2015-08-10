package com.cctv.music.cctv15.network;


import android.content.Context;

import com.cctv.music.cctv15.model.Sex;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;


///cctv15/setClientUser?
// method=setClientUser&
// nickname=%E9%98%B3%E5%85%89%E7%9A%84%E5%B0%8F%E9%83%A8%E7%9A%84
// &username=poolk001&
// usertype=2&
// sex=male&
// userimgguid=be47ea01b0a14d48acfe26941baac5ac&
// userimgformat=.jpg&
// address=%E5%8C%97%E4%BA%AC
public class SetClientUserRequest extends BaseClient{

    public static class Params{
        private String nickname;
        private String username;
        private String usertype;
        private Sex sex;
        private String userimgguid;
        private String userimgformat;
        private String address;

        public Params(String nickname, String username, String usertype, Sex sex, String userimgguid, String userimgformat, String address) {
            this.nickname = nickname;
            this.username = username;
            this.usertype = usertype;
            this.sex = sex;
            this.userimgguid = userimgguid;
            this.userimgformat = userimgformat;
            this.address = address;
        }
    }

    public static class Result{
        private String pkey;
        private String userid;

        public String getPkey() {
            return pkey;
        }

        public String getUserid() {
            return userid;
        }
    }

    private Params params;

    public SetClientUserRequest(Context context, Params params) {
        super(context);
        this.params = params;
    }

    @Override
    protected RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.add("method","setClientUser");
        params.add("nickname",""+this.params.nickname);
        params.add("nickname",""+this.params.nickname);
        params.add("username",""+this.params.username);
        params.add("usertype",""+this.params.usertype);
        params.add("sex",""+this.params.sex.getCode());
        params.add("userimgguid",""+this.params.userimgguid);
        params.add("userimgformat",""+this.params.userimgformat);
        params.add("address",""+this.params.address);
        return params;
    }

    @Override
    protected String getURL() {
        return HOST+"/cctv15/setClientUser";
    }

    @Override
    protected Method getMethod() {
        return Method.POST;
    }

    @Override
    public Object onSuccess(String str) {
        return new Gson().fromJson(str,Result.class);
    }

    @Override
    public void onError(int error, String msg) {

    }
}
