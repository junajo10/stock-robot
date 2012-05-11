package view.wizard;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.Map;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.wizard.WizardModel;
import utils.global.Log;
import view.IView;

import java.awt.Color;
import java.awt.Font;

/**
 * @author Mattias Markehed
 * mattias.markehed@gmail.com
 *
 * filename: WizardGui.java
 * Description:
 * WizardGui is used create wizard of different kinds.
 */
public class WizardView extends JFrame implements IView {

	private static final long serialVersionUID = 2315845664426240653L;
	
	public static final int SCREEN_PORTFOLIO_INFO 	= 0;
	
	private JLabel lblTitle;
	private JLabel lblSubtitle;
	
	private JPanel pnl_MainPane;
	private JPanel pnl_Content;
	
	protected JButton btnCancel;
	protected JButton btnBack;
	protected JButton btnNext;
	protected JButton btnFinish;
	
	public static final String WINDOW_CLOSE 				= "windowClose";
	public static final String GO_NEXT					= "goNext";	
	public static final String GO_BACK 					= "goBack";
	public static final String GO_CANCEL 					= "goCancel";
	public static final String GO_FINISH					= "goFinish";	
	
	private WizardModel model;
	
	private WindowListener windowListener;

	private Map<Integer,WizardPage> pages;
	
