package com.cctv.music.cctv15;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.cctv.music.cctv15.network.BaseClient;
import com.cctv.music.cctv15.network.GetProvinceCityRequest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class CityActivity extends BaseActivity implements BDLocationListener  {

	private static final long serialVersionUID = 1L;


	private String provincecity;

	private TextView city;

	private View confirmBtn;
	
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_city);
		city = (TextView) findViewById(R.id.city);
		findViewById(R.id.back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		confirmBtn = findViewById(R.id.confirm);
		confirmBtn.setEnabled(false);
		confirmBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("city", provincecity);
				setResult(Activity.RESULT_OK, intent);
				finish();

			}
		});
		mLocationClient = new LocationClient(getApplicationContext());
	    mLocationClient.registerLocationListener(this); 
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mLocationClient.stop();
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mLocationClient.start();
	}




	@Override
	public void onReceiveLocation(BDLocation location) {
		GetProvinceCityRequest request = new GetProvinceCityRequest(this,
				new GetProvinceCityRequest.Params(location.getLongitude(), location.getLatitude()));

		request.request(new BaseClient.RequestHandler() {

			@Override
			public void onSuccess(Object object) {
				GetProvinceCityRequest.Result result = (GetProvinceCityRequest.Result) object;
				provincecity = result.getProvincecity();
				confirmBtn.setEnabled(true);
				city.setText("您当前所在城市：" + provincecity);
				mLocationClient.stop();
			}


			@Override
			public void onComplete() {
				// TODO Auto-generated method stub
				
			}


			@Override
			public void onError(int error, String msg) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

}