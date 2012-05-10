package model.portfolio;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.hsqldb.types.TimeData;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.date.MonthConstants;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import utils.global.FinancialLongConverter;
import utils.global.Pair;
import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelper;
import model.database.jpa.tables.PortfolioEntity;
import model.database.jpa.tables.PortfolioHistory;
import model.database.jpa.tables.PortfolioInvestment;
import model.database.jpa.tables.StockPrices;

public class PortfolioHistoryModel {
	IJPAHelper jpaHelper = JPAHelper.getInstance();

	PortfolioEntity selectedPortfolio = jpaHelper.getAllPortfolios().get(0);

	List<PortfolioHistory> history;
	public PortfolioEntity getSelectedPortfolio() {
		return selectedPortfolio;
	}

	public Pair<Object[][], Object[]> getTableData() {
		
		
		
		
		history = new ArrayList<PortfolioHistory>();
		history.addAll(selectedPortfolio.getHistory());
		
		sortHistory(history);
		for (PortfolioHistory ph : history)
			System.out.println(ph.getBuyDate() + " " + ph.getSoldDate());
		
		String[] tableColumnNames = {"Name","Market","Amount","Bought for","Sold for", "Profit %","BuyDate","SellDate"};
		Object[][] rows = new Object[history.size()][tableColumnNames.length];

		DecimalFormat df = new DecimalFormat("#.###");


		DateTimeFormatter fmt = DateTimeFormat.forPattern("yy/MM/ee hh:mm:ss");



		for (int i = 0; i < history.size(); i++) {
			StockPrices sp = history.get(i).getStockPrice();
			rows[i][0] = sp.getStockName().getName();
			rows[i][1] = sp.getStockName().getMarket();

			rows[i][2] = history.get(i).getAmount();

			rows[i][3] = FinancialLongConverter.toStringTwoDecimalPoints(history.get(i).getAmount()*sp.getBuy());


			rows[i][6] = new DateTime(sp.getTime()).toString(fmt);
			if (history.get(i).getSoldDate() != null) {
				StockPrices soldStockPrice = history.get(i).getSoldStockPrice();

				rows[i][5] = df.format((double)(history.get(i).getAmount()*soldStockPrice.getSell())/(history.get(i).getAmount()*sp.getBuy()));
				rows[i][7] = new DateTime(history.get(i).getSoldDate()).toString(fmt);
				rows[i][4] = FinancialLongConverter.toStringTwoDecimalPoints(history.get(i).getAmount()*soldStockPrice.getSell());
			}
		}

		return new Pair<Object[][], Object[]>(rows, tableColumnNames);
	}

	public TableModel getTable() {
		return new DefaultTableModel(getTableData().getLeft(), getTableData().getRight());
	}








	public TimeSeries getTimeSeries() {

		List<PortfolioHistory> history = new ArrayList<PortfolioHistory>();
		history.addAll(selectedPortfolio.getHistory());
		TimeSeries t1 = new TimeSeries("EUR/GBP");
		
		sortHistory(history);
		List<Pair<Date, Long>> valueOnGivenDate = new ArrayList<Pair<Date,Long>>();
		
		
		if (history.size() > 0) {
			SortedMap<Date, Long> sortedMap = new TreeMap<Date, Long>();

			// Add all purchases
			for (PortfolioHistory ph : history) {
				if (sortedMap.containsKey(ph.getBuyDate())) {
					Long current = sortedMap.get(ph.getBuyDate());
					sortedMap.put(ph.getBuyDate(), current -= ph.getAmount()*ph.getStockPrice().getBuy());
				}
				else {
					sortedMap.put(ph.getBuyDate(), -ph.getAmount()*ph.getStockPrice().getBuy());
				}
			}
			// Add all sells
			for (PortfolioHistory ph : history) {
				if (ph.getSoldDate() != null) {
					SortedMap<Date, Long> apa = sortedMap.tailMap(ph.getSoldDate());
					Collection<Long> bepa = apa.values();
					
					for (Long l : bepa) {
						l += ph.getSoldStockPrice().getSell()*ph.getAmount();
					}
				}
			}
			for (PortfolioInvestment pi : selectedPortfolio.getInvestments()) {
				SortedMap<Date, Long> apa = sortedMap.tailMap(pi.getDate());
				Collection<Long> bepa = apa.values();
				
				for (Long l : bepa) {
					if (pi.didInvest())
						l += pi.getAmount();
					else
						l -= pi.getAmount();
				}
			}
			
			DateTime dt = new DateTime(0);
			long currentValue = 0;
			for (Entry<Date, Long> apa : sortedMap.entrySet()) {
				DateTime dta = new DateTime(apa.getKey());
				if (dta.getDayOfYear() == dt.getDayOfYear()) {
					currentValue += apa.getValue();
				}
				else {
					dt = new DateTime(apa.getKey());
					t1.add(new Day(apa.getKey()), currentValue);
				}
			}
			try {
				t1.add(new Day(dt.toDate()), currentValue);
			} catch (Exception e) {
			}
			
		}
		
		return t1;
	}

	public void sortHistory(List<PortfolioHistory> history) {
		Collections.sort(history, new PortfolioHistoryComparator());
		
		
	}

	class PortfolioHistoryComparator extends PortfolioHistory implements Comparator<PortfolioHistory> {
		@Override
		public int compare(PortfolioHistory o1, PortfolioHistory o2) {
			if (o1.getBuyDate().getTime() < o2.getBuyDate().getTime() )
				return -1;
			else if (o1.getBuyDate().getTime() > o2.getBuyDate().getTime() ) {
				return 1;
			}
			else if (o1.getBuyDate().getTime() == o2.getBuyDate().getTime() ) {
				if (o1.getSoldDate() == null)
					return 1;
				if (o2.getSoldDate() == null)
					return -1;
				if (o1.getSoldDate().getTime() < o2.getSoldDate().getTime())
					return -1;
				else 
					return 1;
			}
			return 0;
		}
		
	}

}
