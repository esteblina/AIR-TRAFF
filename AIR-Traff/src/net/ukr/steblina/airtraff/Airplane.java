package net.ukr.steblina.airtraff;

import java.time.LocalTime;

import org.jgroups.JChannel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Airplane extends Aircraft {
	public enum Type {
		L39, A300, B777
	}

	transient private JChannel apChannel;
	private Type type;
	private int number;
	private double latitude;
	private double longitude;
	private double height;
	private FlightPath path;

	public Airplane(Type type, int number, double latitude, double longitude, double height, FlightPath path) {
		this.type = type;
		this.number = number;
		this.latitude = latitude;
		this.longitude = longitude;
		this.height = height;
		this.path = path;
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
			case L39:
				if (height > 11500) {
					this.height = 11500;
					break;
				}
			case A300:
				if (height > 12500) {
					this.height = 12500;
					break;
				}
			case B777:
				if (height > 13140) {
					this.height = 13140;
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
		apChannel = new JChannel();
		apChannel.connect(cluster);
		update();
	}
	
	public void close(){
		apChannel.disconnect();
		apChannel.close();
	}

	public void update() throws Exception {
		if (apChannel != null) {
			Gson gson = new GsonBuilder().registerTypeAdapter(Airplane.class, new InterfaceAdapter<Airplane>())
					.setPrettyPrinting().create();
			String json = gson.toJson(this);
			apChannel.send(null, json);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(LocalTime.now() + ": ");
		sb.append("Airplane model: " + getType() + "; ");
		sb.append("board #: " + getNumber() + "; ");
		sb.append("coordinates: " + getLatitude() + "°," + getLongitude() + "°; ");
		sb.append("height: " + getHeight() + "m; ");
		sb.append("path: " + getPath() + ";\n");
		return sb.toString();
	}

}
