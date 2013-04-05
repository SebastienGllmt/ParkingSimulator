package abebooks;
import java.util.HashSet;
import java.util.Set;

enum VehicleType implements ParkingRights{
	REGULAR, SMALL, BIKE;
	
	/**
	 * Enums representing different types of cars
	 */
}

public class Vehicle {

	private ParkingManager parkingManager; //The parking the vehicle is currently in
	private ParkingSpot parkingSpot; //The parking spot the car is currently in
	private Set<VehicleType> vehicleTypeSet; //A set containing all the different types of vehicle this specific vehicle could fall under
	private Driver driver;

	/**
	 * Creates a vehicle of specifies types
	 * @param typeSet - A set containing all the ways this car can be classified. See VehicleType
	 */
	public Vehicle(Set<VehicleType> typeSet, Driver driver){
		if(typeSet == null){
			typeSet = new HashSet<VehicleType>();
		}
		if(driver == null){
			driver = new Driver(null);
		}
		this.driver = driver;
		this.vehicleTypeSet = typeSet;
	}
	
	/**
	 * Sets a new driver for the car.
	 * @param driver - The driver of the vehicle
	 */
	public void setDriver(Driver driver){
		if(driver == null){
			driver = new Driver(null);
		}
		this.driver = driver;
	}
	/**
	 * @return a set returning the parking rights of a given vehicle
	 */
	public Set<ParkingRights> getParkingRights(){
		Set<ParkingRights> parkingRights = new HashSet<>();
		parkingRights.addAll(vehicleTypeSet);
		parkingRights.addAll(driver.getDriverTypeSet());
		return parkingRights;
	}
	/**
	 * @return the type set for the vehicle
	 */
	public Set<VehicleType> getVehicleTypeSet(){
		return vehicleTypeSet;
	}
	
	/**
	 * Gets the parking spot of the vehicle. Will return null if car is not currently parked
	 * @return the parking spot
	 */
	public ParkingSpot getParkingSpot() {
		return parkingSpot;
	}

	/**
	 * @return the current parking the vehicle is currently in
	 */
	public ParkingManager getParkingManager() {
		return parkingManager;
	}

	/**
	 * Parks the vehicle in a given parking.
	 * Note: If car was previously parked, it will leave the lot and enter/re-enter the specified parking.
	 * Note: Will return null if no parking spot is found
	 * @param parkingManager - The parking for the car to go to
	 * @return the parking spot allocated to the car
	 */
	public ParkingSpot setParking(ParkingManager parkingManager) {
		if(parkingSpot != null){
			this.parkingManager.removeVehicle(this);
			this.parkingSpot = null;
		}
		this.parkingManager = parkingManager;
		if(parkingManager != null){
			this.parkingSpot = parkingManager.addVehicle(this);
			return parkingSpot;
		}else{
			return null;
		}
	}

}
