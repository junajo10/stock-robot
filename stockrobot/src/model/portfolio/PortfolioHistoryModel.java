package model.portfolio;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

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
		
	public PortfolioEntity getSelectedPortfolio() {
		return selectedPortfolio;
	}
	
	public Pair<Object[][], Object[]> getTableData() {
		List<PortfolioHistory> history = new ArrayList<PortfolioHistory>();
		history.addAll(selectedPortfolio.getHistory());
		
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
}
