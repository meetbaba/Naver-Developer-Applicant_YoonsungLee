import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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
import org.json.JSONException;
import org.json.JSONObject;


public class RaspberryPi {

	public static HttpClient httpclient_ivi;
	public static HttpResponse response_ivi;
	public static HttpClient httpclient_cServer;
	public static HttpResponse response_cServer;
	public static HttpPost post_ivi;
	public static HttpPost post_cServer;

	public static HttpParams params_ivi;
	public static HttpParams params_cServer;
	
	public static JSONObject jsonResult;
	public static ArrayList<NameValuePair> postList;
	
	public static UrlEncodedFormEntity entity;
	public static StatusLine statusLine;
	public static final String URL_IVI="http://yul1006.ivyro.net/msg.php";
	public static final String URL_cServer="http://sweyoon.ivyro.net/msg.php";
	public static final int TIMEOUT=10000;
	
	
	public static String authorized_user_id;
	public static String reserved_user_id;
	 //protected static String[][] msgStr;
	
	

	public RaspberryPi(){
		
	
		httpclient_ivi=new DefaultHttpClient();
		response_ivi=null;
		httpclient_cServer=new DefaultHttpClient();
		response_cServer=null;
		jsonResult=null;
		post_ivi=new HttpPost(URL_IVI);
		post_cServer=new HttpPost(URL_cServer);
		postList=new ArrayList<NameValuePair>();
		
		params_ivi=httpclient_ivi.getParams();
		HttpConnectionParams.setConnectionTimeout(params_ivi, TIMEOUT);
		HttpConnectionParams.setSoTimeout(params_ivi, TIMEOUT);
		
		params_cServer=httpclient_cServer.getParams();
		HttpConnectionParams.setConnectionTimeout(params_cServer, TIMEOUT);
		HttpConnectionParams.setSoTimeout(params_cServer, TIMEOUT);
		
		authorized_user_id="yctest40";
		reserved_user_id=null;
	}
	
	
	protected void sendMsgIVI(RaspberryPi objectRaspberry, String[][] msgStr) throws ClientProtocolException, IOException, JSONException{

		
		for(int i=0; i<msgStr.length ;i++){
			postList.add(new BasicNameValuePair(msgStr[i][0], msgStr[i][1]));
		}
		
		entity = new UrlEncodedFormEntity(postList, "UTF-8");
		objectRaspberry.post_ivi.setEntity(entity);
		response_ivi = httpclient_ivi.execute(post_ivi);
	    statusLine = response_ivi.getStatusLine();

	    if(statusLine.getStatusCode() == HttpStatus.SC_OK){
	    	
	    	BufferedReader bufReader = null;
			bufReader = new BufferedReader(new InputStreamReader(response_ivi.getEntity().getContent(), "utf-8"));
			
			String line = null;
			String result="";
			
				while((line=bufReader.readLine())!=null){

					result += line;
				}
			
			System.out.println(result);
			
			jsonResult=new JSONObject(result);
			
			Iterator<String> keys=jsonResult.keys();
			
			System.out.println("Response Result");
			while(keys.hasNext()){
				String key=(String)keys.next();
				String value=jsonResult.getString(key);
				System.out.println(""+key+" : "+value);

			}
			
			interpretMsg(objectRaspberry, jsonResult);
	    }
		
	}
	
	protected void sendMsgCServer(RaspberryPi objectRaspberry, String[][] msgStr) throws ClientProtocolException, IOException, JSONException{
		
		for(int i=0; i<msgStr.length ;i++){
			postList.add(new BasicNameValuePair(msgStr[i][0], msgStr[i][1]));
		}
		
		entity = new UrlEncodedFormEntity(postList, "UTF-8");
		objectRaspberry.post_cServer.setEntity(entity);
		response_cServer = httpclient_cServer.execute(post_cServer);
	    statusLine = response_cServer.getStatusLine();

	    if(statusLine.getStatusCode() == HttpStatus.SC_OK){
	    	
	    	BufferedReader bufReader = null;
			bufReader = new BufferedReader(new InputStreamReader(response_cServer.getEntity().getContent(), "utf-8"));
			
			String line = null;
			String result="";
			
				while((line=bufReader.readLine())!=null){

					result += line;
				}
			
			System.out.println(result);
			
			jsonResult=new JSONObject(result);
			
			Iterator<String> keys=jsonResult.keys();
			
			System.out.println("Response Result");
			while(keys.hasNext()){
				String key=(String)keys.next();
				String value=jsonResult.getString(key);
				System.out.println(""+key+" : "+value);

			}
			
			interpretMsg(objectRaspberry, jsonResult);
	    }
	}
	
