/**
 * <pre>
 * 1. 프로젝트명 : excavateTerm
 * 2. 패키지명(또는 디렉토리 경로) : com.excavateterm
 * 3. 파일명 : EighthActivity.java
 * 4. 작성일 : 2015. 8. 9. 오후 12:12:13
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
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 개발자 : 윤성 작성일 : 2015. 8. 9. 오후 12:12:13 설명 :
 */
public class EighthActivity extends Activity implements OnClickListener {

	private int myActivity = StaticStrings.EIGHTH_ACTIVITY;
	private final String nextStatus = StaticStrings.PRO_STATUS_DEFVOTE;

	private static final String TAG = "eighth ACTIVITY";

	CustomAdapter_Eighth mArrayAdapter;
	ListView mListView;
	MsgHandler mHandler;

	Button btn_send;

	String[] wordArr;

	private int countItem;
	private static int selectedItemPosition;
	private int voteItemPosition;

	private HttpPostTaskInsertDefWord hpt_insert;
	private HttpPostTaskForCheckStatus hpt_checkStatus;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// title bar 및 layout 설정
		Window win = getWindow();
		win.requestFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_eighth);
		win.setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title_vote);

		mArrayAdapter = new CustomAdapter_Eighth();
		mListView = (ListView) findViewById(R.id.eighth_listview);

		mListView.setAdapter(mArrayAdapter);

		mHandler = new MsgHandler(Looper.getMainLooper());

		btn_send = (Button) findViewById(R.id.eighth_button_send);
		btn_send.setOnClickListener(this);

		countItem = 0;

		Intent intent = getIntent();
		selectedItemPosition = intent.getExtras().getInt(
				StaticStrings.EXTRA_POSITION_VALUE);
		wordArr = intent.getExtras().getStringArray(
				StaticStrings.EXTRA_WORD_ARRAY);
		listAddFunction(wordArr);
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
		if (StaticStrings.EXIT_FLAG == false) {

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
		super.onPause();
		if (StaticStrings.EXIT_FLAG == false) {
			StaticStrings.CURRENT_ACTIVITY = myActivity;
		} else if (StaticStrings.EXIT_FLAG == true) {

		}
//		if(hpt_checkStatus.getStatus()==AsyncTask.Status.RUNNING){
//			hpt_checkStatus.cancel(true);
//		}
//		if(hpt_insert.getStatus()==AsyncTask.Status.RUNNING){
//			hpt_insert.cancel(true);
//		}

	}

	public void listAddFunction(String[] strArr) {
		for (int i = 0; i < strArr.length; i++) {
			ItemContent tmpItem = new ItemContent(strArr[i], mHandler);
			mArrayAdapter.add(tmpItem);
			countItem++;
		}
	}

	class MsgHandler extends Handler {

		public MsgHandler(Looper mainLooper) {
			// TODO Auto-generated constructor stub
			super(mainLooper);
		}

		@Override
		public void handleMessage(Message inputMessage) {
			// TODO Auto-generated method stub

			int flag = inputMessage.what;

			switch (flag) {

			case StaticStrings.FLAG_EIGHT_CLICK:

				int position = (Integer) inputMessage.obj;
				voteItemPosition=position;
				
				ItemContent tmpItem = (ItemContent) mArrayAdapter
						.getItem(position);

				if (tmpItem.getStatus() == StaticStrings.STATUS_PRE) {
					tmpItem.setStatus(StaticStrings.STATUS_CHOICE);
					mArrayAdapter.notifyDataSetChanged();
				}

				Toast.makeText(getApplicationContext(),
						"선택 \"" + tmpItem.getText().toString()+"\"", Toast.LENGTH_LONG)
						.show();

				break;

			case StaticStrings.HANDLER_DEF_VOTE:
				checkProjectState();
				break;

			case StaticStrings.HANDLER_NEXT_STATUS:
				Toast.makeText(getApplicationContext(), "투표가 성공했습니다.",
						Toast.LENGTH_LONG).show();
				setResult(StaticStrings.RESULT_CODE_SEVEN_VOTE);
				StaticStrings.EXIT_FLAG = true;
				finish();

			case 900 :
				voteDefWord();
			}
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.eighth_button_send:

			Log.i(TAG, "eighth button clicked");

			btn_send.setBackgroundResource(R.drawable.button_send_choice);

			btn_send.setClickable(false);
			btn_send.setEnabled(false);
			
			voteDefWord();

			break;
		}
	}

	public void voteDefWord() {
		int numOfItem = 4;
		int i = 0;
		//
		String[][] msgStr = new String[numOfItem][2];
		msgStr[i][0] = "mode";
		msgStr[i][1] = "voteDefWord";
		i++;
		msgStr[i][0] = "id";
		msgStr[i][1] = "" + StaticStrings.USER_NUMBER;
		i++;
		msgStr[i][0] = "targetWordIdx";
		msgStr[i][1] = "" + (selectedItemPosition + 1);
		i++;
		msgStr[i][0] = "defWordIdx";
		msgStr[i][1] = ""+(voteItemPosition+1);

		hpt_insert = new HttpPostTaskInsertDefWord();
		Log.i(TAG, "Vote Word");

		hpt_insert.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, msgStr);
	}

	private class HttpPostTaskInsertDefWord extends
			AsyncTask<String[], String, String[]> {

		ArrayList<NameValuePair> postList;
		JSONObject json;
		String[] parsedData;
		String[] jsonName;

		String[] resultArr;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			postList = new ArrayList<NameValuePair>();
			jsonName = new String[] { "success" };
			parsedData = new String[2];
			resultArr = new String[2];

		}

		@Override
		protected String[] doInBackground(String[]... msg) {
			Log.w("Post Task", "do in background");

			for (int i = 0; i < msg.length; i++) {
				postList.add(new BasicNameValuePair(msg[i][0], msg[i][1]));
				Log.w("Post Task", "item [" + i + "] : " + msg[i][0] + ":"
						+ msg[i][1]);
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

							resultArr[0] = parsedData[0];
							resultArr[1] = parsedData[1];
						} else {

							for (int i = 0; i < jsonName.length; i++) {
								parsedData[i] = json.getString(jsonName[i]);
								Log.w("Post Task", "parsed data [" + (i + 1)
										+ "] : " + parsedData[i]);
							}
							resultArr[0] = parsedData[0];
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

			return resultArr;

		}

		@Override
		protected void onPostExecute(String[] result) {
			super.onPostExecute(result);
			// Do anything with response..

			if(result[0]==null){
				
				Message msg=Message.obtain(mHandler, 900);
				mHandler.sendMessage(msg);
				
			}else{
				if (result[0].equals("false")) {

					btn_send.setBackgroundResource(R.drawable.button_send_normal);
					btn_send.setClickable(true);

					Toast.makeText(getApplicationContext(), "에러 : " + result[1],
							Toast.LENGTH_LONG).show();

				} else {
					Log.i(TAG, "Vote Success");

					Message msg = Message.obtain(mHandler,
							StaticStrings.HANDLER_DEF_VOTE);
					mHandler.sendMessage(msg);
				}
			}
			
		}

	}

	public void checkProjectState() {

		Log.i(TAG, "FUNC : checkProjectState");
		int numOfItem = 2;
		int i = 0;
		//
		String[][] msgStr = new String[numOfItem][2];
		msgStr[i][0] = "mode";
		msgStr[i][1] = "viewProject";
		i++;
		msgStr[i][0] = "id";
		msgStr[i][1] = "" + StaticStrings.PROJECT_NUMBER;

		hpt_checkStatus = new HttpPostTaskForCheckStatus();
		Log.i(TAG, "Check Status");

		hpt_checkStatus.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
				msgStr);
	}

	private class HttpPostTaskForCheckStatus extends
			AsyncTask<String[], String, String[]> {

		ArrayList<NameValuePair> postList;
		JSONObject json;
		String[] parsedData;
		String[] jsonName;

		String[] resultArr;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			postList = new ArrayList<NameValuePair>();
			jsonName = new String[] { "success", "words", "Mwords", "status",
					"projectStatus", "wordM" };
			parsedData = new String[jsonName.length];
			resultArr = new String[2];
		}

		@Override
		protected String[] doInBackground(String[]... msg) {
			Log.w("Post Task", "do in background");

			for (int i = 0; i < msg.length; i++) {
				postList.add(new BasicNameValuePair(msg[i][0], msg[i][1]));
				Log.w("Post Task", "item [" + i + "] : " + msg[i][0] + ":"
						+ msg[i][1]);
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

							resultArr[0] = parsedData[0];
							resultArr[1] = parsedData[1];
						} else {

							for (int i = 0; i < jsonName.length; i++) {
								parsedData[i] = json.getString(jsonName[i]);
								Log.w("Post Task", "parsed data [" + (i + 1)
										+ "] : " + parsedData[i]);
							}
							resultArr[0] = parsedData[0];
							resultArr[1] = parsedData[4];
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

			return resultArr;

		}

		@Override
		protected void onPostExecute(String[] result) {
			super.onPostExecute(result);
			// Do anything with response..

			if(result[0]==null){
				Message msg=Message.obtain(mHandler, StaticStrings.HANDLER_DEF_VOTE);
				mHandler.sendMessage(msg);
			}else{
				if (result[0].equals("false")) {
					Toast.makeText(
							getApplicationContext(),
							StaticStrings.TOAST_LIST_UPDATE + "\n에러 : " + result[1],
							Toast.LENGTH_LONG).show();

				} else {

					if (result[1].equals(nextStatus)) {
						//
						Message msg = Message.obtain(mHandler,
								StaticStrings.HANDLER_NEXT_STATUS);
						mHandler.sendMessage(msg);

						Log.w(TAG, "equal next Status : " + result[1]);

					} else {
						Log.w(TAG, "project status is not next : " + result[1]);
						if (result[1].equals(StaticStrings.PRO_STATUS_DEFREADY)) {

							Toast.makeText(getApplicationContext(),
									StaticStrings.TOAST_DEF_TOTAL_DONE,
									Toast.LENGTH_LONG).show();

							setResult(StaticStrings.RESULT_CODE_SEVEN_VOTE);
							StaticStrings.EXIT_FLAG = true;
							finish();
						} else {
							try {
								Thread.sleep(StaticStrings.WAITTIME);
								Toast.makeText(getApplicationContext(),
										StaticStrings.TOAST_WAIT_VOTE,
										Toast.LENGTH_LONG).show();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Message msg = Message.obtain(mHandler,
									StaticStrings.HANDLER_DEF_VOTE);
							mHandler.sendMessage(msg);
						}

					}

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
				alt_bld.setMessage(StaticStrings.DIALOG_EXIT_SEVEN_EIGHT_CONTENT).setCancelable(false).setPositiveButton("종료", new DialogInterface.OnClickListener() {
					
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
				
				alert.setTitle(StaticStrings.DIALOG_EXIT_SEVEN_EIGHT_TITLE);
				alert.setIcon(R.drawable.alert);
				alert.show();
			}
}
