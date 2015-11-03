package net.ukr.steblina.airtraff;

public abstract class Aircraft {

	transient private int number;
	transient private double latitude;
	transient private double longitude;
	transient private double height;
	transient private FlightPath path;

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public FlightPath getPath() {
		return path;
	}

	public void setPath(FlightPath path) {
		this.path = path;
	}

	public int getNumber() {
		return number;
	}

	abstract public void update() throws Exception;

}
