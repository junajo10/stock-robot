package gui.components.wizard;

import gui.components.GUIFactory;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
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
import javax.swing.SwingUtilities;

/**
 * @author Mattias Markehed
 * mattias.markehed@gmail.com
 *
 * filename: WizardGui.java
 * Description:
 * WizardGui is used create wizard of different kinds.
 */
public class WizardGui extends JFrame implements PropertyChangeListener {

	private static final long serialVersionUID = 2315845664426240653L;
	
	private String title = "Wizard";
	private String subtitle = "Subtitle";
	 
	public static final int SCREEN_PORTFOLIO_INFO 	= 0;
	
	private JLabel lbl_Title;
	private JLabel lbl_Subtitle;
	
	private JPanel pnl_MainPane;
	private JPanel pnl_Content;
	
	protected JButton btn_Cancel;
	protected JButton btn_Back;
	protected JButton btn_Next;
	protected JButton btn_Finish;
	
	private Map<String,WizardPage> pages;
	
	//TODO Move event strings to model
	public static final String EVT_SET_CANCEL_LISTENER = "evt_set_cnl";
	public static final String EVT_ADD_CANCEL_LISTENER = "evt_add_cnl";
	public static final String EVT_RMV_CANCEL_LISTENER = "evt_rmv_cnl";
	
	public static final String EVT_SET_BACK_LISTENER = "evt_set_bck";
	public static final String EVT_ADD_BACK_LISTENER = "evt_add_bck";
	public static final String EVT_RMV_BACK_LISTENER = "evt_rmv_bck";
	
	public static final String EVT_SET_NEXT_LISTENER = "evt_set_nxt";
	public static final String EVT_ADD_NEXT_LISTENER = "evt_add_nxt";
	public static final String EVT_RMV_NEXT_LISTENER = "evt_rmv_nxt";
	
	public static final String EVT_SET_FINISH_LISTENER = "evt_set_fns";
	public static final String EVT_ADD_FINISH_LISTENER = "evt_add_fns";
	public static final String EVT_RMV_FINISH_LISTENER = "evt_rmv_fns";
	
	public static final String EVT_CHANGE_PAGE = "evt_change_page";
	
	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WizardGui frame = new WizardGui();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public WizardGui() {
		
		pages = new HashMap<String, WizardPage>();
		
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
		lbl_Title = factory.getLightTitleLabel(title);
		pnl_Title.add(lbl_Title);
		lbl_Subtitle = factory.getLightSubtitleLabel(subtitle);
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
		//================================
		
		//setScreen(SCREEN_PORTFOLIO_INFO);
	}
	
	/**
	 * Sets the title of the this will be shown both in the window and header
	 * of the window.
	 * 
	 * @param title
	 */
	public void setTitle(String title){
		super.setTitle(title);
		this.title = title;
		lbl_Title.setText(title);
	}
	
	/**
	 * Return the title of the wizard
	 * 
	 * @return title of wizard
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	 * Sets the subtitle of the this will be shown both in the window and header
	 * of the window.
	 * 
	 * @param title
	 */
	public void setSubTitle(String subtitle){
		
		if(!lbl_Subtitle.isVisible()){
			lbl_Subtitle.setVisible(true);
		}
		this.subtitle = subtitle;
		lbl_Subtitle.setText(subtitle);
	}
	
	/**
	 * Return the subtitle of the wizard
	 * 
	 * @return title of wizard
	 */
	public String getSubTitle(){
		return title;
	}
	
	/**
	 * Adds a page to the wizard and indexes it by its name.
	 * If page with the same name already exists the old page is replaced.
	 * 
	 * @param pageName the name to index the page with
	 * @param page the page to add
	 * @return true if no page with same index was overwritten. Else false
	 */
	public synchronized boolean addPage(String pageName, WizardPage page){
		
		boolean pageExisted = false;
		
		if(pageName != null && page!=null){
			pageExisted = pages.get(pageName) != null;
			pages.put(pageName, page);
		}
			
		return pageExisted;
	}
	
	/**
	 * 
	 * @param pageName the name of the page to load
	 * @return true if the page existed, else false
	 */
	public synchronized boolean loadPage(String pageName){
		
		boolean pageExisted = false;
		
		WizardPage page = pages.get(pageName);
		pageExisted = page != null;
		if(pageExisted){
			System.out.println("name " + pageName);
			loadScreen(page);
		}
		
		return pageExisted;
	}
	
	/**
	 * Loads a screen in content panel.
	 * Only loads the screen if the screen is not currently loaded.
	 * 
	 * @param screen the screen to display
	 */
	protected synchronized void loadScreen(final WizardPage screen){
		
		boolean viewFound = false;
		for(Component c : pnl_Content.getComponents()){
			
			if(c == screen){
				viewFound = true;
				break;
			}
		}
	
		if(!viewFound){
			screen.load();
			pnl_Content.removeAll();
			pnl_Content.add(screen);
			pnl_Content.validate();
			pnl_Content.repaint();
			System.out.println("loaded " + ((Object)screen).hashCode());
		}
	}
	
	
	//== Cancel listeners ==
	public void addCancelListener(ActionListener listener){
		addListener(btn_Cancel, listener);
	}
	
