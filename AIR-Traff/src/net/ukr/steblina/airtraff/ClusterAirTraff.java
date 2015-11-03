package net.ukr.steblina.airtraff;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ClusterAirTraff {

	public static void main(String[] args) throws Exception{
		
		DispatchCenter dc =new DispatchCenter();
		dc.init("Air");
		List<Aircraft> newAircraft=new ArrayList<Aircraft>();
		for(int i=0;i<3;i++){
			Helicopter hl =new Helicopter(Helicopter.Type.values()[i], 45*i+1, (5+i)*i, (4+i)*i, 100*i, FlightPath.values()[i]);
			Airplane ap = new Airplane(Airplane.Type.values()[i], 4*i+10, (1+i)*i, (3+i)*i, 50*i, FlightPath.values()[i+3]);
			hl.init("Air");
			ap.init("Air");
			newAircraft.add(hl);
			newAircraft.add(ap);
		}
		Timer timer = new Timer();
		TimerTask saveLog = new TimerTask() {
			@Override
			public void run() {
				dc.saveLogs();
			}
		};

		TimerTask fly = new TimerTask() {
			@Override
			public void run() {
				for(Aircraft aircraft: newAircraft){
					
					aircraft.setHeight(aircraft.getHeight()+100.5);
					aircraft.setLatitude(aircraft.getLatitude()+1);
					aircraft.setLongitude(aircraft.getLongitude()+5);
					int randomWay = new Random().nextInt(8);
					aircraft.setPath(FlightPath.values()[randomWay]);
					try {
						aircraft.update();
						
					} catch (Exception e) {
						System.err.print("update failed");
					}
					
				}
			}
		};
			

		timer.schedule(saveLog, 2*1000, 10*1000);
		timer.schedule(fly, 3*1000, 8*1000);
		
	}

}
