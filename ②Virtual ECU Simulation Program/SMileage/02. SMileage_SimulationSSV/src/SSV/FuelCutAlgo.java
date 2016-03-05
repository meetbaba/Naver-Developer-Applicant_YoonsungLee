package SSV;

import java.util.Timer;
import java.util.TimerTask;

public class FuelCutAlgo {
	static Resource resource = null;
	private int beforeAccelerateState = 0;
	private Timer mTimer = null;
	
	public FuelCutAlgo(Resource r) {
		resource = r;
		this.beforeAccelerateState = resource.getAccelState();
		this.mTimer = new Timer();
	}
	
	public void checkFuelCut() {
		System.out.println("RPM: " + resource.getRpm());
		// Check Over Heat
		if(resource.getVelocity() == 0) {
			if(resource.getCoolantState() >= 95) {
				resource.setFuelCutState(ConstantsList.FUELCUT_STATE_ON);
			} else {
				resource.setFuelCutState(ConstantsList.FUELCUT_STATE_OFF);
			}
		} else {
			if(resource.getGearState() == 3) {	// Gear N
				if(resource.getRpm() >= 4000) {
					mTimer.schedule(new MyTimerTask(resource), 0, 1000);
				} else if(resource.getRpm() >= 4700) {
					resource.setFuelCutState(ConstantsList.FUELCUT_STATE_ON);
				} else {
					mTimer.cancel();
					resource.setFuelCutState(ConstantsList.FUELCUT_STATE_OFF);
				}
			} else {	// Gear P, R, D
				if(resource.getRpm() >= 6800) {
					resource.setFuelCutState(ConstantsList.FUELCUT_STATE_ON);
				} else if(resource.getRpm() >= 2000) {
					if(resource.getSlopeState() >= 5) {
						if(resource.getAccelState() <= 2) {
							resource.setFuelCutState(ConstantsList.FUELCUT_STATE_ON);
						}
					} else {
						if(resource.getAccelState() == 0) {
							resource.setFuelCutState(ConstantsList.FUELCUT_STATE_ON);
						}
					}
					if(resource.getSlopeState() >= 5) {
						if(resource.getAccelState() > 2) {
							resource.setFuelCutState(ConstantsList.FUELCUT_STATE_OFF);
						}
					} else {
						if(resource.getAccelState() != 0) {
							resource.setFuelCutState(ConstantsList.FUELCUT_STATE_OFF);
						}
					}
				} else if(resource.getRpm() < 1500) {
					resource.setFuelCutState(ConstantsList.FUELCUT_STATE_OFF);
				}
			}
			
			if(resource.getBrakeState() != 0) {
				resource.setFuelCutState(ConstantsList.FUELCUT_STATE_OFF);
			}
			
		}
	}
}

class MyTimerTask extends TimerTask {
	private int time = 0;
	static Resource resource = null;
	
	public MyTimerTask(Resource r) {
		this.resource = r;
	}
	
	@Override
	public void run() {
		this.time++;
		if(this.time >= ConstantsList.TIME) {
			this.resource.setFuelCutState(ConstantsList.FUELCUT_STATE_ON);
			this.cancel();
		}
	}
}