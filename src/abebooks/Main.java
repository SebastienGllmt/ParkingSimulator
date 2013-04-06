/**
 * @author Sebastien Guillemot -- April 2013
 */

package abebooks;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class Main {

	@Test
	public void allocationTest(){
		ParkingManager testParking = new ParkingManager();
		testParking.allocate(DriverType.HANDICAP, 5);
		testParking.allocate(VehicleType.REGULAR, 10);
		assertEquals("Parking size should be of 15", 15, testParking.size());
	}
	
	@Test
	public void addVehicleTest(){
		ParkingManager testParking = new ParkingManager();
		testParking.allocate(VehicleType.REGULAR, 10);
		testParking.allocate(DriverType.HANDICAP, 5);
		
		Vehicle car = VehicleFactory.createVehicle(null, VehicleType.REGULAR);
		assertTrue("Car should be regular vehicle", car.getParkingRights().contains(VehicleType.REGULAR));
		assertNotNull("Car should have found a spot", car.setParking(testParking));
		assertEquals("Should have been allocated the first spot", 1, car.getParkingSpot().getPosition());
		
		Driver driver = DriverFactory.createDriver(DriverType.HANDICAP);
		Vehicle handicapCar = VehicleFactory.createVehicle(driver, null);
		handicapCar.setParking(testParking);
		assertNotNull("Car should have been placed in handicap spot", handicapCar.getParkingSpot());
	}
	
	@Test
	public void optimalSpotTest(){
		ParkingManager testParking = new ParkingManager();
		testParking.allocate(VehicleType.SMALL, 5);
		testParking.allocate(DriverType.EMPLOYEE, 10);
		
		Vehicle smallCar = VehicleFactory.createVehicle(DriverFactory.createDriver(DriverType.EMPLOYEE), VehicleType.SMALL);
		smallCar.setParking(testParking);
		assertEquals("Should have parked in the optimal small car location", 1, smallCar.getParkingSpot().getPosition());
		smallCar.setParking(null); //remove from parking
		assertNull(smallCar.getParkingSpot()); //verify removing from parking works
		assertNull(smallCar.getParkingManager()); //verify removing from parking works
		for(int i=0; i<10; i++){
			Vehicle fillerCar = VehicleFactory.createVehicle(DriverFactory.createDriver(DriverType.EMPLOYEE), VehicleType.SMALL);
			fillerCar.setParking(testParking); //force fill the small car parking
		}
		smallCar.setParking(testParking);
		assertEquals("Should have been placed in employee parking as no small car parking is left", DriverType.EMPLOYEE, smallCar.getParkingSpot().getType());		
	}
	
	public static void main(String[] args){
		
	}
}
