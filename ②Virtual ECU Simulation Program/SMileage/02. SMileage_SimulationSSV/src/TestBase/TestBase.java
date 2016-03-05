package TestBase;

import com.etas.vrta.comms.VECUConnection;

public class TestBase {
	
	private String vecuName = "Smileage.vrta_ecu";
	private static String outDevice="OutFuelCutDevice.Value";

	private static String CoolantSensor="CoolantTemperature_SensorDevice.Value"; 
	private static String BrakeSenSor="BrakeState_SensorDevice.Value";
	private static String AccelereateSenSor="Accelerate_SensorDevice.Value";
	private static String GearSensor="GearState_SensorDevice.Value";
	private static String VehicleSpeedSensor="VehicleSpeed_SensorDevice.Value";
	private static String GyroSensor="Gyro_SensorDevice.Value";
	private static String RpmSensor="CrankshaftPosition_SensorDevice.Value";
	
	protected VECUConnection m_connection;
	
	public VECUConnection setUp() {
		m_connection = Config.getVECUPath(vecuName);
		
		m_connection.action(AccelereateSenSor).send(0);
		m_connection.action(BrakeSenSor).send(0);
		m_connection.action(GearSensor).send(0);
		m_connection.action(CoolantSensor).send(0);
		m_connection.action(GyroSensor).send(0);
		m_connection.action(RpmSensor).send(0);
		m_connection.action(VehicleSpeedSensor).send(0);
		
		System.out.println("Setup result Fuel Cut : "+m_connection.event(outDevice).state());
		
		return m_connection;
		
	}
	
	public void sendData(int accel_state, int brake_state, int gear_state, int coolant_state, 
			int slope_state, float rpm, float velocity){
		
		m_connection.action(AccelereateSenSor).send(accel_state);
		m_connection.action(BrakeSenSor).send(brake_state);
		m_connection.action(GearSensor).send(gear_state);
		m_connection.action(CoolantSensor).send(coolant_state);
		m_connection.action(GyroSensor).send(slope_state);
		m_connection.action(RpmSensor).send(rpm);
		m_connection.action(VehicleSpeedSensor).send(velocity);
				
	}
	
	public int getFuelCutAction(){
		
		return Integer.parseInt(m_connection.event(outDevice).state().toString());
	}
}
