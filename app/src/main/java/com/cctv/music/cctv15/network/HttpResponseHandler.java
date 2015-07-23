package com.cctv.music.cctv15.network;

import org.apache.http.Header;

public interface HttpResponseHandler {
	public Object onSuccess(String str);
	public void onError(int error, String msg);
}
