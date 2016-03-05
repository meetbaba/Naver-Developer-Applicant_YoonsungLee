/**
 * <pre>
 * 1. ������Ʈ�� : excavateTerm
 * 2. ��Ű����(�Ǵ� ���丮 ���) : com.excavateterm
 * 3. ���ϸ� : Functions.java
 * 4. �ۼ��� : 2015. 8. 10. ���� 2:28:08
 * 5. �ۼ��� : ����
 * 6. ���� :
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
 * ������ : ����
 * �ۼ��� : 2015. 8. 10. ���� 2:28:08
 * ���� : 
 */
public class Functions {

	
	public static String SendByHttp(String msg){
		
		if(msg==null)
			msg="";
		
		DefaultHttpClient client=new DefaultHttpClient();
		
		try{
			
			HttpPost post=new HttpPost(StaticStrings.URL);
			Log.w("Functions", "post success");
			
			/** �����ð� �ִ� 3��			 */
			
			HttpParams params=client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 3000);
			HttpConnectionParams.setSoTimeout(params, 3000);
			
			/* ������ ���� �� �������� ������ �޾ƿ�*/
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