	public void interpretMsg(RaspberryPi objectRaspberry, JSONObject json) throws JSONException, ClientProtocolException, IOException{
		
		System.out.println("autho user : "+authorized_user_id);
		System.out.println("reserve user : "+reserved_user_id);
		String flag=json.getString("flag");
		
		switch(flag){
		
		case "400" :
			break;
		
		case "401" :
			
			if(authorized_user_id==null && reserved_user_id==null){
				reserved_user_id=json.getString("user_id");
				send501(objectRaspberry, reserved_user_id);
				
			}else if(authorized_user_id==null && reserved_user_id!=null){
				
				reserved_user_id=json.getString("user_id");
				send501(objectRaspberry, reserved_user_id);
//				String error="This vehicle is in the authorization process.";
//				send399(objectRaspberry, error);				
			}else{
				String error="This vehicle already has matched with other user.";
				send399(objectRaspberry, error);
			}
			
			break;
			
		case "402" :
			String user_id_402=json.getString("user_id");
			if(authorized_user_id==null){
				String error="There is no authorized user on this vehicle.\nPlease try again after authorization with this vehicle";
				send399(objectRaspberry, error);
				
			}else if(!authorized_user_id.equals(user_id_402)){
				String error="You are not the authorized user with this car.";
				send399(objectRaspberry, error);
				
			}else{
				String departure_time=json.getString("departure_time");
				String destination=json.getString("destination");
				String target_temperature=json.getString("target_temperature");
				String keeping_time=json.getString("keeping_time");
				
				send502(objectRaspberry, departure_time, destination, target_temperature, keeping_time);
			}
			
			break;
			
		case "600" :
			break;
			
		case "601" :
			
			String authorization_result=json.getString("authorization_result");
			String vehicle_code=json.getString("vehicle_code");
			String user_id=json.getString("user_id");
			
			if(authorized_user_id != null){
				String error="There is already authorized user.";
				System.out.println(error);
				send599(objectRaspberry, error);
			}else if(reserved_user_id ==null){
				String error="The authorization process is not doing now.";
				System.out.println(error);

				send599(objectRaspberry, error);
			}else if(!reserved_user_id.equals(user_id)){
				String error="User id is not valid.\nReserved user id is \'"+reserved_user_id+"\'.";
				System.out.println(error);

				send599(objectRaspberry, error);
			}else if(authorization_result.equals("true")){
				//success case
				authorized_user_id=reserved_user_id;
				reserved_user_id=null;
				System.out.println("authorized user : "+authorized_user_id);
				
			}else if(authorization_result.equals("false")){
				//alt  case
				reserved_user_id=null;
				System.out.println("authorization denied!");

			}

			send301(objectRaspberry, authorization_result, vehicle_code, user_id);
			
			break;
		
		case "602" :
			String end_connection=json.getString("end_connection");
			
			if(end_connection.equals("true")){
				authorized_user_id=null;
				reserved_user_id=null;
			}
			
			send302(objectRaspberry, end_connection);
		
			break;
			
		case "603":
			String battery=json.getString("battery");
			String temperature=json.getString("temperature");
			
			send303(objectRaspberry, battery, temperature);
			break;
			
		}
	}
	
	public void send300(RaspberryPi objectRaspberry) throws ClientProtocolException, IOException, JSONException{
		
		System.out.println("send 300");
		String[][] msgStr=new String[1][2];
		msgStr[0][0]="flag";
		msgStr[0][1]="300";
		
		objectRaspberry.sendMsgCServer(objectRaspberry, msgStr);
	}
	
