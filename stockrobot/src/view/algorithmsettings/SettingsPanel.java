package view.algorithmsettings;

import java.awt.TextField;


import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import view.components.GUIFactory;

/**
 * 
 * 
 * @author kristian
 *
 */
public class SettingsPanel extends JPanel implements ChangeListener {
	
	/**
	 * Serial Version!
	 */
	private static final long serialVersionUID = -5023291097157808280L;
	
	private double minValue;
	private double maxValue;
	private double initValue;
	private String desc;
	
	private TextField 	ta;
	
	public SettingsPanel( String desc, double initValue, double minValue, double maxValue ) {
		
		this.desc = desc;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.initValue = initValue;
		
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
		header.setText( desc );
		container.add( header );
		
		JPanel subContainer = fact.getInvisibleContainer();
		container.add( subContainer );
		
		ta = new TextField();
		ta.setBounds(0, 0, 100, 30);
		ta.setText( "" + ((int)initValue) );
		ta.setSize( 100, 30 );
		subContainer.add( ta );
		
		JSlider fromDate = new JSlider( JSlider.HORIZONTAL, (int) Math.round( minValue ), (int) Math.round( maxValue ), (int) Math.round( initValue ) );
		fromDate.addChangeListener(this);
		subContainer.add( fromDate );
	}
	
	/**
	 * TODO: Maybe not have this in the view
	 */
	public void stateChanged( ChangeEvent e ) {
		
		JSlider source = (JSlider) e.getSource();
		ta.setText("" + source.getValue());
		
	}
}