package simulator.model;

public class InterCityRoad extends Road{
	
	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed,
			int contLimit, int length, Weather weather){
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}
	
	@Override
	public void reduceTotalContamination(){
		
		int x = 0;
		
		switch (this.getWeather()) {
			case WINDY: x = 15; break;
			case SUNNY: x = 2; break;
			case CLOUDY: x = 3; break;
			case RAINY: x = 10;	break;		
			default: x = 20;
		}
		
		int pollution = (int)(((100.0-x)/100.0)*this.getTotalPollution());

		this.setTotalPollution(pollution);

	}
	
	@Override
	void updateSpeedLimit() {
		if(this.getMaxPollution() < this.getTotalPollution()) {
			this.setSpeedLimit((int)(0.5*getMaxSpeed()));
		}
		else {
			this.setSpeedLimit(this.getMaxSpeed());
		}
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		int speed = this.getSpeedLimit();
		if(this.getWeather() == Weather.STORM) {
			speed = (int)(getSpeedLimit()*0.8);
		}
		return speed;
	}
}
