/**
 * <pre>
 * 1. 프로젝트명 : excavateTerm
 * 2. 패키지명(또는 디렉토리 경로) : com.excavateterm
 * 3. 파일명 : SeventhActivity.java
 * 4. 작성일 : 2015. 8. 9. 오전 11:47:56
 * 5. 작성자 : 윤성
 * 6. 설명 :
 * </pre>
 */
package com.excavateterm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.EditText;
import android.widget.Toast;

/**
 * 개발자 : 윤성 작성일 : 2015. 8. 9. 오전 11:47:56 설명 :
 */
public class SeventhActivity extends Activity implements OnClickListener {

	private int myActivity = StaticStrings.SEVENTH_ACTIVITY;
	private final String nextStatus = StaticStrings.PRO_STATUS_DEFVOTEREADY;

	private static final String TAG = "seventh ACTIVITY";

	Button btn_send;

	EditText editText_firtst;

	private String saved_first;

	private static int comebackFlag;

	private static int position_from;
	private MsgHandler mHandler;
	private int wordCount;

	private HttpPostTaskViewDefWords hpt_view_defWords;
	private HttpPostTaskForCheckStatus hpt_checkStatus;
	private HttpPostTaskInsertDefWord hpt_insert;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//
		// title bar 및 layout 설정
		Window win = getWindow();
		win.requestFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_seventh);
		win.setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title_vote);

		btn_send = (Button) findViewById(R.id.seventh_button_send);
		btn_send.setOnClickListener(this);

		editText_firtst = (EditText) findViewById(R.id.seventh_edittext_first);

		editText_firtst.setTypeface(Typeface.createFromAsset(getAssets(),
				"yoongodic320.mp3"));

		mHandler = new MsgHandler(Looper.getMainLooper());

		Intent intent = getIntent();
		position_from = intent.getExtras().getInt(
				StaticStrings.EXTRA_POSITION_VALUE);

		wordCount = 0;
		comebackFlag = 0;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		Intent intent;

		switch (resultCode) {
		case StaticStrings.RESULT_CODE_SEVEN_VOTE:

			intent = new Intent();
			intent.putExtra(StaticStrings.EXTRA_POSITION_VALUE, position_from);
			setResult(StaticStrings.RESULT_CODE_SIX_VOTE, intent);
			StaticStrings.EXIT_FLAG = true;

			finish();

			break;

		case StaticStrings.RESULT_CODE_TRANSPARENT:
			int flag = data.getExtras().getInt(
					StaticStrings.EXTRA_TRANSPARENT_VALUE);

			if (flag == StaticStrings.KEEP_GOING_VALUE) {
				StaticStrings.EXIT_FLAG = true;
			} else if (flag == StaticStrings.NEW_START_VALUE) {
				intent = new Intent(this, FirstActivity.class);
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

				editText_firtst.setText(saved_first);

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
			btn_send.setBackgroundResource(R.drawable.button_send_normal);
			btn_send.setEnabled(true);
			
		}

		if (StaticStrings.USER_POSITION == StaticStrings.POSITION_MEMBER) {
			editText_firtst.setEnabled(false);

			if (comebackFlag == 0) {
				Log.i(TAG, "send button clicked");

				btn_send.setBackgroundResource(R.drawable.button_send_choice);

				insertDefWord();
			}else{
				btn_send.setBackgroundResource(R.drawable.button_send_choice);
			}
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

			saved_first = editText_firtst.getText().toString();

			StaticStrings.CURRENT_ACTIVITY = myActivity;
		} else if (StaticStrings.EXIT_FLAG == true) {

		}

		// if(hpt_insert.getStatus()==AsyncTask.Status.RUNNING){
		// hpt_insert.cancel(true);
		// }
		// if(hpt_checkStatus.getStatus()==AsyncTask.Status.RUNNING){
		// hpt_checkStatus.cancel(true);
		// }
		// if(hpt_view_defWords.getStatus()==AsyncTask.Status.RUNNING){
		// hpt_view_defWords.cancel(true);
		// }
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.seventh_button_send:
			/**
			 * 버튼 이미지 변경 후, 다음 액티비티 이동
			 */
			Log.i(TAG, "send button clicked");

			btn_send.setBackgroundResource(R.drawable.button_send_choice);

			editText_firtst.setEnabled(false);
			btn_send.setEnabled(false);
			
			insertDefWord();
			// Intent intent = new Intent(SeventhActivity.this,
			// EighthActivity.class);
			// intent.putExtra(StaticStrings.EXTRA_POSITION_VALUE,
			// position_from);
			// startActivityForResult(intent,
			// StaticStrings.REQUEST_CODE_SEVEN_VOTE);

			//
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

			case 77:
				try {
					Thread.sleep(StaticStrings.WAITTIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				insertDefWord();
				break;

			case StaticStrings.HANDLER_DEF_WRITE_TRUE:

				Log.i(TAG, "DEF WRITE SUCCESS");

				checkProjectState();

				break;

			case StaticStrings.HANDLER_NEXT_STATUS:

				Log.i(TAG, "YES NEXT STATE");

				viewDefWords();
				break;
			}
		}

	}

	public void viewDefWords() {

		int numOfItem = 3;
		int i = 0;
		//
		String[][] msgStr = new String[numOfItem][2];
		msgStr[i][0] = "mode";
		msgStr[i][1] = "viewDefWord";
		i++;
		msgStr[i][0] = "id";
		msgStr[i][1] = "" + StaticStrings.PROJECT_NUMBER;
		i++;
		msgStr[i][0] = "targetWordIdx";
		msgStr[i][1] = "" + (position_from + 1);

		hpt_view_defWords = new HttpPostTaskViewDefWords();
		Log.i(TAG, "View Def Words");

		hpt_view_defWords.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
				msgStr);
	}

	private class HttpPostTaskViewDefWords extends
			AsyncTask<String[], String, String[]> {

		ArrayList<NameValuePair> postList;
		JSONObject json;
		String[] parsedData;
		String[] jsonName;

		String[] resultArr;
		String[] wordArr;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			postList = new ArrayList<NameValuePair>();
			jsonName = new String[] { "success", "targetword", "words",
					"Mwords", "selected", "projectStatus" };
			parsedData = new String[jsonName.length];
			resultArr = new String[2];
			wordCount = 0;

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
								Log.w("Post Task", "parsed data [" + (i)
										+ "] : " + parsedData[i]);
							}

							resultArr[0] = parsedData[0];

							JSONArray tmpJO = new JSONArray(parsedData[3]);

							if (tmpJO.length() < 3) {
								wordArr = new String[tmpJO.length()];

								for (int i = 0; i < tmpJO.length(); i++) {
									wordArr[i] = tmpJO.getString(i);
									Log.i(TAG, "wordArr[" + i + "] : "
											+ wordArr[i]);

								}

							} else {
								wordArr = new String[3];
								for (int i = 0; i < 3; i++) {
									wordArr[i] = tmpJO.getString(i);
									Log.i(TAG, "wordArr[" + i + "] : "
											+ wordArr[i]);

								}
							}

							// JSONObject tmpJO=new JSONObject(parsedData[2]);
							// Iterator<String> keys=tmpJO.keys();
							// wordCount=0;
							// while(keys.hasNext()){
							// String key=(String)keys.next();
							// String value=tmpJO.getString(key);
							// Log.i(TAG, "JSON Ob key["+key+"] : "+value);
							// wordCount++;
							//
							// }
							//
							// keys=tmpJO.keys();
							//
							// if(wordCount<3){
							// wordArr=new String[wordCount];
							// for(int i=0; i<wordArr.length; i++){
							// wordArr[i]=(String)keys.next();
							// Log.i(TAG, "wordArr["+i+"] : "+wordArr[i]);
							// }
							//
							// }else{
							// wordArr=new String[3];
							// for(int i=0;i<wordArr.length;i++){
							// wordArr[i]=(String)keys.next();
							// Log.i(TAG, "wordArr["+i+"] : "+wordArr[i]);
							// }
							// }

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

			if (result[0] == null) {
				Message msg = Message.obtain(mHandler,
						StaticStrings.HANDLER_NEXT_STATUS);
				mHandler.sendMessage(msg);
			} else {
				if (result[0].equals("false")) {

					Toast.makeText(getApplicationContext(), result[1],
							Toast.LENGTH_LONG).show();

				} else {

					if (wordArr == null) {
						Toast.makeText(getApplicationContext(),
								"투표할 단어가 없습니다. 현재 진행 상황을 확인해주세요.",
								Toast.LENGTH_LONG).show();
						btn_send.setBackgroundResource(R.drawable.button_send_normal);
						btn_send.setClickable(true);

					} else {
						Intent intent = new Intent(SeventhActivity.this,
								EighthActivity.class);
						intent.putExtra(StaticStrings.EXTRA_POSITION_VALUE,
								position_from);
						intent.putExtra(StaticStrings.EXTRA_WORD_ARRAY, wordArr);
						StaticStrings.EXIT_FLAG = true;
						comebackFlag = 1;
						startActivityForResult(intent,
								StaticStrings.REQUEST_CODE_SIX_VOTE);
					}
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
			jsonName = new String[] { "success", "words", "Mwords",  "status",
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

			if (result[0] == null) {
				Message msg = Message.obtain(mHandler,
						StaticStrings.HANDLER_DEF_WRITE_TRUE);
				mHandler.sendMessage(msg);
			} else {
				if (result[0].equals("false")) {
					Toast.makeText(
							getApplicationContext(),
							StaticStrings.TOAST_LIST_UPDATE + "\n에러 : "
									+ result[1], Toast.LENGTH_LONG).show();

				} else {

					if (result[1].equals(nextStatus)
							|| result[1]
									.equals(StaticStrings.PRO_STATUS_DEFVOTE)) {
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
							Intent tIntent = new Intent();
							tIntent.putExtra(
									StaticStrings.EXTRA_POSITION_VALUE,
									position_from);
							setResult(StaticStrings.RESULT_CODE_SIX_VOTE,
									tIntent);
							StaticStrings.EXIT_FLAG = true;

							finish();
						} else {
							try {
								Thread.sleep(StaticStrings.WAITTIME * 2);
								Toast.makeText(getApplicationContext(),
										StaticStrings.TOAST_WAIT_TOTAL,
										Toast.LENGTH_LONG).show();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Message msg = Message.obtain(mHandler,
									StaticStrings.HANDLER_DEF_WRITE_TRUE);
							mHandler.sendMessage(msg);
						}

					}

				}
			}

		}

	}

	public void insertDefWord() {

		int numOfItem = 4;
		int i = 0;
		//
		String[][] msgStr = new String[numOfItem][2];
		msgStr[i][0] = "mode";
		msgStr[i][1] = "InsertDefWord";
		i++;
		msgStr[i][0] = "id";
		msgStr[i][1] = "" + StaticStrings.USER_NUMBER;
		i++;
		msgStr[i][0] = "targetWordIdx";
		msgStr[i][1] = "" + (position_from + 1);
		i++;
		msgStr[i][0] = "defWords";
		String tmpStr=""+editText_firtst.getText().toString();
		Log.i(TAG, "Insert Def Words : "+tmpStr +"//"+editText_firtst.getText().toString());
		msgStr[i][1] = tmpStr;

		hpt_insert = new HttpPostTaskInsertDefWord();
		Log.i(TAG, "Insert Word");

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

			if (result[0] == null) {
				Message msg = Message.obtain(mHandler, 77);
				mHandler.sendMessage(msg);
			} else {
				if (result[0].equals("false")) {

					Toast.makeText(getApplicationContext(), result[1],
							Toast.LENGTH_LONG).show();
					Message msg = Message.obtain(mHandler, 77);
					mHandler.sendMessage(msg);

				} else {
					Message msg = Message.obtain(mHandler,
							StaticStrings.HANDLER_DEF_WRITE_TRUE);
					mHandler.sendMessage(msg);

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
		// return super.onKeyDown(keyCode, event);

	}

	private void dialogSimple() {
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		alt_bld.setMessage(StaticStrings.DIALOG_EXIT_SEVEN_EIGHT_CONTENT)
				.setCancelable(false)
				.setPositiveButton("종료", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						android.os.Process.killProcess(android.os.Process
								.myPid());
					}
				})
				.setNegativeButton("취소", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});

		AlertDialog alert = alt_bld.create();

		alert.setTitle(StaticStrings.DIALOG_EXIT_SEVEN_EIGHT_TITLE);
		alert.setIcon(R.drawable.alert);
		alert.show();
	}
}