	/**
	 * Create the frame.
	 */
	public WizardView(WizardModel model) {
		pages = new HashMap<Integer,WizardPage>();
		this.model = model;
		setBounds(100, 100, 600, 400);
		setMinimumSize(new Dimension(600,400));
		
		this.setVisible(true);
		
		pnl_MainPane = new JPanel();
		setContentPane(pnl_MainPane);
		
		
		//============ CONTENT ============
		pnl_Content = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnl_Content.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		//================================
		
		//============ FOOTER ============
		JPanel pnl_Footer = new JPanel();
		pnl_Footer.setBackground(Color.LIGHT_GRAY);
		FlowLayout lou_Footer = new FlowLayout();
		lou_Footer.setAlignment(FlowLayout.CENTER);
		pnl_Footer.setLayout(lou_Footer);
		
		btnBack 		= new JButton("Back");
		btnNext 		= new JButton("Next");
		btnCancel 		= new JButton("Cancel");
		btnFinish		= new JButton("Finish");
		btnFinish.setEnabled(false);
		btnCancel.setEnabled(false);
		btnNext.setEnabled(false);
		btnBack.setEnabled(false);
		pnl_Footer.add(btnBack);
		pnl_Footer.add(btnNext);
		pnl_Footer.add(btnCancel);
		pnl_Footer.add(btnFinish);
		//================================
		
		//============ HEADER ============
		JPanel pnl_Header = new JPanel(); 
		pnl_Header.setBackground(Color.LIGHT_GRAY);
		FlowLayout lou_Header = new FlowLayout();
		lou_Header.setAlignment(FlowLayout.LEFT);
		pnl_Header.setLayout(lou_Header);
		
		JPanel pnlTitle = new JPanel(); 
		pnlTitle.setBackground(null);
		pnlTitle.setLayout(new BoxLayout(pnlTitle, BoxLayout.PAGE_AXIS));
		lblTitle = new JLabel("Wizard");
		lblTitle.setFont(new Font("Dialog", Font.BOLD, 16));
		pnlTitle.add(lblTitle);
		lblSubtitle = new JLabel("Subtitle");
		lblSubtitle.setVisible(false);
		pnlTitle.add(lblSubtitle);
		pnl_Header.add(pnlTitle);
		GroupLayout gl_pnl_MainPane = new GroupLayout(pnl_MainPane);
		gl_pnl_MainPane.setHorizontalGroup(
			gl_pnl_MainPane.createParallelGroup(Alignment.LEADING)
				.addComponent(pnl_Header, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
				.addComponent(pnl_Content, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
				.addComponent(pnl_Footer, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
		);
		gl_pnl_MainPane.setVerticalGroup(
			gl_pnl_MainPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnl_MainPane.createSequentialGroup()
					.addComponent(pnl_Header, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(pnl_Content, GroupLayout.PREFERRED_SIZE, 321, GroupLayout.PREFERRED_SIZE)
					.addComponent(pnl_Footer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		pnl_MainPane.setLayout(gl_pnl_MainPane);
		//================================
		
		//============ MAIN LAYOUT ============
		GroupLayout lou_MainLayout = new GroupLayout(pnl_MainPane);
		lou_MainLayout.setHorizontalGroup(
			lou_MainLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, lou_MainLayout.createSequentialGroup()
					.addGroup(lou_MainLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(pnl_Content, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
						.addGroup(lou_MainLayout.createSequentialGroup()
							.addComponent(pnl_Footer, GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE))
						.addComponent(pnl_Header, GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE))
					)
		);
		lou_MainLayout.setVerticalGroup(
			lou_MainLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(lou_MainLayout.createSequentialGroup()
					.addComponent(pnl_Header, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(pnl_Content, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
					.addComponent(pnl_Footer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		
		pnl_MainPane.setLayout(lou_MainLayout);
		//================================
		
		//setScreen(SCREEN_PORTFOLIO_INFO);
	}
	
	/**
	 * Sets the title of the this will be shown both in the window and header
	 * of the window.
	 * 
	 * @param title
	 */
	public void setTitle(String title) {		
		lblTitle.setText(title);
		this.validate();
	}
	
	/**
	 * Sets the subtitle of the this will be shown both in the window and header
	 * of the window.
	 * 
	 * @param title
	 */
	public void setSubtitle(String subtitle){
		
		
		if(!lblSubtitle.isVisible()){
			lblSubtitle.setVisible(true);
		}
		
		lblSubtitle.setText(subtitle);
	}
	
	/**
	 * Loads a screen in content panel.
	 * Only loads the screen if the screen is not currently loaded.
	 * 
	 * @param screen the screen to display
	 */
	public synchronized void loadScreen(final int screen){
		
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				WizardPage page = pages.get(screen);
				
				Log.log(Log.TAG.NORMAL, "loading screen");
				if(page != null){
					pnl_Content.removeAll();
					pnl_Content.add(page);
					pnl_Content.validate();
					pnl_Content.repaint();					
					
					Log.log(Log.TAG.NORMAL, "Loading page " + screen);
				}
            }
        });
	}
	 
	public synchronized void registerPage(int screen, WizardPage page){
		
		pages.put(screen, page);
	}
	
	//== Cancel listeners ==
	public void setCancelListener(ActionListener listener){
		setListener(btnCancel, listener);
	}
	
	public void addCancelListener(ActionListener listener){
		addListener(btnCancel, listener);
	}
	
	public void removeCancelListener(ActionListener listener){
		removeListener(btnCancel,listener);
	}
	
	public void removeCancelListeners(){
		removeListeners(btnCancel);
	}
	
	public void setEnableCancel(boolean enable){
		disableButton(btnCancel,enable);
	}
	
	//== Finish listeners ==
	public void setFinishListener(ActionListener listener){
		setListener(btnFinish, listener);
	}
	
	public void addFinishListener(ActionListener listener){
		addListener(btnFinish, listener);
	}
	
	public void removeFinishListener(ActionListener listener){
		removeListener(btnFinish,listener);
	}

	public void removeFinishListeners(){
		removeListeners(btnFinish);
	}
	
	public void setEnableFinish(boolean enable){
		disableButton(btnFinish,enable);
	}
	
	//== Next listeners ==
	public void setNextListener(ActionListener listener){
		setListener(btnNext, listener);
	}
	
	public void addNextListener(ActionListener listener){
		addListener(btnNext, listener);
	}
	
	public void removeNextListener(ActionListener listener){
		removeListener(btnNext,listener);
	}

	public void removeNextListeners(){
		removeListeners(btnNext);
	}
	
	public void setEnableNext(boolean enable){
		disableButton(btnNext,enable);
	}
	
	//== Back listeners ==
	public void setBackListener(ActionListener listener){
		setListener(btnBack, listener);
	}
	
	public void addBackListener(ActionListener listener){
		addListener(btnBack, listener);
	}

	public void removeBackListener(ActionListener listener){
		removeListener(btnBack,listener);
	}
	
	public void removeBackListeners(){
		removeListeners(btnBack);
	}
	
	public void setEnableBack(boolean enable){
		disableButton(btnBack,enable);
	}
	
	//== listeners ==
	private void setListener(JButton view, ActionListener listener){
		removeListeners(view);
		addListener(view,listener);
	}
	
	private void addListener(JButton view, ActionListener listener){
		//view.setEnabled(true);
		view.addActionListener(listener);
	}
	
	private void disableButton(JButton btn, boolean enable){
		btn.setEnabled(enable);
	}
	
	private void removeListener(JButton view, ActionListener listener){
		view.removeActionListener(listener);
		if(view.getActionListeners().length == 0)
			view.setEnabled(false);
	}
	
	public void removeListeners(JButton view){
		view.setEnabled(false);
		for(ActionListener listener : view.getActionListeners())
			view.removeActionListener(listener);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		if(evt.getPropertyName().equals(WizardModel.EVT_TITLE_CHANGE)){
			if(evt.getNewValue() instanceof String) {
				String title = (String) evt.getNewValue();
				setTitle(title);
			}
		}
		else if(evt.getPropertyName().equals(WizardModel.EVT_SUBTITLE_CHANGE)){
			if(evt.getNewValue() instanceof String) {
				String subtitle = (String) evt.getNewValue();
				setSubtitle(subtitle);
			}
		}
		else if(evt.getPropertyName() == (WizardModel.EVT_PAGE_CURRENT_CHANGE)){
			
			if(model.getActivePage() instanceof Integer){
				
				Integer page = (Integer)model.getActivePage();
				
				setEnableNext(model.getNextPage() != null);
				setEnableBack(model.getBackPage() != null);
				setEnableFinish(model.isAllowedFinish());
				
				loadScreen(page);
			}
		}
		else if(evt.getPropertyName() == (WizardModel.EVT_PAGE_NEXT_CHANGE)){
			setEnableNext(model.getNextPage() != null);
		}
		else if(evt.getPropertyName() == (WizardModel.EVT_PAGE_BACK_CHANGE)){
			
			setEnableBack(model.getNextPage() != null);
		}
		else if(evt.getPropertyName() == (WizardModel.EVT_CAN_FINISH_CHANGE)){
				
			setEnableFinish(model.isAllowedFinish());
		}
		
	}

	@Override
	public void display(Object model) {
		
		setVisible(true);
	}

	@Override
	public void cleanup() {
		
		model.removeObserver(this);
	}

	@Override
	public void addActions(Map<String, EventListener> actions) {
		
		if(actions.get(GO_BACK) instanceof ActionListener) {
			btnBack.addActionListener((ActionListener) actions.get(GO_BACK));
		}
		if(actions.get(GO_NEXT) instanceof ActionListener) {
			btnNext.addActionListener((ActionListener) actions.get(GO_NEXT));
		}
		if(actions.get(GO_CANCEL) instanceof ActionListener) {
			btnCancel.addActionListener((ActionListener) actions.get(GO_CANCEL));
		}
		if(actions.get(GO_NEXT) instanceof ActionListener) {
			btnFinish.addActionListener((ActionListener) actions.get(GO_FINISH));
		}
		if(actions.get(WINDOW_CLOSE) instanceof WindowListener) {
			windowListener = (WindowListener) actions.get(WINDOW_CLOSE);
			addWindowListener(windowListener);
		}
	}
}
