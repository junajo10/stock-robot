package utils;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * A simple window close adapter. 
 * @author Daniel
 */
public abstract class AbstractWindowCloseAdapter implements WindowListener{

	@Override
	public void windowOpened(WindowEvent e) {}		//NOPMD

	@Override
	public void windowClosed(WindowEvent e) {}		//NOPMD

	@Override
	public void windowIconified(WindowEvent e) {}	//NOPMD

	@Override
	public void windowDeiconified(WindowEvent e) {}	//NOPMD

	@Override
	public void windowActivated(WindowEvent e) {}	//NOPMD

	@Override
	public void windowDeactivated(WindowEvent e) {}	//NOPMD
}