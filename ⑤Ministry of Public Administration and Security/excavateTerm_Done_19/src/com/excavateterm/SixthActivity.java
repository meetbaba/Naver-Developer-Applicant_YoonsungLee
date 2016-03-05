/**
 * <pre>
 * 1. 프로젝트명 : excavateTerm
 * 2. 패키지명(또는 디렉토리 경로) : com.excavateterm
 * 3. 파일명 : SixthActivity.java
 * 4. 작성일 : 2015. 8. 9. 오전 11:15:38
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 개발자 : 윤성
 * 작성일 : 2015. 8. 9. 오전 11:15:38
 * 설명 : 
 */
public class SixthActivity extends Activity{

	private int myActivity=StaticStrings.SIXTH_ACTIVITY;

		private static final String TAG="sixth ACTIVITY";
		private final String nextStatus=StaticStrings.PRO_STATUS_DEFSET;

		private static Boolean[] updatedWords;

	CustomAdapter_Sixth mArrayAdapter;
	ListView mListView;
	MsgHandler mHandler;
	
	String[] wordArr;
	String[] keyArr;
	private int countItem;
	
	private static Boolean okNext;
	Intent globalIntent;

	private int selectedItemPosition;
	
	
	HttpPostTaskInsertDefWord hpt_insert;
	HttpPostTaskForCheckStatus hpt_checkStatus;
	HttpPostTaskForWordStatusUpdate hpt_WordStatusUpdate;
	
	private void setOkNext(Boolean value){
		this.okNext=value;
	}
	private Boolean getOkNext(){
		return this.okNext;
	}
	
	private void defaultUpdatedWordsArray(int length){
		
		updatedWords=new Boolean[length];
		for(int i=0; i<length;i++){
			updatedWords[i]=false;
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// title bar 및 layout 설정
		Window win = getWindow();
		win.requestFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_sixth);
		win.setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title_vote);

		
		mArrayAdapter = new CustomAdapter_Sixth();
		mListView = (ListView) findViewById(R.id.sixth_listview);

		mListView.setAdapter(mArrayAdapter);
		
		mHandler=new MsgHandler(Looper.getMainLooper());
		
		countItem=0;
		setOkNext(false);
		
		Intent intent=getIntent();
		wordArr=intent.getExtras().getStringArray(StaticStrings.EXTRA_WORD_ARRAY);
		Log.i(TAG, "Array : "+ wordArr[0]);
		listAddFunction(wordArr);
		defaultUpdatedWordsArray(wordArr.length);
        globalIntent=new Intent(this, SeventhActivity.class);


