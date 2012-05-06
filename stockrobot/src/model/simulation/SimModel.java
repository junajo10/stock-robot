package model.simulation;

import java.util.List;

import org.junit.Ignore;

import model.algorithms.loader.PluginAlgortihmLoader;

public class SimModel {
	private String algorithm;
	private int stocksBack;
	private PluginAlgortihmLoader algorithmLoader = PluginAlgortihmLoader.getInstance();
	
	public SimModel() {
		if (algorithmLoader.getAlgorithmNames().size() == 0)
			algorithmLoader.reloadAlgorithmClasses();
	}
	
	public String getAlgorithm() {
		return algorithm;
	}
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
	public int getStocksBack() {
		return stocksBack;
	}
	public void setStocksBack(int stocksBack) {
		this.stocksBack = stocksBack;
	}
	public List<String> getAlgorithms() {
		return algorithmLoader.getAlgorithmNames();
	}
	
	
}
