package net.ukr.steblina.aircraft;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.ukr.steblina.airtraff.Aircraft;
import net.ukr.steblina.airtraff.Airplane;
import net.ukr.steblina.airtraff.FlightPath;
import net.ukr.steblina.airtraff.Helicopter;

public class TestAircraft {

	private int number;
	private Double latitude,longitude,height;
	private FlightPath path;
	private Aircraft aircraft,aircraft2;
	
	@Before
	public void setUp() throws Exception {
		number= 10;
		latitude=10.5;
		longitude=11.7;
		height=1000.54;
		path=FlightPath.N;
		aircraft= new Helicopter(net.ukr.steblina.airtraff.Helicopter.Type.Mi17, number, latitude, longitude, height, path);
		aircraft2= new Airplane(net.ukr.steblina.airtraff.Airplane.Type.A300, number, latitude, longitude, height, path);
	}

	@Test
	public void test() {
		assertEquals(net.ukr.steblina.airtraff.Helicopter.Type.Mi17, ((Helicopter)aircraft).getType());
		assertEquals(latitude, (Double)aircraft.getLatitude());
		assertNotEquals(net.ukr.steblina.airtraff.Helicopter.Type.Mi17, ((Airplane)aircraft2).getType());
		assertEquals(path, aircraft2.getPath());
	}

}
