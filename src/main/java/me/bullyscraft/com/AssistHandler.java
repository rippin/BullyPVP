package me.bullyscraft.com;

import java.util.HashMap;

public class AssistHandler {

	private HashMap<String, Double> assist;
	
	public AssistHandler(){
		assist = new HashMap<String, Double>();
	}
	
	public void add(String s, Double d){
		assist.put(s, d);
		
	}
	
	public void clear(){
		assist.clear();
	}

	public boolean containsKey(String s){
		
		return assist.containsKey(s);
	}
	
	public double getValue(String s){
		return assist.get(s);
	}
	
	public HashMap<String, Double> getMap(){
		
		return assist;
	}
}
