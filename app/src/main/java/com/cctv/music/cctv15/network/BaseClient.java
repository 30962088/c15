package com.cctv.music.cctv15.network;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.cctv.music.cctv15.MainActivity;
import com.cctv.music.cctv15.db.OfflineDataField;
import com.cctv.music.cctv15.utils.*;
import com.loopj.android.http.*;
import com.owengriffin.reflectionprism.IntrospectionException;
import com.owengriffin.reflectionprism.Introspector;
import com.owengriffin.reflectionprism.PropertyDescriptor;

import java.lang.reflect.InvocationTargetException;

public abstract class BaseClient implements HttpResponseHandler {

    protected final String HOST = AppConfig.getInstance().getHost();

    private static AsyncHttpClient client = new AsyncHttpClient();

    protected Context context;

    public BaseClient(Context context) {
        this.context = context;
    }

    static {
        client.setMaxConnections(10);
        client.setTimeout(10 * 1000);

    }

    private class ResponseHandler extends AsyncHttpResponseHandler {

        private HttpResponseHandler handler;

        private ResponseHandler(HttpResponseHandler handler) {

            this.handler = handler;

        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                              Throwable arg3) {

            handler.onError(500, "请求失败");
            requestHandler.onError(500, "请求失败");
            requestHandler.onComplete();
        }

        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            String str = new String(arg2);
            // TODO: 2015/8/5
            if (getURL().indexOf(AppConfig.getInstance().getHost()) != -1 && (getURL().indexOf("GetActivistDetails") == -1 && getURL().indexOf("isHaveUserName") == -1)) {
                try {

                    JSONObject object = new JSONObject(str);
                    int result = object.getInt("result");
                    if (result != 1000) {
                        requestHandler.onComplete();

                        if (result == 1019) {
                            Utils.tip(context, "登录过期，请重新登录");
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
                                    | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setAction(MainActivity.ACTION_TOLOGIN);
                            context.startActivity(intent);
                            Preferences.getInstance().logout();
                        } else {
                            requestHandler.onError(result, "请求失败");
                            handler.onError(result, "请求失败");
                        }

                        return;
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            requestHandler.onSuccess(handler.onSuccess(str));
            requestHandler.onComplete();

        }

    }

    public enum Method {

        GET, POST
    }

    protected abstract RequestParams getParams();

    protected abstract String getURL();

    protected abstract Method getMethod();

    public static interface RequestHandler {

        public void onComplete();

        public void onSuccess(Object object);

        public void onError(int error, String msg);

    }


    public static class SimpleRequestHandler implements RequestHandler {

        @Override
        public void onSuccess(Object object) {
            // TODO Auto-generated method stub

        }


        @Override
        public void onComplete() {
            // TODO Auto-generated method stub

        }


        @Override
        public void onError(int error, String msg) {
            // TODO Auto-generated method stub

        }

    }

    private RequestHandler requestHandler;

    public String getOfflineHash() {
        String hash = getURL();
        RequestParams params = getParams();
        if (params != null) {
            hash += params.toString();
        }
        return "" + hash.hashCode();
    }

    public void request(RequestHandler requestHandler) {

        /*if (useOffline()) {

            OfflineDataField dataField = OfflineDataField
                    .getOffline(context,getOfflineHash());
            if (dataField != null) {
                requestHandler.onSuccess(onSuccess(dataField.getData()));
                return;
            }

        }*/

        this.requestHandler = requestHandler;

        if (!isOnline(context)) {
            onError(501, "未发现网络");
            requestHandler.onError(501, "未发现网络");
            return;
        }

        Method method = getMethod();
        if (method == Method.GET) {
            get(getURL(), getParams(), this);
        } else if (method == Method.POST) {
            post(getURL(), getParams(), this);
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private RequestHandle handle;

    private void get(String url, RequestParams params,
                     HttpResponseHandler handler) {
        Header[] headers = fillHeaders();
        if (headers == null) {
            handle = client.get(getURL(), params, new ResponseHandler(
                    handler));
        } else {
            handle = client.get(context, getURL(), headers, params, new ResponseHandler(
                    handler));
        }


    }

    private void post(String url, RequestParams params,
                      HttpResponseHandler handler) {
        Header[] headers = fillHeaders();
        if (headers == null) {
            handle = client.post(getURL(), params, new ResponseHandler(
                    handler));
        } else {
            handle = client.post(context, getURL(), headers, params, contentType(), new ResponseHandler(
                    handler));
        }

    }

    public void cancel(boolean mayInterruptIfRunning) {
        if (handle != null && !handle.isFinished()) {
            handle.cancel(mayInterruptIfRunning);
        }

    }

    protected Header[] fillHeaders() {
        return null;
    }


    protected String contentType() {
        return "application/json";
    }


    protected boolean useOffline() {
        return false;
    }

    public static RequestParams buildParams(Object object)  {
        RequestParams params = new RequestParams();

        try {
            for(PropertyDescriptor propertyDescriptor: Introspector.getBeanInfo(object.getClass()).getPropertyDescriptors()){
                Object value = propertyDescriptor.getReadMethod().invoke(object);
                if(value != null){
                    params.add(propertyDescriptor.getName(), String.valueOf(value));
                }

            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return params;
    }



	/*static final Pattern reUnicode = Pattern.compile("\\\\\\\\u([0-9a-zA-Z]{4})");

	public static String decode1(String s) {
	    Matcher m = reUnicode.matcher(s);
	    StringBuffer sb = new StringBuffer(s.length());
	    while (m.find()) {
	        m.appendReplacement(sb,
	                Character.toString((char) Integer.parseInt(m.group(1), 16)));
	    }
	    m.appendTail(sb);
	    return sb.toString();
	}*/

}
