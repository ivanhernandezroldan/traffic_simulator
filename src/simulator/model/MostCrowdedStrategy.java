package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy{
	
	int timeSlot;
	
	public MostCrowdedStrategy(int timeSlot){
		this.timeSlot = timeSlot;
	}

	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		int max = 0;
		int pos = currGreen;
		if(roads.isEmpty()) {
			return -1;
		}
		else if(currGreen == -1) {
			for(int i = 0; i < qs.size(); i++) {
				if(qs.get(i).size() > max) {
					max = qs.get(i).size();
					pos = i;
				}
			}		
			return pos;
		}
		else if(currTime-lastSwitchingTime <timeSlot) {
			return currGreen;
		}
		else {
			int i = (currGreen+1)%roads.size();
			max = qs.get(currGreen).size();
			while(i != currGreen) {
				if(qs.get(i).size() > max) {
					max = qs.get(i).size();
					pos = i;
				}
				i++;
				if(i == roads.size()) {
					i = 0;
				}
			}
			return pos;
		}
	}
}
