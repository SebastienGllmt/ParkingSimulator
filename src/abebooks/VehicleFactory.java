package abebooks;

import java.util.HashSet;
import java.util.Set;

public class VehicleFactory {

	public static Vehicle createVehicle(Driver driver, VehicleType... types){
		if(types == null){
			return new Vehicle(driver, null);
		}
		Set<VehicleType> typeSet = new HashSet<VehicleType>();
		for(VehicleType t : types){
			if(t != null){
				typeSet.add(t);
			}
		}
		return new Vehicle(driver, typeSet);
	}

}
