package model.simulation;

import java.util.List;

import model.database.jpa.tables.AlgorithmSettingDouble;
import model.database.jpa.tables.AlgorithmSettingLong;
import model.portfolio.AlgortihmLoader;

public class SimModel {
	private String algorithm;
	private int stocksBack;
	private AlgortihmLoader algorithmLoader = AlgortihmLoader.getInstance();
	private long initialValue = Long.valueOf("10000000000000");
	private List<AlgorithmSettingDouble> doubleSettings;
	private List<AlgorithmSettingLong> longSettings;
	
	
	public SimModel() {
		if (algorithmLoader.getAlgorithmNames().size() == 0)
			algorithmLoader.reloadAlgorithmClasses();
		
		if (algorithmLoader.getAlgorithmNames().size() > 0) 
			algorithm = algorithmLoader.getAlgorithmNames().get(0);
		stocksBack = 300;
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
	public void setInitialValue(long value) {
		this.initialValue = value;
	}
	public long getInitialValue() {
		return initialValue;
	}

	public void giveDoubleSettings(List<AlgorithmSettingDouble> asd) {
		doubleSettings = asd;
	}
	public void giveLongSettings(List<AlgorithmSettingLong> asl) {
		longSettings = asl;
	}
	public List<AlgorithmSettingLong> getLongSettings() {
		return longSettings;
	}
	public List<AlgorithmSettingDouble> getDoubleSettings() {
		return doubleSettings;
	}
}
