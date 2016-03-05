package com.app.smartkidshoes;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class InformActivity extends Activity {
	private SharedPreferences sp;
	private String height, gender;

	private RadioGroup rgGender;
	private RadioButton rbBoy, rbGirl;
	private EditText etHeight;
	private Button btnSetting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inform);
		
		sp = getSharedPreferences("HEIGHT", 0);
		height = sp.getString("HEIGHT", "");
		
		sp = getSharedPreferences("GENDER", 0);
		gender = sp.getString("GENDER", "");
		
		rbBoy = (RadioButton)findViewById(R.id.rbBoy);
		rbGirl = (RadioButton)findViewById(R.id.rbGirl);
		
		if(gender.equals("BOY")) {
			Log.e("Debug", "rgBoy");
			rbBoy.setChecked(true);
			rbGirl.setChecked(false);
		} else {
			rbBoy.setChecked(false);
			rbGirl.setChecked(true);
		}
		
		etHeight = (EditText)findViewById(R.id.etHeight);
		rgGender = (RadioGroup)findViewById(R.id.rgGender);
		
		if(!height.equals("")) {
			etHeight.setText(height);
		}
		
		
		
		rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton checkButton = (RadioButton)group.findViewById(checkedId);
				String checkText = checkButton.getText().toString();
				SharedPreferences settings = getSharedPreferences("GENDER", 0);
				SharedPreferences.Editor editor = settings.edit();
				if(checkText.equals("BOY")) {
					editor.putString("GENDER", "BOY");
				} else {
					editor.putString("GENDER", "GRIL");
				}
				editor.commit();
			}
		});

		btnSetting = (Button)findViewById(R.id.btnSetting);
		btnSetting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPreferences settings = getSharedPreferences("HEIGHT", 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("HEIGHT", etHeight.getText().toString());
				editor.commit();
				
				Intent intent = new Intent(getApplicationContext(), BluetoothActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	@Override
	protected void onStop() {
		super.onStop();
		SharedPreferences settings = getSharedPreferences("LAST_ACTIVITY", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("LAST_ACTIVITY", "INFORM_ACTIVITY");
		editor.commit();
	}
}
