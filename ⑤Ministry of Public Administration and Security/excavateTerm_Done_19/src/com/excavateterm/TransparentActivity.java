/**
 * <pre>
 * 1. 프로젝트명 : excavateTerm
 * 2. 패키지명(또는 디렉토리 경로) : com.excavateterm
 * 3. 파일명 : TransparentActivity.java
 * 4. 작성일 : 2015. 8. 11. 오전 7:14:49
 * 5. 작성자 : 윤성
 * 6. 설명 :
 * </pre>
 */
package com.excavateterm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * 개발자 : 윤성
 * 작성일 : 2015. 8. 11. 오전 7:14:49
 * 설명 : 
 */


public class TransparentActivity extends Activity implements OnClickListener {

	private int mDeviceScreenWidth;
	private int mDeviceScreenHeight;
	
	Button btn_keepGoing;
	Button btn_newStart;
		
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transparent);
		
		Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		mDeviceScreenWidth = display.getWidth();
		mDeviceScreenHeight = display.getHeight();
		
		btn_keepGoing=(Button)findViewById(R.id.transparent_button_keep_going);
		btn_newStart=(Button)findViewById(R.id.transparent_button_new_start);
		btn_keepGoing.setOnClickListener(this);
		btn_newStart.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		
		Intent intent;
		
		switch ( v.getId()) {
		case R.id.transparent_button_keep_going:
			Toast.makeText(getApplicationContext(), StaticStrings.TOAST_KEEP_GOING, Toast.LENGTH_LONG).show();
			
			intent =new Intent();
			intent.putExtra(StaticStrings.EXTRA_TRANSPARENT_VALUE, StaticStrings.KEEP_GOING_VALUE);
			setResult(StaticStrings.RESULT_CODE_TRANSPARENT, intent);
			finish();
			
			break;
			
		case R.id.transparent_button_new_start :
			Toast.makeText(getApplicationContext(), StaticStrings.TOAST_NEW_START, Toast.LENGTH_LONG).show();

			intent =new Intent();
			intent.putExtra(StaticStrings.EXTRA_TRANSPARENT_VALUE, StaticStrings.NEW_START_VALUE);
			setResult(StaticStrings.RESULT_CODE_TRANSPARENT, intent);
			finish();
			
			break;
		default:
			break;
		}
	}
	
}