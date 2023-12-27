package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy{

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> list = new ArrayList<>();
		Collections.copy(list, q);
		
		return list;
	}

}
