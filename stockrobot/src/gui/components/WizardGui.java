package gui.components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class WizardGui extends JFrame implements PropertyChangeListener {

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
	 * Loads a screen in content panel.
	 * Only loads the screen if the screen is not currently loaded.
	 * 
	 * @param screen the screen to display
	 */
	protected synchronized void loadScreen(Component screen){
		
		boolean viewFound = false;
		for(Component c : pnl_Content.getComponents()){
			
			if(c == screen){
				viewFound = true;
				break;
			}
		}
		
		if(!viewFound){
			pnl_Content.removeAll();
			pnl_Content.add(screen);
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
	
	//== Next listeners ==
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

	/*@Override
	public abstract void propertyChange(PropertyChangeEvent evt) {
		
	}*/
}
