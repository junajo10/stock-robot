package algorithms.loader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import algorithms.AlgorithmPlugin;
import algorithms.IAlgorithm;


public class PluginLoader {
	public static final String pluginPath = "/home/daniel/plugin/";
	
	/*
	 * This method loads classes found in a given plugin directory using a plugin loader.
	 * The method then filters out those classes and only returns classes with
	 * annotationType.
	 *
	 * @return The returned list of classes sorted according to the given comparator.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static List<Class<?>> getPluginClasses(Class annotationType) {
		List<File> files = FileScanner.getFiles(new File(pluginPath));
		PluginClassLoader pluginLoader = new PluginClassLoader();
		List<Class<?>>  classList = new ArrayList<Class<?>>();


		for (File f : files) {
			if (f.getName().contains(".class")) {
				String ny = f.getName().replaceAll(".class", "");

				//System.out.println(f.getAbsolutePath());
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

	/*
	 * This method filters a list of classes and only adds them to the pluginList if
	 * they are a valid instance of the Pluggable<?> interface.
	 *
	 * @return List of classes being a valid instance of Pluggable<?>.
	 */
	private static List<IAlgorithm> getPluginsFromPluginClasses(List<Class<?>> classList) {
		
		List<IAlgorithm> pluginList = new ArrayList<IAlgorithm>();

		for(Class<?> cl : classList){
			Object plugin;
			try {
				plugin = cl.newInstance();
				if(plugin instanceof IAlgorithm){
					pluginList.add((IAlgorithm)plugin);
					//System.out.println("hej");
				}
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//System.out.println(pluginList.size());
		return pluginList;
	}
	public static List<IAlgorithm> loadAlgorithms() {
		List<Class<?>> pluginClasses = getPluginClasses(AlgorithmPlugin.class);
		List<IAlgorithm> plugins = getPluginsFromPluginClasses(pluginClasses);
		
		return plugins;
	}
}
