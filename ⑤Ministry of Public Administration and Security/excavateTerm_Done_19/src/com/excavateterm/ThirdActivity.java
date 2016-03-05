/**
 * <pre>
 * 1. 프로젝트명 : excavateTerm
 * 2. 패키지명(또는 디렉토리 경로) : com.excavateterm
 * 3. 파일명 : ThirdActivity.java
 * 4. 작성일 : 2015. 8. 7. 오후 7:04:29
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
import android.widget.Toast;

/**
 * 개발자 : 윤성
 * 작성일 : 2015. 8. 7. 오후 7:04:29
 * 설명 : 
 */
public class ThirdActivity extends Activity implements OnClickListener {

	private static final String TAG="third ACTIVITY";
	private final String nextStatus=StaticStrings.PRO_STATUS_DEFREADY;

	Button btn_next;
	private int myActivity=StaticStrings.THIRD_ACTIVITY;
	
	private HttpPostTaskForCheckStatus hpt_checkStatus;
	private HttpPostTask hpt2;

	private MsgHandler mHandler;
	private int countFail;

	private int wordCount;
	private Intent globalIntent;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        //title bar 및 layout 설정
        Window win = getWindow();
        win.requestFeature(Window.FEATURE_CUSTOM_TITLE);       
        setContentView(R.layout.activity_third);
        win.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_start);
        
        btn_next=(Button)findViewById(R.id.third_button_start);
        btn_next.setOnClickListener(this);
        
        globalIntent=new Intent(this, SixthActivity.class);
        mHandler=new MsgHandler(Looper.getMainLooper());
        countFail=0;
        wordCount=0;
	}


	 @Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);
			
			Log.i(TAG, "Activity Result");

			
			switch(resultCode){
			case StaticStrings.RESULT_CODE_TRANSPARENT :
				int flag=data.getExtras().getInt(StaticStrings.EXTRA_TRANSPARENT_VALUE);
				
				if(flag==StaticStrings.KEEP_GOING_VALUE){
					StaticStrings.EXIT_FLAG=true;
				}else if(flag==StaticStrings.NEW_START_VALUE){
					Intent intent=new Intent(this, FirstActivity.class);
					StaticStrings.EXIT_FLAG=true;
					startActivity(intent);
					finish();
				}
				break;
			}
		}

	    /* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(StaticStrings.EXIT_FLAG==false){
			
			if(StaticStrings.CURRENT_ACTIVITY==myActivity){
				
				Intent intent=new Intent(this, TransparentActivity.class);
				StaticStrings.EXIT_FLAG=true;
				startActivityForResult(intent, StaticStrings.REQUEST_CODE_TRANSPARENT);
				
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
			
		}else if(StaticStrings.EXIT_FLAG==true){
			StaticStrings.CURRENT_ACTIVITY=myActivity;
			StaticStrings.EXIT_FLAG=false;
			
			btn_next.setEnabled(true);
			btn_next.setBackgroundResource(R.drawable.button_start_normal);
		}
		
	}

	

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(StaticStrings.EXIT_FLAG==false){
			StaticStrings.CURRENT_ACTIVITY=myActivity;
		}else if(StaticStrings.EXIT_FLAG==true){
			
		}
		
		if(hpt_checkStatus!=null){
			
			if(hpt_checkStatus.getStatus()!=null){
				if(hpt_checkStatus.getStatus()==AsyncTask.Status.RUNNING){
					hpt_checkStatus.cancel(true);
				}
			}
		}
		
		
	}


	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		
		case R.id.third_button_start :
			/**
			 * 버튼 이미지 변경 후, 다음 액티비티 이동
			 */
			btn_next.setBackgroundResource(R.drawable.button_start_choice);
			
			if(StaticStrings.USER_POSITION.equals(StaticStrings.POSITION_LEADER)){
				
				//테스트용
				Intent intent = new Intent(ThirdActivity.this, FourthActivity.class);
				StaticStrings.EXIT_FLAG=true;

	            startActivity(intent);
	            finish();
			}else if (StaticStrings.USER_POSITION.equals(StaticStrings.POSITION_MEMBER)){
				Message msg=Message.obtain(mHandler, StaticStrings.HANDLER_CHECK_STATUS);
				mHandler.sendMessage(msg);
			}

			break;
			
		}
	}
	

	class MsgHandler extends Handler{

		public MsgHandler(Looper mainLooper) {
			// TODO Auto-generated constructor stub
			super(mainLooper);
		}

		@Override
		public void handleMessage(Message inputMessage) {
			// TODO Auto-generated method stub
			
			int flag=inputMessage.what;
			
			switch(flag){
			
			case 11 :
				
				Log.i(TAG, "handler- fifth");
				
				break;
				
			case StaticStrings.HANDLER_NEXT_STATUS :
				Log.i(TAG, "FOR NEXT ACTIVITY");
				
				getWords();
				
			case StaticStrings.HANDLER_CHECK_STATUS :
				
				checkProjectState();
				
			case 444 :
				Toast.makeText(
						getApplicationContext(),
						StaticStrings.TOAST_WAIT_TOTAL, Toast.LENGTH_LONG).show();
				break;
			}
		}

	}
	
	public void checkProjectState(){
		//
		int numOfItem=2;
		int i=0;
		//
		String[][] msgStr=new String[numOfItem][2];
		msgStr[i][0]="mode";
		msgStr[i][1]="viewProject";
		i++;
		msgStr[i][0]="id";
		msgStr[i][1]=""+StaticStrings.PROJECT_NUMBER;
		
		hpt_checkStatus=new HttpPostTaskForCheckStatus();
		Log.i(TAG, "Check Status");

		hpt_checkStatus.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,msgStr);
	}
	
	private class HttpPostTaskForCheckStatus extends AsyncTask<String[], String, String[]> {

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onProgressUpdate(java.lang.Object[])
		 */
		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			Message msg=Message.obtain(mHandler, 444);
			mHandler.sendMessage(msg);
			
		}

		ArrayList<NameValuePair> postList;
		JSONObject json;
		String[] parsedData;
		String[] jsonName;
		
		String[] resultArr;

		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
				postList=new ArrayList<NameValuePair>();
				jsonName=new String[]{"success", "words", "Mwords", "status", "projectStatus", "wordM"};
				parsedData=new String[jsonName.length];
				resultArr=new String[2];
			
			
		}

		@Override
		protected String[] doInBackground(String[]... msg) {
			Log.w("Post Task", "do in background");
			
			while(true){
				for(int i=0; i<msg.length; i++){
					postList.add(new BasicNameValuePair(msg[i][0], msg[i][1]));
					Log.w("Post Task", "item ["+i+"] : "+msg[i][0]+ ":"+msg[i][1]);
				}
				
				 HttpClient httpclient = new DefaultHttpClient();
			        HttpResponse response;
			        try {
			        	HttpPost post=new HttpPost(StaticStrings.URL);
						Log.w("Functions", "post success");
						
						/** 지연시간 최대 3초			 */
						
						HttpParams params=httpclient.getParams();
						HttpConnectionParams.setConnectionTimeout(params, 3000);
						HttpConnectionParams.setSoTimeout(params, 3000);
						
						
						UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postList, "UTF-8");
						post.setEntity(entity);
						response=httpclient.execute(post);
						Log.w("Entity", ""+ EntityUtils.getContentCharSet(entity));
						
			            StatusLine statusLine = response.getStatusLine();
			            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
			            	
			            	BufferedReader bufReader=new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
			    			
			    			String line = null;
			    			String result="";
			    			
			    			while((line=bufReader.readLine())!=null){
			    				result += line;
			    			}
			    			
			    			Log.w("Post Task", "raw msg : "+result);
			    			
			    			json=new JSONObject(result);
			    			
			    			if(json!=null){
			    				

								if (json.getString("success").equals("false")) {
									parsedData[0] = json.getString("success");
									Log.w("Post Task", "parsed data [0] : " +parsedData[0]);
									parsedData[1] = json.getString("message");
									Log.w("Post Task", "parsed data [1] : " +parsedData[1]);

									resultArr[0]=parsedData[0];
									resultArr[1]=parsedData[1];
								} else {
			    				
			    				
			    				for(int i=0; i<jsonName.length; i++){
				    				parsedData[i]=json.getString(jsonName[i]);
				    				Log.w("Post Task", "parsed data ["+(i+1)+"] : "+parsedData[i]);
				    			}
			    				resultArr[0]=parsedData[0];
								resultArr[1]=parsedData[4];
								}
			    				
		    				}
			    			
			    					
			    				
			    			
			    			
			    			
			            } else{
			                //Closes the connection.
			                response.getEntity().getContent().close();
			                throw new IOException(statusLine.getReasonPhrase());
			            }
			        } catch (ClientProtocolException e) {
			            //TODO Handle problems..
//			        	countFail++;
//			        	Log.w("doInBackGround", "http Client protocol fail : "+countFail);
//			        	Message failMsg=Message.obtain(mHandler, StaticStrings.HANDLER_POST_FAIL);
//			        	mHandler.sendMessage(failMsg);
//			        	
			        	
			        } catch (IOException e) {
			            //TODO Handle problems..
//			        	
//			        	countFail++;
//			        	Log.w("doInBackGround", "http io exception fail : "+countFail);
//			        	Message failMsg=Message.obtain(mHandler, StaticStrings.HANDLER_POST_FAIL);
//			        	mHandler.sendMessage(failMsg);
			        } catch (JSONException e) {
						// TODO Auto-generated catch block
//			        	countFail++;
//			        	Log.w("doInBackGround", "json Exception fail : "+countFail);
//			        	Message failMsg=Message.obtain(mHandler, StaticStrings.HANDLER_POST_FAIL);
//			        	mHandler.sendMessage(failMsg);
					}
			        	
			        if(resultArr[1]==null){
			        	onProgressUpdate(parsedData);
			        }else if(resultArr[1].equals(nextStatus)
							|| resultArr[1].equals(StaticStrings.PRO_STATUS_DEFSET)
							|| resultArr[1].equals(StaticStrings.PRO_STATUS_DEFVOTEREADY)
							|| resultArr[1].equals(StaticStrings.PRO_STATUS_DEFVOTE) ){
			        	
			        	break;
			        }else{
			        	onProgressUpdate(parsedData);
			        }
			        
			        try {
						Thread.sleep(StaticStrings.WAITTIME);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			
			
		        return resultArr;
		        
		        
		}

		@Override
		protected void onPostExecute(String[] result) {
			super.onPostExecute(result);
			// Do anything with response..
			
			if(result[0]==null){
				
				Message msg=Message.obtain(mHandler, StaticStrings.HANDLER_CHECK_STATUS);
				mHandler.sendMessage(msg);
			}else{
				if(result[0].equals("false")){
					Toast.makeText(
							getApplicationContext(),
							StaticStrings.TOAST_LIST_UPDATE+ "\n에러 : "
									+ result[1], Toast.LENGTH_LONG).show();
					
				}else{
				
					if (result[1].equals(nextStatus)
							|| result[1].equals(StaticStrings.PRO_STATUS_DEFSET)
							|| result[1].equals(StaticStrings.PRO_STATUS_DEFVOTEREADY)
							|| result[1].equals(StaticStrings.PRO_STATUS_DEFVOTE)) {
						//
					Log.w(TAG, "equal next Status : "+nextStatus);
					
					Message msg=Message.obtain(mHandler, StaticStrings.HANDLER_NEXT_STATUS);
					mHandler.sendMessage(msg);
					
					
				}else{
					Log.w(TAG, "Not equal next Status : "+result[1]);
						 
						btn_next.setBackgroundResource(R.drawable.button_next_normal);
						Toast.makeText(
								getApplicationContext(),
								StaticStrings.TOAST_WAIT_TOTAL_RESTART, Toast.LENGTH_LONG).show();
//						
//						Message msg=Message.obtain(mHandler, StaticStrings.HANDLER_CHECK_STATUS);
//						mHandler.sendMessage(msg);
					
					
				}
			}
			
			
			
			}
		}

	}