	public void send301(RaspberryPi objectRaspberry, String authorization_result, String vehicle_code, String user_id) throws ClientProtocolException, IOException, JSONException{
		System.out.println("send 301");

		String[][] msgStr=new String[4][2];
		msgStr[0][0]="flag";
		msgStr[0][1]="301";
		
		msgStr[1][0]="authorization_result";
		msgStr[1][1]=authorization_result;
		
		msgStr[2][0]="vehicle_code";
		msgStr[2][1]=vehicle_code;
		
		msgStr[3][0]="user_id";
		msgStr[3][1]=user_id;
		objectRaspberry.sendMsgCServer(objectRaspberry, msgStr);
	}
	
	public void send302(RaspberryPi objectRaspberry, String end_connection) throws ClientProtocolException, IOException, JSONException{
		System.out.println("send 302");

		String[][] msgStr=new String[3][2];
		msgStr[0][0]="flag";
		msgStr[0][1]="302";
		
		msgStr[1][0]="end_connection";
		msgStr[1][1]=end_connection;

		msgStr[2][0]="user_id";
		msgStr[2][1]=authorized_user_id;
		
		objectRaspberry.sendMsgCServer(objectRaspberry, msgStr);
	}

	public void send303(RaspberryPi objectRaspberry, String battery,
			String temperature) throws ClientProtocolException, IOException,
			JSONException {
		System.out.println("send 303");

		String[][] msgStr = new String[3][2];
		msgStr[0][0] = "flag";
		msgStr[0][1] = "303";

		msgStr[1][0] = "battery";
		msgStr[1][1] = battery;

		msgStr[2][0] = "temperature";
		msgStr[2][1] = temperature;
		objectRaspberry.sendMsgCServer(objectRaspberry, msgStr);
	}
	
	public void send399(RaspberryPi objectRaspberry, String error) throws ClientProtocolException, IOException, JSONException{
		
		System.out.println("send 399");

		String[][] msgStr=new String[2][2];
		msgStr[0][0]="flag";
		msgStr[0][1]="399";
		
		msgStr[1][0]="error";
		msgStr[1][1]=error;
		
		objectRaspberry.sendMsgCServer(objectRaspberry, msgStr);
	}
	
	public void send500(RaspberryPi objectRaspberry) throws ClientProtocolException, IOException, JSONException{
		System.out.println("send 500");

		String[][] msgStr=new String[1][2];
		msgStr[0][0]="flag";
		msgStr[0][1]="500";
		
		objectRaspberry.sendMsgIVI(objectRaspberry, msgStr);
	}
	
	public void send501(RaspberryPi objectRaspberry, String user_id) throws ClientProtocolException, IOException, JSONException{
		System.out.println("send 501");

		String[][] msgStr=new String[2][2];
		msgStr[0][0]="flag";
		msgStr[0][1]="501";
		
		msgStr[1][0]="user_id";
		msgStr[1][1]=user_id;
		objectRaspberry.sendMsgIVI(objectRaspberry, msgStr);
	}
	
	public void send502(RaspberryPi objectRaspberry, String departure_time, 
			String destination, String target_temperature, String keeping_time) throws ClientProtocolException, IOException, JSONException{
		System.out.println("send 502");

		String[][] msgStr=new String[5][2];
		msgStr[0][0]="flag";
		msgStr[0][1]="502";
		
		msgStr[1][0]="departure_time";
		msgStr[1][1]=departure_time;

		msgStr[2][0]="destination";
		msgStr[2][1]=destination;

		msgStr[3][0]="target_temperature";
		msgStr[3][1]=target_temperature;
		
		msgStr[4][0]="keeping_time";
		msgStr[4][1]=keeping_time;
		objectRaspberry.sendMsgIVI(objectRaspberry, msgStr);
	}
	
	public void send599(RaspberryPi objectRaspberry, String error) throws ClientProtocolException, IOException, JSONException{
		System.out.println("send 599");

		String[][] msgStr=new String[2][2];
		msgStr[0][0]="flag";
		msgStr[0][1]="599";
		
		msgStr[1][0]="error";
		msgStr[1][1]=error;
		
		objectRaspberry.sendMsgIVI(objectRaspberry, msgStr);
	}
}
