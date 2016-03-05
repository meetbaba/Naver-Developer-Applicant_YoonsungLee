/**
 * <pre>
 * 1. 프로젝트명 : excavateTerm
 * 2. 패키지명(또는 디렉토리 경로) : com.excavateterm
 * 3. 파일명 : SecondActivity.java
 * 4. 작성일 : 2015. 8. 7. 오후 6:58:53
 * 5. 작성자 : 윤성
 * 6. 설명 :
 * </pre>
 */
package com.excavateterm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * 개발자 : 윤성 작성일 : 2015. 8. 7. 오후 6:58:53 설명 :
 */
public class SecondActivity extends Activity implements OnClickListener {

	private static final String TAG = "second ACTIVITY";
	private int myActivity = StaticStrings.SECOND_ACTIVITY;


	EditText editText_team_number;
	Button btn_pos_leader;
	Button btn_pos_member;
	
	Button btn_next;
	
	private String savedTeamNumer;
	private String savedPosition;

	

	public String getDiviceDisplay(){
		Display d=((WindowManager)getSystemService(this.WINDOW_SERVICE)).getDefaultDisplay();
		@SuppressWarnings("deprecation")
		int width=d.getWidth();
		@SuppressWarnings("deprecation")
		int height=d.getHeight();
		
		//HVGA : (320 x 480)
		//WVGA : (480 x 800)
		//WSVGA : (600 x 1024)
		
		//세로모드
		
		if(width<height){
			if(width<480){
				return "HVGA";
			}else if( width == 480){
				return "WVGA";
			}else{
				return "WSVGA";
			}
		}else{
			if(height<480){
				return "HVGA";
			}else if( height == 480){
				return "WVGA";
			}else{
				return "WSVGA";
			}
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// title bar 및 layout 설정
		Window win = getWindow();
		win.requestFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_second);
		win.setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title_start);

		/**
		 * setup views on the layout
		 */
		editText_team_number=(EditText)findViewById(R.id.second_edittext_team_number);
		btn_pos_leader=(Button)findViewById(R.id.second_button_position_leader);
		btn_pos_member=(Button)findViewById(R.id.second_button_position_member);
		
		
		editText_team_number.setTypeface(Typeface.createFromAsset(getAssets(),
				"yoongodic320.mp3"));
		btn_next = (Button) findViewById(R.id.second_button_next);
		btn_next.setOnClickListener(this);
		btn_pos_leader.setOnClickListener(this);
		btn_pos_member.setOnClickListener(this);

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
				
				editText_team_number.setText(savedTeamNumer);
				if(savedPosition.equals("팀장")){
					btn_pos_leader.setSelected(true);
				}else if(savedPosition.equals("팀원")){
					btn_pos_member.setSelected(true);
				}