        mListView.setItemsCanFocus(true);
	}

	
	public void listAddFunction(String[] strArr){
		for(int i=0; i<strArr.length; i++){
			ItemContent tmpItem=new ItemContent(strArr[i], mHandler);
			mArrayAdapter.add(tmpItem);
			countItem++;
		}
	}
	
	public Boolean checkCompleteAll(){

		for(int i=0; i<mArrayAdapter.getCount(); i++){
			ItemContent tmpItem=(ItemContent)mArrayAdapter.getItem(i);
			if(tmpItem.getStatus()!=StaticStrings.STATUS_COMPLETE){
				return false;
			}
		}
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		Log.i(TAG, "Activity Result");

		
		switch(resultCode){
		case StaticStrings.RESULT_CODE_SIX_VOTE :
			int position = data.getExtras().getInt(StaticStrings.EXTRA_POSITION_VALUE);
			ItemContent tmpItem=(ItemContent)mArrayAdapter.getItem(position);
			
			Log.i(TAG, "Received position : "+position);

			if(tmpItem.getStatus()==StaticStrings.STATUS_CHOICE){
				tmpItem.setStatus(StaticStrings.STATUS_COMPLETE);
				mArrayAdapter.notifyDataSetChanged();
			}
			break;
			
		case StaticStrings.RESULT_CODE_TRANSPARENT :
			int flag=data.getExtras().getInt(StaticStrings.EXTRA_TRANSPARENT_VALUE);
			//
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
		
		refreshAdapter();
	}



	public void refreshAdapter(){
		
		Log.i(TAG, "refresh Adapter");
//		CustomAdapter_Sixth tmpAdaper=new CustomAdapter_Sixth();
//		
//		for(int i=0; i<mArrayAdapter.getCount(); i++){
//			ItemContent tmpItem=(ItemContent)mArrayAdapter.getItem(i);
//			ItemContent insertItem=new ItemContent(tmpItem.getText(), tmpItem.getHandler(), tmpItem.getStatus());
//			
//			tmpAdaper.add(insertItem);
//			ItemContent tmpItem2=(ItemContent)tmpAdaper.getItem(i);
//			Log.i(TAG, "Added Item : "+tmpItem2.getText());
//		}
//		
//		int tmpCount=mArrayAdapter.getCount();
//		for(int i=0; i<tmpCount; i++){
//			mArrayAdapter.remove(0);
//			Log.i(TAG, "remained item num : "+ mArrayAdapter.getCount());
//		}
//		mArrayAdapter.notifyDataSetChanged();
//		
//		for(int i=0; i<tmpAdaper.getCount(); i++){
//			mArrayAdapter.add((ItemContent)tmpAdaper.getItem(i));
//		}
//		mArrayAdapter.notifyDataSetChanged();
		
	}

	    /* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		refreshAdapter();
		
		//getWordsStatus();
		
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
			
			for(int i=0; i<countItem; i++){
				ItemContent tmpItem=(ItemContent)mArrayAdapter.getItem(i);
				if(tmpItem.getStatus()==StaticStrings.STATUS_CHOICE){
					tmpItem.setStatus(StaticStrings.STATUS_PRE);
					mArrayAdapter.notifyDataSetChanged();
				}
			}
			
			
		}
		
		if(checkCompleteAll()){
			dialogComplete();
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
		if(hpt_insert!=null){
			if(hpt_insert.getStatus()!=null){
				if(hpt_insert.getStatus()==AsyncTask.Status.RUNNING){
					hpt_insert.cancel(true);
					Log.e(TAG, "Cancel hp_insert");
				}
			}
		}
		
		if(hpt_checkStatus!=null){
			if(hpt_checkStatus.getStatus()!=null){
				if(hpt_checkStatus.getStatus()==AsyncTask.Status.RUNNING){
					hpt_checkStatus.cancel(true);
					Log.e(TAG, "Cancel hp_insert");

				}
			}
		}
		
		
//		if(hpt_WordStatusUpdate.getStatus()!=null){
//			if(hpt_WordStatusUpdate.getStatus()==AsyncTask.Status.RUNNING){
//				hpt_WordStatusUpdate.cancel(true);
//				Log.e(TAG, "Cancel hp_insert");
//
//			}
//		}
		
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
			
			case StaticStrings.FLAG_SIX_CLICK :
				
				int position=(Integer) inputMessage.obj;
				ItemContent tmpItem=(ItemContent)mArrayAdapter.getItem(position);
				
				selectedItemPosition=position;
				Log.i(TAG, "get msg FLAG SIX (position) : "+selectedItemPosition);
				
				if(tmpItem.getStatus()==StaticStrings.STATUS_PRE){
					tmpItem.setStatus(StaticStrings.STATUS_CHOICE);
					mArrayAdapter.notifyDataSetChanged();
					
					checkProjectState();
					
//					Intent intent = new Intent(SixthActivity.this, SeventhActivity.class);
//					intent.putExtra(StaticStrings.EXTRA_POSITION_VALUE, position);
//					startActivityForResult(intent, StaticStrings.REQUEST_CODE_SIX_VOTE);
				}
				break;
				
			case StaticStrings.HANDLER_NEXT_STATUS : 
				checkNextFlag();
				break;
				
			case StaticStrings.HANDLER_UPDATE_WORDS_STATUS :
				getWordsStatus();
				break;
			}
		}

	}
	
	public void checkNextFlag(){
		
		if(getOkNext()==true){
			insertDefWord();
		}else{
			ItemContent tmpItem=(ItemContent)mArrayAdapter.getItem(selectedItemPosition);
			tmpItem.setStatus(StaticStrings.STATUS_PRE);
			mArrayAdapter.notifyDataSetChanged();
			
			Toast.makeText(getApplicationContext(), StaticStrings.TOAST_WAIT_DEF_TOTAL_START, Toast.LENGTH_LONG).show();
		}
	}
	

	
	public void insertDefWord(){

		int numOfItem=4;
		int i=0;
		//
		String[][] msgStr=new String[numOfItem][2];
		msgStr[i][0]="mode";
		msgStr[i][1]="InsertDefWord";
		i++;
		msgStr[i][0]="id";
		msgStr[i][1]=""+StaticStrings.USER_NUMBER;
		i++;
		msgStr[i][0]="targetWordIdx";
		msgStr[i][1]=""+(selectedItemPosition+1);
		i++;
		msgStr[i][0]="defWords";
		msgStr[i][1]=" ";
		
		
		hpt_insert=new HttpPostTaskInsertDefWord();
		Log.i(TAG, "Insert Word");

		hpt_insert.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,msgStr);
	}
	
	private class HttpPostTaskInsertDefWord extends AsyncTask<String[], String, String[]> {

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
				jsonName=new String[]{"success"};
				parsedData=new String[2];
				resultArr=new String[2];
			
			
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

								resultArr[0]=parsedData[0];
								resultArr[1]=parsedData[1];
							} else {
		    				
		    				
		    				for(int i=0; i<jsonName.length; i++){
			    				parsedData[i]=json.getString(jsonName[i]);
			    				Log.w("Post Task", "parsed data ["+(i+1)+"] : "+parsedData[i]);
			    			}
		    				resultArr[0]=parsedData[0];
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
		        	
		        return resultArr;
		        
		        
		}

		@Override
		protected void onPostExecute(String[] result) {
			super.onPostExecute(result);
			// Do anything with response..
			
			if(result[0]==null){
				Message msg=Message.obtain(mHandler, StaticStrings.HANDLER_NEXT_STATUS);
				mHandler.sendMessage(msg);
			}else{
				if(result[0].equals("false")){
					ItemContent tmpItem=(ItemContent)mArrayAdapter.getItem(selectedItemPosition);
					tmpItem.setStatus(StaticStrings.STATUS_PRE);
					mArrayAdapter.notifyDataSetChanged();
					
					Toast.makeText(getApplicationContext(), "아직 \""+tmpItem.getText()+"\" 용어 집계가 시작되지 않았습니다.", Toast.LENGTH_LONG).show();

				}else{
					Log.i(TAG, "Next Activity");
					Intent intent = new Intent(SixthActivity.this, SeventhActivity.class);
					intent.putExtra(StaticStrings.EXTRA_POSITION_VALUE, selectedItemPosition);
					StaticStrings.EXIT_FLAG=true;
					startActivityForResult(intent, StaticStrings.REQUEST_CODE_SIX_VOTE);
				
				}
			}
			
			
		}

	}
	

	public void checkProjectState(){
		
		Log.i(TAG, "FUNC : checkProjectState");
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
			
			for(int i=0; i<msg.length; i++){
				postList.add(new BasicNameValuePair(msg[i][0], msg[i][1]));
//				Log.w("Post Task", "item ["+i+"] : "+msg[i][0]+ ":"+msg[i][1]);
			}
			
			 HttpClient httpclient = new DefaultHttpClient();
		        HttpResponse response;
		        try {
		        	HttpPost post=new HttpPost(StaticStrings.URL);
//					Log.w("Functions", "post success");
					
					/** 지연시간 최대 3초			 */
					
					HttpParams params=httpclient.getParams();
					HttpConnectionParams.setConnectionTimeout(params, 3000);
					HttpConnectionParams.setSoTimeout(params, 3000);
					
					
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postList, "UTF-8");
					post.setEntity(entity);
					response=httpclient.execute(post);
