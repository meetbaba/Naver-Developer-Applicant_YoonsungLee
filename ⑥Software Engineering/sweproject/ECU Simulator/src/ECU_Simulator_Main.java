import java.awt.Container;
import java.awt.FlowLayout;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

public class ECU_Simulator_Main extends Thread{

	public static int messageFlag=0;
	public static final int message700=0;
	public static final int message701=1;
	
	public static long startTime;

	
	public static void main(String[] args) throws ClientProtocolException, IOException, JSONException {
		
		ECU_Simulator_Main tmpObject=new ECU_Simulator_Main();
		
		Resource resource = new Resource();
		//resource.start();
		InputUI inputUI=new InputUI(resource);
		OutputUI outputUI=new OutputUI(resource);
		
		CustomFrame customFrame = new CustomFrame(inputUI, outputUI);
		
		MessageManager msgMannager=new MessageManager(resource);
		
		Serial arduino=new Serial(resource, outputUI);
		arduino.initialize();
		
		while(true){
			
			String battery=""+resource.getBattery();
			String temperature=""+resource.getTemperature();
			String seating;
			
			if(resource.isSeat()){
				seating="true";
			}else{
				seating="false";
			}
			
			if(messageFlag==message700){
				msgMannager.send700(msgMannager);
			}else if(messageFlag==701){
				msgMannager.send701(msgMannager, battery, temperature, seating);
				
				long currentTime=System.currentTimeMillis();
				if((currentTime - startTime) >600000){
					messageFlag=message700;
					startTime=0;
				}
				
			}
			
			
			if(resource.isActivation_aircon()){
				resource.defineAirconLevel();
				arduino.write(""+resource.getAirconditioner());
			}else{
				arduino.write(""+resource.AIR_DEFAULT);
			}

			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}

class CustomFrame extends JFrame {
	
	InputUI input_ui = null;
	OutputUI output_ui=null;
	Resource resource = null;

	public CustomFrame(InputUI input_ui, OutputUI output_ui) {
		
		this.input_ui=input_ui;
		this.output_ui=output_ui;
		
		
		JFrame frame = new JFrame();

		Container cp = frame.getContentPane();
		frame.setTitle("ECU Simulator");
		cp.add(this.input_ui);
		cp.add(this.output_ui);
		cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));

		frame.pack();
		frame.setSize(1000, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

