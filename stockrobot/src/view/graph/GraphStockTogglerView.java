package view.graph;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.IView;

public class GraphStockTogglerView extends JPanel implements IView {
	
	public static final String CHECKBOX_CHANGED = "checkBoxChanged";
	
	private static final long serialVersionUID = -1473430321302544640L;
	private JCheckBox checkBox;
	private JLabel textField;
	private final String stockName;
	
	public GraphStockTogglerView( String stockName ) {
		
		//layout = new BoxLayout( this, BoxLayout.X_AXIS );
		//setLayout( layout );
		
		this.stockName = stockName;
		
		init();
	}	
	
	public void init() {
		
		textField = new JLabel();
		textField.setText( stockName );
		checkBox = new JCheckBox();
		
		add( textField );
		add( checkBox );
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {}

	@Override
	public void display(Object model) {
		
		
	}

	@Override
	public void cleanup() {
		
		
	}

	@Override
	public void addActions(Map<String, EventListener> actions) {
		
		checkBox.addActionListener( (ActionListener) actions.get( CHECKBOX_CHANGED ) );
	}
	
	/**
	 * Returns if the checkbox is checked or not
	 * 
	 * @return
	 */
	public boolean isSelected() {
		
		return checkBox.isSelected();
	}
}