package abebooks;
import java.util.HashMap;
import java.util.Map;

public class ParkingManager {

	private int nextEmptyPosition = 1; //total size of parking
	private Map<ParkingRights, Heap<ParkingSpot>> parkingMap = new HashMap<ParkingRights, Heap<ParkingSpot>>(); //Maps vehicle types to locations in the parking

	/**
	 * @return the size of the parking
	 */
	public int size(){
		return nextEmptyPosition-1;
	}
	
	/**
	 * Allocates new parking spots of a certain type
	 * Note: They will all be allocated in linear order after the current spots already in the parking lot
	 * For example, an allocation of 5 spots on a parking with already 20 spots will get numbered 21-26
	 * Note: Parking spots can only be of one type
	 * Note: Allocation of a type that already exists is allowed but their spots may not be consecutive to the old allocation
	 * For example, handicap spots could be #10-20 and #40-60 due to multiple allocations
	 * @param type - The type of spot
	 * @param capacity - The number of those spots to create
	 * @return whether or not the allocation was successful
	 */
	public boolean allocate(ParkingRights type, int capacity) {
		if (capacity <= 0) {
			return false;
		}
		if (type == null) {
			return false;
		}
		if (parkingMap.containsKey(type)) {
			for (int i = nextEmptyPosition; i < nextEmptyPosition + capacity; i++) {
				parkingMap.get(type).insert(new ParkingSpot(this, type, i));
			}
			return true;
		} else {
			Heap<ParkingSpot> parkingHeap = new Heap<ParkingSpot>(HeapType.MIN, capacity, false); //creates and allocates a new heap
			for (int i = nextEmptyPosition; i < nextEmptyPosition + capacity; i++) {
				parkingHeap.insert(new ParkingSpot(this, type, i));
			}
			nextEmptyPosition += capacity;
			parkingMap.put(type, parkingHeap); //adds new parking spots to map
			return true;
		}
	}

	/**
	 * Adds a vehicle to the parking lot. It will add it to the most efficient location based on its type.
	 * Note: This method should not be called outside from the Vehicle class.
	 * @param v - The vehicle to add
	 * @return the Parking Spot where the vehicle was parked. Will return null if no spots were found.
	 */
	public ParkingSpot addVehicle(Vehicle v) {
		if (v == null || v.getParkingManager() != this) {
			return null;
		}
		Heap<ParkingSpot> optimalHeap = null; //Searches for the optimal parking spot for a given vehicle
		for (ParkingRights t : v.getParkingRights()) {
			if (parkingMap.containsKey(t)) {
				if (!parkingMap.get(t).isEmpty()) { //there has to be spots left to allocate
					if (optimalHeap == null) {
						optimalHeap = parkingMap.get(t);
					} else {
						if (optimalHeap.peek().compareTo(parkingMap.get(t).peek()) == 1) { //if the new found spot has a lower position
							optimalHeap = parkingMap.get(t);
						}
					}
				}
			}
		}
		if (optimalHeap == null) { //if no spots were found
			return null;
		} else {
			return optimalHeap.delete(); //returns the spot and removes it from the heap
		}
	}

	/**
	 * Removes a vehicle from the parking lot
	 * @param v - The vehicle to remove
	 * @return whether or not the vehicle was removed successfully (will only fail if it was not there in the first place)
	 */
	public boolean removeVehicle(Vehicle v) {
		if (v != null) {
			if (v.getParkingSpot() != null) {
				parkingMap.get(v.getParkingSpot().getType()).insert(v.getParkingSpot()); //reallocates the parking spot to the heap
				return true;
			}
		}
		return false;
	}

}
