package view.algorithmsettings;

import java.awt.TextField;


import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.database.jpa.tables.AlgorithmSettingDouble;

import view.components.GUIFactory;

/**
 * 
 * 
 * @author kristian
 *
 */
public class SettingsPanelDouble extends JPanel implements ChangeListener {
	
	/**
	 * Serial Version!
	 */
	private static final long serialVersionUID = -5023291097157808280L;
	
	private double minValue;
	private double maxValue;
	private double initValue;
	private String desc;
	
	private TextField 	ta;
	
	private int doubleMultiplier = 100;
	
	private AlgorithmSettingDouble model;
	
	public SettingsPanelDouble( AlgorithmSettingDouble model, String desc, double initValue, double minValue, double maxValue ) {
	
		this.model = model;
		this.desc = desc;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.initValue = initValue;
	}
	
	public void init() {
		
		//Factory baby
		GUIFactory fact = new GUIFactory();
		
		JPanel container = fact.getDefaultContainer();
		add( container );
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		//Instantiate a header text saying
		JLabel header = fact.getDefaultLabel("Something");
		header.setText( desc + "::Double" );
		container.add( header );
		
		JPanel subContainer = fact.getInvisibleContainer();
		BoxLayout subContainerLayout = new BoxLayout( subContainer, BoxLayout.Y_AXIS );
		subContainer.setLayout( subContainerLayout );
		container.add( subContainer );
		
		ta = new TextField();
		ta.setBounds(0, 0, 100, 30);
		ta.setText( "" + initValue );
		ta.setSize( 100, 30 );
		subContainer.add( ta );
		
		JSlider fromDate = new JSlider( SwingConstants.HORIZONTAL, (int)Math.round( minValue * doubleMultiplier ), (int)Math.round( doubleMultiplier * maxValue ), (int)Math.round( doubleMultiplier * initValue ) );
		fromDate.addChangeListener(this);
		subContainer.add( fromDate );
	}
	
	/**
	 * TODO: Maybe not have this in the view
	 */
	@Override
	public void stateChanged( ChangeEvent e ) {
		
		JSlider source = (JSlider) e.getSource();
		ta.setText("" + (source.getValue() / doubleMultiplier) );
		model.setValue((source.getValue() / doubleMultiplier) );
	}
}