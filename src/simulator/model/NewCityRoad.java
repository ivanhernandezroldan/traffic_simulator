package simulator.model;

public class NewCityRoad extends NewRoadEvent{
	public NewCityRoad(int time, String id, String srcJun, String
			destJunc, int length, int co2Limit, int maxSpeed, Weather weather)
			{
			super(time, id, srcJun, destJunc, length, co2Limit, maxSpeed, weather);
			}

	@Override
	void execute(RoadMap map) {
		Junction src = map.getJunction(this.srcJun);
		Junction dest = map.getJunction(this.destJunc);
		if(src != null && dest != null) {
			CityRoad croad = new CityRoad(this.id, src, dest, this.maxSpeed, this.co2Limit, this.length, this.weather);
			map.addRoad(croad);
		}
		else {
			throw new IllegalArgumentException("The junctions that connect the road do not exist");
		}
	}
	
	@Override
	public String toString() {
		return "New CityRoad '"+this.id+"'";
	}
	
}
