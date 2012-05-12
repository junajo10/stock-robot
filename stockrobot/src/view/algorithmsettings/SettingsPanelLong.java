package view.algorithmsettings;

import java.awt.TextField;


import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.database.jpa.tables.AlgorithmSettingLong;

import view.components.GUIFactory;

/**
 * 
 * 
 * @author kristian
 *
 */
public class SettingsPanelLong extends JPanel implements ChangeListener {
	
	/**
	 * Serial Version!
	 */
	private static final long serialVersionUID = -5023291097157808280L;
	
	private long minValue;
	private long maxValue;
	private long initValue;
	private String desc;
	
	private TextField 	ta;
	private long value;
	
	private AlgorithmSettingLong model;
	
	public SettingsPanelLong( AlgorithmSettingLong model, String desc, long initValue, long minValue, long maxValue ) {
		
		this.model = model;
		this.desc = desc;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.initValue = initValue;
		
		value = this.initValue;
		
		init(); //NOPMD
	}
	
	public void init() {
		
		//Factory baby
		GUIFactory fact = new GUIFactory();
		
		JPanel container = fact.getDefaultContainer();
		add( container );
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		//Instantiate a header text saying
		JLabel header = fact.getDefaultLabel("Something");
		header.setText( desc + "::long" );
		container.add( header );
		
		JPanel subContainer = fact.getInvisibleContainer();
		container.add( subContainer );
		
		ta = new TextField();
		ta.setBounds(0, 0, 100, 30);
		ta.setText( "" + ((int)initValue) );
		ta.setSize( 100, 30 );
		subContainer.add( ta );
		
		JSlider fromDate = new JSlider( JSlider.HORIZONTAL, Math.round( minValue ), Math.round( maxValue ), Math.round( initValue ) );
		fromDate.addChangeListener(this);
		subContainer.add( fromDate );
	}
	
	/**
	 * TODO: Maybe not have this in the view
	 */
	public void stateChanged( ChangeEvent e ) {
		
		JSlider source = (JSlider) e.getSource();
		value = source.getValue();
		ta.setText( "" + value );
		model.setValue( source.getValue() );
	}
	
	/**
	 * Whatever the jSlider shows
	 * 
	 * @return
	 */
	public long getValue() {
		
		return value;
	}
}