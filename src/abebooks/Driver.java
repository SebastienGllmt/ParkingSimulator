package abebooks;

import java.util.HashSet;
import java.util.Set;

enum DriverType implements ParkingRights {
	HANDICAP, EMPLOYEE;

	/**
	 * Enum representing different properties of a driver
	 */
}

public class Driver {

	private Set<DriverType> driverTypeSet; // A set containing all the different types of properties the driver could have

	public Driver(Set<DriverType> driverType) {
		if (driverType == null) {
			this.driverTypeSet = new HashSet<DriverType>();
		} else {
			this.driverTypeSet = driverType;
		}
	}

	/**
	 * @return a set containing all the types of the driver
	 */
	public Set<DriverType> getDriverTypeSet() {
		return driverTypeSet;
	}
}
