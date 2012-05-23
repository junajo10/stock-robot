package model.portfolio;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.database.jpa.tables.PortfolioEntity;
import model.database.jpa.tables.PortfolioHistory;
import model.database.jpa.tables.PortfolioInvestment;

import org.joda.time.DateTime;

import utils.global.FinancialLongConverter;
import utils.global.LongContainer;
import utils.global.Pair;

public class PortfolioHistoryModelStatistics {
	private String statisticsString = "Generating statistics";
	public static final String STATISTICSMODELUPDATED = "StatisticModelUpdated";
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private String mostBought = "";
	private String mostSold = "";
	private long amountBought = 0;
	private long amountSold = 0;
	private long days = 0;
	private long totalAmountOfBoughtStocks = 0;
	private long totalAmountOfSoldStocks = 0;
	private long amountRemoved = 0;
	
	public void generateStatistics(List<PortfolioHistory> history, PortfolioEntity selectedPortfolio) {
		propertyChangeSupport.firePropertyChange(STATISTICSMODELUPDATED, null, statisticsString);
		Map<String, Pair<LongContainer, LongContainer>> stocks = new HashMap<String, Pair<LongContainer,LongContainer>>();
		
		if (history.size() > 0) {
			DateTime currentTime = new DateTime(System.currentTimeMillis());
			DateTime dateStart = new DateTime(history.get(0).getBuyDate());

			days = currentTime.getDayOfYear() - dateStart.getDayOfYear();
			int years = currentTime.getYearOfEra() - dateStart.getYearOfEra();
			days += 365.25*years;
		}

		for (PortfolioHistory ph : history) {
			String stockName = ph.getStockPrice().getStockName().getName();
			Pair<LongContainer, LongContainer> current = stocks.get(stockName);
			
			totalAmountOfBoughtStocks += ph.getAmount();
			
			if (current == null) {
				current = new Pair<LongContainer, LongContainer>(new LongContainer(0), new LongContainer(0));
			}
			current.getLeft().add(ph.getAmount());
			
			if (ph.getSoldDate() != null) {
				current.getRight().add(ph.getAmount());
				totalAmountOfSoldStocks += ph.getAmount();
			}
			if (current.getLeft().getValue() > amountBought) {
				mostBought = stockName;
				amountBought = current.getLeft().getValue(); 
			}
			if (current.getRight().getValue() > amountSold) {
				mostSold = stockName;
				amountSold = current.getRight().getValue();
			}
			stocks.put(stockName, current);
		}
		String oldString = statisticsString;
		if (amountBought == 0)
			statisticsString = "Most bought stock:\nNA\n";
		else
			statisticsString = "Most bought stock:\n" + mostBought + "\n";
		
		if (amountSold == 0)
			statisticsString += "Most sold stock:\nNA\n";
		else
			statisticsString += "Most sold stock:\n" + mostSold + "\n";
		
		DecimalFormat df = new DecimalFormat("#.#");
		double boughtPerDay = (double)totalAmountOfBoughtStocks / (double)days;
		double soldPerDay = (double)totalAmountOfSoldStocks / (double)days;
		
		if (totalAmountOfBoughtStocks == 0)
			statisticsString += "Bought stocks/day:\n0\n";
		else
			statisticsString += "Bought stocks/day:\n" + df.format(boughtPerDay) + "\n";
		
		if(totalAmountOfSoldStocks == 0)
			statisticsString += "Sold stocks/day:\n0\n";
		else
			statisticsString += "Sold stocks/day:\n" + df.format(soldPerDay) + "\n";
		
		statisticsString += "Mony invested:\n" + FinancialLongConverter.toStringTwoDecimalPoints(selectedPortfolio.getTotalInvestedAmount()) + "\n";
		
		propertyChangeSupport.firePropertyChange(STATISTICSMODELUPDATED, oldString, statisticsString + "generating more..");
		oldString = statisticsString; 
		
		
		for (PortfolioInvestment pi : selectedPortfolio.getInvestments()) {
			if (!pi.didInvest())
				amountRemoved += pi.getAmount();
		}
		
		statisticsString += "Mony extracted:\n" + FinancialLongConverter.toStringTwoDecimalPoints(amountRemoved) + "\n";
		propertyChangeSupport.firePropertyChange(STATISTICSMODELUPDATED, oldString, statisticsString);
	}
	
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
