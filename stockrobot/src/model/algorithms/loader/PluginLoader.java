package model.algorithms.loader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.portfolio.IAlgorithm;

import utils.SettingParser;
import utils.global.FileHelper;



// KASTA

/**
 * This is the pluginLoader that will load algorithms,
 * It is based upon the calenderapp from workshop 3.
 * 
 * @author adamw
 * @author Daniel
 */
public class PluginLoader {
	Map<String, Class<?>> algorithmMap = new HashMap<String, Class<?>>();
	
	public List<String> getAlgorithmNames() {
		List<String> algorithmNames = new ArrayList<String>();
		algorithmNames.addAll(algorithmMap.keySet());
		return algorithmNames;
	}

	/**
	 * This method loads classes found in a given plugin directory using a plugin loader.
	 * The method then filters out those classes and only returns classes with
	 * annotationType.
	 * 
	 * @param annotationType
	 * @return The returned list of classes sorted according to the given comparator.
	 */
	/*
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static List<Class<?>> getPluginClasses(Class annotationType) {
		String pluginPath = SettingParser.getAlgorithmPath();
		
		List<File> files = FileHelper.getFiles(new File(pluginPath));
		PluginClassLoader pluginLoader = new PluginClassLoader();
		List<Class<?>>  classList = new ArrayList<Class<?>>();


		for (File f : files) {
			if (f.getName().contains(".class")) {
				String ny = f.getName().replaceAll(".class", "");

				try {
					Class<?> c = pluginLoader.loadClass(ny);
					
					if(c.getAnnotation(annotationType) != null)
						classList.add(c);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return classList;
	}
*/
	/**
	 * This method filters a list of classes and only adds them to the pluginList if
	 * they are a valid instance of the Pluggable<?> interface.
	 *
	 * @return List of classes being a valid instance of Pluggable<?>.
	 */
	/*
	private static List<IAlgorithm> getPluginsFromPluginClasses(List<Class<?>> classList) {
		List<IAlgorithm> pluginList = new ArrayList<IAlgorithm>();

		for(Class<?> cl : classList){
			Object plugin;
			try {
				plugin = cl.newInstance();
				if(plugin instanceof IAlgorithm){
					pluginList.add((IAlgorithm)plugin);
				}
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return pluginList;
	}
	public static List<IAlgorithm> loadAlgorithms() {
		List<Class<?>> pluginClasses = getPluginClasses(AlgorithmPlugin.class);
		List<IAlgorithm> plugins = getPluginsFromPluginClasses(pluginClasses);
		
		return plugins;
	}
*/
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
						algorithmMap.put(ny, c);
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
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
}
