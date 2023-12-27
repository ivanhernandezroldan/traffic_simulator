package simulator.model;

public class CityRoad extends Road{

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed,
			int contLimit, int length, Weather weather){
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}
	
	@Override
	void reduceTotalContamination() {
			
		int x;
		
		switch (this.getWeather()) {
			case WINDY: x = 10; break;
			case STORM: x = 10; break;
			default: x = 2;
		}

		if(this.getTotalPollution() < x) {
			this.setTotalPollution(0);
		}
		else { 
			this.setTotalPollution(this.getTotalPollution()-x);
		}
	}

	@Override
	void updateSpeedLimit() {}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		int speed = (int)(((11.0-v.getPollutionGrade())/11.0)*this.getSpeedLimit()); 
		return speed;
	}
}
