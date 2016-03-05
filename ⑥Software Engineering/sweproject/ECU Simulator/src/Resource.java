
public class Resource {

	
	public static final int AIR_DEFAULT=0;
	public static final int AIR_COOL_L=1;
	public static final int AIR_COOL_M=2;
	public static final int AIR_COOL_H=3;
	public static final int AIR_HEAT_L=4;
	public static final int AIR_HEAT_M=5;
	public static final int AIR_HEAT_H=6;
	
	private int battery;
	private int airconditioner;
	private boolean activation_aircon;
	private boolean seat;
	private int temperature;
	private int target_temperature;

	public int getTarget_temperature() {
		return target_temperature;
	}

	public void setTarget_temperature(int target_temperature) {
		this.target_temperature = target_temperature;
	}

	public boolean isActivation_aircon() {
		return activation_aircon;
	}

	public void setActivation_aircon(boolean activation_aircon) {
		this.activation_aircon = activation_aircon;
	}
	public Resource(){
		this.battery=70;
		this.airconditioner=AIR_DEFAULT;
		this.seat=false;
		this.temperature=20;
		this.activation_aircon=false;
		this.target_temperature=20;
	}

	public int getBattery() {
		return battery;
	}

	public void setBattery(int battery) {
		this.battery = battery;
	}

	public int getAirconditioner() {
		return airconditioner;
	}

	public void setAirconditioner(int airconditioner) {
		this.airconditioner = airconditioner;
	}

	public boolean isSeat() {
		return seat;
	}

	public void setSeat(boolean seat) {
		this.seat = seat;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	
public void defineAirconLevel(){
		
		int gap=getTarget_temperature()-getTemperature();
		
		if(gap>8){
			setAirconditioner(Resource.AIR_COOL_H);
		}else if(gap >4){
			setAirconditioner(Resource.AIR_COOL_M);

		}else if(gap >0){
			setAirconditioner(Resource.AIR_COOL_L);

		}else if(gap ==0){
			setAirconditioner(Resource.AIR_DEFAULT);
		}else if(gap >-4){
			setAirconditioner(Resource.AIR_HEAT_L);
		}else if(gap > -8){
			setAirconditioner(Resource.AIR_HEAT_M);

		}else{
			setAirconditioner(Resource.AIR_HEAT_H);

		}
	}

	
}
