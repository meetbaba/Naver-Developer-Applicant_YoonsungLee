package com.excavateterm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 개발자 : 윤성 작성일 : 2015. 8. 7. 오후 6:58:16 설명 :
 */
public class FirstActivity extends Activity {

	private static final String TAG = "first ACTIVITY";

	private int myActivity = StaticStrings.FIRST_ACTIVITY;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.e("행정용어솔루션", "시작");

		setContentView(R.layout.activity_first);

		// 2초간 시작화면
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				
				if(Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1){
					
					dialogVersionMin();
				}
				
				Intent intent = new Intent(FirstActivity.this,
						SecondActivity.class);
				StaticStrings.EXIT_FLAG = true;

				startActivity(intent);
				// 뒤로가기 했을경우 안나오도록 없애주기 >> finish!!
				finish();
			}
		}, 2000); // 이 곳의 2000에 원하는 시간만큼 수정할 수 있다. ms단위
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		Log.i(TAG, "Activity Result");

		switch (resultCode) {
		case StaticStrings.RESULT_CODE_TRANSPARENT:
			int flag = data.getExtras().getInt(
					StaticStrings.EXTRA_TRANSPARENT_VALUE);

			if (flag == StaticStrings.KEEP_GOING_VALUE) {
				StaticStrings.EXIT_FLAG = true;
			} else if (flag == StaticStrings.NEW_START_VALUE) {
				Intent intent = new Intent(this, FirstActivity.class);
				StaticStrings.EXIT_FLAG = true;
				startActivity(intent);
				finish();
			}
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		Log.w(TAG, "OnResume");
		if (StaticStrings.EXIT_FLAG == false) {

			Log.w(TAG, "강제종료인 경우 _ 강제종료된 activity : "
					+ StaticStrings.CURRENT_ACTIVITY);

			if (StaticStrings.CURRENT_ACTIVITY == myActivity) {

				Intent intent = new Intent(this, TransparentActivity.class);
				StaticStrings.EXIT_FLAG = true;
				startActivityForResult(intent,
						StaticStrings.REQUEST_CODE_TRANSPARENT);

			} else {

				if (StaticStrings.CURRENT_ACTIVITY == StaticStrings.FIRST_ACTIVITY) {
					Intent intent = new Intent(this, FirstActivity.class);
					startActivity(intent);
					finish();
				} else if (StaticStrings.CURRENT_ACTIVITY == StaticStrings.SECOND_ACTIVITY) {
					Intent intent = new Intent(this, SecondActivity.class);
					startActivity(intent);
					finish();
				} else if (StaticStrings.CURRENT_ACTIVITY == StaticStrings.THIRD_ACTIVITY) {
					Intent intent = new Intent(this, ThirdActivity.class);
					startActivity(intent);
					finish();
				} else if (StaticStrings.CURRENT_ACTIVITY == StaticStrings.FOURTH_ACTIVITY) {
					Intent intent = new Intent(this, FourthActivity.class);
					startActivity(intent);
					finish();
				} else if (StaticStrings.CURRENT_ACTIVITY == StaticStrings.FIFTH_ACTIVITY) {
					Intent intent = new Intent(this, FifthActivity.class);
					startActivity(intent);
					finish();
				} else if (StaticStrings.CURRENT_ACTIVITY == StaticStrings.SIXTH_ACTIVITY) {
					Intent intent = new Intent(this, SixthActivity.class);
					startActivity(intent);
					finish();
				} else if (StaticStrings.CURRENT_ACTIVITY == StaticStrings.SEVENTH_ACTIVITY) {
					Intent intent = new Intent(this, SeventhActivity.class);
					startActivity(intent);
					finish();
				} else if (StaticStrings.CURRENT_ACTIVITY == StaticStrings.EIGHTH_ACTIVITY) {
					Intent intent = new Intent(this, EighthActivity.class);
					startActivity(intent);
					finish();
				}
			}

		} else if (StaticStrings.EXIT_FLAG == true) {

			Log.w(TAG, "정상종료인 경우  : " + StaticStrings.CURRENT_ACTIVITY);

			StaticStrings.CURRENT_ACTIVITY = myActivity;
			StaticStrings.EXIT_FLAG = false;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Log.w(TAG, "onPause");

		super.onPause();
		if (StaticStrings.EXIT_FLAG == false) {
			Log.w(TAG, "강제종료 됐다!! _ 종료된 activity: " + myActivity);

			StaticStrings.CURRENT_ACTIVITY = myActivity;
		} else if (StaticStrings.EXIT_FLAG == true) {
			Log.w(TAG, "정상 종료다  메롱: ");

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.first, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:
			dialogSimple();

		case KeyEvent.KEYCODE_HOME:

		case KeyEvent.KEYCODE_MENU:

		case KeyEvent.KEYCODE_POWER:

		case KeyEvent.KEYCODE_VOLUME_UP:

		case KeyEvent.KEYCODE_VOLUME_DOWN:

		default:
		}

		return true;
		//return super.onKeyDown(keyCode, event);

	}
	
	private void dialogSimple(){
		AlertDialog.Builder alt_bld=new AlertDialog.Builder(this);
		alt_bld.setMessage(StaticStrings.DIALOG_EXIT_CONTENT).setCancelable(false).setPositiveButton("종료", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		}).setNegativeButton("취소", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		
		AlertDialog alert= alt_bld.create();
		
		alert.setTitle(StaticStrings.DIALOG_EXIT_TITLE);
		alert.setIcon(R.drawable.alert);
		alert.show();
	}
	

	private void dialogVersionMin(){
		AlertDialog.Builder alt_bld=new AlertDialog.Builder(this);
		alt_bld.setMessage(StaticStrings.DIALOG_VERSION_CONTENT).setCancelable(false).setPositiveButton("종료", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		AlertDialog alert= alt_bld.create();
		
		alert.setTitle(StaticStrings.DIALOG_VERSION_TITLE);
		alert.setIcon(R.drawable.alert);
		alert.show();
	}
}
