package view;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JProgressBar;

import utils.WindowCloseAdapter;
import utils.global.Pair;

public class SimResultView extends JFrame implements IView {

	private JPanel contentPane;
	JProgressBar progressBar = new JProgressBar();
	PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	WindowListener windowListener = new WindowCloseAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			pcs.firePropertyChange("Sim Result Close", false, true);
		}
	};
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimResultView frame = new SimResultView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SimResultView() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		progressBar.setStringPainted(true);
		progressBar.setValue(50);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(290, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(257, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		
	}

	@Override
	public void display(Object model) {
		setVisible(true);
		this.addWindowListener(windowListener);
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addActions(List<Pair<String, ActionListener>> actions) {
		// TODO Auto-generated method stub
		
	}

	public void setProgress(int progress) {
		progressBar.setValue(progress);
	}
	
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
    
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
    	pcs.removePropertyChangeListener(listener);
    }
}
