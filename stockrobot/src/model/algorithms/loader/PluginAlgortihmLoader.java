package model.algorithms.loader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.portfolio.IAlgorithm;

import utils.SettingParser;
import utils.global.FileHelper;
import utils.global.Log;
import utils.global.Log.TAG;

/**
 * This is the AlgortihmLoader.
 * It is based upon the calenderapp from workshop 3.
 * 
 * @author Daniel
 */
public final class PluginAlgortihmLoader {
	
	private static PluginAlgortihmLoader instance = null;
	
	private Map<String, Class<?>> algorithmMap = new HashMap<String, Class<?>>();
	
	public List<String> getAlgorithmNames() {
		List<String> algorithmNames = new ArrayList<String>();
		algorithmNames.addAll(algorithmMap.keySet());
		return algorithmNames;
	}
	
	private PluginAlgortihmLoader() {
		reloadAlgorithmClasses();
	}
	
	public void reloadAlgorithmClasses() {
		String pluginPath = SettingParser.getAlgorithmPath();
		
		List<File> files = FileHelper.getFiles(new File(pluginPath));
		PluginClassLoader pluginLoader = new PluginClassLoader();
		
		algorithmMap.clear();

		for (File f : files) {
			if (f.getName().contains(".class")) {
				String ny = f.getName().replaceAll(".class", "");

				try {
					Class<?> c = pluginLoader.loadClass(ny);
					
					if(c.getAnnotation(AlgorithmPlugin.class) != null) {
						Log.log(TAG.VERBOSE, "Found algorithm: " + ny);
						algorithmMap.put(ny, c);
					}
				} catch (ClassNotFoundException e) {
					
					e.printStackTrace();
				}
			}
		}
	}
	
	public IAlgorithm loadAlgorithm(String algorithmName) {
		if (algorithmMap.containsKey(algorithmName)) {
			Object plugin;
			try {
				plugin = algorithmMap.get(algorithmName).newInstance();
				if(plugin instanceof IAlgorithm){
					return (IAlgorithm)plugin;
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * Gets an instance of PluginAlgorithmLoader
	 * @return an instance of PluginAlgorithmLoader
	 */
	public static PluginAlgortihmLoader getInstance() {
		synchronized (PluginAlgortihmLoader.class) {
			if (instance == null)
				instance = new PluginAlgortihmLoader();
		}
		return instance;
	}
}