public void getWords(){
		
		int numOfItem=2;
		int i=0;
		//
		String[][] msgStr=new String[numOfItem][2];
		msgStr[i][0]="mode";
		msgStr[i][1]="viewProject";
		i++;
		msgStr[i][0]="id";
		msgStr[i][1]=""+StaticStrings.PROJECT_NUMBER;
		
		hpt2=new HttpPostTask();
		
		hpt2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,msgStr);
	}
	
	private class HttpPostTask extends AsyncTask<String[], String, String[]> {

		ArrayList<NameValuePair> postList;
		JSONObject json;
		JSONArray jsonArr;
		JSONArray jWordArr;
		String[] parsedData;
		String[] jsonName;
		
		String[] wordArr;
		JSONObject[] originArr;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
				postList=new ArrayList<NameValuePair>();
				jsonName=new String[]{"success", "words", "Mwords", "status","projectStatus", "wordM"};
				parsedData=new String[jsonName.length];
				wordCount=0;
			
		}

		@Override
		protected String[] doInBackground(String[]... msg) {
			Log.w("Post Task", "do in background");
			
			for(int i=0; i<msg.length; i++){
				postList.add(new BasicNameValuePair(msg[i][0], msg[i][1]));
				Log.w("Post Task", "item ["+i+"] : "+msg[i][0]+ ":"+msg[i][1]);
			}
			
			 HttpClient httpclient = new DefaultHttpClient();
		        HttpResponse response;
		        try {
		        	HttpPost post=new HttpPost(StaticStrings.URL);
					Log.w("Functions", "post success");
					
					/** 지연시간 최대 3초			 */
					
					HttpParams params=httpclient.getParams();
					HttpConnectionParams.setConnectionTimeout(params, 3000);
					HttpConnectionParams.setSoTimeout(params, 3000);
					
					
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postList, "UTF-8");
					post.setEntity(entity);
					response=httpclient.execute(post);
					Log.w("Entity", ""+ EntityUtils.getContentCharSet(entity));
					
		            StatusLine statusLine = response.getStatusLine();
		            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
		            	
		            	BufferedReader bufReader=new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
		    			
		    			String line = null;
		    			String result="";
		    			
		    			while((line=bufReader.readLine())!=null){

		    				result += line;
		    			}
		    			
		    			Log.w("Post Task", "raw msg : "+result);
		    			
		    			json=new JSONObject(result);
		    			
		    			if(json!=null){

							if (json.getString("success").equals("false")) {
								parsedData[0] = json.getString("success");
								Log.w("Post Task", "parsed data [0] : " +parsedData[0]);
								parsedData[1] = json.getString("message");
								Log.w("Post Task", "parsed data [1] : " +parsedData[1]);
//
							} else {
		    				for(int i=0; i<jsonName.length; i++){
			    				parsedData[i]=json.getString(jsonName[i]);
			    				Log.w("Post Task", "parsed data ["+(i)+"] : "+parsedData[i]);
			    			}
		    					
		    					JSONArray tmpJO=new JSONArray(parsedData[2]);
		    					
		    					if(tmpJO.length()<10){
		    						wordArr=new String[tmpJO.length()];
		    						
		    						for(int i=0; i<tmpJO.length();i++){
		    							wordArr[i]=tmpJO.getString(i);
				    					Log.i(TAG, "wordArr["+i+"] : "+wordArr[i]);

		    						}
		    						
		    					}else {
		    						wordArr=new String[10];
		    						for(int i=0; i<10;i++){
		    							wordArr[i]=tmpJO.getString(i);
				    					Log.i(TAG, "wordArr["+i+"] : "+wordArr[i]);

		    						}
		    					}
		    					
		    				
//		    					JSONObject tmpJO=new JSONObject(parsedData[1]);
//		    					Iterator<String> keys=tmpJO.keys();
//		    					wordCount=0;
//		    					
//		    					while(keys.hasNext()){
//		    						String key=(String)keys.next();
//		    						String value=tmpJO.getString(key);
//			    					Log.i(TAG, "JSON Ob key["+key+"] : "+value);
//			    					wordCount++;
//			    					Log.i(TAG, "wordCount"+wordCount);
//
//		    					}
//		    					
//		    					keys=tmpJO.keys();
//		    					
//		    					if(wordCount<10){
//		    						wordArr=new String[wordCount];
//		    						for(int i=0; i<wordArr.length ; i++){
//			    						wordArr[i]=(String)keys.next();
//				    					Log.i(TAG, "wordArr["+i+"] : "+wordArr[i]);
//			    					}
//		    						
//		    					}else{
//		    						wordArr=new String[10];
//		    						for(int i=0;i<wordArr.length;i++){
//			    						wordArr[i]=(String)keys.next();
//				    					Log.i(TAG, "wordArr["+i+"] : "+wordArr[i]);
//			    					}
//		    					}
//		    					
		    					
			    			
							}
	    				}
		    			
		    			
		    			
		    			
		            } else{
		                //Closes the connection.
		                response.getEntity().getContent().close();
		                throw new IOException(statusLine.getReasonPhrase());
		            }
		        } catch (ClientProtocolException e) {
		            //TODO Handle problems..
		        	Log.w("doInBackGround", "http fail");
		        } catch (IOException e) {
		            //TODO Handle problems..
		        	Log.w("doInBackGround", "http fail");
		        } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
		        	Log.i(TAG, "return wordArr");
		        	return wordArr;
		        
		        
		}

		@Override
		protected void onPostExecute(String[] result) {
			super.onPostExecute(result);
			// Do anything with response..
			
			if(result[0]==null){
				Message msg=Message.obtain(mHandler, StaticStrings.HANDLER_NEXT_STATUS);
				mHandler.sendMessage(msg);
			}else{
				for(int i=0; i<result.length; i++){
					Log.i(TAG, "array content ["+i+"] : "+result[i]);
				}
				globalIntent.putExtra(StaticStrings.EXTRA_WORD_ARRAY, result);
				StaticStrings.EXIT_FLAG=true;
				startActivity(globalIntent);
				finish();
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
