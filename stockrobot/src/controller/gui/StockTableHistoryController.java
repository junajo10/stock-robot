package controller.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelper;
import model.database.jpa.tables.PortfolioEntity;

import view.StockTableHistoryView;


public class StockTableHistoryController {

	StockTableHistoryView sthw;
	IJPAHelper jpaHelper = JPAHelper.getInstance();
	
	
	public StockTableHistoryController(StockTableHistoryView sthw) {
		this.sthw = sthw;
		
		sthw.addChangePortfolioListener(portfolioListener());
	}

	public ActionListener refreshButton() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: Send signal to view to refresh
			}
		};
	}
	
	/**
	 * Listener for the button change algorithm in portfolio gui
	 */
	public ListDataListener portfolioListener(){
		return new ListDataListener() {
			@Override
			public void intervalRemoved(ListDataEvent e) {
			}
			
			@Override
			public void intervalAdded(ListDataEvent e) {
			}
			
			@Override
			public void contentsChanged(ListDataEvent e) {
				String selected = (String) ((DefaultComboBoxModel)e.getSource()).getSelectedItem();
				
				if (selected != null) {
					sthw.setSelectedPortfolio(1);
					List<PortfolioEntity> portfolios = jpaHelper.getAllPortfolios();
					for (int i = 0; i < portfolios.size(); i++) {
						if (portfolios.get(i).getName().contentEquals(selected)) {
							sthw.setSelectedPortfolio(i);
							break;
						}
					}
				}
			}
		};
	}
	
	
	public static void main(String args[]) {
		StockTableHistoryView sthw = new StockTableHistoryView(1);
		new StockTableHistoryController(sthw);
		
		
	}
}
