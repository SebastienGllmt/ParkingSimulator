package abebooks;
public class ParkingSpot implements Comparable<ParkingSpot> {

	private ParkingManager pm; //The parking that the spot is in
	private ParkingRights type; //The type of parking spot
	private int position; //The position of the parking spot

	public ParkingSpot(ParkingManager pm, ParkingRights type, int position) {
		this.pm = pm;
		this.type = type;
		this.position = position;
	}

	/**
	 * @return the parking that the spot is in
	 */
	public ParkingManager getPM() {
		return pm;
	}

	/**
	 * @return the type of parking spot
	 */
	public ParkingRights getType() {
		return type;
	}

	/**
	 * @return the position of the parking spot
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Compares the position of two parking spots
	 * @param pm - The parking spot to compare to
	 * @return -1, 0, 1 depending on whether the position is lower, equal or higher than this spot's position. Will return 1 if null.
	 */
	@Override
	public int compareTo(ParkingSpot pm) {
		if(pm == null){
			return 1;
		}
		if (getPosition() < pm.getPosition()) {
			return -1;
		} else if (pm.getPosition() == getPosition()) {
			return 0;
		} else {
			return 1;
		}
	}
}
