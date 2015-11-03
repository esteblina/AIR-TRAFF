package net.ukr.steblina.aircraft;

import static org.junit.Assert.*;

import org.jgroups.JChannel;
import org.junit.Before;
import org.junit.Test;

import net.ukr.steblina.airtraff.Aircraft;
import net.ukr.steblina.airtraff.DispatchCenter;
import net.ukr.steblina.airtraff.FlightPath;
import net.ukr.steblina.airtraff.Helicopter;

public class TestDispatchCenter {
	
	private int number;
	private Double latitude,longitude,height;
	private FlightPath path;
	private Aircraft aircraft;
	private DispatchCenter dp;

	@Before
	public void setUp() throws Exception {
		number= 10;
		latitude=10.5;
		longitude=11.7;
		height=1000.54;
		path=FlightPath.N;
		aircraft= new Helicopter(net.ukr.steblina.airtraff.Helicopter.Type.Mi17, number, latitude, longitude, height, path);
		dp=new DispatchCenter();

	}

	@Test
	public void testAddAircraft() {
		dp.addAircraft(aircraft.getNumber(), aircraft);
		assertEquals(aircraft, dp.getAircrafts().get(aircraft.getNumber()));
	}

	@Test
	public void testInit() {
		try {
			JChannel testCh=new JChannel();
			testCh.connect("Test");
			dp.init("Test");
			assertEquals(2, testCh.getView().size());
			dp.close();

		} catch (Exception e) {
			
		}
	}

	@Test
	public void testClose() {
		try {
			JChannel testCh=new JChannel();
			testCh.connect("Test2");
			dp.init("Test2");
			dp.close();
			assertEquals(1, testCh.getView().size());
		} catch (Exception e) {
			fail("Cant init dispatch center");
		}

	}

}