				Intent intent = new Intent(this, TransparentActivity.class);
				StaticStrings.EXIT_FLAG = true;
				startActivityForResult(intent,
						StaticStrings.REQUEST_CODE_TRANSPARENT);

			}else {

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
			
			savedTeamNumer=editText_team_number.getText().toString();
			savedPosition=StaticStrings.USER_POSITION;
			
		} else if (StaticStrings.EXIT_FLAG == true) {
			Log.w(TAG, "정상 종료다  메롱: ");

		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.second_button_position_leader :
			btn_pos_leader.setBackgroundResource(R.drawable.second_button_leader_choice);
			btn_pos_member.setBackgroundResource(R.drawable.second_button_member_normal);
			StaticStrings.USER_POSITION=StaticStrings.POSITION_LEADER;
			break;

		case R.id.second_button_position_member:
			btn_pos_leader.setBackgroundResource(R.drawable.second_button_leader_normal);
			btn_pos_member.setBackgroundResource(R.drawable.second_button_member_choice);
			StaticStrings.USER_POSITION = StaticStrings.POSITION_MEMBER;
			break;
		
		case R.id.second_button_next:

			if (editText_team_number.getText().toString() == null) {
				/**
				 * 팀 번호가 입력되지 않았다면.
				 */
				Toast.makeText(getApplicationContext(),
						StaticStrings.TOAST_WRITE_TEAM, Toast.LENGTH_LONG)
						.show();
			} else if (StaticStrings.USER_POSITION==null) {
				/**
				 * 소속이 선택되지 않았다면
				 */
				Toast.makeText(getApplicationContext(),
						StaticStrings.TOAST_SELECT_POSITION,
						Toast.LENGTH_LONG).show();
			} else {
				/**
				 * 둘다 정상적으로 입력되었다면 버튼 배경 변환, 팀과 소속 정보 메세지 전송 후 , 다음 액티비티로 넘어간다.
				 */

				StaticStrings.USER_TEAM=editText_team_number.getText().toString();
				
				
				btn_next.setBackgroundResource(R.drawable.button_next_choice);

				sendServer();
			}
			break;
		}
	}

	public void sendServer() {

		int numOfItem = 3;
		int i = 0;

		String[][] msgStr = new String[numOfItem][2];
		msgStr[i][0] = "mode";
		msgStr[i][1] = "InsertMember";
		i++;
		msgStr[i][0] = "team_number";
		msgStr[i][1] = editText_team_number.getText().toString();
		i++;
		msgStr[i][0] = "sector";
		msgStr[i][1] = StaticStrings.USER_POSITION;

		HttpPostTask hpt = new HttpPostTask();

		hpt.execute(msgStr);

	}

	private class HttpPostTask extends AsyncTask<String[], String, String[]> {

		ArrayList<NameValuePair> postList;
		JSONObject json;
		String[] jsonName;
		String[] parsedData;

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			postList = new ArrayList<NameValuePair>();
			jsonName = new String[] { "success", "id", "pro_idx" };
			parsedData = new String[jsonName.length];

		}

		@Override
		protected String[] doInBackground(String[]... msg) {
			Log.w("Post Task", "do in background");

			for (int i = 0; i < msg.length; i++) {
				postList.add(new BasicNameValuePair(msg[i][0], msg[i][1]));
			}

			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response;
			try {
				HttpPost post = new HttpPost(StaticStrings.URL);
				Log.w("Functions", "post success");

				/** 지연시간 최대 3초 */

				HttpParams params = httpclient.getParams();
				HttpConnectionParams.setConnectionTimeout(params, 3000);
				HttpConnectionParams.setSoTimeout(params, 3000);

				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
						postList, "UTF-8");
				post.setEntity(entity);
				response = httpclient.execute(post);
				Log.w("Entity", "" + EntityUtils.getContentCharSet(entity));

				StatusLine statusLine = response.getStatusLine();
				if (statusLine.getStatusCode() == HttpStatus.SC_OK) {

					BufferedReader bufReader = new BufferedReader(
							new InputStreamReader(response.getEntity()
									.getContent(), "utf-8"));

					String line = null;
					String result = "";

					while ((line = bufReader.readLine()) != null) {
						Log.w("Post Task", "raw msg length : " + line);

						result += line;
					}

					Log.w("Post Task", "raw msg : " + result);

					json = new JSONObject(result);

					if (json != null) {

						if (json.getString("success").equals("false")) {
							parsedData[0] = json.getString("success");
							Log.w("Post Task", "parsed data [0] : "
									+ parsedData[0]);
							parsedData[1] = json.getString("message");
							Log.w("Post Task", "parsed data [1] : "
									+ parsedData[1]);

						} else {

							for (int i = 0; i < jsonName.length; i++) {
								parsedData[i] = json.getString(jsonName[i]);
								Log.w("Post Task", "parsed data [" + (i + 1)
										+ "] : " + parsedData[i]);
							}
						}
					}

				} else {
					// Closes the connection.
					response.getEntity().getContent().close();
					throw new IOException(statusLine.getReasonPhrase());
				}
			} catch (ClientProtocolException e) {
				// TODO Handle problems..
				Log.w("doInBackGround", "http fail");
			} catch (IOException e) {
				// TODO Handle problems..
				Log.w("doInBackGround", "http fail");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return parsedData;
		}

		@Override
		protected void onPostExecute(String[] result) {
			super.onPostExecute(result);
			// Do anything with response..

			if(result[0]==null){
				sendServer();
			}else{
				if (result[0].equals("false")) {
					btn_next.setBackgroundResource(R.drawable.button_next_normal);
					Toast.makeText(
							getApplicationContext(),
							StaticStrings.TOAST_BUTTON_NEXT + "\n" + "에러 : "
									+ result[1], Toast.LENGTH_LONG).show();
				} else if (result[0].equals("true")) {

					StaticStrings.USER_NUMBER = result[1];
					StaticStrings.PROJECT_NUMBER = result[2];
					Intent intent = new Intent(SecondActivity.this,
							ThirdActivity.class);
					StaticStrings.EXIT_FLAG = true;

					startActivity(intent);
					finish();
				}
			}
			
			

		}

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
}
