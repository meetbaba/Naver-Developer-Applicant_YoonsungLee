/**
 * <pre>
 * 1. 프로젝트명 : excavateTerm
 * 2. 패키지명(또는 디렉토리 경로) : com.excavateterm
 * 3. 파일명 : Functions.java
 * 4. 작성일 : 2015. 8. 10. 오후 2:28:08
 * 5. 작성자 : 윤성
 * 6. 설명 :
 * </pre>
 */
package com.excavateterm;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

/**
 * 개발자 : 윤성
 * 작성일 : 2015. 8. 10. 오후 2:28:08
 * 설명 : 
 */
public class Functions {

	
	public static String SendByHttp(String msg){
		
		if(msg==null)
			msg="";
		
		DefaultHttpClient client=new DefaultHttpClient();
		
		try{
			
			HttpPost post=new HttpPost(StaticStrings.URL);
			Log.w("Functions", "post success");
			
			/** 지연시간 최대 3초			 */
			
			HttpParams params=client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 3000);
			HttpConnectionParams.setSoTimeout(params, 3000);
			
			/* 데이터 보낸 뒤 서버에서 데이터 받아옴*/
			HttpResponse response = client.execute(post);
			BufferedReader bufReader=new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
			
			String line = null;
			String result="";
			
			while((line=bufReader.readLine())!=null){
				result += line;
			}
			
			Log.w("Functions", "raw msg : "+result);
			
			return result;
			
		}catch(Exception e){
			Log.w("Functions", "msg fail");

			e.printStackTrace();
			client.getConnectionManager().shutdown();
			
			return "";
		}
	}
	
	
}
