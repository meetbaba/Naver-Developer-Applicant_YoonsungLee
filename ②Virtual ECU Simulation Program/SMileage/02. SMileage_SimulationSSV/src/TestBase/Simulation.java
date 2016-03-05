package TestBase;

import com.etas.vrta.comms.VECUConnection;

public class Simulation {
	/*
	public static void main(String[] args) {
		TestBase base;
		VECUConnection avecu_connection;
		String sensorDev = "IoHwAb_SensorDevice.Value";	// action name
		String actuatorDev = "OutLightDevice.Value";
		
		base = new TestBase();
		avecu_connection = base.setUp();
		
		avecu_connection.action(sensorDev).send(300);	// vrt monitor에 센서값 300 입력한 것과 같은 역할을 함.
		for(int i=0;i<=900000000;i++);					// wait point
		System.out.println(avecu_connection.event(actuatorDev).state());	// Actuator Device의 Value가 바뀔 때마다 State가 찾아서 읽어옮.

		avecu_connection.action(sensorDev).send(500);
		for(int i=0;i<=900000000;i++);
		
		avecu_connection.action(sensorDev).send(700);
		for(int i=0;i<=900000000;i++);
	}
	*/
}
