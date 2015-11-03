package net.ukr.steblina.airtraff;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DispatchCenter {
	JChannel dispatch;
	
	private Map<Integer,Aircraft> aircrafts;
	private Path p; 

	public DispatchCenter() {
		aircrafts=new HashMap<Integer,Aircraft>();
	}
	
	public void addAircraft(Integer key,Aircraft aircraft){
		aircrafts.put(key, aircraft);
	}
	public Map<Integer,Aircraft> getAircrafts(){
		return aircrafts;
	}
	
	public void init(String cluster) throws Exception{
		dispatch = new JChannel();
		dispatch.setReceiver(new ReceiverAdapter() {
	          public void receive(Message msg) {
	        	  Gson gson=new GsonBuilder().registerTypeAdapter(Aircraft.class, new InterfaceAdapter<Aircraft>()).create();
	        	  Aircraft ac = gson.fromJson((String)msg.getObject(), Aircraft.class);
	        	  addAircraft(ac.getNumber(), ac);
	          }
	      });
		dispatch.connect(cluster);
		p= Paths.get("./logfile_"+dispatch.getName()+".txt");
	}
	
	public void close(){
		dispatch.disconnect();
		dispatch.close();
	}
	
	public void saveLogs(){
		for(Integer bort: aircrafts.keySet()){
			System.out.print(aircrafts.get(bort));
			byte[] data = aircrafts.get(bort).toString().getBytes();
			try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(p, StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
			      out.write(data, 0, data.length);
			    } catch (IOException e) {
			      System.err.println(e);
			    }
		}
		System.out.println("---------------------------------------------------------");
	}

}
