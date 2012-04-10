package algorithms.loader;

import java.util.HashMap;
import java.util.Map;

import algorithms.IAlgorithm;

/**
 * For testing algorithm plugins
 * 
 * @author Daniel
 */
public class Main {
	
	public static void main(String args[]) {
		Map<String, IAlgorithm> algorithms = new HashMap<String, IAlgorithm>();
		
		for (IAlgorithm a : PluginLoader.loadAlgorithms()) {
			if (algorithms.containsKey(a.getName()))
				System.out.println("ERROR duplicate names");
			algorithms.put(a.getName(), a);
		}
		
		for (IAlgorithm a : algorithms.values()) {
			System.out.println(a.getName());
		}
	}

}
