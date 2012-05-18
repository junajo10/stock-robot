package view;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EventListener;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


import model.database.jpa.tables.AlgorithmSettingDouble;
import model.database.jpa.tables.AlgorithmSettingLong;

import utils.global.Log;
import view.algorithmsettings.SettingsPanelDouble;
import view.algorithmsettings.SettingsPanelLong;
import java.awt.SystemColor;

/**
 * View that could be used for modifying an algortihm's settings
 * 
 * It should load all settings in a specific algorithm and then display some sort of mean to change that
 * For instance a dragger between 0 and 100
 * 
 * TODO: The SettingsPanel is not yet written in an MVC manner. Should be in MVC
 * 
 * @author kristian
 *
 */
public class AlgorithmSettingsView extends JFrame implements IView {

	private static final long serialVersionUID = 1L;
	
	public static final String SAVE_DOWN = "savedDown";
	
	private String		algorithmName;
	private JPanel 		container;
	
	private JButton		saveBtn;
	
	public AlgorithmSettingsView( String algorithmName ) {
		
		this.algorithmName = algorithmName;
		
		//Main container for the algorithm settings window
		container = new JPanel();
		container.setBackground(SystemColor.control);
		getContentPane().add( container );
		
		//Add save button
		saveBtn = new JButton();
		saveBtn.setText( "Save settings" );
		container.add( saveBtn );
	}
	
	/**
	 * Populate this view with all settings that are of type Double
	 * 
	 * @param settings Set of settings
	 */
	public void populateDoubleSettings( List<AlgorithmSettingDouble> settings ) {
		
		AlgorithmSettingDouble[] arg = settings.toArray( new AlgorithmSettingDouble[0] );
		
		Arrays.sort( arg, new CompareSettingsDouble() );
		
		for( AlgorithmSettingDouble setting : arg ) {
			
			Log.log(Log.TAG.DEBUG, "populateDoubleSettings::settings.iterator().hasNext()" );
			
			addSetting( setting, setting.getName(), setting.getValue(), setting.getMinValue(), setting.getMaxValue() );
		}
	}
	
	/**
	 * Populate this view with all settings that are of type Long
	 * 
	 * @param settings Set of settings
	 */
	public void populateLongSettings( List<AlgorithmSettingLong> settings ) {
		
		AlgorithmSettingLong[] arg = settings.toArray( new AlgorithmSettingLong[0] );
		
		Arrays.sort( arg, new CompareSettingsLong() );
		
		for( AlgorithmSettingLong setting : arg ) {
			
			Log.log(Log.TAG.DEBUG, "populateLongSettings::settings.iterator().hasNext()" );
			
			addSetting( setting, setting.getName(), setting.getValue(), setting.getMinValue(), setting.getMaxValue() );
		}
	}
	
	public void addSetting( AlgorithmSettingDouble set, String desc, double init, double min, double max ) {
		
		SettingsPanelDouble panel = new SettingsPanelDouble( set, desc, init, min, max );
		container.add(panel);
	}
	
	public void addSetting( AlgorithmSettingLong set, String desc, long init, long min, long max ) {
		
		SettingsPanelLong panel = new SettingsPanelLong( set, desc, init, min, max );
		container.add(panel);
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		
		
	}

	@Override
	public void display(Object model) {
		
		//Settings for the window
		setTitle( "Algorithm settings for " + algorithmName );
		setSize( 320,500 );
		setVisible(true);
	}

	@Override
	public void cleanup() {
		
		
	}

	@Override
	public void addActions(Map<String, EventListener> actions) {
		
		saveBtn.addActionListener( (ActionListener) actions.get( SAVE_DOWN ) );
	}
	
	static class CompareSettingsDouble implements Comparator<AlgorithmSettingDouble> {

		@Override
		public int compare(AlgorithmSettingDouble o1, AlgorithmSettingDouble o2) {
			
			return o1.getName().hashCode() > o2.getName().hashCode() ? -1 : 1;
		}
	}
	
	static class CompareSettingsLong implements Comparator<AlgorithmSettingLong> {

		@Override
		public int compare(AlgorithmSettingLong o1, AlgorithmSettingLong o2) {
			
			return o1.getName().hashCode() > o2.getName().hashCode() ? -1 : 1;
		}
	}
}