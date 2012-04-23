package view.wizard;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.wizard.WizardModel;
import utils.global.Log;
import view.components.GUIFactory;

/**
 * @author Mattias Markehed
 * mattias.markehed@gmail.com
 *
 * filename: WizardGui.java
 * Description:
 * WizardGui is used create wizard of different kinds.
 */
public class WizardView extends JFrame implements PropertyChangeListener {

	private static final long serialVersionUID = 2315845664426240653L;
	
	public static final int SCREEN_PORTFOLIO_INFO 	= 0;
	
	private JLabel lbl_Title;
	private JLabel lbl_Subtitle;
	
	private JPanel pnl_MainPane;
	private JPanel pnl_Content;
	
	protected JButton btn_Cancel;
	protected JButton btn_Back;
	protected JButton btn_Next;
	protected JButton btn_Finish;
	
	private WizardModel model;

	private Map<Integer,WizardPage<?>> pages;
	
	
	/**
	 * Create the frame.
	 */
	public WizardView(WizardModel model) {
		pages = new HashMap<Integer,WizardPage<?>>();
		this.model = model;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		setMinimumSize(new Dimension(600,400));
		
		this.setVisible(true);
		
		GUIFactory factory = new GUIFactory();

		pnl_MainPane = factory.getMainContainer();
		
		pnl_MainPane.setLayout(new FlowLayout());
		setContentPane(pnl_MainPane);
		
		
		//============ CONTENT ============
		pnl_Content = factory.getInvisibleContainer();
		//================================
		
		//============ FOOTER ============
		JPanel pnl_Footer = factory.getDefaultContainer(); 
		FlowLayout lou_Footer = new FlowLayout();
		lou_Footer.setAlignment(FlowLayout.CENTER);
		pnl_Footer.setLayout(lou_Footer);
		
		btn_Back 		= factory.getDefaultButton("Back");
		btn_Next 		= factory.getDefaultButton("Next");
		btn_Cancel 		= factory.getDefaultButton("Cancel");
		btn_Finish		= factory.getDefaultButton("Finish");
		btn_Finish.setEnabled(false);
		btn_Cancel.setEnabled(false);
		btn_Next.setEnabled(false);
		btn_Back.setEnabled(false);
		pnl_Footer.add(btn_Back);
		pnl_Footer.add(btn_Next);
		pnl_Footer.add(btn_Cancel);
		pnl_Footer.add(btn_Finish);
		//================================
		
		//============ HEADER ============
		JPanel pnl_Header = factory.getFstColoredContainer(); 
		FlowLayout lou_Header = new FlowLayout();
		lou_Header.setAlignment(FlowLayout.LEFT);
		pnl_Header.setLayout(lou_Header);
		
		JPanel pnl_Title = factory.getInvisibleContainer();
		pnl_Title.setLayout(new BoxLayout(pnl_Title, BoxLayout.PAGE_AXIS));
		lbl_Title = factory.getLightTitleLabel(model.getTitle());
		pnl_Title.add(lbl_Title);
		lbl_Subtitle = factory.getLightSubtitleLabel(model.getSubtitle());
		lbl_Subtitle.setVisible(false);
		pnl_Title.add(lbl_Subtitle);
		pnl_Header.add(pnl_Title);
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
		this.add(new JLabel("herp"));
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
		lbl_Title.setText(title);
	}
	
	/**
	 * Sets the subtitle of the this will be shown both in the window and header
	 * of the window.
	 * 
	 * @param title
	 */
	public void setSubtitle(String subtitle){
		
		if(!lbl_Subtitle.isVisible()){
			lbl_Subtitle.setVisible(true);
		}
		
		lbl_Subtitle.setText(subtitle);
	}
	
	/**
	 * Loads a screen in content panel.
	 * Only loads the screen if the screen is not currently loaded.
	 * 
	 * @param screen the screen to display
	 */
	public synchronized void loadScreen(int screen){
		
		WizardPage<?> page = pages.get(screen);
	
		Log.instance().log(Log.TAG.NORMAL, "loading screen");
		if(page != null){
			pnl_Content.removeAll();
			pnl_Content.add(page);
			page.add(new JLabel("herp2"));
			//this.validate();
			//this.setVisible(true);
			Log.instance().log(Log.TAG.NORMAL, "page loading");
		}
	}
	 
	public synchronized void registerPage(int screen, WizardPage<?> page){
		
		pages.put(screen, page);
	}
	
	//== Cancel listeners ==
	public void setCancelListener(ActionListener listener){
		setListener(btn_Cancel, listener);
	}
	
	public void removeCancelListeners(){
		removeListeners(btn_Cancel);
	}
	
	public void setEnableCancel(boolean enable){
		removeListeners(btn_Cancel);
	}
	
	//== Finish listeners ==
	public void setFinishListener(ActionListener listener){
		setListener(btn_Finish, listener);
	}

	public void removeFinishListeners(){
		removeListeners(btn_Finish);
	}
	
	public void setEnableFinish(boolean enable){
		removeListeners(btn_Finish);
	}
	
	//== Next listeners ==
	public void setNextListener(ActionListener listener){
		setListener(btn_Next, listener);
	}

	public void removeNextListeners(){
		removeListeners(btn_Next);
	}
	
	public void setEnableNext(boolean enable){
		removeListeners(btn_Next);
	}
	
	//== Back listeners ==
	private void setBackListener(ActionListener listener){
		setListener(btn_Back, listener);
	}

	private void removeBackListeners(){
		removeListeners(btn_Back);
	}
	
	public void setEnableBack(boolean enable){
		removeListeners(btn_Back);
	}
	
	//== listeners ==
	private void setListener(JButton view, ActionListener listener){
		removeListeners(view);
		addListener(view,listener);
	}
	
	private void addListener(JButton view, ActionListener listener){
		view.setEnabled(true);
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
	
	private void removeListeners(JButton view){
		view.setEnabled(false);
		for(ActionListener listener : view.getActionListeners())
			view.removeActionListener(listener);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		if(evt.getPropertyName() == (WizardModel.EVT_TITLE_CHANGE)){
			if(evt.getNewValue() instanceof String) {
				String title = (String) evt.getNewValue();
				setTitle(title);
			}
		}
		else if(evt.getPropertyName() == (WizardModel.EVT_SUBTITLE_CHANGE)){
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
			
		}else if(evt.getPropertyName() == (WizardModel.EVT_PAGE_NEXT_CHANGE)){
			
			setEnableNext(model.getNextPage() != null);
		}else if(evt.getPropertyName() == (WizardModel.EVT_PAGE_BACK_CHANGE)){
			
			setEnableBack(model.getNextPage() != null);
		}else if(evt.getPropertyName() == (WizardModel.EVT_CAN_FINISH_CHANGE)){
				
			setEnableFinish(model.isAllowedFinish());
		}
		
	}
}