//					Log.w("Entity", ""+ EntityUtils.getContentCharSet(entity));
					
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
//			    				Log.w("Post Task", "parsed data ["+(i+1)+"] : "+parsedData[i]);
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
		        	Log.w("doInBackGround", "http fail");
		        } catch (IOException e) {
		            //TODO Handle problems..
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
				
				ItemContent tmpItem=(ItemContent)mArrayAdapter.getItem(selectedItemPosition);
				tmpItem.setStatus(StaticStrings.STATUS_PRE);
				mArrayAdapter.notifyDataSetChanged();
				
				Message msg=Message.obtain(mHandler, StaticStrings.FLAG_SIX_CLICK, selectedItemPosition);
				mHandler.sendMessage(msg);
			}else{
				if(result[0].equals("false")){
					Toast.makeText(
							getApplicationContext(),
							StaticStrings.TOAST_LIST_UPDATE+ "\n에러 : "
									+ result[1], Toast.LENGTH_LONG).show();
					
				}else{
				
				if(result[1].equals(nextStatus) || result[1].equals(StaticStrings.PRO_STATUS_DEFVOTEREADY) || result[1].equals(StaticStrings.PRO_STATUS_DEFVOTE)){
					//
					setOkNext(true);
					
					Message msg=Message.obtain(mHandler, StaticStrings.HANDLER_NEXT_STATUS);
					mHandler.sendMessage(msg);
					
					Log.w(TAG, "equal next Status : "+nextStatus);
					Toast.makeText(getApplicationContext(), StaticStrings.TOAST_DEF_TOTAL_START, Toast.LENGTH_LONG).show();
					
				}else{
					ItemContent tmpItem=(ItemContent)mArrayAdapter.getItem(selectedItemPosition);
					tmpItem.setStatus(StaticStrings.STATUS_PRE);
					mArrayAdapter.notifyDataSetChanged();
					Toast.makeText(getApplicationContext(), "아직 \""+tmpItem.getText()+"\" 용어 집계가 시작되지 않았습니다.", Toast.LENGTH_LONG).show();

						
				}
			}
			
			
			
			}
		}

	}

	

	
	public void getWordsStatus(){
		
		int numOfItem=2;
		int i=0;
		//
		String[][] msgStr=new String[numOfItem][2];
		msgStr[i][0]="mode";
		msgStr[i][1]="viewProject";
		i++;
		msgStr[i][0]="id";
		msgStr[i][1]=""+StaticStrings.PROJECT_NUMBER;
		
		hpt_WordStatusUpdate=new HttpPostTaskForWordStatusUpdate();
		
		hpt_WordStatusUpdate.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,msgStr);
	}
	
	private class HttpPostTaskForWordStatusUpdate extends AsyncTask<String[], String, String[]> {

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onProgressUpdate(java.lang.Object[])
		 */
		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			
			Log.i(TAG, "on Progress Update _ wordState");
			
			if(checkCompleteAll()){
				
				dialogComplete();
				cancel(true);
			}
			
			
			if(parsedData[0].equals("false")){
				Toast.makeText(
						getApplicationContext(),
						StaticStrings.TOAST_LIST_UPDATE+ "\n에러 : "
								+ parsedData[1], Toast.LENGTH_LONG).show();
			}else{
				if(keyArr[0]!=null){
					for(int i=0; i<keyArr.length; i++){
						
						if(keyArr[i]==null){
							break;
						}else{
							if(!keyArr[i].equals("/0")){
								
								if(updatedWords[i]!=true){
									ItemContent tmpItem=(ItemContent)mArrayAdapter.getItem(i);
									tmpItem.setStatus(StaticStrings.STATUS_COMPLETE);
									String tmpStr=tmpItem.getText()+"->"+keyArr[i];
									tmpItem.setText(tmpStr);
									updatedWords[i]=true;
									mArrayAdapter.notifyDataSetChanged();
								}
								
								
								
							}
						}
						
					}
				}
			}
			
			
			
			
			
		}

		ArrayList<NameValuePair> postList;
		JSONObject json;
		JSONArray jsonArr;
		JSONArray jWordArr;
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
			
			
		}

		@Override
		protected String[] doInBackground(String[]... msg) {
			Log.w("Post Task", "do in background");
			
			
			while(true){
				
				for(int i=0; i<msg.length; i++){
					postList.add(new BasicNameValuePair(msg[i][0], msg[i][1]));
//					Log.w("Post Task", "item ["+i+"] : "+msg[i][0]+ ":"+msg[i][1]);
				}
				
				 HttpClient httpclient = new DefaultHttpClient();
			        HttpResponse response;
			        try {
			        	HttpPost post=new HttpPost(StaticStrings.URL);
//						Log.w("Functions", "post success");
						
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
//				    			Log.w("Post Task", "raw msg length : "+line);

			    				result += line;
			    			}
			    			
//			    			Log.w("Post Task", "raw msg : "+result);
			    			
			    			json=new JSONObject(result);
			    			
			    			if(json!=null){
			    				

								if (json.getString("success").equals("false")) {
									parsedData[0] = json.getString("success");
//									Log.w("Post Task", "parsed data [0] : " +parsedData[0]);
									parsedData[1] = json.getString("message");
//									Log.w("Post Task", "parsed data [1] : " +parsedData[1]);
								} else {
			    				
			    				
			    				for(int i=0; i<jsonName.length; i++){
				    				parsedData[i]=json.getString(jsonName[i]);
//				    				Log.w("Post Task", "parsed data ["+(i)+"] : "+parsedData[i]);
				    			}
			    				//
			    				jsonArr=json.getJSONArray(jsonName[3]);		//key{} 안에있는 글자:value 배열
//		    					Log.w("POST TASK", "Status Key : "+jsonArr.toString());

			    				keyArr=new String[mArrayAdapter.getCount()];
			    				
			    				for(int i=0; i< keyArr.length; i++){
			    					keyArr[i]=jsonArr.get(i).toString();
//			    					Log.w("POST TASK", "Words Status Key list : "+keyArr[i]);
			    				}
			    				
								}
			    				
								if(keyArr[0]!=null){
									publishProgress(keyArr);
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
			        	
			        
			        try {
						Thread.sleep(StaticStrings.WAITTIME+2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		        
		        
		}

		@Override
		protected void onPostExecute(String[] result) {
			super.onPostExecute(result);
			// Do anything with response..
			
//			if(keyArr[0]==null){
//				Message msg=Message.obtain(mHandler, StaticStrings.HANDLER_UPDATE_WORDS_STATUS);
//				mHandler.sendMessage(msg);
//			}
//			
//			if(result[0].equals("false")){
//				Toast.makeText(
//						getApplicationContext(),
//						StaticStrings.TOAST_LIST_UPDATE+ "\n에러 : "
//								+ result[1], Toast.LENGTH_LONG).show();
//				
//			}else{
//			
//			//word 에 해당하는 key 값이  "/0" 이면 수집이 완료된 상태
//			for(int i=0; i<keyArr.length; i++){
//				if(!keyArr[i].equals("/0")){
//					ItemContent tmpItem=(ItemContent)mArrayAdapter.getItem(i);
//					tmpItem.setStatus(StaticStrings.STATUS_COMPLETE);
//					String tmpStr=tmpItem.getText()+"->"+keyArr[i];
//					tmpItem.setText(tmpStr);
//					mArrayAdapter.notifyDataSetChanged();
//				}
//			}
//			
//			
//			}
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
	

	private void dialogComplete(){
		AlertDialog.Builder alt_bld=new AlertDialog.Builder(this);
		alt_bld.setMessage(StaticStrings.DIALOG_END_CONTENT).setCancelable(false).setPositiveButton("종료", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
				StaticStrings.CURRENT_ACTIVITY=StaticStrings.FIRST_ACTIVITY;

			}
		});
		
		AlertDialog alert= alt_bld.create();
		
		alert.setTitle(StaticStrings.DIALOG_END_TITLE);
		alert.setIcon(R.drawable.alert);
		alert.show();
	}
}
