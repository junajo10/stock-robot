package model.portfolio;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

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
				StockPrices soldStockPrice = jpaHelper.getPricesForStockPeriod(sp.getStockName(), history.get(i).getSoldDate(), history.get(i).getSoldDate()).get(0);

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
			
			Map<Date, Long> lastBle = new HashMap<Date, Long>();
			
			for (PortfolioHistory ph : history) {
				
				if (lastBle.containsKey(ph.getBuyDate())) {
					Long current = lastBle.get(ph.getBuyDate());
					lastBle.put(ph.getBuyDate(), current -= ph.getAmount()*ph.getStockPrice().getBuy());
				}
				else {
					lastBle.put(ph.getBuyDate(), -ph.getAmount()*ph.getStockPrice().getBuy());
				}
				
			}
		}
		
		t1.add(new Day(2, MonthConstants.JANUARY, 2001), new Double(1.5788));
		t1.add(new Day(3, MonthConstants.JANUARY, 2001), new Double(1.5913));
		t1.add(new Day(4, MonthConstants.JANUARY, 2001), new Double(1.5807));
		t1.add(new Day(5, MonthConstants.JANUARY, 2001), new Double(1.5711));
		t1.add(new Day(8, MonthConstants.JANUARY, 2001), new Double(1.5778));
		t1.add(new Day(9, MonthConstants.JANUARY, 2001), new Double(1.5851));
		t1.add(new Day(10, MonthConstants.JANUARY, 2001), new Double(1.5846));
		t1.add(new Day(11, MonthConstants.JANUARY, 2001), new Double(1.5727));
		t1.add(new Day(12, MonthConstants.JANUARY, 2001), new Double(1.5585));
		t1.add(new Day(15, MonthConstants.JANUARY, 2001), new Double(1.5694));
		t1.add(new Day(16, MonthConstants.JANUARY, 2001), new Double(1.5629));
		t1.add(new Day(17, MonthConstants.JANUARY, 2001), new Double(1.5831));
		t1.add(new Day(18, MonthConstants.JANUARY, 2001), new Double(1.5624));
		t1.add(new Day(19, MonthConstants.JANUARY, 2001), new Double(1.5694));
		t1.add(new Day(22, MonthConstants.JANUARY, 2001), new Double(1.5615));
		t1.add(new Day(23, MonthConstants.JANUARY, 2001), new Double(1.5656));
		t1.add(new Day(24, MonthConstants.JANUARY, 2001), new Double(1.5795));
		t1.add(new Day(25, MonthConstants.JANUARY, 2001), new Double(1.5852));
		t1.add(new Day(26, MonthConstants.JANUARY, 2001), new Double(1.5797));
		t1.add(new Day(29, MonthConstants.JANUARY, 2001), new Double(1.5862));
		t1.add(new Day(30, MonthConstants.JANUARY, 2001), new Double(1.5803));

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
