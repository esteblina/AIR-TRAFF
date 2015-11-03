package net.ukr.steblina.airtraff;

import java.time.LocalTime;

import org.jgroups.JChannel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Helicopter extends Aircraft {
	public enum Type {
		Mi17, Mi171, Mi2
	}

	transient private JChannel hlChannel;
	private Type type;
	private int number;
	private double latitude;
	private double longitude;
	private double height;
	private FlightPath path;

	public Helicopter(Type type, int number, double latitude, double longitude, double height, FlightPath path) {
		this.type = type;
		this.number = number;
		this.latitude = latitude;
		this.longitude = longitude;
		this.height = height;
		this.path = path;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		if (latitude == 90)
			this.latitude = 0.0;
		else if (latitude > 90)
			this.latitude = latitude - 90;
		else
			this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		if (longitude == 180)
			this.longitude = 0.0;
		else if (latitude > 180)
			this.longitude = longitude - 180;
		else
			this.longitude = longitude;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		if (height < 0)
			this.height = 0.0;
		else
			switch (type) {
			case Mi2:
				if (height > 4000) {
					this.height = 4000;
					break;
				}
			case Mi17:
				if (height > 5000) {
					this.height = 5000;
					break;
				}
			case Mi171:
				if (height > 6000) {
					this.height = 6000;
					break;
				}
			default:
				this.height = height;
			}
	}

	public FlightPath getPath() {
		return path;
	}

	public void setPath(FlightPath path) {
		this.path = path;
	}

	public Type getType() {
		return type;
	}

	public int getNumber() {
		return number;
	}

	public void init(String cluster) throws Exception {
		hlChannel = new JChannel();
		hlChannel.connect(cluster);
		update();
	}

	public void close(){
		hlChannel.disconnect();
		hlChannel.close();
	}
	
	public void update() throws Exception {
		if (hlChannel != null) {
			Gson gson = new GsonBuilder().registerTypeAdapter(Helicopter.class, new InterfaceAdapter<Helicopter>())
					.setPrettyPrinting().create();
			String json = gson.toJson(this);
			hlChannel.send(null, json);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(LocalTime.now() + ": ");
		sb.append("Helicopter model: " + getType() + "; ");
		sb.append("board #: " + getNumber() + "; ");
		sb.append("coordinates: " + getLatitude() + "°," + getLongitude() + "°; ");
		sb.append("height: " + getHeight() + "m; ");
		sb.append("path: " + getPath() + ";\n");
		return sb.toString();
	}

}
