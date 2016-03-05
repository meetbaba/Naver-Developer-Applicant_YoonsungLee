/**
 * <pre>
 * 1. 프로젝트명 : excavateTerm
 * 2. 패키지명(또는 디렉토리 경로) : com.excavateterm
 * 3. 파일명 : HttpPostTask.java
 * 4. 작성일 : 2015. 8. 10. 오후 3:21:53
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

import android.os.AsyncTask;
import android.util.Log;

/**
 * 개발자 : 윤성 작성일 : 2015. 8. 10. 오후 3:21:53 설명 :
 */
public class HttpPostTask extends AsyncTask<String, String, String> {

	ArrayList<NameValuePair> postList;
	
	
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		
		postList=new ArrayList<NameValuePair>();
	}

	@Override
	protected String doInBackground(String... msg) {
		Log.w("Post Task", "do in background");
		Log.w("Post Task", "do in background");

		postList.add(new BasicNameValuePair("mode", "InsertMember"));
		postList.add(new BasicNameValuePair("team_number", "2"));
		postList.add(new BasicNameValuePair("sector", "서울시청"));
		
		 HttpClient httpclient = new DefaultHttpClient();
	        HttpResponse response;
	        String responseString = null;
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
				
//	            response = httpclient.execute(post);
	            StatusLine statusLine = response.getStatusLine();
	            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
	            	
	            	BufferedReader bufReader=new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
	    			
	    			String line = null;
	    			String result="";
	    			
	    			while((line=bufReader.readLine())!=null){
		    			Log.w("Post Task", "raw msg length : "+line);

	    				result += line;
	    			}
	    			
	    			Log.w("Post Task", "raw msg : "+result);
	    			
//	                ByteArrayOutputStream out = new ByteArrayOutputStream();
//	                response.getEntity().writeTo(out);
//	                responseString = out.toString();
//	                out.close();
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
	        }
	        return responseString;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		// Do anything with response..
	}

}
