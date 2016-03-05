import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;


public class Main {

	public static final int TIME_INTERVAL=5000;
	
	
	public static void main(String[] args) throws ClientProtocolException, IOException, JSONException, InterruptedException{
		
		
		RaspberryPi raspberry=new RaspberryPi();

		while(true){
			raspberry.send300(raspberry);
			raspberry.send500(raspberry);
			
			Thread.sleep(TIME_INTERVAL);
		}
		
	}

}