	public void setCancelListener(ActionListener listener){
		setListener(btn_Cancel, listener);
	}
	
	public void removeCancelListener(ActionListener listener){
		removeListener(btn_Cancel, listener);
	}
	
	public void removeCancelListeners(){
		removeListeners(btn_Cancel);
	}
	
	//== Finish listeners ==
	public void addFinishListener(ActionListener listener){
		addListener(btn_Finish, listener);
	}
	
	public void setFinishListener(ActionListener listener){
		setListener(btn_Finish, listener);
	}
	
	public void removeFinishListener(ActionListener listener){
		removeListener(btn_Finish, listener);
	}
	
	public void removeFinishListeners(){
		removeListeners(btn_Finish);
	}
	
	//== Next listeners ==
	public void addNextListener(ActionListener listener){
		addListener(btn_Next, listener);
	}
	
	public void setNextListener(ActionListener listener){
		setListener(btn_Next, listener);
	}
	
	public void removeNextListener(ActionListener listener){
		removeListener(btn_Next, listener);
	}
	
	public void removeNextListeners(){
		removeListeners(btn_Next);
	}
	
	//== Back listeners ==
	public void addBackListener(ActionListener listener){
		addListener(btn_Back, listener);
	}
	
	public void setBackListener(ActionListener listener){
		setListener(btn_Back, listener);
	}
	
	public void removeBackListener(ActionListener listener){
		removeListener(btn_Back, listener);
	}
	
	public void removeBackListeners(){
		removeListeners(btn_Back);
	}
	
	public void setListener(JButton view, ActionListener listener){
		removeListeners(view);
		addListener(view,listener);
	}
	
	private void addListener(JButton view, ActionListener listener){
		view.setEnabled(true);
		view.addActionListener(listener);
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
		
		//TODO Remove duplicate code
		//Add/remove cancel listeners
		if(evt.getPropertyName() == (EVT_SET_CANCEL_LISTENER)){
			if(evt.getNewValue() instanceof ActionListener)
				setCancelListener((ActionListener)evt.getNewValue());
		}else if(evt.getPropertyName() == (EVT_ADD_CANCEL_LISTENER)){
			if(evt.getNewValue() instanceof ActionListener)
				addCancelListener((ActionListener)evt.getNewValue());
		}else if(evt.getPropertyName() == (EVT_RMV_CANCEL_LISTENER)){
			if(evt.getNewValue() == null)
				removeCancelListeners();
			else if(evt.getNewValue() instanceof ActionListener)
				removeCancelListener((ActionListener)evt.getNewValue());
		}
		
		//Add/remove back listeners
		else if(evt.getPropertyName() == (EVT_SET_BACK_LISTENER)){
			if(evt.getNewValue() instanceof ActionListener)
				setBackListener((ActionListener)evt.getNewValue());
		}else if(evt.getPropertyName() == (EVT_ADD_BACK_LISTENER)){
			if(evt.getNewValue() instanceof ActionListener)
				addBackListener((ActionListener)evt.getNewValue());
		}else if(evt.getPropertyName() == (EVT_RMV_BACK_LISTENER)){
			if(evt.getNewValue() == null)
				removeBackListeners();
			else if(evt.getNewValue() instanceof ActionListener)
				removeBackListener((ActionListener)evt.getNewValue());
		}
		
		//Add/remove next listeners
		else if(evt.getPropertyName() == (EVT_SET_NEXT_LISTENER)){
			if(evt.getNewValue() instanceof ActionListener)
				setNextListener((ActionListener)evt.getNewValue());
		}else if(evt.getPropertyName() == (EVT_ADD_NEXT_LISTENER)){
			if(evt.getNewValue() instanceof ActionListener)
				addNextListener((ActionListener)evt.getNewValue());
		}else if(evt.getPropertyName() == (EVT_RMV_NEXT_LISTENER)){
			if(evt.getNewValue() == null)
				removeNextListeners();
			else if(evt.getNewValue() instanceof ActionListener)
				removeNextListener((ActionListener)evt.getNewValue());
		}
		
		//Add/remove next listeners
		else if(evt.getPropertyName() == (EVT_SET_FINISH_LISTENER)){
			if(evt.getNewValue() instanceof ActionListener)
				setFinishListener((ActionListener)evt.getNewValue());
		}else if(evt.getPropertyName() == (EVT_ADD_FINISH_LISTENER)){
			if(evt.getNewValue() instanceof ActionListener)
				addFinishListener((ActionListener)evt.getNewValue());
		}else if(evt.getPropertyName() == (EVT_RMV_FINISH_LISTENER)){
			if(evt.getNewValue() == null)
				removeFinishListeners();
			else if(evt.getNewValue() instanceof ActionListener)
				removeFinishListener((ActionListener)evt.getNewValue());
		}
		
		else if(evt.getPropertyName() == (EVT_CHANGE_PAGE)){
			if(evt.getNewValue() instanceof String){
				loadPage((String) evt.getNewValue());
			}
		}
	}
}
