package abebooks;

import java.util.HashSet;
import java.util.Set;

public class DriverFactory {

	public static Driver createDriver(DriverType... types){
		if(types == null){
			return new Driver(null);
		}
		Set<DriverType> typeSet = new HashSet<DriverType>();
		for(DriverType t : types){
			if(t != null){
				typeSet.add(t);
			}
		}
		return new Driver(typeSet);
	}

}
