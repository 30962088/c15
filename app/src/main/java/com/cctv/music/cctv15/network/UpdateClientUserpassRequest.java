package com.cctv.music.cctv15.network;


import android.content.Context;

import com.loopj.android.http.RequestParams;

public class UpdateClientUserpassRequest extends BaseClient{


    //method=updateClientUserpass&userid=189&pkey=fd0e3dc66fce4b169756b1b752554fd4&oldpassword=123123&newpassword=123123

    public static class Params{
        private String userid;
        private String pkey;
        private String oldpassword;
        private String newpassword;

        public Params(String userid, String pkey, String oldpassword, String newpassword) {
            this.userid = userid;
            this.pkey = pkey;
            this.oldpassword = oldpassword;
            this.newpassword = newpassword;
        }

        public String getUserid() {
            return userid;
        }

        public String getPkey() {
            return pkey;
        }

        public String getOldpassword() {
            return oldpassword;
        }

        public String getNewpassword() {
            return newpassword;
        }
    }
    private Params params;

    public UpdateClientUserpassRequest(Context context, Params params) {
        super(context);
        this.params = params;
    }

    @Override
    protected RequestParams getParams() {
        return buildParams(params);
    }

    @Override
    protected String getURL() {
        return HOST+"/cctv15/updateClientUserpass";
    }

    @Override
    protected Method getMethod() {
        return Method.POST;
    }

    @Override
    public Object onSuccess(String str) {
        return null;
    }

    @Override
    public void onError(int error, String msg) {

    }
}
