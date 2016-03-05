package SSV;

import java.rmi.server.SocketSecurityException;

import TestBase.TestBase;

public class Resource {
	
	public static TestBase SSE;
	
	private int brake_state;
	private int accel_state;
	private int gear_state = ConstantsList.GEAR_STATE_P;
	private int coolant_state;
	private int slope_state;
	private float rpm;
	private float velocity;
	
	private int fuelcut_state;
	Calc c = new Calc(this);
	
	public Resource(){
		SSE=new TestBase();
		SSE.setUp();
	}
	
	public void start(){
		c.start();
	}
	public float getRpm(){
		return rpm;
	}
	
	//설정하세요
	public void setRpm(float rpm){
		this.rpm = rpm;
	}
	
	public float getVelocity(){
		return velocity;
	}
	
	//설정하세요
	public void setVelocity(float vel){
		velocity = vel;
		
	}
	
	public int getFuelCutState(){
		return fuelcut_state;
	}
	
	public void setFuelCutState(int fuelcut_state){
		this.fuelcut_state=fuelcut_state;
	}
	
	public int getBrakeState(){
		return brake_state;
	}
	
	public void setBrakeState(int brake_state){
		this.brake_state=brake_state;
	}
	
	public int getAccelState(){
		return accel_state;
	}
	
	public void setAccelState(int accel_state){
		this.accel_state=accel_state;
	}
	
	public int getGearState(){
		return gear_state;
	}
	
	public void setGearState(int gear_state){
		this.gear_state=gear_state;
	}
	
	public int getCoolantState(){
		return coolant_state;
	}
	
	public void setCoolantState(int coolant_state){
		this.coolant_state=coolant_state;
	}
	
	public int getSlopeState(){
		return slope_state;
	}
	
	public void setSlopeState(int slope_state){
		this.slope_state=slope_state;
	}
}

class Calc extends Thread{
	Resource resource;
	FuelCutAlgo algo;
	float velocity=0;
	float rpm=0;
	
	
	public Calc(Resource res){
		resource=res;
		algo = new FuelCutAlgo(resource);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			
			//algo.checkFuelCut();
			
			

			rpm = (float) (rpm + (resource.getAccelState()*25- resource.getBrakeState()*20) - rpm/40);
			if(rpm < 0)
				rpm = 0;
			if(rpm > 8000)
				rpm = 8000;
			resource.setRpm(rpm);	
			
			System.out.println(velocity);
			System.out.println( );
			velocity = velocity + (float)(resource.getAccelState()/2+rpm/2000- resource.getBrakeState()*3/2 + Math.sin(resource.getSlopeState()*Math.PI/180)*2 - velocity/30);
			System.out.println(velocity);
			if(velocity < 0)
				velocity = 0;
			if(velocity > 220)
				velocity = 220;
			resource.setVelocity(velocity);	

			
			resource.SSE.sendData(resource.getAccelState(), resource.getBrakeState(), resource.getGearState(), 
					resource.getCoolantState(), resource.getSlopeState(), rpm, velocity);
			
			//System.out.println("RPM: " + rpm);
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			int melong=resource.SSE.getFuelCutAction();
			
			resource.setFuelCutState(melong);
			//System.out.println(""+melong);
		}
	}

};

