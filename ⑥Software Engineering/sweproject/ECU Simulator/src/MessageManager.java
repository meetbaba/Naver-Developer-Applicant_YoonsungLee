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
import org.json.JSONException;
import org.json.JSONObject;

public class MessageManager {

	Resource resource;

	public static HttpClient httpclient_ivi;
	public static HttpResponse response_ivi;
	public static HttpPost post_ivi;

	public static HttpParams params_ivi;

	public static JSONObject jsonResult;
	public static ArrayList<NameValuePair> postList;

	public static UrlEncodedFormEntity entity;
	public static StatusLine statusLine;
	public static final String URL_IVI = "http://yul1006.ivyro.net/msg.php";
	public static final int TIMEOUT = 10000;

	public MessageManager(Resource resource) {
		this.resource = resource;
		
		httpclient_ivi = new DefaultHttpClient();
		response_ivi = null;
		jsonResult = null;
		post_ivi = new HttpPost(URL_IVI);
		postList = new ArrayList<NameValuePair>();

		params_ivi = httpclient_ivi.getParams();
		HttpConnectionParams.setConnectionTimeout(params_ivi, TIMEOUT);
		HttpConnectionParams.setSoTimeout(params_ivi, TIMEOUT);

	}

	protected void sendMsgIVI(MessageManager objectMsgManager, String[][] msgStr)
			throws ClientProtocolException, IOException, JSONException {

		for (int i = 0; i < msgStr.length; i++) {
			postList.add(new BasicNameValuePair(msgStr[i][0], msgStr[i][1]));
		}

		entity = new UrlEncodedFormEntity(postList, "UTF-8");
		objectMsgManager.post_ivi.setEntity(entity);
		response_ivi = httpclient_ivi.execute(post_ivi);
		statusLine = response_ivi.getStatusLine();

		if (statusLine.getStatusCode() == HttpStatus.SC_OK) {

			BufferedReader bufReader = null;
			bufReader = new BufferedReader(new InputStreamReader(response_ivi
					.getEntity().getContent(), "utf-8"));

			String line = null;
			String result = "";

			while ((line = bufReader.readLine()) != null) {

				result += line;
			}

			System.out.println(result);

			jsonResult = new JSONObject(result);

			Iterator<String> keys = jsonResult.keys();

			System.out.println("Response Result");
			while (keys.hasNext()) {
				String key = (String) keys.next();
				String value = jsonResult.getString(key);
				System.out.println("" + key + " : " + value);

			}

			interpretMsg(objectMsgManager, jsonResult);
		}

	}

	public void interpretMsg(MessageManager objectMsgManager, JSONObject json)
			throws JSONException, ClientProtocolException, IOException {

		String flag = json.getString("flag");

		switch (flag) {

		case "800":
			break;
			
		case "801" :
			String airconditioner=json.getString("airconditioner");
			String target_temperature=json.getString("target_temperature");
			System.out.println("air conditioner: "+airconditioner+"//target temp : "+target_temperature);
			if(airconditioner.equals("true")){
				resource.setActivation_aircon(true);
				resource.setTarget_temperature(Integer.parseInt(target_temperature));
				resource.defineAirconLevel();
			}else{
				resource.setActivation_aircon(false);
				resource.setAirconditioner(Resource.AIR_DEFAULT);
			}
			
			ECU_Simulator_Main.messageFlag=ECU_Simulator_Main.message701;
			ECU_Simulator_Main.startTime=System.currentTimeMillis();
			break;
			
		case "899" :
			
			String error=json.getString("error");
			System.out.println(error);
			break;
		}
	}
	
	public void send700(MessageManager objectMsgManager) throws ClientProtocolException, IOException, JSONException{
		
		System.out.println("send 700");
		String[][] msgStr=new String[1][2];
		msgStr[0][0]="flag";
		msgStr[0][1]="700";
		
		objectMsgManager.sendMsgIVI(objectMsgManager, msgStr);
	}


	public void send701(MessageManager objectMsgManager, String battery, String temperature, String seating) throws ClientProtocolException, IOException, JSONException{
		
		System.out.println("send 701");
		String[][] msgStr=new String[4][2];
		msgStr[0][0]="flag";
		msgStr[0][1]="701";
		
		msgStr[1][0]="battery";
		msgStr[1][1]=battery;
		
		msgStr[2][0]="temperature";
		msgStr[2][1]=temperature;
		
		msgStr[3][0]="seating";
		msgStr[3][1]=seating;
		
		objectMsgManager.sendMsgIVI(objectMsgManager, msgStr);
	}
	
	public void send799(MessageManager objectMsgManager, String error) throws ClientProtocolException, IOException, JSONException{
		
		System.out.println("send 799");
		String[][] msgStr=new String[2][2];
		msgStr[0][0]="flag";
		msgStr[0][1]="799";
		

		msgStr[0][0]="error";
		msgStr[0][1]=error;
		objectMsgManager.sendMsgIVI(objectMsgManager, msgStr);
	}
	
	private void defineAirconLevel(Resource re, int target_temperature){
		
		int gap=target_temperature-re.getTemperature();
		
		if(gap>8){
			re.setAirconditioner(Resource.AIR_COOL_H);
		}else if(gap >4){
			re.setAirconditioner(Resource.AIR_COOL_M);

		}else if(gap >0){
			re.setAirconditioner(Resource.AIR_COOL_L);

		}else if(gap ==0){
			re.setAirconditioner(Resource.AIR_DEFAULT);
		}else if(gap >-4){
			re.setAirconditioner(Resource.AIR_HEAT_L);
		}else if(gap > -8){
			re.setAirconditioner(Resource.AIR_HEAT_M);

		}else{
			re.setAirconditioner(Resource.AIR_HEAT_H);

		}
	}
}
